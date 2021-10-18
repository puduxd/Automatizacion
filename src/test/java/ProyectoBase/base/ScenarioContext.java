package ProyectoBase.base;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public class ScenarioContext {

	private Map<String, Object> context;
	private WebDriver driver;

	public ScenarioContext() {
		context = new HashMap<>();
	}

	public void setContext(String key, Object value) {
		context.put(key, value);
	}

	public Object getContext(String key) {
		return context.get(key);
	}

	public Boolean isContains(String key) {
		return context.containsKey(key);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "ScenarioContext [scenarioContext=" + context + "]";
	}
}
