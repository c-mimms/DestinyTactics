package se300.destinytactics.game.scenes;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class NavBar extends Stage{

	public GameScene myGame;
	public Skin skin;
	public Label nameLabel;
	TextButton quitButton;
	TextButton endTurnButton;
	int edgePadding;
	int buttonPadding = 5;
	
	public NavBar(Viewport vp, int padding, final GameScene myGame) {
		
		//Call super constructor
		super(vp);
		
		this.edgePadding = padding;
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		nameLabel = new Label("Aurora", skin);
		this.addActor(nameLabel);
		
		nameLabel.setAlignment(Align.center);
		nameLabel.setFontScale(2);
		nameLabel.setX(DestinyTactics.SCREEN_WIDTH / 2);
		nameLabel.setY(DestinyTactics.SCREEN_HEIGHT - nameLabel.getHeight() - padding);
		
		
		// quit and end turn buttons
		quitButton = new TextButton("Quit", skin.get("default",
				TextButtonStyle.class));
		endTurnButton = new TextButton("End Turn", skin.get("default",
				TextButtonStyle.class));
		
		
		quitButton.setX(DestinyTactics.SCREEN_WIDTH - quitButton.getWidth()- padding);
		quitButton.setY(DestinyTactics.SCREEN_HEIGHT - quitButton.getHeight() - padding);
		endTurnButton.setX(DestinyTactics.SCREEN_WIDTH - endTurnButton.getWidth()
				- quitButton.getWidth() - padding - buttonPadding);
		endTurnButton.setY(DestinyTactics.SCREEN_HEIGHT - endTurnButton.getHeight()- padding);

		
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
				myGame.goGalaxy();
				return true;
			}
		});

		

		// Set galaxy stage to get inputs.
		this.addActor(quitButton);
		this.addActor(endTurnButton);
	}
	
	
	public void setName(String name){
		System.out.println(name);
		nameLabel.setText(name);
	}
	
}
