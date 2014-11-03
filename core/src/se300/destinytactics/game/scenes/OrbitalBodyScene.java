package se300.destinytactics.game.scenes;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OrbitalBodyScene extends Stage {

	public GameScene myGame;
	public Skin skin;
	TextButton PlayerButton, PlayerButton2, PlayerButton3;
	int edgePadding;
	int buttonPadding = 5;

	static Texture bgimg = new Texture("StarfieldBackground.jpg");
	static Image background = new Image(bgimg);

	public OrbitalBodyScene(Viewport vp, int padding, Skin skin, final GameScene myGame) {

		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;
		
	}
	
	public void changePlanet(OrbitalBody nextOrbitalBody) {

		// System.out.println("Go to MyGame Method");
		// Clear stage to reuse it
		this.clear();

		//Set navBar name
		myGame.navBar.setName(nextOrbitalBody.getName());

		
		// Add image background and stretch to fit
		this.addActor(background);
		background.setFillParent(true);

		
		Image orbitalBody = new Image(
				OrbitalBody.hotBod[nextOrbitalBody.getType()]);
		orbitalBody.setSize(1000, 1000);
		this.addActor(orbitalBody);
		
		orbitalBody.setX(DestinyTactics.SCREEN_WIDTH / 4 - orbitalBody.getWidth() / 2);
		orbitalBody.setY(DestinyTactics.SCREEN_HEIGHT / 2 - orbitalBody.getHeight() / 2);
	}
	
}
