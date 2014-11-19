package se300.destinytactics;

import se300.destinytactics.game.mapgen.Assets;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * 
 * @author Chris Shannon
 * Extends Game in the LibGDX framework.
 * This is the main controller interface for the game. It creates screen objects for every different 
 * view and toggles between active and inactive screen.
 * screens : [ gameScreen : Houses the actual game UI and sub objects, 
 * 			  menuScreen : Displays the main menu (default upon app init), 
 * 			  scoreScreen : Displays the highscore list, 
 * 			  setupScreen : Houses the game setup form. Creates a new game and redirects to the gameScreen, 
 * 			  multiplayerScreen : Displays the multiplayer lobby and 
 * 				registration/login form (will show form if not logged in)
 * ]
 */
public class DestinyTactics extends Game {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;
	public static final int PADDING = 20;
	public static Skin skin2 ;
	
	GameScene gameScreen;
	MenuScene menuScreen;
	HighScoresScene scoreScreen;
	SetupScene setupScreen;
	MultiplayerScreen multiplayerScreen;
	public Skin skin;
	
	public Music musicLoop;
	public Sound selectSound;
	public float masterVolume = 0.5f;
	
	@Override
	/**
	 * Instantiates the DestinyTactics object, sets up non-game background and music, and sets the screen to the menuScreen.
	 */
	public void create() {
		Assets.queueLoading();
		
		// Specify the UI Skin
		skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));
		skin = new Skin(Gdx.files.internal("data/Holo-dark-hdpi.json"), new TextureAtlas("data/Holo-dark-hdpi.atlas"));
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
		Assets.update();
		if (getScreen() != null) {
			getScreen().render(0);
		}
	}
	
	/**
	 * Switches view to menuScreen
	 */
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
	
	/**
	 * Switches view to gameScreen (and stops non-game music)
	 */
	public void startGame(){
		Assets.finish();
		if (gameScreen == null) {
			gameScreen = new GameScene(this, skin2);
		}
		
		//destroyCurrentScreen();
		
		musicLoop.stop();
		setScreen(gameScreen);
	}
	
	/**
	 * Switches view to scoreScreen
	 */
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
	
	/**
	 * Destroys the currently loaded screen. Normally called when switching screens, but is disabled at this time.
	 */
	public void destroyCurrentScreen() {
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen = null;
		}
	}
	
	/**
	 * Switches to multiplayerScreen
	 */
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
