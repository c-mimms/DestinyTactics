package se300.destinytactics.game.orbitalbodies;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Ship;
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
	
	//Shipyard variable declaration
	private int shipyardLevel;
	private int shipyardSize;
	private int shipyardCost;
	private ArrayList<Ship> buildQueue = new ArrayList<Ship>();

	public Planet(int radius, Sector sector){

		super(radius,sector);
		owner = super.owner;
		
		//Mining variable init
		mineLevel = 0;
		mineCost = 25;
		resourcePerTurn = (int) (100 * resourceMultiplier);
		
		//shipyard variable init
		shipyardLevel = 0;
		shipyardSize = 1;
		shipyardCost = 25;

		
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
	
	//Act method
	public void act(float time){
		super.act(time);
		
	}
	
	public void endTurn(){
		if(controlState == 1 && owner != null){
			//System.out.println("Resources: " + resource + "  Efficiency  : " + miningEfficiency);
			owner.addResource(resourcePerTurn); //Basically the get resource method
			spentTurn = false;
			building();
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
			mineCost = (int)(mineCost + mineCost * (float) Math.pow(1.05, mineLevel - 1)); // increase mine
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
		return mineLevel;
	}

	/**
	 * Gets mine cost
	 * 
	 * @return mineCost
	 */
	public Integer getMineCost() {
		return mineCost;
	}
	
	@Override
	public Integer getRPT() {
		return resourcePerTurn;
	}

	/*
	 * Shipyard stuff
	 */
	@Override
	public int getShipyardLevel() {
		return shipyardLevel;
	}
	
	@Override
	public int getShipyardSize() {
		return shipyardSize;
	}
	
	public int getShipyardCost() {
		return shipyardCost;
	}

	@Override
	public void shipyardLevelUp() {
		if(shipyardCost < owner.getResource()) {
			++shipyardLevel;
			owner.spendResource(shipyardCost);
			shipyardCost = (int)(shipyardCost + shipyardCost * (float) Math.pow(1.05, shipyardLevel - 1)); // increase cost
			shipyardSize = shipyardLevel * shipyardLevel;
		} else {
			System.out.println("Not enough resources to upgrade shipyard");
		}
	}
	
	/**
	 * This method takes input from the
	 * @param ship
	 */
	public void addToQueue(Ship ship) {
		buildQueue.add(ship);
	}
	
	/**
	 * Keeps track of turns until completion, removes them when done
	 */
	public void building() {
		for(int i = 0; i < buildQueue.size(); i++){
			buildQueue.get(i).decrementBuildTime();
			if(buildQueue.get(i).getBuildTime() <= 0) {
				System.out.println(buildQueue.get(i).getShipType() + " Built");
				toFleet(buildQueue.get(i));
			}
		}
	}
	
	/**
	 * Take ships out of the build queue, puts them in orbit and returns space to shipyard
	 * @param ship
	 */
	public void toFleet(Ship ship) {
		System.out.println("Ship to fleet");
		buildQueue.remove(ship);
	}



}//end Planet