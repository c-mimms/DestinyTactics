package se300.destinytactics.game.fleet;



/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:59 PM
 */
public class Dreadnaught extends Ship implements hasHangar {

	private int baysEmpty;
	private int combatRating = 100;
	private int galacticTravelSpeed = 1;
	private int hangarSize = 25;
	private int sectorTravelSpeed = 1;
	private int buildTime;
	private String shipType = "Dreadnaught";
	
	public static ShipJSON stats = new ShipJSON();

	public Dreadnaught(){
		buildTime = stats.buildTime;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public int getEmptyBayCount(){
		return 0;
	}

	public int getHangarSize(){
		return 0;
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
	
}//end Dreadnaught