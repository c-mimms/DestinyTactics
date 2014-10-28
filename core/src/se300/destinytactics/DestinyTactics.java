package se300.destinytactics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DestinyTactics extends Game {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;

	GameScene gameScreen;
	MenuScene menuScreen;
	HighScoresScene scoreScreen;
	SetupScene setupScreen;
	Skin skin;

	@Override
	public void create() {
		// Specify the UI Skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
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
		setScreen(menuScreen);
	}
	
	public void startGame(){
		if (gameScreen == null) {
			gameScreen = new GameScene(this, skin);
		}
		//destroyCurrentScreen();
		setScreen(gameScreen);
	}
	
	public void showScores(){
		if (scoreScreen == null) {
			scoreScreen = new HighScoresScene(this, skin);
		}
		//destroyCurrentScreen();
		setScreen(scoreScreen);
	}
	
	public void destroyCurrentScreen() {
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen = null;
		}
	}
}
