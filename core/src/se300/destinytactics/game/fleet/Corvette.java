package se300.destinytactics.game.fleet;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:56 PM
 */
@SuppressWarnings("unused")
public class Corvette extends Ship {

	private int combatRating = 25;
	private int galacticTravelSpeed = 2;
	private int sectorTravelSpeed = 2;
	private int buildTime;
	private String shipType = "Corvette";
	
	public static ShipJSON stats = new ShipJSON();

	public Corvette(){
		buildTime = stats.buildTime;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	@Override
	public String getShipType(){
		return stats.unit;
	}
	
	@Override
	public void decrementBuildTime() {
		buildTime--;
		
	}
	@Override
	public int getBuildTime() {
		return buildTime;
	}

	@Override
	public int getSpaceToBuild() {
		return stats.size;
	}

	@Override
	public int getMetalCost() {
		return stats.metalCost;
	}
	
}//end Corvette