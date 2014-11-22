package se300.destinytactics.game.orbitalbodies;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.fleet.Ship;
import se300.destinytactics.game.mapgen.Assets;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.ui.ToolTip;

/**
 * @author Team Gaurdian
 * @version 2.5
 * @created 10-Oct-2014 5:49:10 PM
 * 
 *          OrbitalBody extends Actor. Player can control orbital bodies. They
 *          have a unique name based on which sector they exist in. They can
 *          contain a fleet. Clicking on and OrbitalBody takes the player to
 *          OrbitalBody view. Hovering over an OrbitalBody sprite will display a
 *          tooltip.
 */
public abstract class OrbitalBody extends Actor {

	protected int controlState;
	public Player owner;
	public String name;
	protected int orbitRadius;
	public Sector sector;

	public Fleet m_Fleet;
	public static int test;
	public int type;
	public static Texture[] planets;

	public static Texture circles[] = {
			new Texture(Gdx.files.internal("images/blueSelect.png")),
			new Texture(Gdx.files.internal("images/redSelect.png")) };

	public Image myCircle = new Image(circles[0]);

	public static final int SPRITE_SIZE = 40;
	public static final int YEDGEEXCLUSION = GameScene.SCREEN_HEIGHT
			- GameScene.SCREEN_HEIGHT / 10 - SPRITE_SIZE;
	public static final int XEDGEEXCLUSION = GameScene.SCREEN_WIDTH - 275;
	public Texture texture;
	public GameScene myGame;
	public OrbitalBody body;

	boolean hovering;
	ToolTip toolTip;

	// Turn
	public Boolean spentTurn = false;

