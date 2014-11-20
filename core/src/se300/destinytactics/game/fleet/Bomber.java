package se300.destinytactics.game.fleet;

public class Bomber extends Ship {

	public String shipType = "Bomber";
	private int buildTime;
	
	public static ShipJSON stats = new ShipJSON();
	
	public Bomber() {
		buildTime = stats.buildTime;
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
}
