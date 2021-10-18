package ProyectoBase.api.definitions;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import ProyectoBase.base.ApiScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Header;

public class ApiRestDefinition extends BaseDefinitions {
	final String BASE_URI = "https://api.github.com";
	final String BASE_PATH = "/gists";

	public ApiRestDefinition(ApiScenarioContext context) {
		super(context);
	}

	@Given("cargo configuración del servicio")
	public void cargoConfiguracion() {
		currentRequest.header(new Header("Authorization", "token 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")); //TODO: Dato mejor sacarlo de una properties.
		currentRequest.contentType(ContentType.JSON).baseUri(BASE_URI).basePath(BASE_PATH);
	}

	@When("llamo al servicio con parámetro {int} y numero de comentario {int}")
	public void llamoALServicio(int numId, int numComment) {
		response = currentRequest.when().get(numId + "/comments");
	}

	@Then("respuesta con estado {int}")
	public void validoStatusCode(int estado) {
		response.then().statusCode(estado).log().all();
	}

	@Then("respuesta con estado {int} y el tiempo de respuesta sea {int} ms")
	public void validoBodyData(int estado, int ms) {
		assert estado == response.getStatusCode();
		assert response.getTimeIn(TimeUnit.MILLISECONDS) < ms; //TODO: se caera porque no es mas rápido que postman.
	}

	@SuppressWarnings("unchecked")
	@When("preparo body {string} y parámetro {int}")
	public void preparoInformacion(String body, int numId) {
		JSONObject request = new JSONObject();
		request.put("body", body);
		response = currentRequest.body(request.toJSONString()).when().post(numId + "/comments");
		assert "201".equalsIgnoreCase(String.valueOf(response.getStatusCode()));
		assert response.then().log().body() != null;
	}

}
