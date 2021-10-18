package ProyectoBase.base;

import java.util.HashMap;
import java.util.Map;

import io.restassured.specification.RequestSpecification;

public class ApiScenarioContext {
	private Map<String, Object> scenarioContext;
	private RequestSpecification request;
	
	public ApiScenarioContext() {
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
	
	@Override
	public String toString() {
		return "ScenarioContext [scenarioContext=" + scenarioContext + "]";
	}

	public RequestSpecification getRequest() {
		return request;
	}

	public void setRequest(RequestSpecification request) {
		this.request = request;
	}
}
