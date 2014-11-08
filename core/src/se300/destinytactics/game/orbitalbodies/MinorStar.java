package se300.destinytactics.game.orbitalbodies;

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
	public void getMiningEfficiency() {
		// TODO Auto-generated method stub
		
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

}//end Minor Star