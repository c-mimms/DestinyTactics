package se300.destinytactics.orbitalbodies;

import se300.destinytactics.logic.Player;
import se300.destinytactics.mapgen.OrbitalBody;
import se300.destinytactics.units.Ship;


/**
 * @author simonsr1
 * @version 1.0
 * @created 10-Oct-2014 5:49:01 PM
 */
public class Fleet {

	private OrbitalBody destination;
	private int distance;
	private int fleetGalacticTravelSpeed;
	private int fleetSectorTravelSpeed;
	private OrbitalBody location;
	private String name;
	private Player playerAssignment;
	private int ships[];
	public Ship m_Ship;

	public Fleet(){

	}

	public void finalize() throws Throwable {

	}
	public OrbitalBody getDestination(){
		return null;
	}

	public int getFleetGalacticTravelSpeed(){
		return 0;
	}

	public int getFleetSectorTravelSpeed(){
		return 0;
	}

	public OrbitalBody getLocation(){
		return null;
	}

	public String getName(){
		return "";
	}

	public Player getPlayerAssignment(){
		return null;
	}

	/**
	 * 
	 * @param unitType
	 */
	public int getShipCount(Ship unitType){
		return 0;
	}

	/**
	 * 
	 * @param unitType
	 * @param num
	 */
	public void getShipCount(Ship unitType, int num){

	}

	/**
	 * 
	 * @param location
	 */
	public fleet moveFleet(OrbitalBody location){
		return null;
	}

	public void recalculateFleetSpeeds(){

	}

	/**
	 * 
	 * @param player
	 */
	public void setPlayerAssignment(Player player){

	}
}//end Fleet