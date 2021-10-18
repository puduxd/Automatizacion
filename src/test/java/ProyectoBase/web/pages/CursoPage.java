package ProyectoBase.web.pages;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import ProyectoBase.web.pages.elements.CursoElement;

public class CursoPage extends WebBasePage {

	public CursoPage(WebDriver driver) {
		super(driver);
	}

	private static Logger logger = Logger.getLogger("CursoPage");

	CursoElement cursoElement = new CursoElement(driver);

	public void openBrowser(String url) {
		getDriver().get(url);
	}

	public void login(String user, String pass) {
		cursoElement.txtUsername.sendKeys(user);
		
		cursoElement.txtPassword.sendKeys(pass);
		
		cursoElement.btnLogin.click();
	}

	public void validateTable(String tableName) {
		cursoElement.tableMyLeaveList.isDisplayed();		
	}


}
