package se300.destinytactics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DestinyTactics extends Game {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int PADDING = 20;

	GameScene gameScreen;
	MenuScene menuScreen;
	HighScoresScene scoreScreen;
	SetupScene setupScreen;
	Skin skin;
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;
	
	@Override
	public void create() {
		// Specify the UI Skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		musicLoop = Gdx.audio.newMusic(Gdx.files
				.internal("music/Butterfly.mp3"));
		selectSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/select2.wav"));
		musicLoop.play();
		musicLoop.setLooping(true);
		
		menuScreen = new MenuScene(this, skin);
		
		setScreen(menuScreen);
	}

	@Override
	public void render() {
		if (getScreen() != null) {
			getScreen().render(0);
		}
	}
	
	public void goMenu(){
		if (menuScreen == null) {
			menuScreen = new MenuScene(this, skin);
		}
		
		//destroyCurrentScreen();
		
		if (!musicLoop.isPlaying()){
			musicLoop.play();
		}
		setScreen(menuScreen);
	}
	
	public void startGame(){
		if (gameScreen == null) {
			gameScreen = new GameScene(this, skin);
		}
		
		//destroyCurrentScreen();
		
		musicLoop.stop();
		setScreen(gameScreen);
	}
	
	public void showScores(){
		if (scoreScreen == null) {
			scoreScreen = new HighScoresScene(this, skin);
		}
		
		//destroyCurrentScreen();
		
		if (!musicLoop.isPlaying()){
			musicLoop.play();
		}
		setScreen(scoreScreen);
	}
	
	public void destroyCurrentScreen() {
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen = null;
		}
	}
}
