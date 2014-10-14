package se300.destinytactics.mapgen;


import java.awt.Rectangle;
import java.util.ArrayList;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:13 PM
 */
public class Quadtree {

	private int MAX_OBJECTS = 4;
	public ArrayList<Sector> sectors;
	public int level;
	public Quadtree nodes[];
	private Rectangle bounds;

	public Quadtree(){

	}

	public Quadtree(Rectangle bounds) {
		level = 0;
		sectors = new ArrayList<Sector>();
		this.bounds = bounds;
		nodes = new Quadtree[4];
	}

	/*
	 * Constructor
	 */
	public Quadtree(int pLevel, Rectangle pBounds) {
		level = pLevel;
		sectors = new ArrayList<Sector>();
		bounds = pBounds;
		nodes = new Quadtree[4];
	}
	public void finalize() throws Throwable {

	}

	public void clear() {
		sectors.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	
	private int getIndex(Sector newObj) {
		int index = -1;
		if (sectors.size() < MAX_OBJECTS)
			return index;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		// Object can completely fit within the top quadrants
		boolean topQuadrant = (newObj.getYPos() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (newObj.getYPos() > horizontalMidpoint);

		// Object can completely fit within the left quadrants
		if (newObj.getXPos() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else if (bottomQuadrant) {
				index = 2;
			}
		}
		// Object can completely fit within the right quadrants
		else if (newObj.getXPos() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else if (bottomQuadrant) {
				index = 3;
			}
		}
		return index;
	}
	
	private int getIndex(Rectangle pRect) {
		int index = -1;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		// Object can completely fit within the top quadrants
		boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect
				.getY() + pRect.getHeight() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

		// Object can completely fit within the left quadrants
		if (pRect.getX() < verticalMidpoint
				&& pRect.getX() + pRect.getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else if (bottomQuadrant) {
				index = 2;
			}
		}
		// Object can completely fit within the right quadrants
		else if (pRect.getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else if (bottomQuadrant) {
				index = 3;
			}
		}

		return index;
	}

	public void insert(Sector newObj) {

		if (nodes[0] != null) {
			int index = getIndex(newObj);

			if (index != -1) {
				nodes[index].insert(newObj);

				return;
			}
		}

		sectors.add(newObj);

		// Insert objects into next level deeper
		if (sectors.size() > MAX_OBJECTS) {
			if (nodes[0] == null) {
				split();
			}

			int i = 0;
			while (i < sectors.size()) {
				int index = getIndex(sectors.get(i));
				if (index != -1) {
					nodes[index].insert(sectors.remove(i));
				} else {
					i++;
				}
			}
		}
	}
	

	public ArrayList<Sector> retrieve(ArrayList<Sector> temp, Rectangle pRect) {
		int index = getIndex(pRect);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(temp, pRect);
		}
		else if(index == -1){
			for(int i = 0; i <4; i++){
				if(nodes[i] !=null){
					nodes[i].retrieve(temp,pRect);
				}
			}
		}

		temp.addAll(sectors);

		return temp;
	}


	private void split() {
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();

		nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y,
				subWidth, subHeight));
		nodes[1] = new Quadtree(level + 1, new Rectangle(x, y, subWidth,
				subHeight));
		nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight,
				subWidth, subHeight));
		nodes[3] = new Quadtree(level + 1, new Rectangle(x + subWidth, y
				+ subHeight, subWidth, subHeight));
	}

}//end Quadtree