package se300.destinytactics;

import java.security.MessageDigest;

import se300.destinytactics.game.mapgen.Sector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LobbyStage extends Stage {

	public MultiplayerScreen myGame;
	public Skin skin;
	int edgePadding;
	int buttonPadding = 5;
	public Sound selectSound;
	public InputMultiplexer multiplexer;
	public Stage menuStage;
	public Table menu;
	public float masterVolume = 0.5f;
	private int userID;
	public Texture bgimg;
	public Image logo, background;
	public TextButton createGameButton, menuButton;
	MessageDigest messageDigest;
	String name, passwordHash;
	Label status;

	public LobbyStage(Viewport vp, int padding, Skin skin,
			final MultiplayerScreen myGame) {

		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;

		bgimg = new Texture("MenuBackground.jpg");
		background = new Image(bgimg);

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
				goMenu();
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
	}

	public void goMenu() {
		myGame.goMenu();
	}
	public void createGame() {
		myGame.game.startGame();
	}

}
