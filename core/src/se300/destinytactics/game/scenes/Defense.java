package se300.destinytactics.game.scenes;

import se300.destinytactics.GameScene;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author Shannon
 * Defense creates a UI to build defenses
 */
public class Defense {
	private Table container, overviewWrapper, moveFormWrapper, buildFormWrapper, attackFormWrapper, overview, moveForm, buildForm, attackForm;
	private TextButton buildButton, moveButton, attackButton;
	public Skin skin;
	
	// Public Methods
	/**
	 * Constructor takes skin as a parameter. Combines defense overview and build form.
	 * @param skin
	 */
	public Defense(Skin skin) {
		this.skin = skin;
		
		
		// Build the overview and forms
		createOverview();
		createBuildForm();
		
		// assemble the container
		container = new Table(skin);
		container.add("DEFENSES").expandX().top().height(GameScene.SCREEN_HEIGHT * 1/10);
		container.row();
		container.add(getOverview()).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		container.row();
		container.add(getBuildForm()).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		
		container.setFillParent(true);
	}
	
	/**
	 * Gets defense container
	 * @return container
	 */
	public Table getDefense() {
		return container;
	}
	
	/**
	 * Gets overviewWrapper
	 * @return overviewWrapper
	 */
	public Table getOverview() {
		return overviewWrapper;
	}
	
	/**
	 * Gets buildFormWrapper
	 * @return buildFormWrapper
	 */
	public Table getBuildForm() {
		return buildFormWrapper;
	}
	
	/**
	 * Creates the overview of defense UI. Displays current defenses
	 */
	private void createOverview() {
		overviewWrapper = new Table(skin);
		overview = new Table(skin);
		
		final ScrollPane overviewScroll = new ScrollPane(overview, skin);
		InputListener stopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		overview.defaults().expandX();
		
		overview.row();
		overview.add(new Label("Light Turret", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		overview.add("50").width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		overview.row();
		overview.add(new Label("Medium Turret", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		overview.add("10").width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		overview.row();
		overview.add(new Label("Heavy Turret", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		overview.add("5").width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		overview.row();
		overview.add(new Label("Ship Cloak", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		overview.add("5").width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		overview.row();
		overview.add(new Label("Shield", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		overview.add("3").width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		
		overviewWrapper.add("Defense Overview").left().expandX();
		overviewWrapper.row().top();                 
		overviewWrapper.add(overviewScroll).expand().fill().top();
		overviewWrapper.row().top().expandX();
		
		overviewWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		overviewWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}
	
	/**
	 * Creates the build defense form. Accepts string inputs.
	 */
	private void createBuildForm() {
		buildFormWrapper = new Table(skin);
		buildForm = new Table(skin);
		
		final ScrollPane formScroll = new ScrollPane(buildForm, skin);
		InputListener formStopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		buildForm.defaults().expandX();
		
		buildForm.row();
		buildForm.add(new Label("Light Turret", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox = new TextField("", skin);
		buildForm.add(unitCountTextBox).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.add(new Label("Medium Turret", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox5 = new TextField("", skin);
		buildForm.add(unitCountTextBox5).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.row();
		buildForm.add(new Label("Heavy Turret", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox2 = new TextField("", skin);
		buildForm.add(unitCountTextBox2).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.add(new Label("Ship Cloak", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox4 = new TextField("", skin);
		buildForm.add(unitCountTextBox4).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.row();
		buildForm.add(new Label("Shield", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox3 = new TextField("", skin);
		buildForm.add(unitCountTextBox3).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		TextButton submitButton = new TextButton("Build Units", skin.get("default", TextButtonStyle.class));
		
		buildFormWrapper.add("Build Defenses").colspan(3).left().expandX();
		buildFormWrapper.row().top();                 
		buildFormWrapper.add(formScroll).expand().colspan(3).fill().top();
		buildFormWrapper.row().top().expandX();
		buildFormWrapper.add(cancelButton).right().top().width(GameScene.SCREEN_WIDTH/6);
		buildFormWrapper.add(clearButton).top().width(GameScene.SCREEN_WIDTH/6);
		buildFormWrapper.add(submitButton).left().top().width(GameScene.SCREEN_WIDTH/6);
		
		buildFormWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		buildFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}
}
