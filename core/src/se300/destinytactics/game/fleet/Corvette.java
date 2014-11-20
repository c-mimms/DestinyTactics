package se300.destinytactics.game.fleet;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:56 PM
 */
public class Corvette extends Ship {

	private int combatRating = 25;
	private int galacticTravelSpeed = 2;
	private int sectorTravelSpeed = 2;
	private String shipType = "Corvette";
	
	public static ShipJSON stats = new ShipJSON();

	public Corvette(){
		buildTime = 1;
		spaceToBuild = 36;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	@Override
	public String getShipType(){
		return shipType;
	}
	
}//end Corvette