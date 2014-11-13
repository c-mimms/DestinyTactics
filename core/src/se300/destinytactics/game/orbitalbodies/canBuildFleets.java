package se300.destinytactics.game.orbitalbodies;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:53 PM
 * 
 * Interface allows OrbitalBody to build fleets. Currently not used.
 */
public interface canBuildFleets {

	/**
	 * Gets shipyard level
	 * @return shipyardLevel
	 */
	public int getShipyardLevel();
	
	/**
	 * Gets shipyard size
	 * @return shipyardSize
	 */
	public int getShipyardSize();

	/**
	 * Shipyard level increases by 1. Resources are used. Shipyard cost increases. Shipyard size increases.
	 */
	public void shipyardLevelUp();

}