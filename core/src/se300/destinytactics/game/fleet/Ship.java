package se300.destinytactics.game.fleet;



/**
 * @author simonsr1
 * @version 1.0
 * @created 10-Oct-2014 5:49:17 PM
 */
public class Ship {

	private int combatRating;
	private Fleet fleetAssignment;
	private int galacticTravelSpeed = 0;
	private int hangarSize = 0;
	private boolean needsHangar = false;
	private int sectorTravelSpeed;
	private String shipType;
	private String[][] supportBenefits;
	
	
	//NEW 11/10/14
	public int buildTime;
	public int spaceToBuild;

	public Ship(){
		
	}

	public void finalize() throws Throwable {

	}
	public int getCombatRating(){
		return 0;
	}

	public Fleet getFleetAssignment(){
		return null;
	}

	public int getGalacticTravelSpeed(){
		return 0;
	}

	public int getSectorTravelSpeed(){
		return 0;
	}

	public String getShipType(){
		return "";
	}

	/**
	 * 
	 * @param ship
	 */
	public void getSupportBenefit(Ship ship){

	}

	public void recalculateSupportBenefits(){

	}

	/**
	 * 
	 * @param rating
	 */
	public void setCombatRating(int rating){

	}

	/**
	 * 
	 * @param assignment
	 */
	public void setFleetAssignment(Fleet assignment){

	}

	public int setGalacticTravelSpeed(){
		return 0;
	}

	public int setSectorTravelSpeed(){
		return 0;
	}
	
	/**
	 * gets build time
	 * @return buildTime
	 */
	public int getBuildTime() {
		return buildTime;
	}
	
	/**
	 * decrements build time by 1
	 */
	public void decrementBuildTime() {
		buildTime--;
	}
	
	/**
	 * Gets amount of space in shipyard that ship needs
	 * @return spaceToBuild
	 */
	public int getSpaceToBuild() {
		return spaceToBuild;
	}
	
}//end Ship