package se300.destinytactics.game;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:12 PM
 */
public class Player {

	private String name;
	private int score;
	private int resource1 = 500;
	private int resource2;

	public Player(){
		name = "Player 1";
		score = 0;
		
	}

	public void finalize() throws Throwable {

	}
	public String getName(){
		return name;
	}

	public String getRace(){
		return "";
	}

	public String setName(){
		return "";
	}

	public int getScore(){
		return score;
	}

	public void takeTurn(){

	}
	
	public void addResource(int num){
		resource1 += num;
	}
	
	public void spendResource(int num){
		resource1 -= num;
	}
	public int getResource(){
		return resource1;
	}
}//end Player