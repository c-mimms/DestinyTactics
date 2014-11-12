package se300.destinytactics.game.fleet;

public class Battleship extends Ship implements hasHangar {

	public String shipType = "Battleship";
	
	public Battleship() {
		buildTime = 4;
		spaceToBuild = 64;
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
		return shipType;
	}
	

}
