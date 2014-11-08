package se300.destinytactics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import se300.destinytactics.game.WebRequest;

public class MultiplayerScreen implements Screen, MakesRequests {

	public DestinyTactics game;
	public Skin skin;
	public Sound selectSound;
	public InputMultiplexer multiplexer;
	public Stage menuStage;
	public Table menu;
	public float masterVolume = 0.5f;
	private int userID;
	public Texture bgimg;
	public Image logo, background;
	public TextButton loginButton, registerButton, menuButton;
	public TextField username, password, email;
	MessageDigest messageDigest;
	String name, passwordHash;
	Label status;
	Boolean inLobby = false;
	LobbyStage lobby;

	/**
	 * Create new Multiplayer screen.
	 * @param game  Parent game class that contains this screen.
	 * @param skin  Skin to use for the screen.
	 */
	public MultiplayerScreen(DestinyTactics game, Skin skin) {

		this.game = game;
		this.skin = skin;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));

		bgimg = new Texture("MenuBackground.jpg");
		background = new Image(bgimg);
		menuStage = new Stage(new FitViewport(DestinyTactics.SCREEN_WIDTH,
				DestinyTactics.SCREEN_HEIGHT));

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(menuStage);
		Gdx.input.setInputProcessor(multiplexer);

		menu = new Table();
		// menu.setDebug(true);

		loginButton = new TextButton("Login", skin.get("default",
				TextButtonStyle.class));
		registerButton = new TextButton("Register", skin.get("default",
				TextButtonStyle.class));
		menuButton = new TextButton("Back to Menu", skin.get("default",
				TextButtonStyle.class));
		username = new TextField("", skin.get("default", TextFieldStyle.class));
		password = new TextField("", skin.get("default", TextFieldStyle.class));
		email = new TextField("", skin.get("default", TextFieldStyle.class));
		password.setPasswordMode(true);
		password.setPasswordCharacter('*');
		password.setMessageText("Password");
		username.setMessageText("Name");
		email.setMessageText("Email");

		status = new Label("", game.skin2);

		loginButton.setWidth(menuButton.getWidth());
		registerButton.setWidth(menuButton.getWidth());

		loginButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				login();
				return true;
			}
		});

		registerButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				registerPlayer();
				return true;
			}
		});

		menuButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectSound.play(masterVolume);
				goMenu();
				return true;
			}
		});

		menu.setFillParent(true);
		menu.row().space(50);
		menu.add(loginButton);
		menu.add(username);
		menu.row().space(50);
		menu.add(registerButton);
		menu.add(password);
		menu.row().space(50);
		menu.add(menuButton);
		menu.add(email);
		menu.setX(game.PADDING);
		menu.setY(game.PADDING);
		menuStage.addActor(background);
		menuStage.addActor(menu);
	}

	@Override
	/**
	 * Render method for screen.
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (inLobby) {
			lobby.act();
			lobby.draw();
		} else {
			menuStage.act();
			menuStage.draw();
		}
	}

	@Override
	/**
	 * Called on screen resize.
	 */
	public void resize(int width, int height) {
		menuStage.getViewport().update(width, height, false);
	}

	@Override
	/**
	 * Called when screen is shown.
	 */
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);

	}

	@Override
	/**
	 * Called when screen is hidden.
	 */
	public void hide() {
	}

	@Override
	/**
	 * Called when game loses focus.
	 */
	public void pause() {
	}

	@Override
	/**
	 * Called when game gains focus.
	 */
	public void resume() {
	}

	@Override
	/**
	 * Dispose of this screen.
	 */
	public void dispose() {
		menuStage.dispose();
	}

	/**
	 * Go to main menu.
	 */
	public void goMenu() {
		game.goMenu();
	}

	/**
	 * Registers player
	 */
	public void registerPlayer() {

		name = username.getText();
		messageDigest.update(password.getText().getBytes());
		passwordHash = new String(messageDigest.digest());
		String emailStr = email.getText();

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "createUser");
		parameters.put("username", name);
		parameters.put("password", passwordHash);
		parameters.put("email", emailStr);
		parameters.put("firstName", name);
		parameters.put("lastName", name);
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest registerReq = new WebRequest(this, httpGet);
		registerReq.start();
	}

	/**
	 * Logs player in.
	 */
	public void login() {
		// TODO add code to return true if it works.

		name = username.getText();
		// Hash password using SHA256
		messageDigest.update(password.getText().getBytes());
		passwordHash = new String(messageDigest.digest());

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "loginUser");
		parameters.put("username", name);
		parameters.put("password", passwordHash);
		parameters.put("returnFormat", "JSON");

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://cesiumdesign.com/DestinyTactics/destinyTactics.cfc?");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		httpGet.setTimeOut(0);

		WebRequest loginReq = new WebRequest(this, httpGet);
		loginReq.start();

	}

	@Override
	/**
	 * Called when http request is completed. Executes code as result of request.
	 */
	public void http(OrderedMap<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println(map);
		String message = map.get("message").toString();
		userID = Float.valueOf(map.get("userID", "-1").toString()).intValue();
		Dialog messageD = new Dialog("Status", skin) {
			protected void result(Object obj) {
				if (userID != -1) {
					goToLobby();
				}
			}
		};

		Label mes = new Label(message, skin);
		mes.setAlignment(Align.center);
		mes.setWrap(true);
		messageD.getContentTable().add(mes).width(800);
		messageD.button("Ok");
		messageD.pack();
		messageD.setMovable(false);
		messageD.setPosition(
				menuStage.getWidth() / 2 - messageD.getWidth() / 2,
				menuStage.getHeight() / 2 - messageD.getHeight() / 2);

		menuStage.addActor(messageD);
	}

	/**
	 * Called on successful login. Changes stage to games lobby.
	 */
	private void goToLobby() {
		lobby = new LobbyStage(menuStage.getViewport(), game.PADDING, skin,
				this);
		multiplexer.clear();
		multiplexer.addProcessor(lobby);
		inLobby = true;
	}

}
