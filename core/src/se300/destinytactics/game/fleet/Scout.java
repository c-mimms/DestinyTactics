package se300.destinytactics.game.fleet;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:14 PM
 */
public class Scout extends Ship {

	private int combatRating = 1;
	private int galacticTravelSpeed = 3;
	private int sectorTravelSpeed = 2;
	private String shipType = "Scout";
	
	public static ShipJSON stats = new ShipJSON();

	public Scout(){
		buildTime = 1;
		spaceToBuild = 4;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	@Override
	public String getShipType(){
		return shipType;
	}
	
}//end Scout