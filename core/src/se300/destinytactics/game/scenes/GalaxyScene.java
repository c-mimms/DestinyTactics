package se300.destinytactics.game.scenes;


import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GalaxyScene extends Stage{

	public Image background;
	public Texture bgimg_galaxy;
	public GameScene myGame;
	public Skin skin;
	Fleet test  = new Fleet();
	Fleet test2  = new Fleet();
	
	
	public GalaxyScene(FitViewport vp, Skin skin, GameScene myGame) {
		super(vp);
		this.myGame = myGame;
		this.skin = skin;

		bgimg_galaxy = new Texture("GalaxyBackground.jpg");
		Image gridOverlay = new Image(new Texture("images/gridOverlay-ps.png"));
		background = new Image(bgimg_galaxy);

		this.addActor(background);
		background.setFillParent(true);

		gridOverlay.setFillParent(true);
		gridOverlay.setTouchable(Touchable.disabled);
		this.addActor(gridOverlay);
		
		
		for (int i = 0; i < myGame.m_Galaxy.sectors.length; i++) {
			if(myGame.m_Galaxy.sectors[i]==null)break;
			this.addActor(myGame.m_Galaxy.sectors[i]);
			String secName = myGame.m_Galaxy.sectors[i].getName();
			
			Label tmpLabel = new Label(secName, skin);
			this.addActor(tmpLabel);
			tmpLabel.setX(myGame.m_Galaxy.sectors[i].getX() + myGame.m_Galaxy.sectors[i].getWidth()/2 - tmpLabel.getWidth() / 2);
			tmpLabel.setAlignment(Align.center);
			
			if (Utility.random.nextBoolean()) {
				tmpLabel.setY(myGame.m_Galaxy.sectors[i].getY() + tmpLabel.getHeight()/2);
			} else {
				tmpLabel.setY(myGame.m_Galaxy.sectors[i].getY() - tmpLabel.getHeight());
			}
		}
		this.addActor(test);
		test.setColor(new Color(0, 0, 1, 1));
		test.setLocation(myGame.m_Galaxy.sectors[0].bodyList[0]);
		test.setDestination(myGame.m_Galaxy.sectors[6].bodyList[0]);

		this.addActor(test2);
		test2.setColor(new Color(1, 0, 0, 1));
		test2.setLocation(myGame.m_Galaxy.sectors[1].bodyList[0]);
		test2.setDestination(myGame.m_Galaxy.sectors[7].bodyList[0]);
		
		
	}
	public void endTurn(){
		test.moveFleet();
		if(test.getDestination() == null){
			test.setDestination(myGame.m_Galaxy.sectors[Utility.random.nextInt(20)].bodyList[0]);
		}
		test2.moveFleet();
		if(test2.getDestination() == null){
			test2.setDestination(myGame.m_Galaxy.sectors[Utility.random.nextInt(20)].bodyList[0]);
		}
	}
}
