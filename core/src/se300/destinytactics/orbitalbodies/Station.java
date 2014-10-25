package se300.destinytactics.orbitalbodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se300.destinytactics.logic.Utility;
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
		
		type = Utility.random.nextInt(2)+10;
		System.out.println(type);
		this.setY(Utility.random.nextInt(YEDGEEXCLUSION));
		this.setX(XEDGEEXCLUSION-150*orbitRadius);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public void getLevel(){

	}

	public void incrementLevel(){

	}
	

	@Override
	public void getMiningEfficiency() {
		// TODO Auto-generated method stub
		
	}


	

}//end Station