package se300.destinytactics.game;

import se300.destinytactics.MakesRequests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;

public class WebRequest extends Thread {

	MakesRequests cl;
	HttpRequest httpGet;
	
	public WebRequest(MakesRequests cl, HttpRequest httpGet){
		this.cl = cl;
		this.httpGet = httpGet;
	}
	
	public void run(){
		send();
		try {
			this.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send() {

		RequestHandler req = new RequestHandler();
		Gdx.net.sendHttpRequest(httpGet, req);
		while (req.getStatus() == -1) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		cl.http(req.getMap());
		
	}
}
