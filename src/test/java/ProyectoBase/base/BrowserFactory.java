package ProyectoBase.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {
	public enum Browser {
		firefox, chrome, iexplorer, safari, edge
	}

	public static final int WEB_HEIGHT = 768;
	public static final int WEB_WIDTH = 1366;
	public static String remote;
	public static boolean headless;
	public static String dimension;
	static {
		remote = System.getProperty("remote", "not");
		headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
		dimension = System.getProperty("dimension", "1366x768");
	}

	public static Dimension setWebDimension() {
		String[] dimensionSplit = dimension.toLowerCase().split("x");
		if (dimensionSplit.length < 2) {
			return new Dimension(WEB_WIDTH, WEB_HEIGHT);
		}
		return new Dimension(Integer.parseInt(dimensionSplit[0]), Integer.parseInt(dimensionSplit[1]));
	}

	public static WebDriver instanceChromeDriver() throws MalformedURLException {
		WebDriverManager.chromedriver().arch32().setup();
		ChromeOptions options = generateDefaultChrome();
		if (!remote.equals("not")) {
			return new RemoteWebDriver(new URL(remote), options);
		}
		return new ChromeDriver(options);
	}

	public static WebDriver instanceFirefoxDriver() throws MalformedURLException {
		WebDriverManager.firefoxdriver().arch32().setup();
		FirefoxOptions options = generateDefaultFirefox();
		if (!remote.equals("not")) {
			return new RemoteWebDriver(new URL(remote), options);
		}
		return new FirefoxDriver(options);
	}

	public static WebDriver instanceIExplorerDriver() throws MalformedURLException {
		WebDriverManager.iedriver().arch32().browserVersion("11").setup();
		InternetExplorerOptions options = generateDefaultIExplorer();
		if (!remote.equals("not")) {
			return new RemoteWebDriver(new URL(remote), options);
		}
		return new InternetExplorerDriver(options);
	}

	public static WebDriver instanceSafariDriver() throws MalformedURLException {
		SafariOptions options = generateDefaultSafari();
		if (!remote.equals("not")) {
			return new RemoteWebDriver(new URL(remote), options);
		}
		return new SafariDriver(options);
	}

	public static WebDriver instanceEdgeDriver() throws MalformedURLException {
		WebDriverManager.edgedriver().arch32().setup();
		EdgeOptions options = generateDefaultEdge();
		if (!remote.equals("not")) {
			return new RemoteWebDriver(new URL(remote), options);
		}
		return new EdgeDriver(options);
	}

	private static ChromeOptions generateDefaultChrome() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--incognito");
		String sandbox = System.getProperty("sandbox");
		if (sandbox != null && sandbox.equalsIgnoreCase("no")) {
			chromeOptions.addArguments("--no-sandbox");
		}
		String lang = System.getProperty("lang");
		if (lang == null || lang.isEmpty()) {
			lang = "es";
		}
		chromeOptions.addArguments("--lang=" + lang);
		chromeOptions.setHeadless(headless);

		Map<String, Object> prefs = new HashMap<>();
		if ("not".equals(remote)) {
			prefs.put("download.default_directory",
					new File(System.getProperty("downloadFolder", "/temp")).getAbsolutePath());
			chromeOptions.setExperimentalOption("prefs", prefs);
		}

		return chromeOptions;
	}

	private static FirefoxOptions generateDefaultFirefox() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setLegacy(true);
		firefoxOptions.addArguments("--disable-extensions", "-private", "safebrowsing.enabled=false");
		firefoxOptions.setCapability("marionette", true);
		String lang = System.getProperty("lang");
		if (lang == null || lang.isEmpty()) {
			lang = "es";
		}
		firefoxOptions.addArguments("--lang=" + lang);
		firefoxOptions.setHeadless(headless);
		return firefoxOptions;
	}

	private static InternetExplorerOptions generateDefaultIExplorer() {
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		ieOptions.disableNativeEvents();
		ieOptions.destructivelyEnsureCleanSession();
		ieOptions.addCommandSwitches("-private");
		return ieOptions;
	}

	private static SafariOptions generateDefaultSafari() {
		SafariOptions safariOptions = new SafariOptions();
		safariOptions.setCapability("safari.cleanSession", true);
		return safariOptions;
	}

	private static EdgeOptions generateDefaultEdge() {
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.setCapability("InPrivate", true);
		return edgeOptions;
	}
}
