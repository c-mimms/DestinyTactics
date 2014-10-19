package se300.destinytactics.logic;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import se300.destinytactics.mapgen.Galaxy;
import se300.destinytactics.mapgen.Sector;
import se300.destinytactics.ui.Drawable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:03 PM
 */
public class MyGame extends Game {

	// OLD Variables, Decide which need to be kept
	private SpriteBatch batch;
	private Rectangle sun;
	private Texture selImage;
	private Galaxy gameMap;
	private ArrayList<Drawable> objToDraw;
	private boolean start;
	public AI m_AI;
	public User m_User;
	public Galaxy m_Galaxy;
	private float zoomLevel;
	OrthographicCamera camera;
	float ww;
	float hh;
	ShapeRenderer renderer;
	Vector3 mousePos = new Vector3();

	// NEW variables, put necessary ones here.
	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 800;
	public static final int GALAXY_WIDTH = 1200;
	public static final int GALAXY_HEIGHT = 800;
	public static final int NUMBER_SECTORS = 20;

	public Stage galaxyStage, sectorStage;
	public boolean sectorView = false;
	public boolean galaxyView = true;
	public Texture bgimg;

	public void create() {
		bgimg = new Texture("background.png");
		// Create galaxy stage on game initialization.
		galaxyStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		sectorStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		// Set galaxy stage to get inputs.
		Gdx.input.setInputProcessor(galaxyStage);
		galaxyStage.setDebugAll(true);

		Image background = new Image(bgimg);
		galaxyStage.addActor(background);
		background.setFillParent(true);

		m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS,this);

		for (int i = 0; i < m_Galaxy.sectors.length; i++) {
			galaxyStage.addActor(m_Galaxy.sectors[i]);

		}

	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (galaxyView) {
			galaxyStage.act();
			galaxyStage.draw();
		} 
		else if (sectorView) {
			sectorStage.act();
			sectorStage.draw();
		}

	}

	public void resize(int width, int height) {

		// Resize stage to fill window.
		galaxyStage.getViewport().update(width, height, false);

	}

	public void finalize() throws Throwable {

	}

	public void dispose() {
		galaxyStage.dispose();
	}

	public void endTurn() {

	}

	public void switchView(Sector nextSector) {
		
		// Clear stage to reuse it
		sectorStage.clear();

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		galaxyStage.addActor(background);
		background.setFillParent(true);

		// Add all planet objects
		for (int i = 0; i < nextSector.getNumBodies(); i++) {
			sectorStage.addActor(nextSector.bodyList[i]);
		}
		
		galaxyView = false;
		sectorView = true;

	}

	public int getGameState() {
		return 0;

	}

	public void setGameState() {

	}

}// end Game