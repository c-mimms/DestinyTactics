package se300.destinytactics.game.orbitalbodies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:11 PM
 */
public class Planet extends OrbitalBody implements canBuildFleets, canBuildDefense {

	//private int miningEfficiency2;
	//private int miningEfficiency1;
	private Structure structure[];
	public Structure m_Structure;
	
	//Owner
	public Player owner;
	
	//Mining Variable declaration
	private int mineLevel;
	private float resourceMultiplier;
	private int mineCost; 
	private int resourcePerTurn;

	public Planet(int radius, Sector sector){

		super(radius,sector);
		owner = super.owner;
		//Mining variable init
		mineLevel = 0;
		mineCost = 25;
		resourcePerTurn = (int) (100 * resourceMultiplier);

		
		type = Utility.random.nextInt(8)+2; //Use only planet images

		setY((GameScene.SCREEN_HEIGHT/5) + (int)(Utility.random.nextInt(YEDGEEXCLUSION-GameScene.SCREEN_HEIGHT/5) +1));
		setX(XEDGEEXCLUSION-150*orbitRadius);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	public int getType(){
		return type;
	}


	public void getLevel(){
		
	}

	public void incrementLevel(){
		
	}
	
	//Act method
	public void act(float time){
		super.act(time);
		
	}
	
	public void endTurn(){
		if(controlState == 1 && owner != null){
			//System.out.println("Resources: " + resource + "  Efficiency  : " + miningEfficiency);
			owner.addResource(resourcePerTurn); //Basically the get resource method
		}
	}


	
	//Methods below relate to mining and resource acquisition
	/**
	 * Mining level increased by 1. Resources used. Mine cost increases.
	 * Resource multiplier increases.
	 * 
	 * @param
	 * @return
	 */
	
	@Override
	public void mineLevelUp() {
		if (mineCost < owner.getResource()) {
			++mineLevel;
			owner.spendResource(mineCost); // Spend resources on mine
			mineCost = (int)(mineCost + mineCost * (float) Math.pow(1.05, mineLevel - 1)); // increase// mine
			resourceMultiplier = mineLevel * (float) Math.pow(1.1, mineLevel);
			resourcePerTurn = (int)(resourceMultiplier * 100);
			System.out.println("Mine Level " + mineLevel + " Mine Cost: " + mineCost + " Resource Multiplier: " + 
					resourceMultiplier + " Resources per Turn: " + resourcePerTurn);
		} else {
			System.out.println("Not enough resources");
		}
	}

	/**
	 * Gets mine level
	 * 
	 * @return mineLevel
	 */
	public Integer getMineLevel() {
		return (Integer)mineLevel;
	}

	/**
	 * Gets mine cost
	 * 
	 * @return mineCost
	 */
	public int getMineCost() {
		return mineCost;
	}

	// Shipyard

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
	public void getMiningEfficiency() {
		// TODO Auto-generated method stub

	}

}//end Planet