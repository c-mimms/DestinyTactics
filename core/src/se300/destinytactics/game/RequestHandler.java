package se300.destinytactics.game;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.Net.HttpResponseListener;

/**
* <h1>RequestHandler</h1>
* 
* A universal JSON HTTP request handler. Parses the JSON returned from an HTTP request into a map.
*
* @author  Chris Mimms
* @version 1.0
* @since   2014-11-12
* 
*/
public class RequestHandler implements HttpResponseListener {

//Map to store JSON results in
private OrderedMap<String, Object> resultMap = new OrderedMap<String, Object>();

// 0 is success, 1 is failure, 2 is cancelled, -1 is incomplete
public int status = -1;
	

	/**
	 * Default method that is called on successful HTTP response.
	 */
	public void handleHttpResponse(HttpResponse httpResponse) {
		
		String ret = httpResponse.getResultAsString();
		
		Json json = new Json();
		@SuppressWarnings("unchecked")
		OrderedMap<String, Object> map = json.fromJson(OrderedMap.class, ret);
		status = 0;
		resultMap = map;
	}

	/**
	 * Called when HTTP request fails.
	 */
	public void failed(Throwable t) {
		System.out.println("Fail");
		status = 1;
	}

	@Override
	/**
	 * Called when HTTP request is cancelled.
	 */
	public void cancelled() {
		System.out.println("Cancelled");
		status = 2;
	}
	
	/**
	 * Return map of results of HTTP request, only makes sense for JSON requests.
	 * @return map of JSON results <String, Object>
	 */
	public OrderedMap<String, Object> getMap(){
		return resultMap;
	}

	/**
	 * Gets status of http request.
	 * @return  0 on success, 1 on failure, 2 on cancelled, -1 while incomplete
	 */
	public int getStatus(){
		return status;
	}
}
