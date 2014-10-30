package se300.destinytactics;

import se300.destinytactics.game.AI;
import se300.destinytactics.game.User;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.scenes.Defense;
import se300.destinytactics.game.scenes.FleetCommand;
import se300.destinytactics.game.scenes.GalaxyScene;
import se300.destinytactics.game.scenes.InfoBar;
import se300.destinytactics.game.scenes.Infrastructure;
import se300.destinytactics.game.scenes.NavBar;
import se300.destinytactics.ui.Drawable;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.javafx.scene.control.skin.LabelSkin;

public class GameScene implements Screen {

	// OLD Variables, Decide which need to be kept
	// private SpriteBatch batch;
	// private Rectangle sun;
	// private Texture selImage;
	// private Galaxy gameMap;
	// private ArrayList<Drawable> objToDraw;
	// private boolean start;
	// public AI m_AI;
	// public User m_User;
	// ShapeRenderer renderer;
	// Vector3 mousePos = new Vector3();
	public Galaxy m_Galaxy;

	// NEW variables, put necessary ones here.
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int GALAXY_WIDTH = 1024;
	public static final int GALAXY_HEIGHT = 640;
	public static final int NUMBER_SECTORS = 20;

	public Stage galaxyStage, sectorStage, planetStage, sectorUI, planetUI,
			infoBarStage;
	public NavBar navBar;
	public InfoBar infoBar;
	public static final int PADDING = 20;

	public Table managementInterface;
	public FleetCommand fc;
	public Infrastructure inf;
	public Defense def;
	public boolean sectorView = false;
	public boolean galaxyView = true;
	public boolean planetView = false;
	public Texture bgimg, bgimg_galaxy;
	public Texture sectorSun;
	public Texture backButton;
	InputMultiplexer multiplexer;
	public Skin skin;
	private String spriteLib = "realorbitalbody";
	public DestinyTactics game;
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;
	public Image background;
	public Sector curSector;
	// Label infoBar;
	TextField txt1, txt2;

