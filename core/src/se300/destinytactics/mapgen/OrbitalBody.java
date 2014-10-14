package se300.destinytactics.mapgen;

import com.badlogic.gdx.graphics.Texture;

import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.Drawable;
import se300.destinytactics.units.Fleet;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public class OrbitalBody extends Drawable {

	private int controlState;
	private Fleet fleet;
	private int miningEfficiency = 0;
	private String name;
	private int orbitRadius;
	private Sector sector;
	public Fleet m_Fleet;
	public Button m_Button;

	public OrbitalBody(int radius, Sector sect){

		orbitRadius = radius;
		name = Names.newName();
		sector = sect;
		controlState = 0;
		
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
	public int getDistance(OrbitalBody body){
		int bodyPos = sector.getNumBodies() - orbitRadius;
		int otherBodyPos = body.getSector().getNumBodies() - body.getPos();
		int sectorDist = sector.getDistance(body.getSector());
		return bodyPos + otherBodyPos + sectorDist;
	}

	public String getName(){
		return name;
	}
	
	public int getMiningEfficiency(){
		return miningEfficiency;
	}
	
	public Fleet getFleet(){
		return fleet;
	}
	
	public Sector getSector(){
		return sector;
	}

	public int getPos(){
		return orbitRadius;
	}

	public int getState(){
		return controlState;
	}
}//end OrbitalBody