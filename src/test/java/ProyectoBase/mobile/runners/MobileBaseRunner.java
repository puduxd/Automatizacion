package ProyectoBase.mobile.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "ProyectoBase.mobile.definitions" }, features = "src/test/resources/features/mobile", plugin = {
		"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })
public class MobileBaseRunner {

}
