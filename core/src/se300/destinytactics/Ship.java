package se300.destinytactics;


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
}//end Ship