	/**
	 * Constructor takes orbit radius and sector as an argument.
	 * 
	 * @param radius
	 * @param sect
	 */
	public OrbitalBody(int radius, Sector sect) {

		orbitRadius = radius;
		String radius_RN = "";
		switch (radius) {
		case 0:
			radius_RN = "I";
			break;
		case 1:
			radius_RN = "II";
			break;
		case 2:
			radius_RN = "III";
			break;
		case 3:
			radius_RN = "IV";
			break;
		case 4:
			radius_RN = "V";
			break;
		case 5:
			radius_RN = "VI";
			break;
		case 6:
			radius_RN = "VII";
			break;
		case 7:
			radius_RN = "VIII";
			break;
		case 8:
			radius_RN = "IX";
			break;
		case 9:
			radius_RN = "X";
			break;
		case 10:
			radius_RN = "XI";
			break;
		case 11:
			radius_RN = "XII";
			break;
		case 12:
			radius_RN = "XIII";
			break;
		case 13:
			radius_RN = "XIV";
			break;
		case 14:
			radius_RN = "XV";
			break;
		}
		name = sect.getName() + " " + radius_RN;
		sector = sect;
		controlState = 0;
		// galaxy = sector.galaxy;
		controlState = 1;
		owner = GameScene.localPlayer;
		myCircle.setOrigin(Align.center);
		myCircle.setSize(50, 50);
		myCircle.setPosition(getX() + 30, getY() + 30);

		setY((GameScene.SCREEN_HEIGHT / 5)
				+ (int) (Utility.random.nextInt(YEDGEEXCLUSION
						- GameScene.SCREEN_HEIGHT / 5) + 1));
		setX(XEDGEEXCLUSION - 150 * orbitRadius);

		setHeight(SPRITE_SIZE);
		setWidth(SPRITE_SIZE);
		setBounds(0, 0, SPRITE_SIZE, SPRITE_SIZE);

		addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				switchToPlanetView();
				return true;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn();
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				// System.out.println("Exit at " + x + ", " + y);
				hoverOff();
			}

		});
	}

	public static void loadAssets() {
		planets = Assets.getPlanetTypes();
		for (int i = 0; i < planets.length; i++) {
			planets[i].setFilter(TextureFilter.MipMapLinearLinear,
					TextureFilter.Linear);
		}
	}

	/**
	 * Called when mouse hovers over planet sprite. Creates new ToolTip, adds it
	 * to the stage, and sets hovering to true.
	 */
	public void hoverOn() {
		if (toolTip == null) {
			toolTip = new ToolTip(name, this);
		}
		this.getStage().addActor(toolTip);
		toolTip.addAction(sequence(Actions.alpha(0), Actions.delay(0.3f),
				Actions.fadeIn(0.4f, Interpolation.fade)));

		hovering = true;
	}

	/**
	 * Called when mouse hovers off planet sprite. Sets hovering to false,
	 * removes the current tooltip, and creates the next tooltip to be
	 * displayed.
	 */
	public void hoverOff() {
		hovering = false;
		toolTip.remove();
		toolTip = new ToolTip(name, this);
	}

	/**
	 * Returns true or false depending on whether or not a planet has a fleet.
	 * 
	 * @return true or false
	 */
	public boolean hasFleet() {
		if (this.m_Fleet != null) {
			return true;
		}
		return false;
	}

	/**
	 * Currently assigns owner to whoever clicks it. Calls GameScene's
	 * switchToPlanetView method.
	 */
	public void switchToPlanetView() {
		owner = GameScene.localPlayer;
		GameScene.localPlayer.addOrbitalBody(this);
		this.getStage().mouseMoved(20, 20);
		sector.galaxy.thisgame.switchToPlanetView(this);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Finds distance between any two orbital bodies.
	 * 
	 * @param body
	 * @return bodyPos + otherBodyPos + sectorDist
	 */
	public int getDistance(OrbitalBody body) {
		int bodyPos = sector.getNumBodies() - orbitRadius;
		int otherBodyPos = body.getSector().getNumBodies() - body.getPos();
		int sectorDist = sector.getDistance(body.getSector());
		return bodyPos + otherBodyPos + sectorDist;
	}

	/**
	 * Gets name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets fleet
	 * 
	 * @return m_Fleet
	 */
	public Fleet getFleet() {
		return m_Fleet;
	}

	/**
	 * Gets sector
	 * 
	 * @return sector
	 */
	public Sector getSector() {
		return sector;
	}

	/**
	 * Gets distance from sun
	 * 
	 * @return orbitRadius
	 */
	public int getPos() {
		return orbitRadius;
	}

	/**
	 * Gets control state
	 * 
	 * @return controlState
	 */
	public int getState() {
		return controlState;
	}

	/**
	 * Gets planet type
	 * 
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets sprite size
	 * 
	 * @return SPRITE_SIZE
	 */
	public int getSpriteSize() {
		return SPRITE_SIZE;
	}

	// I guess abstract? For Mine
	/**
	 * Levels up mining facility, affects cost and RPT
	 */
	public abstract void mineLevelUp();

	/**
	 * Gets mine level
	 * 
	 * @return mineLevel
	 */
	public abstract Integer getMineLevel();

	/**
	 * Gets mine cost
	 * 
	 * @return mineCost
	 */
	public abstract Integer getMineCost();

	/**
	 * Gets resources per turn
	 * 
	 * @return RPT
	 */
	public abstract Integer getRPT(); // Resources per turn

	// The Methods below relate to the shipyard implemented
	/**
	 * Increments shipyard level by 1, affects cost and size
	 */
	public abstract void shipyardLevelUp();

	/**
	 * Gets shipyard level
	 * 
	 * @return shipyardLevel
	 */
	public abstract int getShipyardLevel();

	/**
	 * Gets shipyard size
	 * 
	 * @return shipyardSize
	 */
	public abstract int getShipyardSize();

	/**
	 * Gets shipyard cost
	 * 
	 * @return shipyardCost
	 */
	public abstract int getShipyardCost();

	/**
	 * Adds ship to the building queue
	 * 
	 * @param ship
	 */
	public abstract void addToQueue(Ship ship);

	/**
	 * Iterates through the building queue, keeps track of building progress,
	 * calls toFleet(ship)
	 */
	public abstract void building();

	/**
	 * Takes ship out of the queue and puts it in orbit
	 * 
	 * @param ship
	 */
	public abstract void toFleet(Ship ship);

	/**
	 * Gets the size of the build queue
	 * 
	 * @return buildQueue.size()
	 */
	public abstract int getBuildQueueSize();

	/**
	 * Gets number of specified ships: Fighter, Corvette, Bomber, Carrier,
	 * Scout, Battle, and Dreadnaught ships[0 - 6] respectively
	 * 
	 * @param x
	 * @return
	 */
	public abstract int getShips(int x);

	/**
	 * Sets spentTurn = false. Calls build method. Adds resources.
	 */
	public abstract void endTurn();

	/**
	 * Spends OB's turn, disallowing any more infrastructure development until
	 * next turn
	 * 
	 * @return none
	 */
	public void spendTurn() {
		spentTurn = true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(planets[type], getX(), getY(), SPRITE_SIZE, SPRITE_SIZE);
	}

	public void act(float time) {
		super.act(time);
	}

	public void setShipyardLevel(int shipyardLevel) {
		// TODO Auto-generated method stub
		if (this.getClass() == Planet.class) {
			((Planet) this).shipyardLevel = shipyardLevel;
		}
	}

}// end OrbitalBody