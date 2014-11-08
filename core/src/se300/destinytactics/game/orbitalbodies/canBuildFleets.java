package se300.destinytactics.game.orbitalbodies;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:53 PM
 */
public interface canBuildFleets {

	public static int shipyardLevel = 0;
	public static int shipyardSize = 0;

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
	public void incrementLevel();

}