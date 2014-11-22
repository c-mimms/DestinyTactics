package se300.destinytactics.game.fleet;



/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:54 PM
 */
@SuppressWarnings("unused")
public class Carrier extends Ship implements hasHangar {

	private int baysEmpty;
	private int combatRating = 10;
	private int galacticTravelSpeed = 1;
	private int hangarSize = 50;
	private int sectorTravelSpeed = 1;
	private String shipType = "Carrier";
	private int buildTime;
	
	public static ShipJSON stats = new ShipJSON();

	public Carrier(){
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
	
}//end Carrier