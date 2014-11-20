package se300.destinytactics.game.fleet;


 

/**
 * @author simonsr1
 * @version 1.0
 * @created 10-Oct-2014 5:49:00 PM
 */
public class Fighter extends Ship implements needsHangar {

	private int combatRating = 1;
	private int hangarcost = 1;
	private int sectorTravelSpeed = 1;
	private int buildTime;

	public static ShipJSON stats = new ShipJSON();
	
	public Fighter(){
		buildTime = stats.buildTime;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param units
	 */
	public int getHangarCost(int units){
		return 0;
	}

	public boolean isHangared(){
		return false;
	}

	public int getHangarCost(){
		return stats.needsHangar;
	}
	
	@Override
	public String getShipType(){
		return stats.unit;
	}
	


	@Override
	public int getSpaceToBuild() {
		return stats.size;
	}

	@Override
	public int getMetalCost() {
		return stats.metalCost;
	}

	@Override
	public void decrementBuildTime() {
		buildTime--;
		
	}
	@Override
	public int getBuildTime() {
		return buildTime;
	}
	
}//end Fighter