package ProyectoBase.web.definitions;

import ProyectoBase.base.ScenarioContext;
import ProyectoBase.web.pages.CursoPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CursoDefinition extends BaseDefinitions {
	CursoPage cursoPage;

	public CursoDefinition(ScenarioContext context) {
		super(context);
		cursoPage = new CursoPage(context.getDriver());
	}

	@Given("ingreso a url {string}")
	public void ingresoAUrl(String url) {
		cursoPage.openBrowser(url);
	}

	@When("ingrese las credenciales usuario {string} y clave {string}")
	public void ingreseLasCredencialesUsuarioYClave(String user, String pass) {
		cursoPage.login(user, pass);
	}

	@Then("validar el ingreso a pagina {string}")
	public void validarElIngresoAPagina(String tableName) {
		cursoPage.validateTable(tableName);
	}

}
