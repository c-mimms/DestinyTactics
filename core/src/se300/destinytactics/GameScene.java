package se300.destinytactics;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import se300.destinytactics.game.AI;
import se300.destinytactics.game.User;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.scenes.*;
import se300.destinytactics.ui.Drawable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:03 PM
 */
public class GameScene extends Game {

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
	ShapeRenderer renderer;
	Vector3 mousePos = new Vector3();

	// NEW variables, put necessary ones here.
	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 800;
	public static final int GALAXY_WIDTH = 1200;
	public static final int GALAXY_HEIGHT = 800;
	public static final int NUMBER_SECTORS = 20;

	public Stage galaxyStage, sectorStage, planetStage, sectorUI, planetUI;
	public boolean sectorView = false;
	public boolean galaxyView = true;
	public boolean planetView = false;
	public Texture bgimg;
	public Texture sectorSun;
	public Texture backButton; 
	InputMultiplexer multiplexer;

	public void create() {
		
		bgimg = new Texture("background.png");
		sectorSun = new Texture("realorbitalbody/sun1.png");
		backButton = new Texture("backbutton.png");
		
		// Create galaxy stage on game initialization.
		galaxyStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		sectorStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		planetStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		sectorUI = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		planetUI = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(galaxyStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		//Add buttons to sector UI
		Image backbut = new Image(backButton);
		sectorUI.addActor(backbut);
		backbut.setX(0);
		backbut.setY(SCREEN_HEIGHT - backbut.getHeight());
		backbut.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				goGalaxy();
				return true;
			}

		});
		
		//Addbuttons to planet UI
		Image backbutt = new Image(backButton);
		Image managefleet = new Image(new Texture("managefleet.png"));
		Image manageInfrastructure = new Image(new Texture("manageinfrastructure.png"));
		Image manageDefense = new Image(new Texture("managedefense.png"));
		planetUI.addActor(backbutt);
		planetUI.addActor(managefleet);
		planetUI.addActor(manageInfrastructure);
		planetUI.addActor(manageDefense);
		backbutt.setX(0);
		backbutt.setY(SCREEN_HEIGHT - backbutt.getHeight());
		managefleet.setX(0);
		managefleet.setY(2*managefleet.getHeight());
		manageInfrastructure.setX(0);
		manageInfrastructure.setY(managefleet.getHeight());
		manageDefense.setX(0);
		manageDefense.setY(0);
		backbutt.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				goSystem();
				return true;
			}

		});
		
		// Set galaxy stage to get inputs.
		galaxyStage.setDebugAll(true);
		sectorStage.setDebugAll(true);
		planetStage.setDebugAll(true);
		sectorUI.setDebugAll(true);
		planetUI.setDebugAll(true);

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
			sectorUI.draw();
		}
		else if (planetView) {
			planetStage.act();
			planetStage.draw();
			planetUI.draw();
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
		sectorStage.dispose();
		planetStage.dispose();
	}

	public void endTurn() {

	}

	public void switchView(Sector nextSector) {
		
		// Clear stage to reuse it
		sectorStage.clear();

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		Image sun = new Image(sectorSun);
		sectorStage.addActor(background);
		sectorStage.addActor(sun);
		sun.setX(SCREEN_WIDTH-sectorSun.getWidth());
		sun.setY(SCREEN_HEIGHT/2 - sectorSun.getHeight()/2);
		
		
		background.setFillParent(true);

		// Add all planet objects
		for (int i = 0; i < nextSector.getNumBodies(); i++) {
			sectorStage.addActor(nextSector.bodyList[i]);
		}
		
		galaxyView = false;
		planetView = false;
		sectorView = true;
		multiplexer.addProcessor(sectorStage);
		multiplexer.addProcessor(sectorUI);
		multiplexer.removeProcessor(galaxyStage);


	}
	
	public void switchToPlanetView(OrbitalBody nextOrbitalBody) {
		System.out.println("Go to MyGame Method");
		// Clear stage to reuse it
		planetStage.clear();

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		Image orbitalBody = new Image(nextOrbitalBody.hotBod[nextOrbitalBody.getType()]);
		planetStage.addActor(background);
		planetStage.addActor(orbitalBody);
		orbitalBody.setX(SCREEN_WIDTH/4-orbitalBody.getWidth()/2);
		orbitalBody.setY(SCREEN_HEIGHT/2-orbitalBody.getHeight()/2);
		
		
		background.setFillParent(true);

		
		planetView = true;
		galaxyView = false;
		sectorView = false;
		multiplexer.addProcessor(planetStage);
		multiplexer.addProcessor(planetUI);
		multiplexer.removeProcessor(sectorStage);
		multiplexer.removeProcessor(sectorUI);

	}
	
	public void goGalaxy(){
		planetView = false;
		sectorView = false;
		galaxyView = true;
		multiplexer.addProcessor(galaxyStage);
		multiplexer.removeProcessor(sectorStage);
		multiplexer.removeProcessor(sectorUI);
	}
	
	public void goSystem(){
		System.out.println("goSystem method()");
		planetView = false;
		sectorView = true;
		galaxyView = false;
		multiplexer.addProcessor(sectorStage);
		multiplexer.addProcessor(sectorUI);
		multiplexer.removeProcessor(planetStage);
		multiplexer.removeProcessor(planetUI);
	}

	public int getGameState() {
		return 0;

	}

	public void setGameState() {

	}

}// end Game