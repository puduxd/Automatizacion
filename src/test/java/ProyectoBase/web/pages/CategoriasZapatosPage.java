package ProyectoBase.web.pages;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ProyectoBase.web.pages.elements.CategoriasZapatosElement;

public class CategoriasZapatosPage extends WebBasePage {

	public CategoriasZapatosPage(WebDriver driver) {
		super(driver);
	}

	private static Logger logger = Logger.getLogger("CategoriasZapatosPage");

	CategoriasZapatosElement cz = new CategoriasZapatosElement(driver);

	public void openBrowser(String url) {
		getDriver().get(url);
	}

	public void navToCategorias() {
		cz.popup.click();
		cz.menuCategorias.click();
	}

	public void navZapatos() {
		cz.pZapatos.click();
	}

	public void navZapatosSeguridad() {
		cz.aZapatosSeguridad.click();
	}

	public void filterToBotas(String tipo) {
		cz.spanZapatosSeguridadFiltro.click();
		try {
			Thread.sleep(5000);
			cz.inputTipoZapato.sendKeys(tipo);
			cz.spanBotas.click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void selectImgBotas(String producto, String talla) {
		try {
			driver.findElement(By.xpath(cz.xpathImg(producto))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(cz.xpathButtonTallaBota(talla))).click();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void addCarro(Integer cantidad) {
		try {
			cz.btnAgregarAlCarro.click();
			Thread.sleep(5000);
			int count = Integer.parseInt(cz.divContadorDProductos.getText());
			
			for (int i = count; i < cantidad; i++) {
				cz.btnIncrement.click();
				Thread.sleep(3000);
				logger.log(Level.INFO, "" +i);
			}
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void goToCarroDCompras() {
		try {
			cz.btnCarroDCompras.click();
			Thread.sleep(5000);
			String resumen = cz.spanResumenCompra.getText();
			logger.log(Level.INFO, "Resumen de compra: " + resumen);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		
	}

	public void GraciasPorLaOportunidad() {
		logger.log(Level.INFO, "Gracias por la oportunidad...");
	}

}
