package se300.destinytactics.game.scenes;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Mike
 * 
 * SectorScene extends Stage
 * This class builds the intermediate view between galaxy and planet.
 */
public class SectorScene extends Stage {

	public GameScene myGame;
	public Skin skin;
	TextButton PlayerButton, PlayerButton2, PlayerButton3;
	int edgePadding;
	int buttonPadding = 5;
	Sector curSector;

	static Texture bgimg = new Texture("StarfieldBackground.jpg");
	static Image background = new Image(bgimg);

	/**
	 * @param vp
	 * @param padding
	 * @param skin
	 * @param myGame
	 */
	public SectorScene(Viewport vp, int padding, Skin skin,
			final GameScene myGame) {

		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;

	}

	/**
	 * Change sector takes sector as a parameter.
	 * Builds the stage based off sector input.
	 * @param nextSector
	 */
	public void changeSector(Sector nextSector) {

		curSector = nextSector;

		// Clear stage to reuse it
		this.clear();

		myGame.navBar.setName(nextSector.getName());

		// Add image background and stretch to fit
		this.addActor(background);
		background.setFillParent(true);

		Image sun = new Image(Sector.sunTypes[nextSector.sunType]);
		this.addActor(sun);
		sun.setSize(DestinyTactics.SCREEN_HEIGHT, DestinyTactics.SCREEN_HEIGHT);
		sun.setOrigin(DestinyTactics.SCREEN_HEIGHT / 2,
				DestinyTactics.SCREEN_HEIGHT / 2);
		sun.setRotation(nextSector.sunRotation);
		sun.setX(DestinyTactics.SCREEN_WIDTH - sun.getWidth() * (3.0f / 8.0f));
		sun.setY(DestinyTactics.SCREEN_HEIGHT / 2 - sun.getHeight() / 2);

		// Add all planet objects
		for (int i = 0; i < nextSector.getNumBodies(); i++) {
			this.addActor(nextSector.bodyList[i]);
			if (nextSector.bodyList[i].hasFleet()) {
				this.addActor(nextSector.bodyList[i].getFleet());
			}
		}
	}

	public void act(float time) {
		super.act(time);
	}
}
