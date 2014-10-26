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
	public Table container, overview, manager, dataGrid, form;
	public Skin skin;

	public void create() {
		
		bgimg = new Texture("background.png");
		sectorSun = new Texture("realorbitalbody/sun1.png");
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
		
		//Add buttons to sector UI
		Image backbut = new Image(backButton);
		Image bar = new Image(new Texture("sun.png"));
		Image bar2 = new Image(new Texture("sun.png"));
		sectorUI.addActor(bar);
		sectorUI.addActor(bar2);
		sectorUI.addActor(backbut);
		bar.setScaleX(SCREEN_WIDTH);
		bar.setHeight(SCREEN_HEIGHT/10);
		bar.setY(SCREEN_HEIGHT - bar.getHeight());
		bar2.setScaleX(GALAXY_WIDTH);
		bar2.setHeight(SCREEN_HEIGHT/5);
		bar2.setY(0);
		backbut.setX(0);
		backbut.setY(SCREEN_HEIGHT - backbut.getHeight());
		backbut.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				goGalaxy();
				return true;
			}

		});
		
		container = new Table(skin);
		overview = new Table(skin);
		dataGrid = new Table(skin);
		manager = new Table(skin);
		form = new Table(skin);

		final ScrollPane overviewScroll = new ScrollPane(dataGrid, skin);
		InputListener stopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		dataGrid.defaults().expandX();
		
		dataGrid.row();
		dataGrid.add(new Label("Fighters", skin)).width(SCREEN_WIDTH/8);
		dataGrid.add("1000").width(SCREEN_WIDTH/8);
		
		dataGrid.add(new Label("Corvettes", skin)).width(SCREEN_WIDTH/8);
		dataGrid.add("40").width(SCREEN_WIDTH/8);
		
		dataGrid.row();
		dataGrid.add(new Label("Bombers", skin)).expandX().fillX();
		dataGrid.add("300").expandX().fillX();
		
		dataGrid.add(new Label("Carriers", skin)).expandX().fillX();
		dataGrid.add("12").expandX().fillX();
		
		dataGrid.row();
		dataGrid.add(new Label("Scoutships", skin)).expandX().fillX();
		dataGrid.add("3").expandX().fillX();
		
		dataGrid.add(new Label("Battleships", skin)).expandX().fillX();
		dataGrid.add("7").expandX().fillX();
		
		dataGrid.row();
		dataGrid.add().expandX().fillX();
		dataGrid.add().expandX().fillX();
		dataGrid.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		dataGrid.add("1").expandX().fillX();
		
		final TextButton buildButton = new TextButton("Build Units", skin.get("default", TextButtonStyle.class));
		final TextButton moveButton = new TextButton("Move Fleet", skin.get("default", TextButtonStyle.class));
		final TextButton attackButton = new TextButton("Attack", skin.get("default", TextButtonStyle.class));
		
		overview.add("Fleet Overview").colspan(3).left().expandX();
		overview.row().top();                 
		overview.add(overviewScroll).expand().colspan(3).fill().top();
		overview.row().top().expandX();
		overview.add(buildButton).right().top();
		overview.add(moveButton).top();
		overview.add(attackButton).left().top();
		
		overview.setWidth(SCREEN_WIDTH/2);
		overview.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
		
		final ScrollPane formScroll = new ScrollPane(form, skin);
		InputListener formStopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		form.defaults().expandX();
		
		form.row();
		form.add(new Label("Fighters", skin)).width(SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox = new TextField("", skin);
		//unitCountTextBox.DigitsOnlyFilter();
		form.add(unitCountTextBox).width(SCREEN_WIDTH/8).expandX().fillX();
		
		form.add(new Label("Corvettes", skin)).width(SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox5 = new TextField("", skin);
		form.add(unitCountTextBox5).width(SCREEN_WIDTH/8).expandX().fillX();
		
		form.row();
		form.add(new Label("Bombers", skin)).expandX().fillX();
		TextField unitCountTextBox2 = new TextField("", skin);
		form.add(unitCountTextBox2).width(SCREEN_WIDTH/8).expandX().fillX();
		
		form.add(new Label("Carriers", skin)).expandX().fillX();
		TextField unitCountTextBox4 = new TextField("", skin);
		form.add(unitCountTextBox4).width(SCREEN_WIDTH/8).expandX().fillX();
		
		form.row();
		form.add(new Label("Scoutships", skin)).expandX().fillX();
		TextField unitCountTextBox3 = new TextField("", skin);
		form.add(unitCountTextBox3).width(SCREEN_WIDTH/8).expandX().fillX();
		
		form.add(new Label("Battleships", skin)).expandX().fillX();
		TextField unitCountTextBox6 = new TextField("", skin);
		form.add(unitCountTextBox6).width(SCREEN_WIDTH/8).expandX().fillX();
		
		form.row();
		form.add().expandX().fillX();
		form.add().expandX().fillX();
		
		form.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		TextField unitCountTextBox7 = new TextField("", skin);
		form.add(unitCountTextBox7).width(SCREEN_WIDTH/8).expandX().fillX();

		final TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		final TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		final TextButton submitButton = new TextButton("Send Fleet", skin.get("default", TextButtonStyle.class));
		
		manager.add("Move Fleet").colspan(3).left().expandX();
		manager.row().top();                 
		manager.add(formScroll).expand().colspan(3).fill().top();
		manager.row().top().expandX();
		manager.add(cancelButton).right().top();
		manager.add(clearButton).top();
		manager.add(submitButton).left().top();
		
		manager.setWidth(SCREEN_WIDTH/2);
		manager.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
		
		container.add("FLEET COMMAND").expandX().top().height(GameScene.SCREEN_HEIGHT * 1/10);
		container.row();
		container.add(overview).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		container.row();
		container.add(manager).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		
		container.setHeight(SCREEN_HEIGHT * 7/10);
		container.setWidth(SCREEN_WIDTH/2);
		container.setY(SCREEN_HEIGHT * 2/10);
		container.setX(SCREEN_WIDTH/2);
		
		
		//Addbuttons to planet UI
		Image backbutt = new Image(backButton);
		final TextButton managefleet = new TextButton("Fleet Command", skin.get("default", TextButtonStyle.class));
		final TextButton manageInfrastructure = new TextButton("Infrastructure", skin.get("default", TextButtonStyle.class));
		final TextButton manageDefense = new TextButton("Defense", skin.get("default", TextButtonStyle.class));
		Image bar3 = new Image(new Texture("sun.png"));
		Image bar4 = new Image(new Texture("sun.png"));	
		
		planetUI.addActor(managefleet);
		planetUI.addActor(manageInfrastructure);
		planetUI.addActor(manageDefense);
		planetUI.addActor(container);
		planetUI.addActor(backbutt);
		planetUI.addActor(bar3);
		planetUI.addActor(bar4);
		
		bar3.setScaleX(SCREEN_WIDTH);
		bar3.setHeight(SCREEN_HEIGHT/10);
		bar3.setY(SCREEN_HEIGHT - bar.getHeight());
		bar4.setScaleX(GALAXY_WIDTH);
		bar4.setHeight(SCREEN_HEIGHT/5);
		bar4.setY(0);
		backbutt.setX(0);
		backbutt.setY(SCREEN_HEIGHT - backbutt.getHeight());
		managefleet.setX(0);
		managefleet.setY(managefleet.getHeight()*9);
		manageInfrastructure.setX(0);
		manageInfrastructure.setY(managefleet.getHeight()*8);
		manageDefense.setX(0);
		manageDefense.setY(managefleet.getHeight()*7);
		backbutt.addListener(new ClickListener() {
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
		System.out.println("Go to MyGame Method");
		// Clear stage to reuse it
		planetStage.clear();

		// Add image background and stretch to fit
		Image background = new Image(bgimg);
		Image orbitalBody = new Image(nextOrbitalBody.hotBod[nextOrbitalBody.getType()]);
		orbitalBody.setSize(nextOrbitalBody.getSpriteSize()*10, nextOrbitalBody.getSpriteSize()*10);
		planetStage.addActor(background);
		planetStage.addActor(orbitalBody);
		//planetStage.addActor(container);
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