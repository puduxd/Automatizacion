package ProyectoBase.api.definitions;

import java.util.HashMap;
import java.util.Objects;

import ProyectoBase.base.ApiScenarioContext;
import ProyectoBase.base.Constant;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseDefinitions {
	protected ApiScenarioContext context;
	protected HashMap<String, String> data_global;
	protected RequestSpecification currentRequest;
	protected Response response;

	@SuppressWarnings("unchecked")
	public BaseDefinitions(ApiScenarioContext context) {
		this.context = context;
		data_global = (HashMap<String, String>) context.getContext(Constant.GLOBAL_DATA);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		if (Objects.isNull(context.getRequest())) {
			context.setRequest(RestAssured.given());
		}
		currentRequest = context.getRequest();
	}
}
