package se300.destinytactics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DestinyTactics extends Game {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 640;

	GameScene gameScreen;
	MenuScene menuScreen;
	HighScoresScene scoreScreen;
	SetupScene setupScreen;
	

	@Override
	public void create() {
		gameScreen =  new GameScene(this);
		menuScreen = new MenuScene(this);
		scoreScreen = new HighScoresScene(this);
		setupScreen = new SetupScene(this);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		getScreen().render(0);
	}
	
	public void goMenu(){
		setScreen(menuScreen);
	}
	
	public void startGame(){
		setScreen(gameScreen);
	}
	
	public void showScores(){
		setScreen(scoreScreen);
	}
}
