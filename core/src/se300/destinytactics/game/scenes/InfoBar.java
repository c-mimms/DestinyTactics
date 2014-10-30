package se300.destinytactics.game.scenes;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InfoBar extends Stage{

	private Label label1, label2;
	private TextField txt1, txt2;
	public Skin skin;
	TextButton PlayerButton, PlayerButton2, PlayerButton3;
	int edgePadding;
	int buttonPadding = 5;
	
	
	
	public InfoBar(Viewport vp, int padding, final GameScene myGame){
		super(vp);
		edgePadding = padding;
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		label1 = new Label("infoBar", skin);
		label1.setX(DestinyTactics.SCREEN_WIDTH / 2);
		label1.setY(0);
		label1.setAlignment(Align.center);
		
		PlayerButton = new TextButton("Player Info", skin.get("default",
				TextButtonStyle.class));
		PlayerButton2 = new TextButton("Current game status", skin.get(
				"default", TextButtonStyle.class));
		PlayerButton3 = new TextButton("Current Fleet Status", skin.get(
				"default", TextButtonStyle.class));
		txt1 = new TextField("txt1", skin);

		PlayerButton.setX(edgePadding);
		PlayerButton.setY(3 * PlayerButton.getHeight());
		PlayerButton2.setX(edgePadding);
		PlayerButton2.setY(2 * PlayerButton.getHeight());
		PlayerButton3.setX(edgePadding);
		PlayerButton3.setY(PlayerButton.getHeight());
		txt1.setX(DestinyTactics.SCREEN_WIDTH - txt1.getWidth() - edgePadding);
		txt1.setY(edgePadding);
		
		this.addActor(label1);
		this.addActor(PlayerButton);
		this.addActor(PlayerButton2);
		this.addActor(PlayerButton3);
		this.addActor(txt1);
	}
		
}

	
