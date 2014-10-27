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
import se300.destinytactics.game.scenes.FleetCommand;
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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScene extends Game {

	// OLD Variables, Decide which need to be kept
//	private SpriteBatch batch;
//	private Rectangle sun;
//	private Texture selImage;
//	private Galaxy gameMap;
//	private ArrayList<Drawable> objToDraw;
//	private boolean start;
//	public AI m_AI;
//	public User m_User;
//	ShapeRenderer renderer;
//	Vector3 mousePos = new Vector3();
	public Galaxy m_Galaxy;
	
	// NEW variables, put necessary ones here.
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int GALAXY_WIDTH = 1024;
	public static final int GALAXY_HEIGHT = 640;
	public static final int NUMBER_SECTORS = 20;

	public Stage galaxyStage, sectorStage, planetStage, sectorUI, planetUI;
	public boolean sectorView = false;
	public boolean galaxyView = true;
	public boolean planetView = false;
	public Texture bgimg;
	public Texture sectorSun;
	public Texture backButton; 
	InputMultiplexer multiplexer;
	public Skin skin;
	private String spriteLib = "realorbitalbody";
	
	public void create() {
		
		bgimg = new Texture("background.png");
		sectorSun = new Texture(spriteLib + "/sun1.png");
		backButton = new Texture("backbutton.png");
		
		// Specify the UI Skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		// Create galaxy stage on game initialization.
		galaxyStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		sectorStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		planetStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		sectorUI = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		planetUI = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		// Debugger toggles. Make borders around actors and regions. Turn OFF for demo
//		galaxyStage.setDebugAll(true);
//		sectorStage.setDebugAll(true);
//		planetStage.setDebugAll(true);
//		sectorUI.setDebugAll(true);
//		planetUI.setDebugAll(true);
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(galaxyStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		//Add Actors to sector UI
		final TextButton backButton_Galaxy = new TextButton("Back to Galaxy", skin.get("default", TextButtonStyle.class));
		Image bar = new Image(new Texture("sun.png"));
		Image bar2 = new Image(new Texture("sun.png"));
		sectorUI.addActor(bar);
		sectorUI.addActor(bar2);
		sectorUI.addActor(backButton_Galaxy);
		bar.setScaleX(SCREEN_WIDTH);
		bar.setHeight(SCREEN_HEIGHT/10);
		bar.setY(SCREEN_HEIGHT - bar.getHeight());
		bar2.setScaleX(GALAXY_WIDTH);
		bar2.setHeight(SCREEN_HEIGHT/5);
		bar2.setY(0);
		backButton_Galaxy.setX(0);
		backButton_Galaxy.setY(SCREEN_HEIGHT - backButton_Galaxy.getHeight());
		backButton_Galaxy.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				goGalaxy();
				return true;
			}
		});
		
		//Add Actors to planet UI
		FleetCommand fc = new FleetCommand(skin);
		final TextButton backButton_Sector = new TextButton("Back to Sector", skin.get("default", TextButtonStyle.class));
		final TextButton managefleet = new TextButton("Fleet Command", skin.get("default", TextButtonStyle.class));
		final TextButton manageInfrastructure = new TextButton("Infrastructure", skin.get("default", TextButtonStyle.class));
		final TextButton manageDefense = new TextButton("Defense", skin.get("default", TextButtonStyle.class));
		Image bar3 = new Image(new Texture("sun.png"));
		Image bar4 = new Image(new Texture("sun.png"));	
		
		planetUI.addActor(managefleet);
		planetUI.addActor(manageInfrastructure);
		planetUI.addActor(manageDefense);
		planetUI.addActor(fc.getFleetCommand());
		planetUI.addActor(bar3);
		planetUI.addActor(bar4);
		planetUI.addActor(backButton_Sector);
		
		bar3.setScaleX(SCREEN_WIDTH);
		bar3.setHeight(SCREEN_HEIGHT/10);
		bar3.setY(SCREEN_HEIGHT - bar.getHeight());
		bar4.setScaleX(GALAXY_WIDTH);
		bar4.setHeight(SCREEN_HEIGHT/5);
		bar4.setY(0);
		backButton_Sector.setX(0);
		backButton_Sector.setY(SCREEN_HEIGHT - backButton_Sector.getHeight());
		managefleet.setX(0);
		managefleet.setY(managefleet.getHeight()*9);
		manageInfrastructure.setX(0);
		manageInfrastructure.setY(managefleet.getHeight()*8);
		manageDefense.setX(0);
		manageDefense.setY(managefleet.getHeight()*7);
		backButton_Sector.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				goSystem();
				return true;
			}

		});
		
		// Set galaxy stage to get inputs.
		Image background = new Image(bgimg);
		Image bar5 = new Image(new Texture("sun.png"));
		Image bar6 = new Image(new Texture("sun.png"));
		bar5.setScaleX(SCREEN_WIDTH);
		bar5.setHeight(SCREEN_HEIGHT/10);
		bar5.setY(SCREEN_HEIGHT - bar.getHeight());
		bar6.setScaleX(GALAXY_WIDTH);
		bar6.setHeight(SCREEN_HEIGHT/5);
		bar6.setY(0);
		galaxyStage.addActor(background);
		galaxyStage.addActor(bar5);
		galaxyStage.addActor(bar6);
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
		sectorStage.getViewport().update(width, height, false);
		planetStage.getViewport().update(width, height, false);
		sectorUI.getViewport().update(width, height, false);
		planetUI.getViewport().update(width, height, false);
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
		sun.setY(SCREEN_HEIGHT/2+SCREEN_HEIGHT/20 - sectorSun.getHeight()/2);
		
		
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
		//System.out.println("Go to MyGame Method");
		// Clear stage to reuse it
		planetStage.clear();

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		Image orbitalBody = new Image(nextOrbitalBody.hotBod[nextOrbitalBody.getType()]);
		orbitalBody.setSize(nextOrbitalBody.getSpriteSize()*10, nextOrbitalBody.getSpriteSize()*10);
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