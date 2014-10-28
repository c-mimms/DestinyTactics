package se300.destinytactics;

import se300.destinytactics.game.AI;
import se300.destinytactics.game.User;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.game.orbitalbodies.Planet;
import se300.destinytactics.game.scenes.Defense;
import se300.destinytactics.game.scenes.FleetCommand;
import se300.destinytactics.game.scenes.Infrastructure;
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
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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

	public Stage galaxyStage, sectorStage, planetStage, sectorUI, planetUI;
	public Table managementInterface;
	public FleetCommand fc;
	public Infrastructure inf;
	public Defense def;
	public boolean sectorView = false;
	public boolean galaxyView = true;
	public boolean planetView = false;
	public Texture bgimg;
	public Texture sectorSun;
	public Texture backButton;
	InputMultiplexer multiplexer;
	public Skin skin;
	private String spriteLib = "realorbitalbody";
	public DestinyTactics game;
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;

	public GameScene(DestinyTactics game) {
		this.game = game;
		
		musicLoop = Gdx.audio.newMusic(Gdx.files.internal("music/galaxyLoop.mp3"));
		selectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/select2.wav"));
		
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

		// Debugger toggles. Make borders around actors and regions. Turn OFF
		// for demo
		// galaxyStage.setDebugAll(true);
		// sectorStage.setDebugAll(true);
		// planetStage.setDebugAll(true);
		// sectorUI.setDebugAll(true);
		// planetUI.setDebugAll(true);

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(galaxyStage);
		Gdx.input.setInputProcessor(multiplexer);

		// Add Actors to sector UI
		final TextButton backButton_Galaxy = new TextButton("Back to Galaxy",
				skin.get("default", TextButtonStyle.class));
		Image bar = new Image(new Texture("sun.png"));
		Image bar2 = new Image(new Texture("sun.png"));
		sectorUI.addActor(bar);
		sectorUI.addActor(bar2);
		sectorUI.addActor(backButton_Galaxy);
		bar.setScaleX(SCREEN_WIDTH);
		bar.setHeight(SCREEN_HEIGHT / 10);
		bar.setY(SCREEN_HEIGHT - bar.getHeight());
		bar2.setScaleX(GALAXY_WIDTH);
		bar2.setHeight(SCREEN_HEIGHT / 5);
		bar2.setY(0);
		backButton_Galaxy.setX(0);
		backButton_Galaxy.setY(SCREEN_HEIGHT - backButton_Galaxy.getHeight());
		backButton_Galaxy.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				goGalaxy();
				return true;
			}
		});

		// Add Actors to planet UI
		fc = new FleetCommand(skin);
		inf = new Infrastructure(skin);
		def = new Defense(skin);

		managementInterface = new Table();
		managementInterface.add(fc.getFleetCommand()).expand().top();
		managementInterface.setHeight(GameScene.SCREEN_HEIGHT * 7 / 10);
		managementInterface.setWidth(GameScene.SCREEN_WIDTH / 2);
		managementInterface.setY(GameScene.SCREEN_HEIGHT * 2 / 10);
		managementInterface.setX(GameScene.SCREEN_WIDTH / 2);

		TextButton backButton_Sector = new TextButton("Back to Sector",
				skin.get("default", TextButtonStyle.class));
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

		Image bar3 = new Image(new Texture("sun.png"));
		Image bar4 = new Image(new Texture("sun.png"));

		planetUI.addActor(managefleet);
		planetUI.addActor(manageInfrastructure);
		planetUI.addActor(manageDefense);
		planetUI.addActor(managementInterface);
		// planetUI.addActor(def.getDefense());
		// planetUI.addActor(inf.getInfrastructure());
		// planetUI.addActor(fc.getFleetCommand());
		planetUI.addActor(bar3);
		planetUI.addActor(bar4);
		planetUI.addActor(backButton_Sector);

		bar3.setScaleX(SCREEN_WIDTH);
		bar3.setHeight(SCREEN_HEIGHT / 10);
		bar3.setY(SCREEN_HEIGHT - bar.getHeight());
		bar4.setScaleX(GALAXY_WIDTH);
		bar4.setHeight(SCREEN_HEIGHT / 5);
		bar4.setY(0);
		backButton_Sector.setX(0);
		backButton_Sector.setY(SCREEN_HEIGHT - backButton_Sector.getHeight());
		managefleet.setX(0);
		managefleet.setY(managefleet.getHeight() * 9);
		manageInfrastructure.setX(0);
		manageInfrastructure.setY(managefleet.getHeight() * 8);
		manageDefense.setX(0);
		manageDefense.setY(managefleet.getHeight() * 7);
		backButton_Sector.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				goSystem();
				return true;
			}

		});

		// Set galaxy stage to get inputs.
		Image background = new Image(bgimg);
		Image bar5 = new Image(new Texture("sun.png"));
		Image bar6 = new Image(new Texture("sun.png"));
		bar5.setScaleX(SCREEN_WIDTH);
		bar5.setHeight(SCREEN_HEIGHT / 10);
		bar5.setY(SCREEN_HEIGHT - bar.getHeight());
		bar6.setScaleX(GALAXY_WIDTH);
		bar6.setHeight(SCREEN_HEIGHT / 5);
		bar6.setY(0);
		galaxyStage.addActor(background);
		galaxyStage.addActor(bar5);
		galaxyStage.addActor(bar6);
		background.setFillParent(true);

		m_Galaxy = new Galaxy(GALAXY_WIDTH, GALAXY_HEIGHT, NUMBER_SECTORS, this);

		for (int i = 0; i < m_Galaxy.sectors.length; i++) {
			galaxyStage.addActor(m_Galaxy.sectors[i]);
		}
	}

	public void render() {
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
		
		selectSound.play();

		// Clear stage to reuse it
		sectorStage.clear();

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		Image sun = new Image(sectorSun);
		sectorStage.addActor(background);
		sectorStage.addActor(sun);
		sun.setX(SCREEN_WIDTH - sectorSun.getWidth());
		sun.setY(SCREEN_HEIGHT / 2 + SCREEN_HEIGHT / 20 - sectorSun.getHeight()
				/ 2);

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

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		Image orbitalBody = new Image(
				nextOrbitalBody.hotBod[nextOrbitalBody.getType()]);
		orbitalBody.setSize(nextOrbitalBody.getSpriteSize() * 10,
				nextOrbitalBody.getSpriteSize() * 10);
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
		multiplexer.addProcessor(galaxyStage);
		multiplexer.removeProcessor(sectorStage);
		multiplexer.removeProcessor(sectorUI);
	}

	public void goSystem() {
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
	}
}// end Game