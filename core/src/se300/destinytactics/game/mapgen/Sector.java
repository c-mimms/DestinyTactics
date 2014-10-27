package se300.destinytactics.game.mapgen;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.orbitalbodies.Station;
import se300.destinytactics.ui.Button;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:15 PM
 */
public class Sector extends Actor {

	
	//Old variables, clean these
	public static Galaxy galaxy;
	private int controlState;
	private int numBodies;
	private String name;
	public OrbitalBody bodyList[];
	private int posX;
	private int posY;
	public Button m_Button;
	private Texture sprite1;
	
	//New variables
	private static final double EDGE_EXCLUSION = 60;
	private static final int SPRITE_SIZE = 50;
	public GameScene thisgame;

	public Sector() {

		super.setVisible(true);
		sprite1 = new Texture(
				Gdx.files.internal("realorbitalbody/galaxySun.png"));
		controlState = 0;
		numBodies = (int) (Math.random() * 15) + 1;
		bodyList = new OrbitalBody[numBodies];
		posX = (int) (EDGE_EXCLUSION + (Math.random() * (galaxy.getGalaxyWidth() - 2 * EDGE_EXCLUSION)));
		posY = (int) (GameScene.SCREEN_HEIGHT/5 + (Utility.random.nextInt((GameScene.SCREEN_HEIGHT-GameScene.SCREEN_HEIGHT/10-SPRITE_SIZE)-GameScene.SCREEN_HEIGHT/5)));

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
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				//System.out.println("Hello from " + this);
				// Game, etc.

				switchView();
				return true;
			}

		});
	}

	public void switchView(){
		
		galaxy.thisgame.switchView(this);
		
	}
	public void finalize() throws Throwable {
		super.finalize();
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

		batch.draw(sprite1, getXPos(), getYPos(), SPRITE_SIZE, SPRITE_SIZE);

	}
}// end Sector