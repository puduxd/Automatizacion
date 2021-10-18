package ProyectoBase.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.Gson;

import ProyectoBase.base.BrowserFactory.Browser;
import io.appium.java_client.remote.MobileCapabilityType;

public class Utils {
	public static String readTestCaseTags(Collection<String> sourceTagNames) {
		String cleanTag;
		String tcId = null;
		for (String tag : sourceTagNames) {
			cleanTag = tag.replace("@", "");
			if (cleanTag.toUpperCase().startsWith("TC")) {
				tcId = cleanTag;
				break;
			}
		}
		return tcId;
	}
	
	public static Browser readAndLoadByTags(Collection<String> sourceTagNames) {
		String cleanTag;
		Browser browserSet = Browser.chrome;
		
		for (String tag : sourceTagNames) {
			cleanTag = tag.replace("@", "");
			if (cleanTag.equalsIgnoreCase(Browser.firefox.toString())) {
				browserSet = Browser.firefox;
				break;
			} else if (cleanTag.equalsIgnoreCase(Browser.chrome.toString())) {
				browserSet = Browser.chrome;
				break;
			} else if (cleanTag.equalsIgnoreCase(Browser.iexplorer.toString())) {
				browserSet = Browser.iexplorer;
				break;
			} else if (cleanTag.equalsIgnoreCase(Browser.safari.toString())) {
				browserSet = Browser.safari;
				break;
			} else if (cleanTag.equalsIgnoreCase(Browser.edge.toString())) {
				browserSet = Browser.edge;
				break;
			} else {
				browserSet = Browser.chrome;
			}
		}
		
		return browserSet;
	}
	
	public static Configuration configureDeviceCapabilities(String configId, String configCaps) {
		Configuration deviceConfiguration = null;
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(configCaps));
			List<Configuration> listConfig = Arrays.asList(new Gson().fromJson(bufferReader, Configuration[].class));
			deviceConfiguration = listConfig.stream().filter(x -> x.getId().equals(configId)).findFirst().orElse(null);
			deviceConfiguration = setAppFullPathCapability(deviceConfiguration);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Json file not found at path : " + configCaps);
		}
		
		return deviceConfiguration;
	}
	
	private static Configuration setAppFullPathCapability(Configuration deviceConf) {
		Configuration newConf = deviceConf;
		Object appObject = deviceConf.getCapability().getCapability(MobileCapabilityType.APP);
		if (appObject != null) {
			File appPath = new File(appObject.toString());
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.APP, appPath.getAbsolutePath());
			newConf.getCapability().merge(caps);
		}
		return newConf;
	}
	
	public static File[] findFiles(String basePath, String screenshotPath) throws Exception {
		String excecutionPath = findExcecutionPath(basePath);
		File screenshotsDir = new File(excecutionPath + screenshotPath);

		if (screenshotsDir.exists() && screenshotsDir.isDirectory()) {
			return screenshotsDir.listFiles();
		} else {
			throw new Exception("Error getting the path of: " + screenshotsDir);
		}
	}

	private static String findExcecutionPath(String basePath) throws Exception {
		List<Calendar> folderList = new ArrayList<>();
		File dir = new File(basePath);
		File[] files = dir.listFiles();
		for (File file : files) {
			folderList.add(convertFolderDate(file.getName()));
		}
		
		Calendar recentFolder = folderList.get(0);
		int position = 0;
		for(int i = 1; i < folderList.size(); i++) {
			if(recentFolder.compareTo(folderList.get(i)) < 0) {
				recentFolder = folderList.get(i);
				position = i;
			}
		}

		File excecutionDir = files[position];
		System.out.println("Evidence Folder: " + excecutionDir.getName());

		if (excecutionDir.exists() && excecutionDir.isDirectory()) {
			return excecutionDir.getAbsolutePath();
		} else {
			throw new Exception("Error getting the path of: " + basePath);
		}
	}
	
	private static Calendar convertFolderDate(String folder) throws Exception {
		String[] splitName = folder.split(" ");
		String[] monthDate = splitName[1].split("-");
		String[] dayTime = splitName[2].split("-");

		int year = Integer.parseInt(monthDate[0]);
		int month = convertMonth(monthDate[1]);
		int day = Integer.parseInt(monthDate[2]);
		int hour = Integer.parseInt(dayTime[0]);
		int minutes = Integer.parseInt(dayTime[1]);
		int seconds = Integer.parseInt(dayTime[2]);

		Calendar dayCalendar = Calendar.getInstance();
		dayCalendar.set(year, month, day, hour, minutes, seconds);

		return dayCalendar;
	}

	private static int convertMonth(String month) throws Exception {
		int result;
		
		switch (month.toLowerCase()) {
		case "ene":
		case "jan":
			result = 1;
			break;
		case "feb":
			result = 2;
			break;
		case "mar":
			result = 3;
			break;
		case "abr":
		case "apr":
			result = 4;
			break;
		case "may":
			result = 5;
			break;
		case "jun":
			result = 6;
			break;
		case "jul":
			result = 7;
			break;
		case "ago":
		case "aug":
			result = 8;
			break;
		case "sep":
		case "sept":
			result = 9;
			break;
		case "oct":
			result = 10;
			break;
		case "nov":
			result = 11;
			break;
		case "dic":
		case "dec":
			result = 12;
			break;
		default:
			throw new Exception("The month was not found for convertion [ " + month + " ]");
		}
		
		return result;
	}
	
	public static HashMap<String, String> getGlobalData(String environment) {
		String dataPath = null;
		if(environment.equalsIgnoreCase(Constant.CERT_ENV)) {
			dataPath = Constant.CERT_DATA;
		} else if(environment.equalsIgnoreCase(Constant.PROD_ENV)) {
			dataPath = Constant.PROD_DATA;
		} else if(environment.equalsIgnoreCase(Constant.LOCAL_ENV)) {
			dataPath = Constant.LOCAL_DATA;
		} else {
			throw new RuntimeException("The environment for load the data is incorrect. Try using [ " + Constant.LOCAL_ENV + " | " + Constant.CERT_ENV + " | " + Constant.PROD_ENV + " ]");
		}
		
		return getData(dataPath);
	}
	
	public static HashMap<String, String> getData(String dataPath) {
		HashMap<String, String> result;
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(dataPath));
			result = new Gson().fromJson(bufferReader, HashMap.class);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Json file not found at path : " + dataPath);
		}
		return result;
	}
	
	public static void waitTime(int seconds) {
		int timeToWait = seconds * 1000;
		try {
			Thread.sleep(timeToWait);
		} catch (Exception e) {
			System.out.println("Error waiting " + seconds + " seconds.");
		}
	}
}
