package se300.destinytactics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MenuScene implements Screen{
	
	public DestinyTactics game;
	public Sound selectSound;
	public float masterVolume = 0.5f;
	public InputMultiplexer multiplexer;
	public Stage menuStage;
	public Table menu;
	public Texture bgimg;
	public Image logo, background;
	public TextButton newGameButton, highscoreButton, quitButton, multiplayerGameButton;
	
	public MenuScene(DestinyTactics game, Skin skin){
		this.game = game;
		
		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));
		
		bgimg = new Texture("MenuBackground.jpg");
		background = new Image(bgimg);
		menuStage = new Stage(new FitViewport(DestinyTactics.SCREEN_WIDTH, DestinyTactics.SCREEN_HEIGHT));
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(menuStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		menu = new Table();
		
		logo = new Image(new Texture("images/DestinyTacticsLogo.png"));
		newGameButton = new TextButton("New Game", skin.get("default", TextButtonStyle.class));
		multiplayerGameButton = new TextButton("Online Game", skin.get("default", TextButtonStyle.class));
		highscoreButton = new TextButton("High Scores",skin.get("default", TextButtonStyle.class));
		quitButton = new TextButton("Quit", skin.get("default", TextButtonStyle.class));
		
		newGameButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				startGame();
				return true;
			}
		});
		
		highscoreButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				showScores();
				return true;
			}
		});
		
		quitButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				Gdx.app.exit();
				return true;
			}
		});
		
		multiplayerGameButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				multiplayerSetup();
				return true;
			}
		});
		
		
		menu.setFillParent(true);
		menu.add(logo);
		menu.row().space(50);
		menu.add(newGameButton);
		menu.row().space(50);
		menu.add(multiplayerGameButton);
		menu.row().space(50);
		menu.add(highscoreButton);
		menu.row().space(50);
		menu.add(quitButton);
		
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

	public void startGame() {
		game.startGame();
	}
	
	public void showScores() {
		game.showScores();
	}
	public void multiplayerSetup() {
		game.multiplayerSetup();
	}
}
