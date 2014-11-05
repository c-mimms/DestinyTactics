package se300.destinytactics.game.mapgen;

import java.awt.Rectangle;
import java.util.ArrayList;

import se300.destinytactics.GameScene;
import se300.destinytactics.ui.Drawable;
import se300.destinytactics.game.mapgen.Sector;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:02 PM
 */
public class Galaxy {

	private Quadtree map;
	private String name;
	public Sector sectors[];
	private int sizeY;
	private int sizeX;
	private int numSystems;
	public Sector m_Sector;
	public Quadtree m_Quadtree;
	public GameScene thisgame;
	public int minSpacing = 75;

	public Galaxy(int x, int y, int n, GameScene thisgame) {

		this.thisgame = thisgame;
		numSystems = n;
		sizeX = x;
		sizeY = y;

		sectors = new Sector[numSystems];
		name = Names.newName();
		// Sector.setGalaxy(this);

		for (int i = 0; i < numSystems; i++) {
			boolean placeFree = false;
			Sector temp = null;
			while (!placeFree) {

				temp = new Sector(this);

				placeFree = true;
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

	/**
	 * 
	 * @param screenRect
	 */
	public ArrayList<Drawable> getSectorsOnScreen(Rectangle screenRect) {
		return null;
	}
}// end Galaxy