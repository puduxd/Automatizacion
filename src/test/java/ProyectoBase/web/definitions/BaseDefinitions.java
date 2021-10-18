package ProyectoBase.web.definitions;

import java.util.HashMap;

import ProyectoBase.base.Constant;
import ProyectoBase.base.ScenarioContext;

public class BaseDefinitions {

	protected ScenarioContext context;
	protected HashMap<String, String> dataGlobal;

	@SuppressWarnings("unchecked")
	public BaseDefinitions(ScenarioContext context) {
		this.context = context;
		dataGlobal = (HashMap<String, String>) context.getContext(Constant.GLOBAL_DATA);
	}
	
}
