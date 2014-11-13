package se300.destinytactics.game.orbitalbodies;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Ship;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:18 PM
 * 
 * Station extends OrbitalBody implements canBuildFleets, canBuildDefense
 * Station cannot harvest resources.
 */
public class Station extends OrbitalBody implements canBuildFleets,
		canBuildDefense {

	private Structure structure[];
	public Structure m_Structure;

	// Mining Variable declaration
	private int mineLevel;
	private float resourceMultiplier;
	private int mineCost;
	private int resourcePerTurn = 0;

	// Shipyard variable declaration
	private int shipyardLevel;
	private int shipyardSize;
	private int shipyardCost;
	public ArrayList<Ship> buildQueue = new ArrayList<Ship>();

	/**
	 * Constructor uses parent radius and sector
	 * @param radius
	 * @param sector
	 */
	public Station(int radius, Sector sector) {
		super(radius, sector);

		// Mining variable init
		mineLevel = 0;
		mineCost = 25;
		resourcePerTurn = (int) (100 * resourceMultiplier);

		// shipyard variable init
		shipyardLevel = 0;
		shipyardSize = 1;
		shipyardCost = 25;

		type = Utility.random.nextInt(2) + 10;
		this.setY((GameScene.SCREEN_HEIGHT / 5)
				+ (int) (Utility.random.nextInt(YEDGEEXCLUSION
						- GameScene.SCREEN_HEIGHT / 5) + 1));
		this.setX(XEDGEEXCLUSION - 150 * orbitRadius);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public int getShipyardLevel() {
		return shipyardLevel;
	}

	@Override
	public int getShipyardSize() {
		return shipyardSize;
	}

	@Override
	public void mineLevelUp() {

	}

	@Override
	public Integer getMineLevel() {
		return mineLevel;
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getMineCost() {
		return mineCost;
	}

	@Override
	public Integer getRPT() {
		return resourcePerTurn;
	}

	@Override
	public void shipyardLevelUp() {

	}

	@Override
	public int getShipyardCost() {
		return shipyardCost;
	}

	@Override
	public void addToQueue(Ship ship) {

	}

	@Override
	public void building() {

	}

	@Override
	public void toFleet(Ship ship) {

	}

	@Override
	public int getBuildQueueSize() {
		return buildQueue.size();
	}

	@Override
	public int getShips(int x) {
		return 0;
	}

}// end Station