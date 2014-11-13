package se300.destinytactics.game.mapgen;

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
	private int numSystems;
	public Sector m_Sector;
	public GameScene thisgame;
	public int minSpacing = 75;

	/**
	 * Generate a new galaxy.
	 * @param x
	 * @param y
	 * @param n
	 * @param thisgame
	 */
	public Galaxy(int x, int y, int n, GameScene thisgame) {

		this.thisgame = thisgame;
		numSystems = n;
		sizeX = x;
		sizeY = y;

		sectors = new Sector[numSystems];
		name = Names.newName();

		for (int i = 0; i < numSystems; i++) {
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