package ProyectoBase.mobile.pages;

import java.net.URL;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class HomePage extends MobileBasePage {
	public HomePage(AppiumDriver<?> driver) {
		super(driver);		
	}

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.Button")
	MobileElement btnPagar;
	
	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView")
	MobileElement txtPDP;
	
	@FindBy(xpath = "//*[@text='Más']")
	MobileElement AbrirMenu;
	
	@FindBy (xpath = "//*[@text='Tu consumo']")
	MobileElement BoletaMenu;
	
	@FindBy (xpath = "//mv-button[@id=\"pagar-boleta\"]/button/span/mv-caption/span")
	MobileElement pagarXpath;
	
	@FindBy (id = "pagar-boleta")
	MobileElement PagarID;
	
	@FindBy(xpath = "//android.widget.Button")
	MobileElement btnPagar2;
	
	@FindBy(xpath = "//*[@text='Más']")
	MobileElement xpathTest;
	
	public void validateHome() throws Exception {		
		if(waitUntilElementIsPresentBoolean(btnPagar) == true) {
			System.out.println("linea con deuda");
		}
		else {
			System.out.println("linea sin deuda");
		}
	}
	public void clickPagar() {		
		btnPagar.click();
	}
	public void validarPDP() throws Exception {
		waitUntilElementIsPresent(txtPDP);
	}
	public void seleccionarMisBoletas(){
		
		AbrirMenu.click();
		BoletaMenu.click();		
	}
	public void  clickPagarMisBoletas() {
		pagarXpath.click();
		
	}
	
}






























