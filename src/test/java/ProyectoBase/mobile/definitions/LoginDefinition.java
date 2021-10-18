package ProyectoBase.mobile.definitions;

import ProyectoBase.base.ScenarioContext;
import ProyectoBase.mobile.pages.HomePage;
import ProyectoBase.mobile.pages.LoginPage;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginDefinition extends BaseDefinitions {
	LoginPage loginPage;
	HomePage homePage;

	public LoginDefinition(ScenarioContext context) {
		super(context);
	}
	@Given("ingreso a la aplicación mobile")
	public void ingreso_a_la_aplicacion_mobile() {
		loginPage = new LoginPage(context.getDriver());
	}
	@When("ingreso usuario {string} y contraseña {string} y me logeo")
	public void iniciarSesionApp(String usuario, String contraseña) throws Throwable {
		loginPage.loginApp(usuario, contraseña);
	}	
	@Then("espero ver el resumen de mi sesión")
	public void ver_resumen_sesion() throws Exception {
		homePage = new HomePage((AppiumDriver<?>) context.getDriver());
		homePage.validateHome();
	}

}
