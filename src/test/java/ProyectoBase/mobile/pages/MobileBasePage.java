package ProyectoBase.mobile.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileBasePage {

	protected static final int DEFAULT_WAIT_TIMEOUT = 30;
	protected static final int POLLING = 1;
	protected AppiumDriver<?> driver;
	protected final WebDriverWait wait;

	public MobileBasePage(WebDriver driver) {
		this.driver = (AppiumDriver<?>) driver;
		this.wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT, POLLING);
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT)), this);
	}

	protected MobileBasePage(WebDriver driver, int timeOutSec) {
		this.driver = (AppiumDriver<?>) driver;
		this.wait = new WebDriverWait(driver, timeOutSec, POLLING);
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT)), this);
	}

	protected MobileBasePage(WebDriver driver, int timeOutSec, int pollingSec) {
		this.driver = (AppiumDriver<?>) driver;
		this.wait = new WebDriverWait(driver, timeOutSec, pollingSec);
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT)), this);
	}

	public void waitUntilElementIsPresent(MobileElement wElement) throws Exception {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(wElement));
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}
	public boolean waitUntilElementIsPresentBoolean(MobileElement wElement) throws Exception {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(wElement));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected WebDriver getDriver() {
		return driver;
	}
}
