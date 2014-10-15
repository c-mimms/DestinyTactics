package se300.destinytactics.orbitalbodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se300.destinytactics.mapgen.OrbitalBody;
import se300.destinytactics.mapgen.Sector;
import se300.destinytactics.orbitalbodies.interfaces.canBuildDefense;
import se300.destinytactics.orbitalbodies.interfaces.canBuildFleets;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:18 PM
 */
public class Station extends OrbitalBody implements canBuildFleets, canBuildDefense {

	private Structure structure[];
	public Structure m_Structure;

	public Station(int radius, Sector sector){
		super(radius,sector);
		sprite =  new Texture("realorbitalbody/station1.png");
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public void getLevel(){

	}

	public void incrementLevel(){

	}
	public void drawImage(SpriteBatch batch, float zoomLevel) {
		batch.draw(sprite,sector.getXPos()-25*orbitRadius,sector.getYPos()-25*zoomLevel,200*zoomLevel,200*zoomLevel,0,0,200,200,false,false);		
	}

}//end Station