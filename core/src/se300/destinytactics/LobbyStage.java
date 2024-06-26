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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
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
	public Table lobby, gameDetailsView, lobbyView;
	public VerticalGroup gameListView, playerListView;
	public ScrollPane gameListScroll, playerListScroll;
	public float masterVolume = 0.5f;
	public int userID, loadedGameID;
	public GameListItem gameDetails;
	public Texture bgimg;
	public Image logo, background;
	public TextButton createGameButton, menuButton, continueButton, startButton, stopButton, joinButton, waitingButton;
	public Container<TextButton> creatorButtonContainer, buttonContainer;
	MessageDigest messageDigest;
	String name, passwordHash;
	Label status, galaxyLabel, sectorCountLabel, maxPlayersLabel, statusLabel;
	ClickListener continueListener;
	public ArrayList<GameListItem> gameList;
	public ArrayList<PlayerListItem> playerList;
	public ArrayList<GalaxyListItem> galaxyList;
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
		this.userID = MultiplayerScreen.userID;
		loadedGameID = 0;
		gameList = new ArrayList<GameListItem>();
		galaxyList = new ArrayList<GalaxyListItem>();
		playerList = new ArrayList<PlayerListItem>();

		skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));

		// Create table to organize entire screen
		lobby = new Table(skin);
		lobby.setHeight(DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding));
		lobby.setWidth(DestinyTactics.SCREEN_WIDTH - (2 * edgePadding));
		lobby.setY(edgePadding);
		lobby.setX(edgePadding);
		lobby.pad(edgePadding);
		
		// Create Buttons
		 createGameButton = new TextButton("Create Game", skin.get("default", TextButtonStyle.class));
		 menuButton = new TextButton("Back to Menu", skin.get("default", TextButtonStyle.class));
		 continueButton = new TextButton("Enter", skin.get("default", TextButtonStyle.class));
		 startButton = new TextButton("Start Game", skin.get("default", TextButtonStyle.class));
		 stopButton = new TextButton("Stop Game", skin.get("default", TextButtonStyle.class));
		 joinButton = new TextButton("Join Game", skin.get("default", TextButtonStyle.class));
		 waitingButton = new TextButton("Waiting for Host", skin.get("default", TextButtonStyle.class));
		
		 createGameButton.setWidth(menuButton.getWidth());
		 continueButton.setWidth(menuButton.getWidth());
		 startButton.setWidth(menuButton.getWidth());
		 stopButton.setWidth(menuButton.getWidth());
		 joinButton.setWidth(menuButton.getWidth());
		 waitingButton.setWidth(menuButton.getWidth());
		
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
				 	showCreateGameForm();
				 	return true;
				 }
		 });
		 
		continueButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					continueGame(loadedGameID);
					return true;
			}
		});
		
		startButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setGameStatus(loadedGameID, "Active");
					return true;
			}
		});
		
		stopButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setGameStatus(loadedGameID, "Finished");
					return true;
			}
		});
		
		joinButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					joinGame();
					return true;
			}
		});
		
		creatorButtonContainer = new Container<TextButton>(); 
		buttonContainer = new Container<TextButton>();
		
		// Create table for lobby view 
		lobbyView = new Table(skin);
		lobbyView.pad(edgePadding);
		
		// Create a vertical group for list of games
		gameListView = new VerticalGroup();
		gameListView.space(10).pad(edgePadding).fill();
		gameListView.addActor(new HorizontalGroup(){{
			addActor(new Label("Creator", skin2));
			addActor(new Label("Alliances", skin2));
			addActor(new Label("Players", skin2));
			addActor(new Label("Status", skin2));
			space(75);
		}}
		);
		
		// Scroll pane to hold the list of games
		gameListScroll = new ScrollPane(gameListView, skin2);
		gameListScroll.setScrollingDisabled(true, false);
		
		// Fill lobby view
		lobbyView.add(gameListScroll).expand().top();
		lobbyView.row();
		lobbyView.add(createGameButton);
		
		galaxyLabel = new Label("", skin2);
		sectorCountLabel = new Label("", skin2);
		maxPlayersLabel = new Label("", skin2);
		statusLabel = new Label("", skin2);
		
		// Create table for detail info of chosen game
		gameDetailsView = new Table(skin2);
		gameDetailsView.pad(edgePadding);
		gameDetailsView.add("Game Details").colspan(2);
		gameDetailsView.row();
		gameDetailsView.add(new Label("Galaxy:", skin2)).expandX();
		gameDetailsView.add(galaxyLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Sectors:", skin2)).expandX();
		gameDetailsView.add(sectorCountLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Max Players:", skin2)).expandX();
		gameDetailsView.add(maxPlayersLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Status:", skin2)).expandX();
		gameDetailsView.add(statusLabel).expandX();
		gameDetailsView.row();
		gameDetailsView.add(new Label("Players", skin2)).colspan(2).expandX();
		
		// Create a vertical group for list of games
		playerListView = new VerticalGroup();
		playerListView.space(10).pad(edgePadding).fill();
		playerListView.addActor(new HorizontalGroup(){{
			addActor(new Label("Username", skin2));
			addActor(new Label("Alliance", skin2));
			addActor(new Label("Rank", skin2));
			space(113);
		}}
		);
		
		playerListScroll = new ScrollPane(playerListView, skin2);
		playerListScroll.setScrollingDisabled(true, false);
		
		// Fill game details content
		gameDetailsView.row();
		gameDetailsView.add(playerListScroll).colspan(2).expand().top();
		gameDetailsView.row();
		gameDetailsView.add(creatorButtonContainer).expandX().left().bottom();
		gameDetailsView.add(buttonContainer).expandX().right().bottom();

		// Fill Lobby Content
		lobby.add("Lobby").align(Align.left).height(lobby.getHeight() / 14);
		lobby.add(menuButton).align(Align.right).height(lobby.getHeight() / 14);
		lobby.row();
		lobby.add(lobbyView).left().fill().width(Value.percentWidth(0.50f, lobby));
		lobby.add(gameDetailsView).left().top().fill().expandY().width(Value.percentWidth(0.50f, lobby));

//		lobby.debugAll();
//		lobbyView.debugAll();
//		gameDetailsView.debugAll();
//		creatorButtonContainer.debugAll();
//		buttonContainer.debugAll();
		
		this.addActor(lobby);
		
		// perform HTTP calls to load the game and galaxy lists
		listGames();
		listGalaxies();
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
	
	@SuppressWarnings("unchecked")
	public void showCreateGameForm() {
		
		Label galaxySelectLabel = new Label("Galaxy", skin);
		@SuppressWarnings("rawtypes")
		final SelectBox galaxySelect = new SelectBox(skin);
		
		String[] galaxyNames = new String[galaxyList.size()];
		int i = 0;
		for (GalaxyListItem galaxy: galaxyList) { 
			galaxyNames[i] = galaxy.getGalaxy();
			i++;
		}
		
		galaxySelect.setItems(galaxyNames);
		
		Dialog createGameForm = new Dialog("New Game", skin) {
			protected void result(Object obj) {
				String selectedGalaxyName = (String) galaxySelect.getSelected();
				selectedGalaxyName = selectedGalaxyName.toLowerCase();

				for (GalaxyListItem galaxy : galaxyList) {
					String galaxyName = galaxy.getGalaxy().toLowerCase();
					if (galaxyName.contains(selectedGalaxyName)){
						createGame(galaxy);
					}
				}
			}
		};
		
		createGameForm.getContentTable().add(galaxySelectLabel);
		createGameForm.getContentTable().add(galaxySelect);
		createGameForm.button("Create Game");
		createGameForm.pack();
		createGameForm.setMovable(false);
		createGameForm.setPosition(
				this.getWidth() / 2 - createGameForm.getWidth() / 2,
				this.getHeight() / 2 - createGameForm.getHeight() / 2);

		this.addActor(createGameForm);
	}
	
	/**
	 * Called after refreshing the game list. 
	 * Refreshes the gameDetails object after getting new data from server
	 */
	public void refreshGameDetails() {
		GameListItem loadedGame = new GameListItem();
		
		for (GameListItem game : gameList) {
			if (game.gameID == loadedGameID) {
				loadedGame = game;
			}
		}
		loadGameDetails(loadedGame);
	}
	
	/**
	 * Populates the gameDetails UI with user-appropriate buttons
	 */
	public void updateNavigation() {
		// Clear button actors
		creatorButtonContainer.clear();
		buttonContainer.clear();
		
		// Show navigation for creator user
		if (userID == gameDetails.createdBy) {
			if (gameDetails.status.toUpperCase().contains("PENDING")) {
				// Start Game
				creatorButtonContainer.setActor(startButton);
			}
			else if (gameDetails.status.toUpperCase().contains("ACTIVE")) {
				// End Game
				creatorButtonContainer.setActor(stopButton);
				
				// Continue
				buttonContainer.setActor(continueButton);
			}
		}
		
		// Show navigation for non-creator users
		else {
			boolean isPlayer = false;
			for (PlayerListItem player : playerList) {
				if (userID == player.userID) {
					isPlayer = true;
				}
			}			
			if (isPlayer && gameDetails.status.toUpperCase().contains("ACTIVE")) {
				if (gameDetails.status.toUpperCase().contains("ACTIVE")) {
					// Continue Game
					buttonContainer.setActor(continueButton);
				}
			}
			else if (!isPlayer && gameDetails.status.toUpperCase().contains("PENDING")){
				// Join Game
				buttonContainer.setActor(joinButton);
			}
			
		}
		
	}
	
	/**
	 * Populates the game details UI with gameDetail data
	 * @param gameDetails
	 */
	public void loadGameDetails(GameListItem gameDetails) {
		this.gameDetails = gameDetails;
		
		galaxyLabel.setText(gameDetails.galaxy);
		sectorCountLabel.setText(gameDetails.sectors);
		maxPlayersLabel.setText(gameDetails.maxPlayers);
		statusLabel.setText(gameDetails.status);
		
		playerListView.clearChildren();
		
		loadedGameID = gameDetails.gameID;
		listPlayers(gameDetails.gameID);
	}
	
	/**
	 * Directs the HTTP response to the appropriate success action
	 */
	@Override
	public void http(OrderedMap<String, Object> map) {
		Json json = new Json();
		// System.out.println(map);
		String message = map.containsKey("message") ? map.get("message").toString().toLowerCase() : "";
		
		if (message.contains(("Game list loaded.").toLowerCase())) {
			listGamesAction(json, map);
		}
		
		// load galaxies return
		else if (message.contains(("Galaxy list loaded.").toLowerCase())) {
			listGalaxiesAction(json, map);
		}
		
		// load players return
		else if (message.contains(("Player list loaded.").toLowerCase())) {
			listPlayersAction(json, map);
		}
		
		// load game return
		else if (message.contains(("Game loaded.").toLowerCase())) {
			loadGameAction(json, map);
		}
		
		// Save game return
		else if (message.contains(("Game saved.").toLowerCase())) {
			saveGameAction(json, map);
		}
		
		// Create game return
		else if (message.contains(("Game created.").toLowerCase())) {
			createGameAction(json, map);
		}
		
		// Join game return
		else if (message.contains(("Game joined.").toLowerCase())) {
			joinGameAction(json, map);
		}
		gameListView.validate();
	}
	
	/**
	 * Called from HTTP override function. Refreshes the game list.
	 * @param json
	 * @param map
	 */
	public void listGamesAction(Json json, OrderedMap<String, Object> map) {
		JsonValue root = new JsonReader().parse(map.get("gameList").toString());
		
		// Clear and reload game list data
		gameList.clear();	
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			final GameListItem tmpGame = json.fromJson(GameListItem.class, entry.toString());
			tmpGame.update();
			tmpGame.addListener(new ClickListener() {
				public boolean touchDown(InputEvent event, float x,
						float y, int pointer, int button) {
					loadGameDetails(tmpGame);
					//joinGame(tmp.gameID);
					return true;
				}
			});
			
			gameList.add(tmpGame);
		}
		
		// Clear and reload game list view
		gameListView.clear();
		gameListView.addActor(new HorizontalGroup(){{
			addActor(new Label("Creator", skin2));
			addActor(new Label("Alliances", skin2));
			addActor(new Label("Players", skin2));
			addActor(new Label("Status", skin2));
			space(75);
		}}
		);
		
		for (GameListItem game : gameList) {
			gameListView.addActor(game);
		}
		
		// Refresh viewed game details
		if (loadedGameID != 0) {
			refreshGameDetails();
		}
	}
	
	/**
	 * Called from the HTTP override function. Refreshes the galaxy list.
	 * @param json
	 * @param map
	 */
	public void listGalaxiesAction(Json json, OrderedMap<String, Object> map) {
		JsonValue root = new JsonReader().parse(map.get("galaxyList").toString());
		
		// Clear and reload galaxy list data
		galaxyList.clear();
		
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			final GalaxyListItem tmp = json.fromJson(GalaxyListItem.class, entry.toString());
			tmp.update();
			galaxyList.add(tmp);
		}
	}
	
	/**
	 * Called from the HTTP override function. Refreshes the player list for the selected game.
	 * @param json
	 * @param map
	 */
	public void listPlayersAction(Json json, OrderedMap<String, Object> map) {
		JsonValue root = new JsonReader().parse(map.get("playerList").toString());
		
		// Clear and reload player list data
		playerList.clear();
		
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			final PlayerListItem tmp = json.fromJson(PlayerListItem.class, entry.toString());
			tmp.update();
			playerList.add(tmp);
		}
		
		// Clear and reload player list view
		playerListView.clear();
		playerListView.addActor(new HorizontalGroup(){{
			addActor(new Label("Username", skin2));
			addActor(new Label("Alliance", skin2));
			addActor(new Label("Rank", skin2));
			space(113);
		}}
		);
		
		for (PlayerListItem player : playerList) {
			playerListView.addActor(player);
		}
		
		updateNavigation();
	}
	
	/**
	 * Called from the HTTP override function. Starts the loaded game.
	 * @param json
	 * @param map
	 */
	public void loadGameAction(Json json, OrderedMap<String, Object> map) {
		GameJson game = json.fromJson(GameJson.class, map.get("gameDataObj").toString());
		Utility.setSeed(game.galaxySeed);
		this.lastGame = game;	
		this.startGame = true;		
	}
	
	/**
	 * Called from the HTTP override function. Refreshes the game list and updates the UI
	 * @param json
	 * @param map
	 */
	public void saveGameAction(Json json, OrderedMap<String, Object> map) {
		listGames();
		
		if (gameDetails.status.contains(("active").toLowerCase())) {
			creatorButtonContainer.clear(); 
			buttonContainer.clear();
			creatorButtonContainer.setActor(stopButton); 
			buttonContainer.setActor(waitingButton); 
		}
		else if (gameDetails.status.contains(("Finished").toLowerCase())) {
			creatorButtonContainer.clear(); 
			buttonContainer.clear();
		}
	}
	
	/**
	 * Called from the HTTP override function. Refreshes the game list and selects the new game.
	 * @param json
	 * @param map
	 */
	public void createGameAction(Json json, OrderedMap<String, Object> map) {
		@SuppressWarnings("unused")
		GameJson game = json.fromJson(GameJson.class, map.get("gameDataObject").toString());
		listGames();
	}
	
	/**
	 * Called from the HTTP override function. Either starts the loaded game or refreshes the game list.
	 * @param json
	 * @param map
	 */
	public void joinGameAction(Json json, OrderedMap<String, Object> map) {
		if (gameDetails.status.toUpperCase().contains("ACTIVE")) {
			this.startGame = true;
		}
		else {
			listGames();
		}
	}
	
	/**
	 * HTTP call. Starts the loaded game.
	 */
	public void createGame(GalaxyListItem galaxy) {

		Utility.setSeed(Utility.random.nextLong());
		GameScene.preloadGalaxy(galaxy);

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

		//this.startGame = true;

	}
	
	/**
	 * HTTP call. List active & pending games.
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
	 * HTTP call. List galaxies.
	 */
	public void listGalaxies() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "getGalaxyList");
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
	 * HTTP call. List players.
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
	
	/**
	 * HTTP call. Updates the selected game with a new status
	 * @param gameID
	 * @param status
	 */
	public void setGameStatus(int gameID, String status) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "saveGame");
		parameters.put("gameID", "" + gameDetails.gameID);
		parameters.put("status", status);
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
	 * HTTP call. Load and continue game.
	 * @param gameID
	 */
	public void continueGame(int gameID) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "loadGame");
		parameters.put("gameID", "" + gameID);
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest joinReq = new WebRequest(this, httpGet);
		joinReq.start();

	}
	
	/**
	 * HTTP call. Adds a player for the logged in user to the selected game.
	 */
	public void joinGame() {
		int isHost = gameDetails.createdBy == userID ? 1 : 0;
		PlayerJson player = new PlayerJson(gameDetails.gameID, userID, isHost, "");
		Json json = new Json();
		json.setOutputType(OutputType.json);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "joinGame");
		parameters.put("playerObj", json.toJson(player));
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
