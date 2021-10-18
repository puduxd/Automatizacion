package ProyectoBase.base;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MobileFactory {

	public static String remote;
	static {
		remote = System.getProperty("remote", "http://127.0.0.1:4723/wd/hub");
	}

	public static AppiumDriver<MobileElement> instanceMobile(Configuration mobileConfiguration) throws Exception {
		System.out.println("APPIUM CONFIGURADO: [" + remote + "]");
		System.out.println("MOBILE CONFIG: [" + mobileConfiguration + "]");
		return new AppiumDriver<>(new URL(remote), mobileConfiguration.getCapability());
	}
}
