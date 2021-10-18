package ProyectoBase.web.definitions;


import ProyectoBase.base.ScenarioContext;
import ProyectoBase.web.pages.CategoriasZapatosPage;
import io.cucumber.java.en.Given;


public class Background extends BaseDefinitions {
	CategoriasZapatosPage categoriaZapatos;

	public Background(ScenarioContext context) {
		super(context);
		categoriaZapatos = new CategoriasZapatosPage(context.getDriver());
	}
	
	@Given("ingreso a URL {string}")
	public void ingresoAURL(String string) {
		categoriaZapatos.openBrowser(string);
	}
}
