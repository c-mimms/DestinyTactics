package se300.destinytactics.game.fleet;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:14 PM
 */
@SuppressWarnings("unused")
public class Scout extends Ship {

	private int combatRating = 1;
	private int galacticTravelSpeed = 3;
	private int sectorTravelSpeed = 2;
	private int buildTime;
	private String shipType = "Scout";
	
	public static ShipJSON stats = new ShipJSON();

	public Scout(){
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
	
}//end Scout