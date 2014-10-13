package se300.destinytactics.units;

import se300.destinytactics.planets.hasHangar;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:54 PM
 */
public class Carrier extends Ship implements hasHangar {

	private int baysEmpty;
	private int combatRating = 10;
	private int galacticTravelSpeed = 1;
	private int hangarSize = 50;
	private int sectorTravelSpeed = 1;
	private String shipType = "Carrier";

	public Carrier(){

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
}//end Carrier