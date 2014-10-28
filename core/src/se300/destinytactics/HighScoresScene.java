package se300.destinytactics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HighScoresScene implements Screen{
	
	public Stage highscoreStage;

	public DestinyTactics game;
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;
	public InputMultiplexer multiplexer;
	public Stage scoreStage;
	public Table menu, headers;
	public Texture bgimg;
	public Image logo, background;
	public TextButton menuButton;
	
	public HighScoresScene(DestinyTactics game, Skin skin){
		this.game = game;
		
		musicLoop = Gdx.audio.newMusic(Gdx.files
				.internal("music/SimplicityIsBliss.mp3"));
		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));
		
		bgimg = new Texture("MenuBackground.jpg");
		background = new Image(bgimg);
		scoreStage = new Stage(new FitViewport(DestinyTactics.SCREEN_WIDTH, DestinyTactics.SCREEN_HEIGHT));
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(scoreStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		menu = new Table(skin);
		
		menuButton = new TextButton("Back to Menu", skin.get("default", TextButtonStyle.class));
		
		menuButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectSound.play(masterVolume);
				goMenu();
				return true;
			}
		});
		
		headers = new Table(skin);
		headers.setFillParent(true);
		headers.add("Player").top().expand().fill();
		headers.add("Galaxy").top().expand().fill();
		headers.add("Sectors Controlled").top().expand().fill();
		headers.add("Resources").top().expand().fill();
		headers.add("Total Fleet Strength").top().expand().fill();
		headers.add("Star Date").top().expand().fill();
		
		menu.setFillParent(true);
		menu.row().height(150);
		menu.add("High Scores").top();
		menu.row().height(300);
		menu.add(headers).top().expand().fill();
		menu.row();
		menu.add(menuButton).height(30);
		
		scoreStage.addActor(background);
		scoreStage.addActor(menu);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		scoreStage.act();
		scoreStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		scoreStage.getViewport().update(width, height, false);	
	}

	@Override
	public void show() {
		musicLoop.play();
		musicLoop.setLooping(true);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {
		musicLoop.stop();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		scoreStage.dispose();
	}

	public void goMenu() {
		game.goMenu();
	}
}
