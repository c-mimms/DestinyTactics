package se300.destinytactics.mapgen;

import se300.destinytactics.orbitalbodies.Fleet;
import se300.destinytactics.ui.Button;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public class OrbitalBody extends Quadtree {

	private int controlState;
	private Fleet fleet;
	private int miningEfficiency = 0;
	private String name;
	private int orbitRadius;
	private Sector sector;
	public Fleet m_Fleet;
	public Button m_Button;

	public OrbitalBody(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public void click(){

	}

	/**
	 * 
	 * @param body
	 */
	public int getDistance(OrbitingBody body){
		return 0;
	}

	public String getName(){
		return "";
	}

	public Point getPos(){
		return null;
	}

	public int getState(){
		return 0;
	}
}//end OrbitalBody