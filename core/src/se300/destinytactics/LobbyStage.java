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
	public Table lobby, menu, gameListView, gameDetailsView, playerListWrapper;
	public VerticalGroup gamesList, playerList;
	public ScrollPane gameListScroll, playerListScroll;
	public float masterVolume = 0.5f;
	public int userID, loadedGameID;
	public Texture bgimg;
	public Image logo, background;
	public TextButton createGameButton, menuButton, continueButton;
	MessageDigest messageDigest;
	String name, passwordHash;
	Label status, galaxyLabel, sectorCountLabel, maxPlayersLabel;
	ClickListener continueListener;
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
		loadedGameID = 0;
		gameList = new ArrayList<GameListItem>();

		// AHH WTF TABLES!
		// Mimms implementation

		skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));

		// Create table to organize entire screen
		lobby = new Table(skin2);
		lobby.setHeight(DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding));
		lobby.setWidth(DestinyTactics.SCREEN_WIDTH - (2 * edgePadding));
		lobby.setY(edgePadding);
		lobby.setX(edgePadding);
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
		 
		 continueButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					joinGame(loadedGameID);
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
		
		galaxyLabel = new Label("", skin2);
		sectorCountLabel = new Label("", skin2);
		maxPlayersLabel = new Label("", skin2);
		
		// Create table for detail info of chosen game
		gameDetailsView = new Table(skin2);
		gameDetailsView.pad(edgePadding);
		//gameDetailsView.setWidth(Value.percentWidth(0.45f, lobby));
		gameDetailsView.add("Game Details").colspan(2);
		gameDetailsView.row();
		gameDetailsView.add(new Label("Galaxy:", skin2)).expandX();
		gameDetailsView.add(galaxyLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Sectors:", skin2)).expand();
		gameDetailsView.add(sectorCountLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Max Players:", skin2)).expand();
		gameDetailsView.add(maxPlayersLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Players", skin2)).colspan(2).expandX();
		
		// Create a vertical group for list of games
		playerList = new VerticalGroup();
		playerList.space(10).pad(edgePadding).fill();
		
		playerListScroll = new ScrollPane(playerList, skin2);
		
		playerListWrapper = new Table(skin2);
		playerListWrapper.add("Username");
		playerListWrapper.add("Alliance");
		playerListWrapper.add("Rank");
		playerListWrapper.row();
		playerListWrapper.add(playerListScroll).colspan(3);
		
		gameDetailsView.row();
		gameDetailsView.add(playerListWrapper).colspan(2).expand().top();


		// Add everything to lobby
		lobby.add("Lobby").align(Align.left).height(lobby.getHeight() / 14);
		lobby.add(menuButton).align(Align.right).height(lobby.getHeight() / 14);
		lobby.row();
		lobby.add(gameListScroll).left().fill().width(Value.percentWidth(0.50f, lobby));
		lobby.add(gameDetailsView).left().top().fill().expandY().width(Value.percentWidth(0.50f, lobby));
		lobby.row();
		lobby.add(createGameButton).align(Align.left).height(lobby.getHeight() / 14);
		lobby.add(continueButton).align(Align.right).height(lobby.getHeight() / 14);

		lobby.debugAll();

		this.addActor(lobby);

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

		WebRequest createReq = new WebRequest(this, httpGet);
		createReq.start();

		this.startGame = true;

	}

	@Override
	public void http(OrderedMap<String, Object> map) {
		Json json = new Json();
		// System.out.println(map);
		if (map.containsKey("gameList")) {
			JsonValue root = new JsonReader().parse(map.get("gameList").toString());

			for (JsonValue entry = root.child; entry != null; entry = entry.next) {
				final GameListItem tmp = json.fromJson(GameListItem.class, entry.toString());

				tmp.update();
				
				tmp.addListener(new ClickListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						loadGameDetails(tmp);
						//joinGame(tmp.gameID);
						return true;
					}
				});

				gamesList.addActor(tmp);

			}
		}
		if (map.containsKey("playerList")) {
			JsonValue root = new JsonReader().parse(map.get("playerList").toString());

			for (JsonValue entry = root.child; entry != null; entry = entry.next) {
				final PlayerListItem tmp = json.fromJson(PlayerListItem.class, entry.toString());

				tmp.update();
				
				playerList.addActor(tmp);
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
	 * List players.
	 */
	public void listPlayers(int gameID) {

		// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"",
		// "createDate":"","gameID":"","status":"","sectors":[],"roundsCompleted":""}}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "getPlayerList");
		parameters.put("gameID", "" + gameID);
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest listReq = new WebRequest(this, httpGet);
		listReq.start();

	}
	
	public void loadGameDetails(GameListItem gameDetails) {
		galaxyLabel.setText(gameDetails.galaxy);
		sectorCountLabel.setText(gameDetails.sectors);
		maxPlayersLabel.setText(gameDetails.maxPlayers);
		
		playerList.clearChildren();
		
		loadedGameID = gameDetails.gameID;
		listPlayers(gameDetails.gameID);
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
