package se300.destinytactics.mapgen;
import java.awt.Rectangle;
import java.util.ArrayList;

import se300.destinytactics.ui.Drawable;

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

	public Galaxy(int x, int y, int n){
		
		
		numSystems = n;
		sizeX = x;
		sizeY = y;
		sectors = new Sector[numSystems];
		map = new Quadtree(new Rectangle(0, 0, sizeX, sizeY));
		name = Names.newName();
		Sector.galaxy = this;
	
		for (int i = 0; i < numSystems; i++) {
			sectors[i] = new Sector();
			map.insert(sectors[i]);
		}


	}

	public int getGalaxyWidth(){
		
		return sizeX;
	
	}
	public int getGalaxyHeight(){
		
		return sizeY;
	
	}
	
	public String getName(){
		return name;
	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param screenRect
	 */
	public ArrayList<Drawable> getSectorsOnScreen(Rectangle screenRect){
		return null;
	}
}//end Galaxy