package se300.destinytactics;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * 
 * @author Team Guardian
 * Implements Screen from the LibGDX framework. 
 * Displays the UI for creating a new game. 
 */
public class SetupScene implements Screen{
	
	public Stage setupStage;

	/**
	 * SetupScene constructor. 
	 * @param game
	 * @param skin
	 */
	public SetupScene(DestinyTactics game, Skin skin){

		setupStage = new Stage(new FitViewport(DestinyTactics.SCREEN_WIDTH, DestinyTactics.SCREEN_HEIGHT));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
}
