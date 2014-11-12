package se300.destinytactics.game.orbitalbodies;

import se300.destinytactics.game.fleet.Ship;
import se300.destinytactics.game.mapgen.Sector;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:08 PM
 */
public class MinorStar extends OrbitalBody {

	public MinorStar(int radius, Sector sector){
		super(radius,sector);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}


	@Override
	public void mineLevelUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getMineLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getMineCost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRPT() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shipyardLevelUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getShipyardLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getShipyardSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getShipyardCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addToQueue(Ship ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void building() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toFleet(Ship ship) {
		// TODO Auto-generated method stub
		
	}

}//end Minor Star