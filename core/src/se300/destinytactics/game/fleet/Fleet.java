package se300.destinytactics.game.fleet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import se300.destinytactics.game.Player;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.scenes.GalaxyScene;

/**
 * @author simonsr1
 * @version 1.0
 * @created 10-Oct-2014 5:49:01 PM
 */
public class Fleet extends Actor {

	private OrbitalBody destination;
	private int distanceToDestination;
	private int fleetGalacticTravelSpeed = 50;
	private int fleetSectorTravelSpeed;
	private OrbitalBody location;
	private String name;
	private Player playerAssignment;
	private int ships[];
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

	public Fleet(OrbitalBody loc, int ship[]) {
		super();
		setLocation(loc);
		this.ships = ship;
		this.setSize(10, 10);
		System.out.println("New fleet created at " + location.getName());
	}

	public void finalize() throws Throwable {

	}

	public OrbitalBody getDestination() {
		return destination;
	}

	public int getFleetGalacticTravelSpeed() {
		return 0;
	}

	public int getFleetSectorTravelSpeed() {
		return 0;
	}

	public OrbitalBody getLocation() {
		return location;
	}

	public String getName() {
		return "";
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
	public int getShipCount(Ship unitType) {
		return 0;
	}

	/**
	 * 
	 * @param unitType
	 * @param num
	 */
	public void getShipCount(Ship unitType, int num) {

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
			// TODO add a totalDistance variable so this calculation doesn't
			// have to
			// run constantly
			else {
				percentTravelled = (float) distanceToDestination
						/ (float) location.getDistance(destination);
				float difx = sectorLocation.getX() - sectorDestination.getX();
				float dify = sectorLocation.getY() - sectorDestination.getY();
				// TODO make everything have a getCenterX() and setCenter() so
				// we don't have to do this...
				this.setX(sectorDestination.getX()
						+ (sectorDestination.getWidth() / 2) - (getWidth() / 2)
						+ (difx * percentTravelled));
				this.setY(sectorDestination.getY()
						+ (sectorDestination.getHeight() / 2)
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
		distanceToDestination = location.getDistance(destination);
	}

	/**
	 * Draw the fleet
	 */
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
			
			batch.draw(sprite1, this.getLocation().getX(Align.center)-5 + (float)(40*Math.sin(angle)), this
					.getLocation().getY(Align.center)-5 + (float)(40*Math.cos(angle)), SPRITE_SIZE, SPRITE_SIZE);

		}
		batch.setColor(Color.WHITE);

	}
	public void act(float time){
		angle += time * 2;
	}
}