	public GameScene(DestinyTactics game, Skin skin) {
		this.game = game;

		musicLoop = Gdx.audio.newMusic(Gdx.files
				.internal("music/SimplicityIsBliss.mp3"));

		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));

		bgimg = new Texture("StarfieldBackground.jpg");
		bgimg_galaxy = new Texture("GalaxyBackground.jpg");
		sectorSun = new Texture(spriteLib + "/sun1.png");
		backButton = new Texture("backbutton.png");

		m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS, this);

		// Create galaxy stage on game initialization.
		galaxyStage = new GalaxyScene(new FitViewport(SCREEN_WIDTH,
				SCREEN_HEIGHT), this);
		sectorStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		planetStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		sectorUI = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		planetUI = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		infoBar = new InfoBar(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT),
				PADDING, this);
		navBar = new NavBar(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT),
				PADDING, this);

		// Debugger toggles. Make borders around actors and regions. Turn OFF
		// for demo
		// galaxyStage.setDebugAll(true);
		// sectorStage.setDebugAll(true);
		// planetStage.setDebugAll(true);
		// sectorUI.setDebugAll(true);
		// planetUI.setDebugAll(true);
		//navBar.setDebugAll(true);

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(galaxyStage);
		multiplexer.addProcessor(navBar);
		multiplexer.addProcessor(infoBar);
		Gdx.input.setInputProcessor(multiplexer);

		// Add Actors to planet UI
		fc = new FleetCommand(skin);
		inf = new Infrastructure(skin);
		def = new Defense(skin);

		managementInterface = new Table();
		managementInterface.add(fc.getFleetCommand()).expand().top();
		managementInterface.setHeight(GameScene.SCREEN_HEIGHT * 7 / 10);
		managementInterface.setWidth(GameScene.SCREEN_WIDTH / 2);
		managementInterface.setY(GameScene.SCREEN_HEIGHT * 2 / 10);
		managementInterface.setX(GameScene.SCREEN_WIDTH / 2 - PADDING);

		TextButton managefleet = new TextButton("Fleet Command", skin.get(
				"default", TextButtonStyle.class));
		TextButton manageInfrastructure = new TextButton("Infrastructure",
				skin.get("default", TextButtonStyle.class));
		TextButton manageDefense = new TextButton("Defense", skin.get(
				"default", TextButtonStyle.class));

		// Add Click listeners. Changes the loaded form and the toggled button.
		managefleet.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				setManagementInterface("Fleet");
				return true;
			}
		});

		manageInfrastructure.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				setManagementInterface("Infrastructure");
				return true;
			}
		});

		manageDefense.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				setManagementInterface("Defense");
				return true;
			}
		});

		planetUI.addActor(managefleet);
		planetUI.addActor(manageInfrastructure);
		planetUI.addActor(manageDefense);
		planetUI.addActor(managementInterface);


		managefleet.setX(PADDING);
		managefleet.setY(managefleet.getHeight() * 9);
		manageInfrastructure.setX(PADDING);
		manageInfrastructure.setY(managefleet.getHeight() * 8);
		manageDefense.setX(PADDING);
		manageDefense.setY(managefleet.getHeight() * 7);

	}

	public void resize(int width, int height) {
		// Resize stage to fill window.
		galaxyStage.getViewport().update(width, height, false);
		sectorStage.getViewport().update(width, height, false);
		planetStage.getViewport().update(width, height, false);
		sectorUI.getViewport().update(width, height, false);
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

	public void endTurn() {

	}

	public void switchView(Sector nextSector) {

		curSector = nextSector;
		selectSound.play();

		// Clear stage to reuse it
		sectorStage.clear();

		navBar.setName(nextSector.getName());

		// Add image background and stretch to fit
		background = new Image(bgimg);
		Image sun = new Image(Sector.sunTypes[nextSector.sunType]);
		sectorStage.addActor(background);
		sectorStage.addActor(sun);
		sun.setSize(SCREEN_HEIGHT, SCREEN_HEIGHT);
		sun.setOrigin(SCREEN_HEIGHT / 2, SCREEN_HEIGHT / 2);
		sun.setRotation(nextSector.sunRotation);
		sun.setX(SCREEN_WIDTH - sun.getWidth() * (3.0f / 8.0f));
		sun.setY(SCREEN_HEIGHT / 2 - sun.getHeight() / 2);

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

		selectSound.play();
		// System.out.println("Go to MyGame Method");
		// Clear stage to reuse it
		planetStage.clear();

		navBar.setName(nextOrbitalBody.getName());

		// Add image background and stretch to fit
		background = new Image(bgimg);
		Image orbitalBody = new Image(
				OrbitalBody.hotBod[nextOrbitalBody.getType()]);
		orbitalBody.setSize(1000, 1000);
		planetStage.addActor(background);
		planetStage.addActor(orbitalBody);
		orbitalBody.setX(SCREEN_WIDTH / 4 - orbitalBody.getWidth() / 2);
		orbitalBody.setY(SCREEN_HEIGHT / 2 - orbitalBody.getHeight() / 2);

		background.setFillParent(true);

		planetView = true;
		galaxyView = false;
		sectorView = false;
		multiplexer.addProcessor(planetStage);
		multiplexer.addProcessor(planetUI);
		multiplexer.removeProcessor(sectorStage);
		multiplexer.removeProcessor(sectorUI);
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
		multiplexer.removeProcessor(sectorUI);
	}

	public void goSystem() {
		planetView = false;
		sectorView = true;
		galaxyView = false;
		navBar.setName(curSector.getName());
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

	public void setManagementInterface(String formType) {
		Cell cell = getFormCell();

		if (cell.hasActor()) {
			cell.clearActor();

			if (formType == "Fleet") {
				cell.setActor(fc.getFleetCommand());
			} else if (formType == "Infrastructure") {
				cell.setActor(inf.getInfrastructure());
			} else if (formType == "Defense") {
				cell.setActor(def.getDefense());
			}
		}
	}

	// Private Methods
	private Cell getFormCell() {
		Cell cell;

		cell = managementInterface.getCell(fc.getFleetCommand());
		if (cell == null) {
			cell = managementInterface.getCell(inf.getInfrastructure());
		}
		if (cell == null) {
			cell = managementInterface.getCell(def.getDefense());
		}

		return cell;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
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
			galaxyStage.act();
			galaxyStage.draw();
		} else if (sectorView) {
			sectorStage.act();
			sectorStage.draw();
			sectorUI.draw();
		} else if (planetView) {
			planetStage.act();
			planetStage.draw();
			planetUI.draw();

		}
		navBar.act();
		navBar.draw();
		infoBar.draw();
	}

	public void goMenu() {
		game.goMenu();
	}
}// end Game