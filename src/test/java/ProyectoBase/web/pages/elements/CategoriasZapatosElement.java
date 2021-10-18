package ProyectoBase.web.pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CategoriasZapatosElement extends ProyectoBase.web.pages.WebBasePage {

	public CategoriasZapatosElement(WebDriver driver) {
		super(driver);
	}

	/** Menú */

	@FindBy(xpath = "//span[text()='Categorías']")
	public WebElement menuCategorias;

	@FindBy(xpath = "//li//div//p[text()='Zapatos']")
	public WebElement pZapatos;

	@FindBy(xpath = "//a[text()='Zapatos de seguridad']")
	public WebElement aZapatosSeguridad;
	
	/** Formulario categoria zapatos */
	
	@FindBy(xpath = "//span[text()='Zapatos de seguridad']")
	public WebElement spanZapatosSeguridadFiltro;
	
	@FindBy(id = "testId-Multiselect-Tipo")
	public WebElement inputTipoZapato;
	
	@FindBy(xpath = "//span[text()='Botas']")
	public WebElement spanBotas;
	
	public String xpathImg(String producto) {
		return "//img[@alt='"+ producto +"']";
	}
	
	public String xpathButtonTallaBota(String talla) {
		return "//button[@id='testId-sizeButton-"+ talla +"']";
	}
	
	@FindBy(xpath = "//button[text()='AGREGAR AL CARRO']")
	public WebElement btnAgregarAlCarro;
	
	@FindBy(xpath = "//div[contains(@class, 'product-count-value')]")
	public WebElement divContadorDProductos;
	
	@FindBy(xpath = "//button[contains(@class, 'increment')]")
	public WebElement btnIncrement;
	
	@FindBy(id = "linkButton")
	public WebElement btnCarroDCompras;
	
	@FindBy(xpath= "(//span[contains(@data-testid, 'total-amount')])[1]")
	public WebElement spanResumenCompra;

	@FindBy(id= "testId-onboarding-mobile-skip")	
	public WebElement popup;
	
}
