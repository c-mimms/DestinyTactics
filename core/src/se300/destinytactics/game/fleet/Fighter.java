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
	private String shipType = "Fighter";

	public static ShipJSON stats = new ShipJSON();
	
	public Fighter(){
		
		
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
		return 0;
	}
	
	@Override
	public String getShipType(){
		return shipType;
	}
	
}//end Fighter