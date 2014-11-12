package se300.destinytactics.game.fleet;

public class Bomber extends Ship {

	public String shipType = "Bomber";
	
	public Bomber() {
		buildTime = 3;
		spaceToBuild = 36;
	}
	
	@Override
	public String getShipType(){
		return shipType;
	}
}
