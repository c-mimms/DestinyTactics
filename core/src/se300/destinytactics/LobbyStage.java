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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
	public Table lobby, menu, gameListView, gameDetailsView;
	public float masterVolume = 0.5f;
	private int userID;
	public Texture bgimg;
	public Image logo, background;
	public TextButton createGameButton, menuButton;
	MessageDigest messageDigest;
	String name, passwordHash;
	Label status;
	ArrayList<GameListItem> gameList;

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
		
		//DestinyTactics.SCREEN_WIDTH
		lobby = new Table(skin);
		menu = new Table();
		gameListView = new Table();
		gameDetailsView = new Table();
		
		lobby.setDebug(true);
		lobby.setHeight(DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding));
		lobby.setWidth(DestinyTactics.SCREEN_WIDTH - (2 * edgePadding));
		
		
		//.height((DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding)) /6)
		//.width(lobby.getWidth()/2 - edgePadding)
		lobby.add("Lobby").align(Align.left).height((DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding)) / 14);
		lobby.add("Menu").align(Align.right).height((DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding)) / 14);
		lobby.row();
		lobby.add().align(Align.left).expand().width(lobby.getWidth()/2 - (edgePadding / 2)).height(lobby.getHeight() - ((DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding)) / 14));
		lobby.add().align(Align.right).expand().width(lobby.getWidth()/2 - (edgePadding / 2)).height(lobby.getHeight() - ((DestinyTactics.SCREEN_HEIGHT - (2 * edgePadding)) / 14));
		//lobby.add("Game List").expandY().align(Align.left).width(DestinyTactics.SCREEN_WIDTH - (2 * edgePadding)).top();
		//lobby.add("Game Details").expandY().align(Align.right).width(DestinyTactics.SCREEN_WIDTH - (2 * edgePadding)).top();
		
		
		lobby.setY(edgePadding);
		lobby.setX(edgePadding);
		this.addActor(background);
		this.addActor(lobby);

		/*
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
		*/
		
		
		//listGames();
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

		// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"",
		// "createDate":"","gameID":"","status":"","sectors":[],"roundsCompleted":""}}

		GameJson game = new GameJson(Utility.getSeed());
		Json json = new Json();
		json.setOutputType(OutputType.json);
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

		myGame.game.startGame();
	}

	@Override
	public void http(OrderedMap<String, Object> map) {
		Json json = new Json();
		//System.out.println(map);
		if (map.containsKey("gameList")) {
			JsonValue root = new JsonReader().parse(map.get("gameList")
					.toString());

			for (JsonValue entry = root.child; entry != null; entry = entry.next) {
				final GameListItem tmp = json.fromJson(GameListItem.class, entry.toString());
				gameList.add(tmp);

				tmp.addListener(new ClickListener() {
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						joinGame(tmp.gameID);
						return true;
					}
				});
				this.addActor(tmp);
			}
			for(GameListItem item : gameList){
				item.update();
			}
		}
		else if(map.containsKey("gameObj")){
			GameJson games = json.fromJson(GameJson.class, map.get("gameObj").toString());
			//System.out.println(map);
		}
		else if(map.containsKey("gameDataObject")){
			GameJson games = json.fromJson(GameJson.class, map.get("gameDataObject").toString());
			System.out.println(games.sectors);
		}
		//System.out.println(Thread.activeCount());
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
