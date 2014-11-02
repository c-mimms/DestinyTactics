package se300.destinytactics.game.orbitalbodies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:11 PM
 */
public class Planet extends OrbitalBody implements canBuildFleets, canBuildDefense {

	private int miningEfficiency2;
	private int miningEfficiency1;
	private Structure structure[];
	public Structure m_Structure;

	public Planet(int radius, Sector sector){
		super(radius,sector);
		miningEfficiency1 = 0;
		miningEfficiency2 = 0;
		resource = (int) (Math.random()*1000);
		resource2 = (int) (Math.random()*1000);
		
		type = Utility.random.nextInt(8)+2; //Use only planet images
		this.setY((GameScene.SCREEN_HEIGHT/5) + (int)(Utility.random.nextInt(YEDGEEXCLUSION-GameScene.SCREEN_HEIGHT/5) +1));
		this.setX(XEDGEEXCLUSION-150*orbitRadius);
		
		
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	public int getType(){
		return type;
	}

	public void incrementMining(){
		miningEfficiency1 ++;
	}

	public void getLevel(){
		
	}

	public void incrementLevel(){
		
	}

	@Override
	public void getMiningEfficiency() {
		// TODO Auto-generated method stub
		
	}

}//end Planet