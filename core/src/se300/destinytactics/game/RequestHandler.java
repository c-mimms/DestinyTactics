package se300.destinytactics.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.Net.HttpResponseListener;


public class RequestHandler implements HttpResponseListener {

//Map to store JSON results in
private OrderedMap<String, Object> resultMap = new OrderedMap<String, Object>();

// 0 is success, 1 is failure, 2 is cancelled, -1 is incomplete
public int status = -1;
	
	
	public void handleHttpResponse(HttpResponse httpResponse) {
		
		String ret = httpResponse.getResultAsString();
		
		Json json = new Json();
		OrderedMap<String, Object> map = json.fromJson(OrderedMap.class, ret);
		map.get("MESSAGE");
		status = 0;
		resultMap = map;
	}

	public void failed(Throwable t) {
		// status = "failed";
		// do stuff here based on the failed attempt
		System.out.println("Fail");
		status = 1;
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		System.out.println("Cancelled");
		status = 2;

	}
	
	public OrderedMap<String, Object> getMap(){
		return resultMap;
	}

	public int getStatus(){
		return status;
	}
}
