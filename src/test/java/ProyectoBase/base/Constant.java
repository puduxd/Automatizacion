package ProyectoBase.base;

public class Constant {
	public static final String GLOBAL_DATA = "globalData";
	public static final String CERT_ENV = "Certification";
	public static final String LOCAL_ENV = "Local";
	public static final String PROD_ENV = "Production";
	public static final String RESOURCES_PATH = "src/test/resources/";
	public static final String GLOBAL_DATA_PATH = RESOURCES_PATH + "data/";
	public static final String CERT_DATA = GLOBAL_DATA_PATH + "certificationData.json";
	public static final String LOCAL_DATA = GLOBAL_DATA_PATH + "localData.json";
	public static final String PROD_DATA = GLOBAL_DATA_PATH + "productionData.json";
	public static final String CONFIG_CAPABILITIES_PATH = GLOBAL_DATA_PATH + "capabilities.json";
	public static final String RESULTS_PATH = System.getProperty("user.dir") + "/results";
	public static final String SCREENSHOTS_PATH = "/test-output/screenshots";
	public static final String RALLY_DATA_PATH = GLOBAL_DATA_PATH +"rallyData.json";
	public static final String EVIDENCE_NAME = "embedded";
	public static final String EVIDENCE_EXTENSION = ".png";
	
	/* PARAM KEYS */
	public static final String RALLY_EXE_PARAM = "rallyExecution";
	public static final String BUILD_PARAM = "build";
	public static final String RUN_ENV_PARAM = "runEnvironment";
	public static final String EVIDENCE_PARAM = "evidence";
	public static final String MOBILE_CAPS_PARAM = "mobileCaps";
	public static final String DESK_CAPS_PARAM = "desktopCaps";
	public static final String REMOTE_PARAM = "remote";
	public static final String HEADLESS_PARAM = "headless";
	public static final String DIMENSION_PARAM = "dimension";
}
