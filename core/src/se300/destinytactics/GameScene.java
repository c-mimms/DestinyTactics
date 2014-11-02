package se300.destinytactics;

import se300.destinytactics.game.Player;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
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

public class GameScene implements Screen {

	// Constants
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int GALAXY_WIDTH = 1024;
	public static final int GALAXY_HEIGHT = 640;
	public static final int NUMBER_SECTORS = 20;
	public static final int PADDING = 20;

	// Galaxy model
	public static Galaxy m_Galaxy;
	
	//Scenes
	public Stage galaxyStage;
	public NavBar navBar;
	public InfoBar infoBar;
	public OrbitalBodyUI planetUI;
	public OrbitalBodyScene planetStage;
	public SectorScene sectorStage;

	//State variables
	public boolean galaxyView = true;
	public boolean sectorView = false;
	public boolean planetView = false;
	
	//Game logic required stuff
	public DestinyTactics game;
	InputMultiplexer multiplexer;
	public Sector curSector;
	
	//Sound stuff
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;
	
	//Players?
	public Player curPlayer;
	
	
	public static void preloadGalaxy(){

		m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS, null);
	}

	public GameScene(DestinyTactics game, Skin skin) {
		
		
		//Keep track of the game object so we can return to main menu
		this.game = game;
		//m_Galaxy.thisgame = this;
		curPlayer = new Player();

		// Load music and sounds (we should have a static sound/music class
		// maybe?)
		musicLoop = Gdx.audio.newMusic(Gdx.files
				.internal("music/SimplicityIsBliss.mp3"));
		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));


		//Time each scene generation.
		long time = System.currentTimeMillis();
		long time2 = System.currentTimeMillis();
		
		// Generate the galaxy model. Moved this to DestinyTactics so it is preloaded
		m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS,this);

		
		// Create galaxy stage and constants UIs
		galaxyStage = new GalaxyScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), this);

		System.out.println("Galaxy time taken: " + (System.currentTimeMillis()-time));

		 time = System.currentTimeMillis();
		
		infoBar = new InfoBar(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT),
				PADDING, this);

		System.out.println("Infobar time taken: " + (System.currentTimeMillis()-time));
		 time = System.currentTimeMillis();
		
		navBar = new NavBar(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT),
				PADDING, this);
		

		System.out.println("Navbar time taken: " + (System.currentTimeMillis()-time));

		 time = System.currentTimeMillis();
		
		planetUI = new OrbitalBodyUI(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), PADDING, this);

		System.out.println("Planetui time taken: " + (System.currentTimeMillis()-time));

		
		// Create empty stages that are populated when selected
		sectorStage = new SectorScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), PADDING, this);
		planetStage = new OrbitalBodyScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), PADDING, this);


		// Debugger toggles. Make borders around actors and regions. Turn OFF
		// for demo
//		 galaxyStage.setDebugAll(true);
//		 sectorStage.setDebugAll(true);
//		 planetStage.setDebugAll(true);
//		 planetUI.setDebugAll(true);
//		 navBar.setDebugAll(true);

		// Create multiplexer to get input from all stages
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(galaxyStage);
		multiplexer.addProcessor(navBar);
		multiplexer.addProcessor(infoBar);
		Gdx.input.setInputProcessor(multiplexer);

		System.out.println("Total time taken: " + (System.currentTimeMillis()-time2));


	}

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

	public void dispose() {
		galaxyStage.dispose();
		sectorStage.dispose();
		planetStage.dispose();
	}

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

	public void switchToPlanetView(OrbitalBody nextOrbitalBody) {

		selectSound.play();
		planetStage.changePlanet(nextOrbitalBody);
		
		
		planetView = true;
		galaxyView = false;
		sectorView = false;
		
		multiplexer.addProcessor(planetStage);
		multiplexer.addProcessor(planetUI);
		multiplexer.removeProcessor(sectorStage);
	}

	public void goGalaxy() {
		planetView = false;
		sectorView = false;
		galaxyView = true;
		
		navBar.setName(m_Galaxy.getName());
		
		multiplexer.addProcessor(galaxyStage);
		multiplexer.removeProcessor(planetStage);
		multiplexer.removeProcessor(planetUI);
		multiplexer.removeProcessor(sectorStage);
	}

	public void goSystem() {
		planetView = false;
		sectorView = true;
		galaxyView = false;
		
		navBar.setName(curSector.getName());
		
		multiplexer.addProcessor(sectorStage);
		multiplexer.removeProcessor(planetStage);
		multiplexer.removeProcessor(planetUI);
	}

	@Override
	public void show() {
		//Start music and take input when you switch to this window
		musicLoop.play();
		musicLoop.setLooping(true);
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
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (galaxyView) {
			galaxyStage.draw();
		} else if (sectorView) {
			sectorStage.draw();
		} else if (planetView) {
			planetStage.draw();
			planetUI.draw();

		}
		navBar.act();
		navBar.draw();
		infoBar.act();
		infoBar.draw();
	}

	public void goMenu() {
		game.goMenu();
	}
	
	public void endTurn() {
		galaxyStage.act();
		planetStage.act();
		sectorStage.act();
		goGalaxy();
	}
}