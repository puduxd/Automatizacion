package ProyectoBase.base;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.cucumber.messages.internal.com.google.gson.Gson;

public class Configuration {

	private String id;
	private DesiredCapabilities capability;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DesiredCapabilities getCapability() {
		return capability;
	}

	public void setCapability(DesiredCapabilities capability) {
		this.capability = capability;
	}
}
