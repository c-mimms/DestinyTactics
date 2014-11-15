package se300.destinytactics.game.mapgen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
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
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.orbitalbodies.Station;
import se300.destinytactics.ui.ToolTip;


/**
* <h1>Sector</h1>
* Object to instantiate sectors with random planets, and interact with the sector objects.
* <p>
*
* @author  Chris Mimms
* @version 1.0
* @since   2014-11-12
*/
public class Sector extends Actor {

	public static final double EDGE_EXCLUSION = 60;
	public static final int SPRITE_SIZE = 14;

	// TODO Remove after player color array is created
	private static Texture sprite1 = new Texture(
			Gdx.files.internal("realorbitalbody/SectorIcon.png"), true);

	// TODO Replace with player color array with SectorIcon from above with 8
	// different colors + white
	public static Texture circles[] = {
			new Texture(Gdx.files.internal("images/blueSelect.png")),
			new Texture(Gdx.files.internal("images/redSelect.png")) };

	public static Image myCircle = new Image(circles[0]);
	public static AssetManager manager = new AssetManager();
	public static Texture sunTypes[];

	// New variables
	private String name;
	public OrbitalBody bodyList[];
	private int posX, posY;
	public int sunType;
	public float sunRotation;
	public int padding = 50;
	public boolean hovering = false;
	public Galaxy galaxy;
	private int controlState;
	private int numBodies;

	// ToolTip
	public ToolTip toolTip;

	public Sector(Galaxy gal) {

		galaxy = gal;
		sunTypes = Assets.getSunTypes();
		sprite1.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		myCircle.setOrigin(Align.center);
		myCircle.setSize(50, 50);

		super.setVisible(true);
		this.sunType = Utility.random.nextInt(6);
		this.sunRotation = Utility.random.nextFloat() * 360;
		controlState = 0;
		numBodies = (int) (Utility.random.nextDouble() * 15) + 1;
		bodyList = new OrbitalBody[numBodies];
		posX = (int) (EDGE_EXCLUSION + (Utility.random.nextDouble() * (galaxy
				.getGalaxyWidth() - 2 * EDGE_EXCLUSION)));
		posY = (int) (GameScene.SCREEN_HEIGHT / 5 + padding + (Utility.random
				.nextInt((GameScene.SCREEN_HEIGHT - GameScene.SCREEN_HEIGHT
						/ 10 - SPRITE_SIZE)
						- GameScene.SCREEN_HEIGHT / 5 - (padding * 2))));

		this.setOrigin(SPRITE_SIZE / 2, SPRITE_SIZE / 2);
		setWidth(SPRITE_SIZE);
		setHeight(SPRITE_SIZE);
		setBounds(0, 0, SPRITE_SIZE, SPRITE_SIZE);
		setX(posX);
		setY(posY);

		name = Names.newName();
		
		double stationChance = 0.2;
		//Generate planets
		for (int i = 0; i < numBodies; i++) {
			if (stationChance > Utility.random.nextDouble()) {
				bodyList[i] = new Station(i, this);
				stationChance /= 10;
			} else {
				bodyList[i] = new Planet(i, this);
			}
		}

		
		this.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				switchView();
				return true;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn();
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				//System.out.println("Exit at " + x + ", " + y);
				hoverOff();
			}

		});

	}

	/**
	 * Run when mouse hovers over sector
	 */
	public void hoverOn() {
//		System.out.println("Hovering on " + name);
		if (toolTip == null) {
			toolTip = new ToolTip(name, this);
		} 

		this.getStage().addActor(toolTip);
		toolTip.addAction(sequence(Actions.alpha(0), Actions.delay(0.3f),
				Actions.fadeIn(0.4f, Interpolation.fade)));

		hovering = true;
	}

	/**
	 * Run when mouse hovers off sector
	 */
	public void hoverOff() {
		if(toolTip != null)
			toolTip.remove();
		hovering = false;
		toolTip = new ToolTip(name, this);

	}

	/**
	 * Switch view to selected sector.
	 */
	public void switchView() {
		controlState = 1;
		this.getStage().touchDown(20, 20, 0, Input.Buttons.LEFT);
		this.getStage().mouseMoved(20, 20);
		galaxy.thisgame.switchView(this);

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public String getName() {
		return name;
	}

	/**
	 * Get position as a Point object.
	 * @return Point object representing position.
	 */
	public Point getPos() {
		return new Point(posX, posY);
	}

	public int getXPos() {
		return posX;
	}

	public int getYPos() {
		return posY;
	}
	
	/**
	 * Get number of orbital bodies in the sector.
	 * @return
	 */
	public int getNumBodies() {
		return numBodies;
	}

	/**
	 * Get distance between this sector and another sector, relative to galaxy size.
	 * @param sector 
	 * @return Distance between sectors.
	 */
	public int getDistance(Sector sector) {
		return (int) sector.getPos().distance(getPos());
	}

	/**
	 * Returns 1 if sector is owned by a player.
	 * @return
	 */
	public int getState() {
		return controlState;
	}

	public void draw(Batch batch, float parentAlpha) {

		batch.setColor(this.getColor());
		batch.draw(sprite1, this.getX(), this.getY(), SPRITE_SIZE, SPRITE_SIZE);
		batch.setColor(Color.WHITE);

		if (controlState == 1) {
			batch.draw(circles[1], getX() + 7 - 25, getY() + 7 - 25, 25,
					25, 50, 50, 1, 1, myCircle.getRotation(), 0, 0,
					circles[1].getWidth(), circles[1].getHeight(), false, false);
		}
	}

	public void act(float time) {
		super.act(time);
		myCircle.rotateBy(time * 3);
	}
}// end Sector