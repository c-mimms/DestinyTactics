package se300.destinytactics.game.orbitalbodies;

import java.util.ArrayList;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.fleet.Ship;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:11 PM
 * 
 *          Planet extends OrbitalBody implements canBuildFleets,
 *          canBuildDefense
 * 
 *          Planets have mines and shipyards.
 */
public class Planet extends OrbitalBody implements canBuildFleets,
		canBuildDefense {

	// private int miningEfficiency2;
	// private int miningEfficiency1;
	//private Structure structure[];
	public Structure m_Structure;

	// Owner
	public Player owner;

	// Mining Variable declaration
	private int mineLevel;
	private float resourceMultiplier;
	private int mineCost;
	private int resourcePerTurn;

	// Shipyard variable declaration
	private int shipyardLevel;
	private int shipyardSize;
	private int shipyardCost;
	public ArrayList<Ship> buildQueue = new ArrayList<Ship>();

	public int ships[] = new int[7];

	/**
	 * Constructor uses parent radius and sector
	 * 
	 * @param radius
	 * @param sector
	 */
	public Planet(int radius, Sector sector) {

		super(radius, sector);
		owner = super.owner;

		// Mining variable init
		mineLevel = 0;
		mineCost = 25;
		resourcePerTurn = (int) (100 * resourceMultiplier);

		// shipyard variable init
		shipyardLevel = 0;
		shipyardSize = 4000;
		shipyardCost = 25;

		type = Utility.random.nextInt(8) + 2; // Use only planet images

		setY((GameScene.SCREEN_HEIGHT / 5)
				+ (int) (Utility.random.nextInt(YEDGEEXCLUSION
						- GameScene.SCREEN_HEIGHT / 5) + 1));
		setX(XEDGEEXCLUSION - 150 * orbitRadius);
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public int getType() {
		return type;
	}

	// Act method
	public void act(float time) {
		super.act(time);

	}

	@Override
	public void endTurn() {
		if (controlState == 1 && owner != null) {
			// System.out.println("Resources: " + resource + "  Efficiency  : "
			// + miningEfficiency);
			buildQueue.trimToSize();
			owner.addResource(resourcePerTurn); // Basically the get resource
												// method
			spentTurn = false;
			building();
		}
	}

	// Methods below relate to mining and resource acquisition
	@Override
	public void mineLevelUp() {
		if (mineCost < owner.getResource()) {
			++mineLevel;
			owner.spendResource(mineCost); // Spend resources on mine
			mineCost = (int) (mineCost + mineCost
					* (float) Math.pow(1.05, mineLevel - 1)); // increase mine
			resourceMultiplier = mineLevel * (float) Math.pow(1.1, mineLevel);
			resourcePerTurn = (int) (resourceMultiplier * 100);
			System.out.println("Mine Level " + mineLevel + " Mine Cost: "
					+ mineCost + " Resource Multiplier: " + resourceMultiplier
					+ " Resources per Turn: " + resourcePerTurn);
		} else {
			System.out.println("Not enough resources");
		}
	}

	@Override
	public Integer getMineLevel() {
		return mineLevel;
	}

	@Override
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

	@Override
	public int getShipyardCost() {
		return shipyardCost;
	}

	@Override
	public void shipyardLevelUp() {
		if (shipyardCost < owner.getResource()) {
			++shipyardLevel;
			owner.spendResource(shipyardCost);
			shipyardCost = (int) (shipyardCost + shipyardCost
					* (float) Math.pow(1.05, shipyardLevel - 1)); // increase
																	// cost
			shipyardSize = shipyardLevel * shipyardLevel;
		} else {
			System.out.println("Not enough resources to upgrade shipyard");
		}
	}

	@Override
	public void addToQueue(Ship ship) {
		if (ship.getSpaceToBuild() < shipyardSize) {
			buildQueue.add(ship);
			addShipOrdered(ship);
			shipyardSize -= ship.getSpaceToBuild();
		} else {
			System.out.println("Shipyard Full");
		}
	}

	@Override
	public void building() {
		ArrayList<Ship> removeQueue = new ArrayList<Ship>();
		for (Ship ship : buildQueue) {
			ship.decrementBuildTime();
			//System.out.println(ship.getBuildTime());
			if (ship.getBuildTime() <= 0) {
				System.out.println(ship.getShipType() + " Built");
				toFleet(ship);
				removeQueue.add(ship);
			}
		}
		for (Ship ship : removeQueue) {
			buildQueue.remove(ship);
			if (ship.getShipType() == "Fighter") {
				ships[0] = ships[0] - 1;
			} else if (ship.getShipType() == "Corvette") {
				ships[1] -= 1;
			} else if (ship.getShipType() == "Bomber") {
				ships[2] -= 1;
			} else if (ship.getShipType() == "Carrier") {
				ships[3] -= 1;
			} else if (ship.getShipType() == "Scout") {
				ships[4] -= 1;
			} else if (ship.getShipType() == "Battleship") {
				ships[5] -= 1;
			} else if (ship.getShipType() == "Dreadnaught") {
				ships[6] -= 1;
			}
		}
	}

	@Override
	public void toFleet(Ship ship) {
		System.out.println("Ship to fleet");
		if (this.m_Fleet == null) {
			m_Fleet = new Fleet(this);
		}
		m_Fleet.addShip(ship);

		shipyardSize += ship.getSpaceToBuild();
	}

	@Override
	public int getBuildQueueSize() {
		return buildQueue.size();
	}

	/**
	 * Records number of each type of ship in queue
	 */
	public void addShipOrdered(Ship ship) {
		if (ship.getShipType() == "Fighter") {
			ships[0] = ships[0] + 1;
		} else if (ship.getShipType() == "Corvette") {
			ships[1] += 1;
		} else if (ship.getShipType() == "Bomber") {
			ships[2] += 1;
		} else if (ship.getShipType() == "Carrier") {
			ships[3] += 1;
		} else if (ship.getShipType() == "Scout") {
			ships[4] += 1;
		} else if (ship.getShipType() == "Battleship") {
			ships[5] += 1;
		} else if (ship.getShipType() == "Dreadnaught") {
			ships[6] += 1;
		}
	}

	@Override
	public int getShips(int x) {
		return ships[x];
	}

}// end Planet