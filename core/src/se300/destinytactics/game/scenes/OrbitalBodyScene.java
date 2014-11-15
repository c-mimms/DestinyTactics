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

/**
 * 
 * @author Mike
 * 
 * OrbitalBodyScene extends Stage
 * This class builds the planet view.
 */
public class OrbitalBodyScene extends Stage {

	public GameScene myGame;
	public Skin skin;
	TextButton PlayerButton, PlayerButton2, PlayerButton3;
	int edgePadding;
	int buttonPadding = 5;

	static Texture bgimg = new Texture("StarfieldBackground.jpg");
	static Image background = new Image(bgimg);

	/**
	 * @param vp
	 * @param padding
	 * @param skin
	 * @param myGame
	 */
	public OrbitalBodyScene(Viewport vp, int padding, Skin skin, final GameScene myGame) {

		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;
		
	}
	
	/**
	 * Takes orbitalBody as a parameter. 
	 * Updates UI based on OB.
	 * @param nextOrbitalBody
	 */
	public void changePlanet(OrbitalBody nextOrbitalBody) {

		// System.out.println("Go to MyGame Method");
		// Clear stage to reuse it
		this.clear();

		//Set navBar name
		myGame.navBar.setName(nextOrbitalBody.getName());
		
		//Update planetUI infrastructure upon click
		myGame.planetUI.inf.mineStuff.setText("Lv: " + myGame.curOrbitalBody.getMineLevel() 
				+ ". Cost: " + myGame.curOrbitalBody.getMineCost()
			    + ". RPT: " + myGame.curOrbitalBody.getRPT());
		myGame.planetUI.inf.shipyardStuff.setText("Lv: " + myGame.curOrbitalBody.getShipyardLevel() 
				+ ". Cost: " + myGame.curOrbitalBody.getShipyardCost()
        		+ ". Size: " + myGame.curOrbitalBody.getShipyardSize());
		
		//Update planetUI fleet command upon click
		if(myGame.curOrbitalBody.m_Fleet != null){
			myGame.planetUI.fc.fighterNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Fighter")
				+"("
				+myGame.curOrbitalBody.getShips(0)
				+")");
			myGame.planetUI.fc.corvetteNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Corvette")
				+"("
				+myGame.curOrbitalBody.getShips(1)
				+")");
			myGame.planetUI.fc.bomberNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Bomber")
				+"("
				+myGame.curOrbitalBody.getShips(2)
				+")");
			myGame.planetUI.fc.carrierNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Carrier")
				+"("
				+myGame.curOrbitalBody.getShips(3)
				+")");
			myGame.planetUI.fc.scoutNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Scout")
				+"("
				+myGame.curOrbitalBody.getShips(4)
				+")");
			myGame.planetUI.fc.battleNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Battleship")
				+"("
				+myGame.curOrbitalBody.getShips(5)
				+")");
			myGame.planetUI.fc.dreadNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Dreadnaught")
				+"("
				+myGame.curOrbitalBody.getShips(6)
				+")");
		} else {
			myGame.planetUI.fc.fighterNum.setText("0( )");
			myGame.planetUI.fc.corvetteNum.setText("0( )");
			myGame.planetUI.fc.bomberNum.setText("0( )");
			myGame.planetUI.fc.carrierNum.setText("0( )");
			myGame.planetUI.fc.scoutNum.setText("0( )");
			myGame.planetUI.fc.battleNum.setText("0( )");
			myGame.planetUI.fc.dreadNum.setText("0( )");
		}
		
		// Add image background and stretch to fit
		this.addActor(background);
		background.setFillParent(true);

		
		Image orbitalBody = new Image(
				OrbitalBody.planets[nextOrbitalBody.getType()]);
		//orbitalBody.setDrawable(OrbitalBody.hotBod[nextOrbitalBody.getType()]);
		orbitalBody.setSize(1000, 1000);
		this.addActor(orbitalBody);
		
		orbitalBody.setX(DestinyTactics.SCREEN_WIDTH / 4 - orbitalBody.getWidth() / 2);
		orbitalBody.setY(DestinyTactics.SCREEN_HEIGHT / 2 - orbitalBody.getHeight() / 2);
	}
	
}
