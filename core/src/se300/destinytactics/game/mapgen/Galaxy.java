package se300.destinytactics.game.mapgen;

import se300.destinytactics.GalaxyListItem;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;

/**
 * 
* <h1>Galaxy Object</h1>
* Creating a galaxy object generates a world for the game to use.
* 
 * @author Chris
* @version 1.0
* @since   2014-11-12
 *
 */
public class Galaxy {

	private String name;
	public Sector sectors[];
	private int sizeY;
	private int sizeX;
	private int sectorCount;
	public Sector m_Sector;
	public GameScene thisgame;
	public int minSpacing = 75;

	/**
	 * Generate a new galaxy.
	 * @param sizeX
	 * @param sizeY
	 * @param sectorCount
	 * @param thisgame
	 */
	public Galaxy(int sizeX, int sizeY, int sectorCount, GameScene thisgame) {

		this.thisgame = thisgame;
		this.sectorCount = sectorCount;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		sectors = new Sector[this.sectorCount];
		name = Names.newName();

		for (int i = 0; i < this.sectorCount; i++) {
			boolean placeFree = false;
			Sector temp = null;
			while (!placeFree) {

				temp = new Sector(this);

				placeFree = true;
				//Generate all sectors and place them in an array.
				for (int j = 0; j < sectors.length; j++) {
					if (sectors[j] == null)
						break;
					if (temp.getDistance(sectors[j]) < minSpacing) {
						placeFree = false;
						break;
					}
				}

			}
			sectors[i] = temp;
		}
	}
	
	/**
	 * Generate a new galaxy.
	 * @param sizeX
	 * @param sizeY
	 * @param galaxy
	 * @param thisgame
	 */
	public Galaxy(int sizeX, int sizeY, GalaxyListItem galaxy, GameScene thisgame) {

		this.thisgame = thisgame;
		this.sectorCount = galaxy.getSectors();
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		sectors = new Sector[this.sectorCount];
		name = Names.newName(); //Need to use the name generated from the seed or everything is one random number off...
								//Hours spent finding this bug : 1.0  (Shannon add your hours to this)
		
		for (int i = 0; i < this.sectorCount; i++) {
			boolean placeFree = false;
			Sector temp = null;
			while (!placeFree) {

				temp = new Sector(this);

				placeFree = true;
				//Generate all sectors and place them in an array.
				for (int j = 0; j < sectors.length; j++) {
					if (sectors[j] == null)
						break;
					if (temp.getDistance(sectors[j]) < minSpacing) {
						placeFree = false;
						break;
					}
				}

			}
			sectors[i] = temp;
		}
	}
	
	public int getGalaxyWidth() {

		return sizeX;

	}

	public int getGalaxyHeight() {

		return sizeY;

	}

	public String getName() {
		return name;
	}

	public void finalize() throws Throwable {

	}
}// end Galaxy