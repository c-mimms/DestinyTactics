package se300.destinytactics;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se300.destinytactics.game.WebRequest;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Team Guardian Extends Stage in the LibGDX framework. Displays the UI
 *         for selecting and joining games.
 */
public class LobbyStage extends Stage implements MakesRequests {

	public MultiplayerScreen myGame;
	public Skin skin;
	int edgePadding;
	int buttonPadding = 5;
	public Sound selectSound;
	public InputMultiplexer multiplexer;
	public Stage menuStage;
	public Table menu;
	public float masterVolume = 0.5f;
	public int userID;
	public Texture bgimg;
	public Image logo, background;
	public TextButton createGameButton, menuButton;
	MessageDigest messageDigest;
	String name, passwordHash;
	Label status;
	ArrayList<GameListItem> gameList;
	boolean startGame = false;
	GameJson lastGame;

	/**
	 * LobbyStage constructor. Builds the Lobby menu actors.
	 * 
	 * @param vp
	 * @param padding
	 * @param skin
	 *            Skin to use for the screen.
	 * @param myGame
	 *            Parent game class that contains this screen.
	 * 
	 */
	public LobbyStage(Viewport vp, int padding, Skin skin,
			final MultiplayerScreen myGame) {

		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;
		bgimg = new Texture("MenuBackground.jpg");
		background = new Image(bgimg);
		gameList = new ArrayList<GameListItem>();
		menu = new Table();
		// menu.setDebug(true);

		createGameButton = new TextButton("Create Game", skin.get("default",
				TextButtonStyle.class));
		menuButton = new TextButton("Back to Menu", skin.get("default",
				TextButtonStyle.class));

		createGameButton.setWidth(menuButton.getWidth());

		menuButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				goMenu();
				return true;
			}
		});

		createGameButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				createGame();
				return true;
			}
		});

		menu.setFillParent(true);
		menu.add(createGameButton);
		menu.add(menuButton);
		menu.setX(padding);
		menu.setY(padding);
		this.addActor(background);
		this.addActor(menu);
		listGames();
	}

	public void act(float time) {
		super.act(time);
		if (startGame) {
			startGame = false;
			myGame.game.startGame();
			if (lastGame != null) {
				lastGame.update();
			}
		}
	}

	/**
	 * Calls the goMenu method in DestinyTactics object to switch screens.
	 */
	public void goMenu() {
		myGame.goMenu();
	}

	/**
	 * Starts the loaded game.
	 */
	public void createGame() {

		GameScene.preloadGalaxy();

		// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"",
		// "createDate":"","gameID":"","status":"","sectors":[],"roundsCompleted":""}}

		GameJson game = new GameJson(Utility.getSeed());
		Json json = new Json();
		json.setOutputType(OutputType.json);
		//json.setUsePrototypes(false);
		System.out.println(json.toJson(game));

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "saveGame");
		parameters.put("gameDataObject", json.toJson(game));
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		System.out.println(httpGet.getContent());

		WebRequest createReq = new WebRequest(this, httpGet);
		createReq.start();

		this.startGame = true;

	}

	@Override
	public void http(OrderedMap<String, Object> map) {
		Json json = new Json();
		//System.out.println(map);
		if (map.containsKey("gameList")) {
			JsonValue root = new JsonReader().parse(map.get("gameList")
					.toString());

			for (JsonValue entry = root.child; entry != null; entry = entry.next) {
				final GameListItem tmp = json.fromJson(GameListItem.class,
						entry.toString());
				gameList.add(tmp);

				tmp.addListener(new ClickListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						joinGame(tmp.gameID);
						return true;
					}
				});
				this.addActor(tmp);
			}
			for (GameListItem item : gameList) {
				item.update();
			}
		}
		// Join game return
		else if (map.containsKey("gameObj")) {
			GameJson games = json.fromJson(GameJson.class, map.get("gameObj")
					.toString());
			// System.out.println(map);
			Utility.setSeed(games.galaxySeed);
			this.startGame = true;
			this.lastGame = games;

		}
		// Create game return
		else if (map.containsKey("gameDataObject")) {
			GameJson games = json.fromJson(GameJson.class,
					map.get("gameDataObject").toString());
		}
		// System.out.println(Thread.activeCount());
	}

	/**
	 * List pending games.
	 */
	public void listGames() {

		// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"",
		// "createDate":"","gameID":"","status":"","sectors":[],"roundsCompleted":""}}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "getGameList");
		parameters.put("status", "pending");
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest listReq = new WebRequest(this, httpGet);
		listReq.start();

	}

	/**
	 * Join a game.
	 */
	public void joinGame(int id) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "loadGame");
		parameters.put("gameID", "" + id);
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest joinReq = new WebRequest(this, httpGet);
		joinReq.start();

	}
}
