package se300.destinytactics.game.fleet;

public class Battleship extends Ship implements hasHangar {

	public String shipType = "Battleship";
	private int buildTime;
	public static ShipJSON stats = new ShipJSON();
	
	public Battleship() {
		buildTime = stats.buildTime;
	}
	
	@Override
	public int getEmptyBayCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHangarSize() {
		// TODO Auto-generated method stub
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
	

}
