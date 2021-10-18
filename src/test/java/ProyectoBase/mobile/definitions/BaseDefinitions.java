package ProyectoBase.mobile.definitions;

import java.util.HashMap;

import ProyectoBase.base.ScenarioContext;

public class BaseDefinitions {

	protected ScenarioContext context;
	protected HashMap<String, String> data_global;

	public BaseDefinitions(ScenarioContext context) {
		this.context = context;
		data_global = (HashMap<String, String>) context.getContext("global_data");
	}
}
