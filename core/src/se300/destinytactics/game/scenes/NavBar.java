package se300.destinytactics.game.scenes;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Mike
 *
 * Adds UI along the top edge of the screen. 
 */
public class NavBar extends Stage {

	public GameScene myGame;
	public Skin skin;
	public Label nameLabel;
	TextButton quitButton;
	TextButton endTurnButton;
	int edgePadding;
	int buttonPadding = 5;
	TextButton backButton_Sector,backButton_Galaxy;

	/**
	 * @param vp
	 * @param padding
	 * @param skin
	 * @param myGame
	 */
	public NavBar(Viewport vp, int padding, Skin skin, final GameScene myGame) {

		// Call super constructor
		super(vp);
		this.myGame = myGame;
		this.edgePadding = padding;
		this.skin = skin;
		nameLabel = new Label(GameScene.m_Galaxy.getName(), skin);
		this.addActor(nameLabel);

		nameLabel.setAlignment(Align.center);
		nameLabel.setFontScale(2);
		nameLabel.setX(DestinyTactics.SCREEN_WIDTH / 2 - nameLabel.getWidth()/2);
		nameLabel.setY(DestinyTactics.SCREEN_HEIGHT - nameLabel.getHeight()
				- padding);

		// quit and end turn buttons
		quitButton = new TextButton("Quit", skin.get("default",
				TextButtonStyle.class));
		endTurnButton = new TextButton("End Turn", skin.get("default",
				TextButtonStyle.class));

		quitButton.setX(DestinyTactics.SCREEN_WIDTH - quitButton.getWidth()
				- padding);
		quitButton.setY(DestinyTactics.SCREEN_HEIGHT - quitButton.getHeight()
				- padding);
		endTurnButton.setX(DestinyTactics.SCREEN_WIDTH
				- endTurnButton.getWidth() - quitButton.getWidth() - padding
				- buttonPadding);
		endTurnButton.setY(DestinyTactics.SCREEN_HEIGHT
				- endTurnButton.getHeight() - padding);

		quitButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.goMenu();
				return true;
			}
		});

		endTurnButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.endTurn();
				return true;
			}
		});

		// Add Actors to sector UI
		backButton_Galaxy = new TextButton("Back to Galaxy",
				skin.get("default", TextButtonStyle.class));

		backButton_Sector = new TextButton("Back to Sector",
				skin.get("default", TextButtonStyle.class));
		

		backButton_Galaxy.setX(edgePadding);
		backButton_Galaxy.setY(DestinyTactics.SCREEN_HEIGHT - backButton_Galaxy.getHeight()
				- edgePadding);
		backButton_Galaxy.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.selectSound.play(myGame.masterVolume);
				myGame.goGalaxy();
				return true;
			}
		});
		
		backButton_Sector.setX(edgePadding);
		backButton_Sector.setY(DestinyTactics.SCREEN_HEIGHT - backButton_Sector.getHeight()
				- edgePadding);
		backButton_Sector.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.selectSound.play(myGame.masterVolume);
				myGame.goSystem();
				return true;
			}

		});

		// Set galaxy stage to get inputs.
		this.addActor(quitButton);
		this.addActor(endTurnButton);
		this.addActor(backButton_Sector);
		this.addActor(backButton_Galaxy);
	}

	/**
	 * Sets "name" of label in center top.
	 */
	public void setName(String name) {
		nameLabel.setText(name);
	}
	
	public void act(){
		super.act();
		if(myGame.galaxyView){
			backButton_Sector.setVisible(false);
			backButton_Galaxy.setVisible(false);
		}
		else if(myGame.planetView){
			backButton_Sector.setVisible(true);
			backButton_Galaxy.setVisible(false);
		}
		else if(myGame.sectorView){
			backButton_Sector.setVisible(false);
			backButton_Galaxy.setVisible(true);
		}
	}

}
