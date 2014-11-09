package se300.destinytactics.game.orbitalbodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;


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
		this.setY((GameScene.SCREEN_HEIGHT/5) + (int)(Utility.random.nextInt(YEDGEEXCLUSION-GameScene.SCREEN_HEIGHT/5) +1));
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

	@Override
	public int getShipyardLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getShipyardSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void mineLevelUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getMineLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}


	

}//end Station