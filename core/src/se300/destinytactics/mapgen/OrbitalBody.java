package se300.destinytactics.mapgen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.Drawable;
import se300.destinytactics.units.Fleet;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public abstract class OrbitalBody extends Actor {

	private int controlState;
	private Fleet fleet;
	private int miningEfficiency = 0;
	private String name;
	protected int orbitRadius;
	protected Sector sector;
	public Fleet m_Fleet;
	public Button m_Button;
	
	//Import all planet textures
	static Texture[] hotBod = new Texture[12];
	

	

	public OrbitalBody(int radius, Sector sect){

		orbitRadius = radius;
		name = Names.newName();
		sector = sect;
		controlState = 0;

		hotBod[0] = new Texture("realorbitalbody/activatedgate.png");
		hotBod[1] = new Texture("realorbitalbody/deactivatedgate.png");
		hotBod[2] = new Texture("realorbitalbody/gasplanet1.png");
		hotBod[3] = new Texture("realorbitalbody/gasplanet2.png");
		hotBod[4] = new Texture("realorbitalbody/lifeplanet1.png");
		hotBod[5] = new Texture("realorbitalbody/lifeplanet2.png");
		hotBod[6] = new Texture("realorbitalbody/moon1.png");
		hotBod[7] = new Texture("realorbitalbody/moon2.png");
		hotBod[8] = new Texture("realorbitalbody/rockplanet1.png");
		hotBod[9] = new Texture("realorbitalbody/rockplanet2.png");
		hotBod[10] = new Texture("realorbitalbody/station1.png");
		hotBod[11] = new Texture("realorbitalbody/station2.png");
		
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
	
	public abstract void getMiningEfficiency();
	
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