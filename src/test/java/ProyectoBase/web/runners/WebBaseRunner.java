package ProyectoBase.web.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "ProyectoBase.web.definitions" }, features = "src/test/resources/features/web/curso.feature", plugin = {
		"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, snippets = SnippetType.CAMELCASE)
public class WebBaseRunner {

}
