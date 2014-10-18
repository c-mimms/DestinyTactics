package se300.destinytactics.logic;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import se300.destinytactics.mapgen.Galaxy;
import se300.destinytactics.mapgen.Sector;
import se300.destinytactics.ui.Drawable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:03 PM
 */
public class Game extends ApplicationAdapter implements InputProcessor {

	
	//OLD Variables, Decide which need to be kept
	private SpriteBatch batch;
	private Texture bgimg;
	private Rectangle sun;
	private Texture selImage;
	private Galaxy gameMap;
	private ArrayList<Drawable> objToDraw;
	private boolean start;
	public AI m_AI;
	public User m_User;
	public Galaxy m_Galaxy;
	private float zoomLevel;
	OrthographicCamera camera;
	float ww;
	float hh;
	ShapeRenderer renderer;
	Vector3 mousePos = new Vector3();
	
	//NEW variables, put necessary ones here.
	
	
	
	Stage galaxyStage, sectorStage;
	
	
	

	public Game() {

	}

	public void create() {

		m_Galaxy = new Galaxy(12000, 8000, 100);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 12000, 8000);
		batch = new SpriteBatch();
		bgimg = new Texture("galaxyBG.jpg");
		selImage = new Texture("circle.png");
		zoomLevel = 1;

		ww = Gdx.graphics.getWidth();
		hh = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);

		sun = new Rectangle();
		sun.x = (int) ww / 2 - 50;
		sun.y = (int) hh / 2 - 50;
		sun.width = 100;
		sun.height = 100;
	}

	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bgimg, 0, 0, 12000, 8000, 0, 0, 2048, 1024, false, false);
		batch.draw(selImage, mousePos.x-100*zoomLevel, mousePos.y-100*zoomLevel, sun.width*zoomLevel, sun.height*zoomLevel, 0, 0, 200,186, false, false);
		for(int i = 0; i < 100; i++){
			Sector temp = m_Galaxy.sectors[i];
			temp.drawImage(batch,zoomLevel);
		}
		batch.end();
	}

	public void zoomed(int amount) {
		if (amount == 1) {
			zoomLevel += 0.1;
		}
		if (amount == -1) {
			if (zoomLevel <= 1) {
				zoomLevel -= 0.1;
			}
		}
		if(zoomLevel > 1) zoomLevel = 1;
		if(zoomLevel <= 0.001) zoomLevel = 0.01f;

	
		camera.zoom = zoomLevel;
		if (zoomLevel < 1) {
			camera.position.set(sun.x + 50, sun.y + 50, 0);
		} else {
			camera.position.set(ww / 2, hh / 2, 0);
		}
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 12000 - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 8000 - effectiveViewportHeight / 2f);
        
		camera.update();
	}

	public void resize(int width, int height) {
		ww = width;
		hh = height;
	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param 1
	 */
	public void addActionListener(ActionListener actList) {

	}

	public void endTurn() {
sectorStage = new Stage();
sectorStage.
	}

	public int getGameState() {
		return 0;

	}

	public void setGameState() {

	}

	public void setup() {

	}

	private void tick() {

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mousePos.set((int) Gdx.input.getX(), (int) Gdx.input.getY(), 0);
		camera.unproject(mousePos);

		sun.x = (int) mousePos.x - 50;
		sun.y = (int) mousePos.y - 50;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		zoomed(amount);
		return false;
	}
}// end Game