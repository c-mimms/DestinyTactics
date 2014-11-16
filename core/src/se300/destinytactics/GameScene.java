package se300.destinytactics;

import se300.destinytactics.game.Player;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.scenes.GalaxyScene;
import se300.destinytactics.game.scenes.InfoBar;
import se300.destinytactics.game.scenes.NavBar;
import se300.destinytactics.game.scenes.OrbitalBodyScene;
import se300.destinytactics.game.scenes.OrbitalBodyUI;
import se300.destinytactics.game.scenes.SectorScene;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * 
 * @author Team Guardian
 * Implements Screen from the LibGDX framework. 
 * Defines game rules, generates the gameboard, and creates the three major stages and relevant sub-stages for the game.
 * stages : [galaxyStage : First game view. Displays the galaxy map with clickable/hoverable sectors.
 * 		     sectorStage : Second game view. Shown after clicking on a sector in the galaxyStage. Displays clickable/hoverable orbital bodies.
 * 		     planetStage : Third game view. Shown after clicking an orbital body in the sectorStage.]
 * sub-stages : [navBar : Displayed at the top of the view of every main stage. 
 * 					Contains navigation and the name of the current view (i.e.: the sector name).
 * 				 infoBar : Displayed at the bottom of the view of every main stage. 
 * 					Contains game-wide and player-specific data (i.e. current amount of resources, score, fleet size, etc..)
 * 				 planetUI : Shown within the planetStage. Displays fleet, defense, and infrastructure management interfaces.
 */
public class GameScene implements Screen {

	// Constants
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int GALAXY_WIDTH = 1024;
	public static final int GALAXY_HEIGHT = 640;
	public static final int NUMBER_SECTORS = 20;
	public static final int PADDING = 20;

	// Galaxy model
	public Galaxy m_Galaxy;

	// Scenes
	public GalaxyScene galaxyStage;
	public NavBar navBar;
	public InfoBar infoBar;
	public OrbitalBodyUI planetUI;
	public OrbitalBodyScene planetStage;
	public SectorScene sectorStage;

	// State variables
	public boolean galaxyView = true;
	public boolean sectorView = false;
	public boolean planetView = false;

	// Game logic required stuff
	public DestinyTactics game;
	InputMultiplexer multiplexer;
	public Sector curSector;
	public OrbitalBody curOrbitalBody;

	// Sound stuff
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;

	// Players?
	public static Player localPlayer;
	public Player onlinePlayers[];

	// public static void preloadGalaxy(){
	//
	// m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS, null);
	// }
	
	/**
	 * GameScene constructor. Instantiates the stages.
	 * @param game  Parent game class that contains this screen.
	 * @param skin  Skin to use for the screen.
	 */
	public GameScene(DestinyTactics game, Skin skin) {

		// Keep track of the game object so we can return to main menu
		this.game = game;
		// m_Galaxy.thisgame = this;
		localPlayer = new Player();

		// Load music and sounds (we should have a static sound/music class
		// maybe?)
		musicLoop = Gdx.audio.newMusic(Gdx.files
				.internal("music/SimplicityIsBliss.mp3"));
		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));

		//Testing seed
		Utility.setSeed(1);
		
		//Generate the Galaxy
		m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS, this);

		// Create galaxy stage and constants UIs
		galaxyStage = new GalaxyScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), skin, this);

		infoBar = new InfoBar(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT),
				PADDING, skin, this);

		navBar = new NavBar(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT),
				PADDING, skin, this);

		planetUI = new OrbitalBodyUI(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), PADDING, skin, this);

		// Create empty stages that are populated when selected
		sectorStage = new SectorScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), PADDING, skin, this);
		planetStage = new OrbitalBodyScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), PADDING, skin, this);

		// Debugger toggles. Make borders around actors and regions. Turn OFF
		// for demo
		 galaxyStage.setDebugAll(true);
		 sectorStage.setDebugAll(true);
		 planetStage.setDebugAll(true);
		 planetUI.setDebugAll(true);
		 navBar.setDebugAll(true);

		// Create multiplexer to get input from all stages
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(galaxyStage);
		multiplexer.addProcessor(navBar);
		multiplexer.addProcessor(infoBar);
		Gdx.input.setInputProcessor(multiplexer);

	}
	
	/**
	 * LibGDX override. Called every tick. Resizes the dimensions of the stages to the current app window size.
	 */
	public void resize(int width, int height) {
		// Resize stages to fill window.
		galaxyStage.getViewport().update(width, height, false);
		sectorStage.getViewport().update(width, height, false);
		planetStage.getViewport().update(width, height, false);
		planetUI.getViewport().update(width, height, false);
		navBar.getViewport().update(width, height, false);
	}
	
	public void finalize() throws Throwable {

	}
	
	/**
	 * LibGDX override. Clears the stages from memory
	 */
	public void dispose() {
		galaxyStage.dispose();
		sectorStage.dispose();
		planetStage.dispose();
	}
	
	/**
	 * Switches to the sectorStage view and loads a sector. This is called from both the galaxyStage (clicking on sector) and planetStage (navBar).
	 * @param nextSector
	 */
	public void switchView(Sector nextSector) {

		selectSound.play();
		curSector = nextSector;

		sectorStage.changeSector(nextSector);

		galaxyView = false;
		planetView = false;
		sectorView = true;

		multiplexer.addProcessor(sectorStage);
		multiplexer.removeProcessor(galaxyStage);
	}
	
	/**
	 * Switches to the planetStage view and loads an orbital body.
	 * @param nextOrbitalBody
	 */
	public void switchToPlanetView(OrbitalBody nextOrbitalBody) {

		selectSound.play();
		
		curOrbitalBody = nextOrbitalBody;
		planetStage.changePlanet(nextOrbitalBody);

		planetView = true;
		galaxyView = false;
		sectorView = false;

		multiplexer.clear();
		multiplexer.addProcessor(navBar);
		multiplexer.addProcessor(infoBar);
		multiplexer.addProcessor(planetStage);
		multiplexer.addProcessor(planetUI);
	}
	
	/**
	 * Switches to the galaxyStage view.
	 */
	public void goGalaxy() {
		planetView = false;
		sectorView = false;
		galaxyView = true;

		navBar.setName(m_Galaxy.getName());

		multiplexer.clear();
		multiplexer.addProcessor(navBar);
		multiplexer.addProcessor(infoBar);
		multiplexer.addProcessor(galaxyStage);
	}
	
	/**
	 * Switches to the sectorStage view.
	 */
	public void goSystem() {
		planetView = false;
		sectorView = true;
		galaxyView = false;

		navBar.setName(curSector.getName());
		multiplexer.clear();
		multiplexer.addProcessor(sectorStage);
		multiplexer.addProcessor(navBar);
		multiplexer.addProcessor(infoBar);
	}

	@Override
	public void show() {
		// Start music and take input when you switch to this window
		//musicLoop.play();
		//musicLoop.setLooping(true);
		Gdx.input.setInputProcessor(multiplexer);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		musicLoop.stop();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	@Override
	/**
	 * LibGDX override. Renders the actors loaded in the viewed stage and 
	 * calls the act methods (bubbles down from the stages to the actors). 
	 */
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (galaxyView) {
			galaxyStage.act();
			galaxyStage.draw();
		} else if (sectorView) {
			sectorStage.act();
			sectorStage.draw();
		} else if (planetView) {
			planetStage.act();
			planetStage.draw();
			planetUI.act();
			planetUI.draw();

		}
		navBar.act();
		navBar.draw();
		infoBar.act();
		infoBar.draw();
	}
	
	/**
	 * Calls the goMenu method in DestinyTactics object to switch screens. Switches to menuScene.
	 */
	public void goMenu() {
		game.goMenu();
	}
	
	/**
	 * Ends the player's turn.
	 */
	public void endTurn() {
		galaxyStage.endTurn();
		localPlayer.endTurn();
		goGalaxy();
	}
}