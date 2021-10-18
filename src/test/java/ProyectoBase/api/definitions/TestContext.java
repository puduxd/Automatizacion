package ProyectoBase.api.definitions;

import java.net.URISyntaxException;
import java.util.Date;

import ProyectoBase.base.ApiScenarioContext;
import ProyectoBase.base.Constant;
import ProyectoBase.base.DefaultValues;
import ProyectoBase.base.RallyIntegration;
import ProyectoBase.base.Utils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class TestContext {
	private Scenario scenario;
	private ApiScenarioContext context;
	private RallyIntegration rallyIntegration;
	private String tcId;
	private Date startDate;
	private static String rallyExecution;
	private static String buildRefference;
	private static String runEnvironment;
	static {
		rallyExecution = System.getProperty(Constant.RALLY_EXE_PARAM, DefaultValues.RALLY_EXECUTION);
		buildRefference = System.getProperty(Constant.BUILD_PARAM, DefaultValues.BUILD);
		runEnvironment = System.getProperty(Constant.RUN_ENV_PARAM, DefaultValues.RUN_ENVIRONMENT);
	}

	public TestContext(ApiScenarioContext context) {
		this.context = context;
	}

	@Before
	public void prepareTest(Scenario scenario) throws URISyntaxException {
		startDate = new Date();
		this.scenario = scenario;
		this.context.setContext("starDate", startDate);
		tcId = Utils.readTestCaseTags(scenario.getSourceTagNames());
		this.context.setContext(Constant.GLOBAL_DATA, Utils.getGlobalData(runEnvironment));
		this.startRallyConnection();
	}

	@After
	public void tearDown() throws Exception {
		if (tcId != null) {
			rallyIntegration.updateTestCase(rallyExecution, tcId, scenario.isFailed(), scenario.getName(),
					buildRefference, startDate);
			rallyIntegration.closeConnection();
		}
	}

	private void startRallyConnection() throws URISyntaxException {
		if (tcId != null) {
			rallyIntegration = new RallyIntegration();
		}
	}
}
