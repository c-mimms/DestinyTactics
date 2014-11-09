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

public class Infrastructure {
	public GameScene myGame;
	private Table container, overviewWrapper, buildFormWrapper, overview, buildForm;
	public Skin skin;
	
	// Public Methods
	public Infrastructure(Skin skin, final GameScene myGame) {
		this.skin = skin;
		this.myGame = myGame;
		
		// Build the overview and forms
		createOverview();
		createBuildForm();
		
		// assemble the container
		container = new Table(skin);
		container.add("INFRASTRUCTURE").expandX().top().height(GameScene.SCREEN_HEIGHT * 1/10);
		container.row();
		container.add(getOverview()).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		container.row();
		container.add(getBuildForm()).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		
		container.setFillParent(true);

	}
	
	public Table getInfrastructure() {
		return container;
	}
	
	public Table getOverview() {
		return overviewWrapper;
	}
	
	public Table getBuildForm() {
		return buildFormWrapper;
	}
	
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
		overview.add(new Label("Shipyard", skin)).width(GameScene.SCREEN_WIDTH/4);
		overview.add("Lv. 7").width(GameScene.SCREEN_WIDTH/4);
		
		overview.row();
		overview.add(new Label("Gas Mining Facilities", skin)).width(GameScene.SCREEN_WIDTH/4);
		if(myGame.curOrbitalBody != null){
			overview.add(new Label("Lv. " + myGame.curOrbitalBody.getMineLevel(), skin)).width(GameScene.SCREEN_WIDTH/4);
		} else {
			overview.add(new Label("Lv. " + 0, skin)).width(GameScene.SCREEN_WIDTH/4);

		}
		
		overview.row();
		overview.add(new Label("Refinery", skin)).expandX().fillX();
		overview.add("Lv. 12").expandX().fillX();
		
		overviewWrapper.add("Infrastructure Overview").left().expandX();
		overviewWrapper.row().top();                 
		overviewWrapper.add(overviewScroll).expand().fill().top();

		overviewWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		overviewWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}
	
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
		buildForm.add(new Label("Shipyard", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		TextButton upgradeButton_Shipyard = new TextButton("Upgrade", skin.get("default", TextButtonStyle.class));
		
		
		//BUTTON CLICKABLE
		upgradeButton_Shipyard.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("Upgrade Shipyard");
		        return true;
		}});
		buildForm.add(upgradeButton_Shipyard).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		buildForm.row();
		buildForm.add(new Label("Gas Mining Facilities", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		TextButton upgradeButton_GasMining = new TextButton("Upgrade", skin.get("default", TextButtonStyle.class));
		
		
		//BUTTON CLICKABLE
		upgradeButton_GasMining.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("Upgrade Gas");
		        return true;
		}});
		buildForm.add(upgradeButton_GasMining).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		buildForm.row();
		buildForm.add(new Label("Refinery", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		TextButton upgradeButton_Refinery = new TextButton("Upgrade", skin.get("default", TextButtonStyle.class));
		
		
		//BUTTON CLICKABLE
		upgradeButton_Refinery.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("Upgrade Mine");
		        myGame.curOrbitalBody.mineLevelUp();
		        return true;
		}});
		buildForm.add(upgradeButton_Refinery).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		
		buildFormWrapper.add("Build Fleet").left().expandX();
		buildFormWrapper.row().top();                 
		buildFormWrapper.add(formScroll).expand().fill().top();
		buildFormWrapper.row().top().expandX();
		
		buildFormWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		buildFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}
	
}
