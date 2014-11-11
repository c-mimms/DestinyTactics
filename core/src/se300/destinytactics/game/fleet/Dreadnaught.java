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
	private String shipType = "Dreadnaught";

	public Dreadnaught(){
		buildTime = 7;
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
		return shipType;
	}
	
}//end Dreadnaught