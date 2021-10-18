package ProyectoBase.base;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;

public class DesktopScenarioContext {

	private Map<String, Object> scenarioContext;
	private WindowsDriver<WebElement> driver;

	public DesktopScenarioContext() {
		scenarioContext = new HashMap<>();
	}

	public void setContext(String key, Object value) {
		scenarioContext.put(key, value);
	}

	public Object getContext(String key) {
		return scenarioContext.get(key);
	}

	public Boolean isContains(String key) {
		return scenarioContext.containsKey(key);
	}

	public WindowsDriver<WebElement> getDriver() {
		return driver;
	}

	public void setDriver(WindowsDriver<WebElement> driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "ScenarioContext [scenarioContext=" + scenarioContext + "]";
	}
}
