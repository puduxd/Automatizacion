package ProyectoBase.web.pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CursoElement extends ProyectoBase.web.pages.WebBasePage {

	

	public CursoElement(WebDriver driver) {
		super(driver);
	}

	/** 
	 * Credenciales
	 * 
	 *  txtUsername
	 * 
	 *  txtPassword
	 *    
	 *  btnLogin
	 *  
	 *  */
	
	@FindBy(id= "txtUsername")
	public WebElement txtUsername;
	
	@FindBy(id= "txtPassword")
	public WebElement txtPassword;
	
	@FindBy(xpath= "//*[@id='btnLogin']")
	public WebElement btnLogin;
	
	
	@FindBy(xpath="//*[text()='My Leave List']")
	public WebElement tableMyLeaveList;
	

}
