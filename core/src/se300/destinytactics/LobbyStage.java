package se300.destinytactics;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se300.destinytactics.game.WebRequest;
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
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Team Guardian Extends Stage in the LibGDX framework. Displays the UI
 *         for selecting and joining games.
 */
public class LobbyStage extends Stage implements MakesRequests {

	public MultiplayerScreen myGame;
	public Skin skin, skin2;
	int edgePadding;
	int buttonPadding = 5;
	public Sound selectSound;
	public InputMultiplexer multiplexer;
	public Stage menuStage;
	public Table lobby, menu, gameListView, gameDetailsView, gameListRows,
			playerRows;
	public VerticalGroup gamesList;
	public ScrollPane gameListScroll, playerRowsScroll;
	public float masterVolume = 0.5f;
	public int userID;
	public Texture bgimg;
	public Image logo, background;
	public TextButton createGameButton, menuButton, continueButton;
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
		this.addActor(background);

		gameList = new ArrayList<GameListItem>();

		// AHH WTF TABLES!
		// Mimms implementation

		skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));

		// Create table to organize entire screen
		lobby = new Table(skin2);
		lobby.setHeight(DestinyTactics.SCREEN_HEIGHT);
		lobby.setWidth(DestinyTactics.SCREEN_WIDTH);
		lobby.setY(0);
		lobby.setX(0);
		lobby.pad(edgePadding);
		
		// Create Buttons
		 createGameButton = new TextButton("Create Game", 
				 						   skin.get("default", TextButtonStyle.class));
		 menuButton = new TextButton("Back to Menu",
				 					 skin.get("default", TextButtonStyle.class));
		 
		 continueButton = new TextButton("Continue",
					 skin.get("default", TextButtonStyle.class));
		
		 createGameButton.setWidth(menuButton.getWidth());
		 
		 continueButton.setWidth(menuButton.getWidth());
		
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
		 
		 createGameButton.addListener(new ClickListener() {
			 public boolean touchDown(InputEvent event, float x, float y,
				 int pointer, int button) {
					 createGame();
					 return true;
				 }
		 });
		
		// Create a vertical group for list of games
		gamesList = new VerticalGroup();
		gamesList.space(10).pad(edgePadding).fill();
		gamesList.addActor(new HorizontalGroup(){{
			addActor(new Label("Creator", skin2));
			addActor(new Label("Alliances", skin2));
			addActor(new Label("Players", skin2));
			addActor(new Label("Status", skin2));
			space(85);
		}}
		);
		
		// Scroll pane to hold the list of games
		gameListScroll = new ScrollPane(gamesList, skin2);
		gameListScroll.setScrollingDisabled(true, false);
		
		// Create table for detail info of chosen game
		gameDetailsView = new Table();
		gameDetailsView.pad(edgePadding);
		gameDetailsView.add(new HorizontalGroup(){{
			addActor(new Label("Creator", skin2));
			addActor(new Label("Alliances", skin2));
			addActor(new Label("Players", skin2));
			addActor(new Label("Date", skin2));
			space(10);}}.top()).top().left().fill().expand();

		// Add everything to lobby
		lobby.add("Lobby").align(Align.left).height(lobby.getHeight() / 14);
		lobby.add(menuButton).align(Align.right).height(lobby.getHeight() / 14);
		lobby.row();
		lobby.add(gameListScroll).left().fill().width(Value.percentWidth(0.50f, lobby));
		lobby.add(gameDetailsView).left().top().fill().expand();
		lobby.row();
		lobby.add(createGameButton).align(Align.left).height(lobby.getHeight() / 14);
		lobby.add(continueButton).align(Align.right).height(lobby.getHeight() / 14);

		lobby.debugAll();

		this.addActor(lobby);

						// skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));
						// lobby = new Table(skin2);
						//
						// lobby.setHeight(DestinyTactics.SCREEN_HEIGHT);
						// lobby.setWidth(DestinyTactics.SCREEN_WIDTH);
						// lobby.setY(0);
						// lobby.setX(0);
						// lobby.pad(edgePadding);
						//
						// int menuCellHeight = (DestinyTactics.SCREEN_HEIGHT - (2 *
						// edgePadding)) / 14;
						// int lobbyCellWidth = (int) ((lobby.getWidth() / 2) - (edgePadding /
						// 2));
						// int lobbyCellHeight = (int) (lobby.getHeight() - menuCellHeight);
						//
						// gameListView = new Table();
						//
						// gameListRows = new Table(skin2);
						// gameListRows.setWidth(lobbyCellWidth);
						// gameListRows.setHeight(lobbyCellHeight);
						// gameListScroll = new ScrollPane(gameListRows, skin2);
						// gameListView.add(gameListScroll).fillX();
						//
						// gameListRows.add("Creator").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Alliances").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Players").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Status").top().width(lobbyCellWidth / 4);
						// gameListRows.row();
						// gameListRows.add("Username1").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Yes").top().width(lobbyCellWidth / 4);
						// gameListRows.add("8/8").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Active").top().width(lobbyCellWidth / 4);
						// gameListRows.row();
						// gameListRows.add("Username2").top().width(lobbyCellWidth / 4);
						// gameListRows.add("No").top().width(lobbyCellWidth / 4);
						// gameListRows.add("4/4").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Active").top().width(lobbyCellWidth / 4);
						// gameListRows.row();
						// gameListRows.add("Username3").top().width(lobbyCellWidth / 4);
						// gameListRows.add("No").top().width(lobbyCellWidth / 4);
						// gameListRows.add("4/8").top().width(lobbyCellWidth / 4);
						// gameListRows.add("Pending").top().width(lobbyCellWidth / 4);
						//
						//
						// gameDetailsView = new Table();
						// gameDetailsView.setFillParent(true);
						// playerRows = new Table(skin2);
						// playerRowsScroll = new ScrollPane(playerRows, skin2);
						// gameDetailsView.add(playerRowsScroll);
						//
						//
						// lobby.add("Lobby").align(Align.left).height(menuCellHeight);
						// lobby.add("Menu").align(Align.right).height(menuCellHeight);
						// lobby.row();
						// lobby.add(gameListView);
						// lobby.add(gameDetailsView).expand();
						//
						// this.addActor(lobby);
						//
						// lobby.setDebug(true);
						// gameListView.setDebug(true);
						// gameDetailsView.setDebug(true);
						// gameListRows.setDebug(true);
						// playerRows.setDebug(true);
						//
						// System.out.println(lobby.getX());

		

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
		// json.setUsePrototypes(false);
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
		// System.out.println(map);
		if (map.containsKey("gameList")) {
			JsonValue root = new JsonReader().parse(map.get("gameList")
					.toString());

			for (JsonValue entry = root.child; entry != null; entry = entry.next) {
				final GameListItem tmp = json.fromJson(GameListItem.class,
						entry.toString());

				tmp.update();

				tmp.addListener(new ClickListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						joinGame(tmp.gameID);
						return true;
					}
				});

				gamesList.addActor(tmp);

			}
		}
		// Join game return
		else if (map.containsKey("gameObj")) {
			GameJson games = json.fromJson(GameJson.class, map.get("gameObj")
					.toString());
			//System.out.println(map);
			Utility.setSeed(games.galaxySeed);
			this.startGame = true;
			this.lastGame = games;

		}
		// Create game return
		else if (map.containsKey("gameDataObject")) {
			@SuppressWarnings("unused")
			GameJson games = json.fromJson(GameJson.class,
					map.get("gameDataObject").toString());
		}
		// System.out.println(Thread.activeCount());
		gamesList.validate();
		lobby.debugAll();
	}

	/**
	 * List active & pending games.
	 */
	public void listGames() {

		// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"",
		// "createDate":"","gameID":"","status":"","sectors":[],"roundsCompleted":""}}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "getGameList");
		//parameters.put("status", "pending");
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
