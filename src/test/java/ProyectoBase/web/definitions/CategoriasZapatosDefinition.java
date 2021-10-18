package ProyectoBase.web.definitions;

import ProyectoBase.base.ScenarioContext;
import ProyectoBase.web.pages.CategoriasZapatosPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CategoriasZapatosDefinition extends BaseDefinitions {
	CategoriasZapatosPage categoriaZapatosPage;

	public CategoriasZapatosDefinition(ScenarioContext context) {
		super(context);
		categoriaZapatosPage = new CategoriasZapatosPage(context.getDriver());
	}

	@Given("navegar a menu {string} luego a {string}")
	public void navegarAMenuLuegoA(String string, String string2) {
		categoriaZapatosPage.navToCategorias();
		categoriaZapatosPage.navZapatos();
	}

	@When("realizar click en {string}")
	public void realizarClickEn(String string) {
		categoriaZapatosPage.navZapatosSeguridad();
	}

	@When("realizar filtración por {string}")
	public void realizarFiltraciónPor(String tipo) {
		categoriaZapatosPage.filterToBotas(tipo);
	}

	@When("realizar click en algun producto {string} y talla {string}")
	public void realizarClickEnAlgunProductoYTalla(String producto, String talla) {
		categoriaZapatosPage.selectImgBotas(producto, talla);
	}

	@When("Aumentar la cantidad hasta {int}")
	public void aumentarLaCantidadHasta(Integer cantidad) {
		categoriaZapatosPage.addCarro(cantidad);
	}

	@Then("Validar al realizar click en {string}, para desplegar el detalle de la bolsa de compras")
	public void validarAlRealizarClickEnParaDesplegarElDetalleDeLaBolsaDeCompras(String string) {
		categoriaZapatosPage.goToCarroDCompras();
	}

	@Then("Culminar la automatización")
	public void culminarLaAutomatización() {
		categoriaZapatosPage.GraciasPorLaOportunidad();
	}

}
