package ProyectoBase.base;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class RallyIntegration {
	static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	HashMap<String, String> globalData;
	RallyRestApi restApi = null;
	QueryRequest userRequest;

	public RallyIntegration() throws URISyntaxException {
		globalData = Utils.getData(Constant.RALLY_DATA_PATH);
		restApi = new RallyRestApi(new URI(globalData.get("url")), globalData.get("apikey"));
		restApi.setApplicationName(globalData.get("team"));
	}

	public String getUserRef() throws IOException {
		String userRef = null;

		userRequest = new QueryRequest("User");
		userRequest.setFetch(new Fetch("UserName", "Subscription"));
		userRequest.setQueryFilter(new QueryFilter("UserName", "=", globalData.get("user")));
		QueryResponse userQueryResponse = restApi.query(userRequest);
		JsonArray queryResults = userQueryResponse.getResults();
		if (queryResults.size() > 0) {
			JsonObject queryObject = queryResults.get(0).getAsJsonObject();
			userRef = queryObject.get("_ref").getAsString();
		} else {
			System.out.println("User cannot be found: " + globalData.get("user"));
		}

		return userRef;
	}

	public String getTestCaseRef(String type, String tcId) throws IOException {
		QueryFilter methodFilter = new QueryFilter("Method", "=", "Automated");
		QueryFilter typeFilter = validateType(type);
		QueryFilter testCaseIdFilter = new QueryFilter("FormattedID", "=", tcId);
		JsonObject queryObject = null;
		String testCaseRef = null;

		userRequest = new QueryRequest("TestCases");
		userRequest.setFetch(new Fetch("Name", "FormattedID", "Priority", "Method", "Type"));

		if (typeFilter == null) {
			userRequest.setQueryFilter(methodFilter.and(testCaseIdFilter));
		} else {
			userRequest.setQueryFilter(methodFilter.and(typeFilter).and(testCaseIdFilter));
		}
		QueryResponse queryResponse = restApi.query(userRequest);
		JsonArray queryResults = queryResponse.getResults();
		if (queryResults.size() > 0) {
			System.out.println("TestCase found: " + tcId);
			queryObject = queryResults.get(0).getAsJsonObject();
			testCaseRef = queryObject.get("_ref").getAsString();
		} else {
			System.out.println("TestCase cannot be found with ID: " + tcId);
		}

		return testCaseRef;
	}

	private QueryFilter validateType(String type) {
		if (type.equalsIgnoreCase("Any")) {
			return null;
		} else if (!type.equals("Regression") && !type.equals("Acceptance") && !type.equals("Functional")
				&& !type.equals("Performance") && !type.equals("Usability") && !type.equals("User Interface")) {
			return new QueryFilter("Type", "=", "Regression");
		} else {
			return new QueryFilter("Type", "=", type);
		}
	}

	public String createTestCaseResult(String tcRef, String veredict, String notes, Date date, String userRef,
			String buildMessage, Date endDate) throws IOException {
		String responseRef = null;
		JsonObject newTestCaseResult = new JsonObject();
		newTestCaseResult.addProperty("Verdict", veredict);
		newTestCaseResult.addProperty("Date", formatDate(date));
		newTestCaseResult.addProperty("Notes", notes);
		newTestCaseResult.addProperty("Build", buildMessage);
		newTestCaseResult.addProperty("TestCase", tcRef);
		newTestCaseResult.addProperty("Tester", userRef);
		newTestCaseResult.addProperty("Duration", calculateDuration(date, endDate));

		CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
		CreateResponse createResponse = restApi.create(createRequest);

		if (createResponse.wasSuccessful()) {
			responseRef = createResponse.getObject().get("_ref").getAsString();
			System.out.println("TestCase Updated");
		} else {
			for (int i = 0; i < createResponse.getErrors().length; i++) {
				System.out.println("Error on create TestCase result: " + createResponse.getErrors()[i]);
			}
		}

		return responseRef;
	}

	public void addAttachment(String testCaseResultRef, String userRef, String imagePath, String imageName,
			String description) throws IOException {
		String imageBase64String;
		long attachmentSize;
		RandomAccessFile fileHandle = new RandomAccessFile(imagePath, "r");

		long longLength = fileHandle.length();
		long maxLength = 5000000;

		if (longLength >= maxLength) {
			throw new IOException("File size >= 5 MB Upper limit for Rally.");
		}

		int fileLength = (int) longLength;
		byte[] fileBytes = new byte[fileLength];
		fileHandle.readFully(fileBytes);
		imageBase64String = Base64.encodeBase64String(fileBytes);
		attachmentSize = fileLength;

		JsonObject attachmentContent = new JsonObject();
		attachmentContent.addProperty("Content", imageBase64String);
		CreateRequest createRequest = new CreateRequest("AttachmentContent", attachmentContent);

		CreateResponse contentResponse = restApi.create(createRequest);
		String attachmentContentRef = contentResponse.getObject().get("_ref").getAsString();

		createAttachment(testCaseResultRef, userRef, attachmentContentRef, imageName, description, attachmentSize);
	}

	private void createAttachment(String testCaseResultRef, String userRef, String attachmentContentRef,
			String imageName, String description, long attachmentSize) throws IOException {
		JsonObject attachment = new JsonObject();
		attachment.addProperty("TestCaseResult", testCaseResultRef);
		attachment.addProperty("Content", attachmentContentRef);
		attachment.addProperty("Name", imageName);
		attachment.addProperty("Description", description);
		attachment.addProperty("ContentType", "image/png");
		attachment.addProperty("Size", attachmentSize);
		attachment.addProperty("User", userRef);

		CreateRequest attachmentCreateRequest = new CreateRequest("Attachment", attachment);
		CreateResponse attachmentResponse = restApi.create(attachmentCreateRequest);

		if (attachmentResponse.wasSuccessful()) {
			System.out.println("Attachment Created: " + imageName);
		} else {
			for (int i = 0; i < attachmentResponse.getErrors().length; i++) {
				System.out.println("Error on create attachment: " + attachmentResponse.getErrors()[i]);
			}
		}
	}

	public int updateTestCase(String rallyExcecution, String tcId, boolean scenarioFailed, String scenarioName,
			String buildRefference, Date startDate) throws Exception {
		return updateTestCase(rallyExcecution, tcId, scenarioFailed, scenarioName, buildRefference, startDate, null, 0);
	}

	public int updateTestCase(String rallyExcecution, String tcId, boolean scenarioFailed, String scenarioName,
			String buildRefference, Date startDate, String takeEvidence, int lastEvidence) throws Exception {
		String veredict;
		String notes;
		String userRef = getUserRef();
		String testCaseRef = getTestCaseRef(rallyExcecution, tcId.toUpperCase());

		if (testCaseRef != null) {
			if (scenarioFailed) {
				veredict = Veredict.Fail.toString();
				notes = "The test fail in scenario: " + scenarioName;
			} else {
				veredict = Veredict.Pass.toString();
				notes = "The test was successfully excecuted";
			}

			String testCaseResultRef = createTestCaseResult(testCaseRef, veredict, notes, startDate, userRef,
					buildRefference, new Date());

			if (takeEvidence != null) {
				if (takeEvidence.equalsIgnoreCase("fullEvidence")) {
					File[] evidences = Utils.findFiles(Constant.RESULTS_PATH, Constant.SCREENSHOTS_PATH);
					int failedPosition = -1;

					if (scenarioFailed) {
						failedPosition = evidences.length - 1;
					}

					for (int i = lastEvidence; i < evidences.length; i++) {
						String description = "Evidence Step Passed";
						if (i == failedPosition) {
							description = "Evidence Step Failed";
						}

						for (File evidence : evidences) {
							String nameEvidence = Constant.EVIDENCE_NAME + String.valueOf(i + 1)
									+ Constant.EVIDENCE_EXTENSION;
							if (evidence.getName().equals(nameEvidence)) {
								addAttachment(testCaseResultRef, userRef, evidence.getAbsolutePath(),
										evidence.getName(), description);
								break;
							}
						}
					}

					lastEvidence = evidences.length;
				} else if (scenarioFailed) {
					File[] defectFileList = Utils.findFiles(Constant.RESULTS_PATH, Constant.SCREENSHOTS_PATH);
					for (File evidence : defectFileList) {
						String nameEvidence = Constant.EVIDENCE_NAME + String.valueOf(defectFileList.length)
								+ Constant.EVIDENCE_EXTENSION;
						if (evidence.getName().equals(nameEvidence)) {
							addAttachment(testCaseResultRef, userRef, evidence.getAbsolutePath(), evidence.getName(),
									"Evidence Step Failed");
							break;
						}
					}
				}
			}
		}

		return lastEvidence;
	}

	public void closeConnection() throws IOException {
		if (restApi != null) {
			restApi.close();
		}
	}

	private String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
		return dateFormat.format(date);
	}

	private Double calculateDuration(Date start, Date end) {
		long diffInMillies = end.getTime() - start.getTime();
		long diffInSecs = TIME_UNIT.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return new Double(diffInSecs);
	}
}
