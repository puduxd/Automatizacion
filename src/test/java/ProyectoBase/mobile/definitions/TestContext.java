package ProyectoBase.mobile.definitions;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import ProyectoBase.base.Configuration;
import ProyectoBase.base.Constant;
import ProyectoBase.base.MobileFactory;
import ProyectoBase.base.RallyIntegration;
import ProyectoBase.base.ScenarioContext;
import ProyectoBase.base.Utils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class TestContext {
	private RallyIntegration rallyIntegration;
	private String tcId;
	private static int lastEvidence = 0;
	private Scenario scenario;
	private ScenarioContext context;
	private Date startDate;
	private Configuration configuration;
	private static String takeEvidence;
	private static String capabilities;
	private static String rallyExcecution;
	private static String buildRefference;
	static {
		takeEvidence = System.getProperty("evidence", "onlyErrors");
		capabilities = System.getProperty("mobileCaps", "defaultAndroid");
		rallyExcecution = System.getProperty("rallyExcecution", "Regression");
		buildRefference = System.getProperty("build", "Regression Test Excecution");
	}

	public TestContext(ScenarioContext context) {
		this.context = context;
	}

	@Before
	public void prepareTest(Scenario scenario) throws Throwable {
		startDate = new Date();
		this.scenario = scenario;
		tcId = Utils.readTestCaseTags(scenario.getSourceTagNames());
		this.startRallyConnection();
		this.context.setContext("starDate", startDate);
		System.setProperty("downloadFolder",
				new File("output/" + new SimpleDateFormat("yyyyMMddHHmmss").format(startDate)).getAbsolutePath());
		this.context.setContext("global_data", Utils.getData(Constant.GLOBAL_DATA_PATH));
		this.configuration = Utils.configureDeviceCapabilities(capabilities, Constant.CONFIG_CAPABILITIES_PATH);
		this.context.setDriver(instanceDriver());
	}

	public MobileDriver<MobileElement> instanceDriver() throws Exception {
		return MobileFactory.instanceMobile(configuration);

	}

	@After
	public void tearDown() throws Exception {
		if (this.context.getDriver() != null) {
			this.context.getDriver().quit();
		}
		
		if (tcId != null) {
			lastEvidence = rallyIntegration.updateTestCase(rallyExcecution, tcId, scenario.isFailed(),
					scenario.getName(), buildRefference, startDate, takeEvidence, lastEvidence);
			rallyIntegration.closeConnection();
		}
	}

	@AfterStep
	public void afterStep() {
		if (this.scenario.isFailed()) {
			generateEvidence("[FAIL] Scenario ScreenShots");
		} else if (TestContext.takeEvidence.equalsIgnoreCase("fullEvidence")) {
			generateEvidence("[SUCCESS] Step ScreenShots");
		}
	}

	private void generateEvidence(String imageRefName) {
		byte[] screenShot = ((TakesScreenshot) this.context.getDriver()).getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenShot, "image/png", imageRefName);
	}
	
	private void startRallyConnection() throws URISyntaxException {
		if (tcId != null) {
			rallyIntegration = new RallyIntegration();
		}
	}
}
