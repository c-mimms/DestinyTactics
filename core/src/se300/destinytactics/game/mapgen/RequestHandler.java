package se300.destinytactics.game.mapgen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.Net.HttpResponseListener;


public class RequestHandler implements HttpResponseListener {

//Map to store JSON results in
private Map<String, String> resultMap = new HashMap<String, String>();

// 0 is success, 1 is failure, 2 is cancelled
public int status;
	
	
	public void handleHttpResponse(HttpResponse httpResponse) {
		
		String ret = httpResponse.getResultAsString();
		
		Json json = new Json();
		ObjectMap<String, String> map = json.fromJson(ObjectMap.class, ret);
		System.out.println(map);
		map.get("MESSAGE");
		// ret = map.get("MESSAGE");
	}

	public void failed(Throwable t) {
		// status = "failed";
		// do stuff here based on the failed attempt
		System.out.println("Fail");
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		System.out.println("Cancelled");

	}
	
	public Map<String, String> getMap(){
		return resultMap;
	}

}
