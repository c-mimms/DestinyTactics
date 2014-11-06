package se300.destinytactics.game;

import se300.destinytactics.MakesRequests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;

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
