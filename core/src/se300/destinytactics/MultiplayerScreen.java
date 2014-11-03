package se300.destinytactics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MultiplayerScreen implements Screen{
	
	public DestinyTactics game;
	public Sound selectSound;
	public InputMultiplexer multiplexer;
	public Stage menuStage;
	public Table menu;
	public float masterVolume = 0.5f;
	public Texture bgimg;
	public Image logo, background;
	public TextButton loginButton, registerButton,  menuButton;
	public TextField username, password, email;
	MessageDigest messageDigest;
	String name , passwordHash;
	
	public MultiplayerScreen(DestinyTactics game, Skin skin){
		
		this.game = game;
		
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
		menuStage = new Stage(new FitViewport(DestinyTactics.SCREEN_WIDTH, DestinyTactics.SCREEN_HEIGHT));
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(menuStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		menu = new Table();
		//menu.setDebug(true);
		
		loginButton = new TextButton("Login", skin.get("default", TextButtonStyle.class));
		registerButton = new TextButton("Register",skin.get("default", TextButtonStyle.class));
		menuButton = new TextButton("Back to Menu", skin.get("default", TextButtonStyle.class));
		username = new TextField("",skin.get("default",TextFieldStyle.class));
		password = new TextField("",skin.get("default",TextFieldStyle.class));
		email = new TextField("",skin.get("default",TextFieldStyle.class));
		password.setPasswordMode(true);
		password.setPasswordCharacter('*');
		password.setMessageText("Password");
		username.setMessageText("Name");
		email.setMessageText("Email");

		loginButton.setWidth(menuButton.getWidth());
		registerButton.setWidth(menuButton.getWidth());
		
		
		loginButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				login();
				return true;
			}
		});
		
		registerButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				registerPlayer();
				return true;
			}
		});
		
		menuButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		menuStage.act();
		menuStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		menuStage.getViewport().update(width, height, false);	
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		menuStage.dispose();
	}
	
	public void goMenu() {
		game.goMenu();
	}
	
	public void registerPlayer(){
		//TODO add code to send stuff to server
		name = username.getMessageText();
		messageDigest.update(password.getMessageText().getBytes());
		passwordHash = new String(messageDigest.digest());
	}
	
	public void login(){
		//TODO add code to send login and return true if it works. 

		name = username.getMessageText();
		//Hash password using SHA256
		//TODO For more security generate salt also...
		messageDigest.update(password.getMessageText().getBytes());
		passwordHash = new String(messageDigest.digest());
		//passwordHash = password.getMessageText().hashCode();
		
	}
	
}
