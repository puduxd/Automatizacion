package ProyectoBase.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebBasePage {

	protected static final int DEFAULT_WAIT_TIMEOUT = 30;
	protected static final int POLLING = 1;
	protected WebDriver driver;
	protected final WebDriverWait wait;

	public WebBasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT, POLLING);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, DEFAULT_WAIT_TIMEOUT), this);
	}

	protected WebBasePage(WebDriver driver, int timeOutSec) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeOutSec, POLLING);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, DEFAULT_WAIT_TIMEOUT), this);
	}

	protected WebBasePage(WebDriver driver, int timeOutSec, int pollingSec) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeOutSec, pollingSec);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, DEFAULT_WAIT_TIMEOUT), this);
	}

	protected void waitUntilElementIsPresent(WebElement wElement) throws Exception {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(wElement));
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}
	
	protected void switchToIframe(WebElement iframe) throws Exception {
		waitUntilElementIsPresent(iframe);
		driver.switchTo().frame(iframe);
	}
	
	protected void returnToParentFrame() {
		driver.switchTo().parentFrame();
	}
	
	protected void returnToMainFrame() {
		driver.switchTo().defaultContent();
	}

	protected WebDriver getDriver() {
		return driver;
	}
}
