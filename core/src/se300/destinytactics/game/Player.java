package se300.destinytactics.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;


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
	public ArrayList<OrbitalBody> ownedBodies;
	public ArrayList<Fleet> fleets;
	public Color color;
	
	public Player(){
		name = "Halo Guy 117";
		score = 0;
		ownedBodies = new ArrayList<OrbitalBody>();
		fleets = new ArrayList<Fleet>();
		color = new Color(1,0,0,1);
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
	public Color getColor(){
		return color;
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

	public void addOrbitalBody(OrbitalBody ob){
		ownedBodies.add(ob);
	}
	
	public void removeOrbitalBody(OrbitalBody ob){
		ownedBodies.remove(ob);
	}
	
	public void addFleet(Fleet fl){
		fleets.add(fl);
		fl.setPlayerAssignment(this);
	}
	
	public void removeFleet(Fleet fl){
		fleets.remove(fl);
	}

	public void endTurn() {
		// TODO Auto-generated method stub
		for(OrbitalBody ob :ownedBodies){
			ob.endTurn();
		}
		for(Fleet fl : fleets){
			fl.moveFleet();
		}
		
	}
}//end Player