package se300.destinytactics.game;


import se300.destinytactics.MakesRequests;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;

/**
* <h1>WebRequest</h1>
* 
* Asynchronous method to make web requests. When request is finished, the callback method http() is called
* on the calling class. 
* <p>
* Implement as shown in snippet below.
* 
* <code>Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "createUser");
		parameters.put("username", name);
		parameters.put("password", passwordHash);
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest registerReq = new WebRequest(this, httpGet);
		registerReq.start(); </code>
*
* @author  Chris Mimms
* @version 1.0
* @since   2014-11-12
* 
*/
public class WebRequest extends Thread {

	MakesRequests cl;
	HttpRequest httpGet;
	
	/**
	 * Create a new http request thread.
	 * @param cl Calling class, when HTTP request finished, will call http() method of this class.
	 * @param httpGet An HttpRequest with all parameters set.
	 */
	public WebRequest(MakesRequests cl, HttpRequest httpGet){
		this.cl = cl;
		this.httpGet = httpGet;
	}
	
	/**
	 * Run the HttpRequest thread.
	 */
	public void run(){
		send();
		try {
			//End this thread.
			this.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Send the http request and wait for result.
	 */
	public void send() {

		RequestHandler req = new RequestHandler();
		Gdx.net.sendHttpRequest(httpGet, req);
		//Check if the request has returned yet
		while (req.getStatus() == -1) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//Call the http function of calling class and pass in the JSON map.
		cl.http(req.getMap());
		
	}
}
