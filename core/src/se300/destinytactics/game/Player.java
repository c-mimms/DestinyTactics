package se300.destinytactics.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;


/**
* <h1>Player</h1>
* 
* Player in the current game.
*
* @author  Team Gaurdian
* @version 1.0
* @since   2014-11-12
* 
*/
public class Player {

	private String name;
	private int score;
	private int resource1 = 500;
	@SuppressWarnings("unused")
	private int resource2;
	public ArrayList<OrbitalBody> ownedBodies;
	public ArrayList<Fleet> fleets;
	public Color color;

	public Player(){
		name = "Nobody";
		score = 0;
		ownedBodies = new ArrayList<OrbitalBody>();
		fleets = new ArrayList<Fleet>();
		color = new Color(1,0,0,1);
	}
	
	/**
	 * Create a new player with a name, at the startPos with a Fleet, with a player color.
	 * 
	 * @param name
	 * @param startPos
	 * @param startFleet
	 * @param color
	 */
	public Player(String name, OrbitalBody startPos , Fleet startFleet, Color color){
		this.name = name;
		score = 0;
		ownedBodies = new ArrayList<OrbitalBody>();
		ownedBodies.add(startPos);
		fleets = new ArrayList<Fleet>();
		fleets.add(startFleet);
		this.color = color;
	}

	public void finalize() throws Throwable {

	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getScore(){
		return score;
	}

	/**
	 * Take player turn.
	 */
	public void takeTurn(){

	}
	
	public Color getColor(){
		return color;
	}
	
	/**
	 * Add resources to the player.
	 * @param num
	 */
	public void addResource(int num){
		resource1 += num;
	}
	
	/**
	 * Subtract resources from the player.
	 * @param num
	 */
	public void spendResource(int num){
		resource1 -= num;
	}
	
	/**
	 * Get the amount of resources the player has.
	 * @return
	 */
	public int getResource(){
		return resource1;
	}

	/**
	 * Add an orbital body owned by the player.
	 * @param ob
	 */
	public void addOrbitalBody(OrbitalBody ob){
		ownedBodies.add(ob);
	}
	
	/*
	 * Remove an orbital body no longer owned by the player.
	 */
	public void removeOrbitalBody(OrbitalBody ob){
		ownedBodies.remove(ob);
	}
	
	/**
	 * Add a Fleet created by the player.
	 * @param fl
	 */
	public void addFleet(Fleet fl){
		fleets.add(fl);
		fl.setPlayerAssignment(this);
	}
	
	/**
	 * Remove a fleet that was destroyed.
	 * @param fl
	 */
	public void removeFleet(Fleet fl){
		fleets.remove(fl);
	}

	/**
	 * Execute all endTurn methods for the player.
	 */
	public void endTurn() {
		
		for(OrbitalBody ob :ownedBodies){
			ob.endTurn();
		}
		for(Fleet fl : fleets){
			fl.moveFleet();
		}
		
	}
}//end Player