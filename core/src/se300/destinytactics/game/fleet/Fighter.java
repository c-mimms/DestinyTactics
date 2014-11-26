package se300.destinytactics.game.fleet;


 

/**
 * @author simonsr1
 * @version 1.0
 * @created 10-Oct-2014 5:49:00 PM
 */
@SuppressWarnings("unused")
public class Fighter extends Ship implements needsHangar {

	private int combatRating = 1;
	private int hangarcost = 1;
	private int sectorTravelSpeed = 1;
	private int buildTime;
	private String name;
	private boolean hangared = false;

	public static ShipJSON stats = new ShipJSON();
	
	public Fighter(){
		name = stats.unit;
		buildTime = stats.buildTime;
	}
	
	public Fighter(String names){
		name = names;
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

	public boolean getHangared(){
		return hangared;
	}

	public int getHangarCost(){
		return stats.needsHangar;
	}
	
	@Override
	public String getShipType(){
		return name;
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
	
	/**
	 * Set hangared to true or false
	 * @param hangared
	 */
	public void setHangared(boolean hangared){
		this.hangared = hangared;
	}

	@Override
	public boolean isHangared() {
		// TODO Auto-generated method stub
		return false;
	}
	
}//end Fighter