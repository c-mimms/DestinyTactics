package se300.destinytactics.game.fleet;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.game.Player;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.scenes.GalaxyScene;

/**
 * <h1>Fleet</h1>
 * 
 * Fleet in the current game.
 *
 * @author Chris Mimms
 * @version 1.0
 * @since 2014-11-12
 * 
 */
public class Fleet extends Actor {

	private final int ORBITRAD = 20;

	private OrbitalBody destination;
	private int distanceToDestination;
	private int distance;
	private int fleetGalacticTravelSpeed = 50;
	private int fleetSectorTravelSpeed;
	private OrbitalBody location;
	private Player playerAssignment;
	public Ship m_Ship;
	public float percentTravelled = 0f;
	private Sector sectorDestination;
	private Sector sectorLocation;
	private static ShapeRenderer renderer = new ShapeRenderer();

	// TODO Replace with read fleet sprite
	private Texture sprite1 = new Texture(
			Gdx.files.internal("realorbitalbody/SectorIcon.png"));

	private int SPRITE_SIZE = 10;
	private float angle = 0;
	private Map<String, Integer> shipMap;

	/**
	 * Create fleet with orbital body location.
	 * 
	 * @param loc
	 */
	public Fleet(OrbitalBody loc) {
		super();
		setLocation(loc);
		shipMap = new HashMap<String, Integer>();
		this.setSize(10, 10);

		this.setBounds(0, 0, loc.getWidth() + 2 * ORBITRAD, loc.getHeight() + 2
				* ORBITRAD);
		// System.out.println("New fleet created at " + location.getName());

		this.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				System.out.println("Clicked!");
				return true;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				// System.out.println("Exit at " + x + ", " + y);
			}

		});

	}

	public void finalize() throws Throwable {

	}

	public OrbitalBody getDestination() {
		return destination;
	}

	public int getFleetGalacticTravelSpeed() {
		return fleetGalacticTravelSpeed;
	}

	public int getFleetSectorTravelSpeed() {
		return fleetSectorTravelSpeed;
	}

	public OrbitalBody getLocation() {
		return location;
	}

	/**
	 * Return player that owns fleet
	 * 
	 * @return
	 */
	public Player getPlayerAssignment() {
		return playerAssignment;
	}

	/**
	 * Set fleet owner
	 * 
	 * @param player
	 */
	public void setPlayerAssignment(Player player) {
		playerAssignment = player;
	}

	/**
	 * Get number of ships in fleet
	 * 
	 * @param unitType
	 */
	public int getShipCount(String unitType) {
		if (shipMap.containsKey(unitType)) {
			return shipMap.get(unitType).intValue();
		}
		return 0;

	}

	/**
	 * Adds ship to the fleet
	 * 
	 * @param ship
	 */
	public void addShip(Ship ship) {
		if (shipMap.containsKey(ship.getShipType())) {
			shipMap.put(ship.getShipType(), shipMap.get(ship.getShipType()) + 1);
		} else {
			shipMap.put(ship.getShipType(), 1);
		}
	}

	/**
	 * Moves fleet closer to destination. If it reaches destination, set new
	 * location and clear destination. Otherwise, updates percentage complete to
	 * draw on galaxy map
	 * 
	 * @param location
	 */
	public void moveFleet() {
		// If it has a destination
		if (this.sectorDestination != null) {
			this.distanceToDestination -= this.fleetGalacticTravelSpeed;
			if (distanceToDestination <= 0) {
				// System.out.println("Loc: " + location.getName()
				// + "\n Destination: " + destination.getName());
				this.location = destination;
				location.m_Fleet = this;
				sectorLocation = sectorDestination;
				distanceToDestination = 0;
				this.destination = null;
				sectorDestination = null;
				percentTravelled = 0f;
				this.setX(sectorLocation.getX()
						+ (sectorLocation.getWidth() / 2) - (getWidth() / 2));
				this.setY(sectorLocation.getY()
						+ (sectorLocation.getHeight() / 2) - (getHeight() / 2));

			}

			else {
				percentTravelled = (float) distanceToDestination
						/ (float) distance;
				float difx = sectorLocation.getX() - sectorDestination.getX();
				float dify = sectorLocation.getY() - sectorDestination.getY();
				// TODO make everything have a getCenterX() and setCenter() so
				// we don't have to do this...
				this.setX(sectorDestination.getX(Align.center)
						- (getWidth() / 2) + (difx * percentTravelled));
				this.setY(sectorDestination.getY(Align.center)
						- (getHeight() / 2) + (dify * percentTravelled));
			}
		}

	}

	/**
	 * Set location of the fleet
	 * 
	 * @param location
	 */
	public void setLocation(OrbitalBody loc) {
		this.location = loc;
		sectorLocation = location.getSector();
		location.m_Fleet = this;
	}

	/**
	 * Sets destination Orbiting Body and sector and updates distance to the
	 * destination
	 * 
	 * @param dest
	 */
	public void setDestination(OrbitalBody dest) {
		this.destination = dest;
		sectorDestination = destination.getSector();
		distance = distanceToDestination = location.getDistance(destination);
		location.m_Fleet = null;
		this.location = null;
		this.setX(this.sectorLocation.getX(Align.center));
		this.setY(this.sectorLocation.getY(Align.center));
		System.out.println("Destination!" + dest.getName() +distance);

	}

	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(this.getColor());

		if (this.getStage().getClass() == GalaxyScene.class) {

			if (destination != null) {
				batch.draw(sprite1, this.getX(), this.getY(), SPRITE_SIZE,
						SPRITE_SIZE);
			}
			batch.end();

			renderer.setProjectionMatrix(batch.getProjectionMatrix());
			renderer.setTransformMatrix(batch.getTransformMatrix());

			renderer.begin(ShapeType.Line);
			renderer.setColor(Color.WHITE);
			renderer.line(sectorLocation.getX()
					+ (sectorLocation.getWidth() / 2), sectorLocation.getY()
					+ (sectorLocation.getHeight() / 2),
					sectorDestination.getX()
							+ (sectorDestination.getWidth() / 2),
					sectorDestination.getY()
							+ (sectorDestination.getHeight() / 2));
			renderer.end();

			batch.begin();
		} else {
			this.setX(this.getLocation().getX() - ORBITRAD);
			this.setY(this.getLocation().getY() - ORBITRAD);
			batch.draw(
					sprite1,
					this.getLocation().getX(Align.center) - 5
							+ (float) (this.getWidth() / 2 * Math.sin(angle)),
					this.getLocation().getY(Align.center) - 5
							+ (float) (this.getWidth() / 2 * Math.cos(angle)),
					SPRITE_SIZE, SPRITE_SIZE);

		}
		batch.setColor(Color.WHITE);

	}

	public void act(float time) {
		angle += time / 3;
	}
}