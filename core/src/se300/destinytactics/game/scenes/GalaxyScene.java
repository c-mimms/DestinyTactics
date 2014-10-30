package se300.destinytactics.game.scenes;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GalaxyScene extends Stage{

	public Image background;
	public Texture bgimg_galaxy;
	public GameScene myGame;
	public Skin skin;
	
	
	public GalaxyScene(FitViewport vp, GameScene myGame) {
		super(vp);
		this.myGame = myGame;
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		bgimg_galaxy = new Texture("GalaxyBackground.jpg");
		Image gridOverlay = new Image(new Texture("images/gridOverlay-ps.png"));
		background = new Image(bgimg_galaxy);

		this.addActor(background);
		background.setFillParent(true);

		gridOverlay.setFillParent(true);
		gridOverlay.setTouchable(Touchable.disabled);
		this.addActor(gridOverlay);
		
		for (int i = 0; i < myGame.m_Galaxy.sectors.length; i++) {
			this.addActor(myGame.m_Galaxy.sectors[i]);
			String secName = myGame.m_Galaxy.sectors[i].getName();
			Label tmpLabel = new Label(secName, skin);
			this.addActor(tmpLabel);
			tmpLabel.setX(myGame.m_Galaxy.sectors[i].getX() - tmpLabel.getWidth() / 2);
			if (Utility.random.nextBoolean()) {
				tmpLabel.setY(myGame.m_Galaxy.sectors[i].getY() + tmpLabel.getHeight()/2);
			} else {
				tmpLabel.setY(myGame.m_Galaxy.sectors[i].getY() - tmpLabel.getHeight());
			}
		}
		
	}
}
