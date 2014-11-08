package se300.destinytactics.game.mapgen;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.orbitalbodies.Station;
import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.ToolTip;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:15 PM
 */
public class Sector extends Actor {

	// Old variables, clean these
	public static int test;
	public Galaxy galaxy;
	private int controlState;
	private int numBodies;
	private String name;
	public OrbitalBody bodyList[];
	private int posX;
	private int posY;

	public Button m_Button;

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
	public static final double EDGE_EXCLUSION = 60;
	public static final int SPRITE_SIZE = 14;
	public int sunType;
	public float sunRotation;
	public int padding = 50;
	public boolean hovering = false;
	
	//ToolTip
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
		numBodies = (int) (Math.random() * 15) + 1;
		bodyList = new OrbitalBody[numBodies];
		posX = (int) (EDGE_EXCLUSION + (Math.random() * (galaxy
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

		double stationChance = 0.2;
		for (int i = 0; i < numBodies; i++) {
			if (stationChance > Math.random()) {
				bodyList[i] = new Station(i, this);
				stationChance /= 10;
			} else {
				bodyList[i] = new Planet(i, this);
			}
		}

		name = Names.newName();
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
				hoverOff();
			}

		});
		
		
		//ToolTip

	}

	public void switchView() {
		controlState = 1;
		toolTip.remove();
		hoverOff();
		galaxy.thisgame.switchView(this);

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void hoverOn() {		
		toolTip = new ToolTip(name, this);
		hovering = true;
		myCircle.setX(getX() + this.getOriginX() - (myCircle.getWidth() / 2));
		myCircle.setY(getY() + this.getOriginY() - (myCircle.getHeight() / 2));
	}

	public void hoverOff() {
		toolTip.remove();
		hovering = false;
		this.getStage().mouseMoved(0, 1);

	}

	public String getName() {
		return name;
	}

	public void act() {
	}

	public Point getPos() {
		return new Point(posX, posY);
	}

	public int getXPos() {
		return posX;
	}

	public int getYPos() {
		return posY;
	}

	public int getNumBodies() {
		return numBodies;
	}

	public int getDistance(Sector sector) {
		return (int) sector.getPos().distance(getPos());
	}

	public int getState() {
		return controlState;
	}

	public void draw(Batch batch, float parentAlpha) {

		batch.setColor(this.getColor());
		batch.draw(sprite1, getXPos(), getYPos(), SPRITE_SIZE, SPRITE_SIZE);
		batch.setColor(Color.WHITE);
		
		if (hovering) {
			myCircle.draw(batch, parentAlpha);
			toolTip.draw(batch, parentAlpha);
		
		} else {
			
		}

		if (controlState == 1) {
			batch.draw(circles[1], getXPos() + 7 - 25, getYPos() + 7 - 25, 25,
					25, 50, 50, 1, 1, myCircle.getRotation(), 0, 0,
					circles[1].getWidth(), circles[1].getHeight(), false, false);
		}
	}

	public void act(float time) {
		myCircle.rotateBy(time * 3);
	}
}// end Sector