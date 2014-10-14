package se300.destinytactics.orbitalbodies;

import se300.destinytactics.mapgen.OrbitalBody;
import se300.destinytactics.mapgen.Sector;
import se300.destinytactics.orbitalbodies.interfaces.canBuildDefense;
import se300.destinytactics.orbitalbodies.interfaces.canBuildFleets;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:18 PM
 */
public class Station extends OrbitalBody implements canBuildFleets, canBuildDefense {

	private Structure structure[];
	public Structure m_Structure;

	public Station(int radius, Sector sector){
		super(radius,sector);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public void getLevel(){

	}

	public void incrementLevel(){

	}

}//end Station