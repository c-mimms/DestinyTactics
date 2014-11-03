package se300.destinytactics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DestinyTactics extends Game {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int PADDING = 20;

	GameScene gameScreen;
	MenuScene menuScreen;
	HighScoresScene scoreScreen;
	SetupScene setupScreen;
	MultiplayerScreen multiplayerScreen;
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
		
		//Generate galaxy and time how long it takes. I am putting this on a separate thread later...
		 long startTime = System.currentTimeMillis();
		 //(new PreloadGalaxyThread()).start();
		 //GameScene.preloadGalaxy();
		 long estimatedTime = System.currentTimeMillis() - startTime;
		 System.out.println("Generate galaxy : " + estimatedTime);
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
	
	public void multiplayerSetup(){
		if (multiplayerScreen == null) {
			multiplayerScreen = new MultiplayerScreen(this, skin);
		}
		
		//destroyCurrentScreen();
		
		if (!musicLoop.isPlaying()){
			musicLoop.play();
		}
		setScreen(multiplayerScreen);
	}
}
