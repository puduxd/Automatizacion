package ProyectoBase.base;

import java.net.URL;

import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;

public class DesktopFactory {
	public static String remote;
	static {
		remote = System.getProperty("remote", "http://127.0.0.1:4723/wd/hub");
	}

	public static WindowsDriver<WebElement> instanceApp(Configuration mobileConfiguration) throws Exception {
		System.out.println("APPIUM CONFIGURADO: [" + remote + "]");
		System.out.println("MOBILE CONFIG: [" + mobileConfiguration + "]");
		return new WindowsDriver<>(new URL(remote), mobileConfiguration.getCapability());
	}
}
