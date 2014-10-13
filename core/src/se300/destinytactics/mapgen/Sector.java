package se300.destinytactics.mapgen;

import java.awt.Point;

import se300.destinytactics.planets.Planet;
import se300.destinytactics.planets.Station;
import se300.destinytactics.ui.Button;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:15 PM
 */
public class Sector extends Quadtree {

	public static Galaxy galaxy;
	private int controlState;
	private int numBodies;
	private String name;
	private OrbitalBody bodyList[];
	private int posX;
	private int posY;
	public OrbitalBody m_OrbitalBody;
	public Button m_Button;

	public Sector() {

		numBodies = (int)  (Math.random()* 15) + 1;
		bodyList = new OrbitalBody[numBodies];
		posX = (int) (Math.random() * galaxy.getGalaxyWidth());
		posY = (int) (Math.random() * galaxy.getGalaxyHeight());

		double stationChance = 0.2;
		for (int i = 0; i < numBodies; i++) {
			if (stationChance > Math.random()) {
				bodyList[i] = new Station();
				stationChance /= 10;
			} else {
				bodyList[i] = new Planet();
			}
		}
		name = Names.newName();

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void click() {

	}

	public String getName() {
		return name;
	}

	public Point getPos() {
		return new Point(posX, posY);
	}
	
	public int getXPos() {
		return posX;
	}

	public int getYPos() {
		return posY;
	}
	
	
	public int getState() {
		return 0;
	}
}// end Sector