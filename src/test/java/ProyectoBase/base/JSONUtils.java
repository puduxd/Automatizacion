package ProyectoBase.base;

import io.cucumber.messages.internal.com.google.gson.Gson;

public final class JSONUtils {
	  private static final Gson gson = new Gson();

	  private JSONUtils(){}

	  public static boolean isJSONValid(String jsonInString) {
	      try {
	          gson.fromJson(jsonInString, Object.class);
	          return true;
	      } catch(com.google.gson.JsonSyntaxException ex) { 
	          return false;
	      }
	  }
	}