package se300.destinytactics.game.scenes;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fleet;

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

public class FleetCommand {
	private Table container, overviewWrapper, moveFormWrapper, buildFormWrapper, attackFormWrapper, overview, moveForm, buildForm, attackForm;
	private TextButton buildButton, moveButton, attackButton;
	public Skin skin;
	public GameScene myGame;
	
	// Public Methods
	public FleetCommand(Skin skin,  GameScene myGame) {
		this.skin = skin;
		this.myGame = myGame;
		
		// Create management buttons (displayed within overview)
		buildButton = new TextButton("Build Units", skin.get("default", TextButtonStyle.class));
		moveButton = new TextButton("Move Fleet", skin.get("default", TextButtonStyle.class));
		attackButton = new TextButton("Attack", skin.get("default", TextButtonStyle.class));
		
		// Add Click listeners. Changes the loaded form and the toggled button.
		buildButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				setForm("Build");
				return true;
			}
		});
		
		moveButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				setForm("Move");
				return true;
			}
		});
		
		attackButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				setForm("Attack");
				return true;
			}
		});
		
		// Build the overview and forms
		createOverview();
		createMoveForm();
		createBuildForm();
		createAttackForm();
		
		// assemble the container
		container = new Table(skin);
		container.add("FLEET COMMAND").expandX().top().height(GameScene.SCREEN_HEIGHT * 1/10);
		container.row();
		container.add(getOverview()).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		container.row();
		container.add(getBuildForm()).expandX().top().height(GameScene.SCREEN_HEIGHT * 3/10);
		
		container.setFillParent(true);
		
		// Fleet Command is instantiated with Build Form. Toggle button to select.
		buildButton.toggle();
	}
	
	public Table getFleetCommand() {
		return container;
	}
	
	public Table getOverview() {
		return overviewWrapper;
	}
	
	public Table getMoveForm() {
		return moveFormWrapper;
	}
	
	public Table getBuildForm() {
		return buildFormWrapper;
	}
	
	public Table getAttackForm() {
		return attackFormWrapper;
	}
	
	public void setForm(String formType) {
		Cell cell = getFormCell();
		
		if (cell.hasActor()) {
			cell.clearActor();
			
			if (formType == "Move") {
				cell.setActor(getMoveForm());
			}
			else if (formType == "Build") {
				cell.setActor(getBuildForm());
			}
			else if (formType == "Attack") {
				cell.setActor(getAttackForm());
			}
		}
	}
	
	// Private Methods
	private Cell getFormCell() {
		Cell cell;
		Table fc = getFleetCommand();
		
		cell = fc.getCell(getMoveForm());
		if (cell == null) {
			cell = fc.getCell(getBuildForm());
		} 
		if (cell == null) {
			cell = fc.getCell(getAttackForm());
		} 
		
		return cell;
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
		overview.add(new Label("Fighters", skin)).width(GameScene.SCREEN_WIDTH/8);
		overview.add("1000").width(GameScene.SCREEN_WIDTH/8);
		
		overview.add(new Label("Corvettes", skin)).width(GameScene.SCREEN_WIDTH/8);
		overview.add("40").width(GameScene.SCREEN_WIDTH/8);
		
		overview.row();
		overview.add(new Label("Bombers", skin)).expandX().fillX();
		overview.add("300").expandX().fillX();
		
		overview.add(new Label("Carriers", skin)).expandX().fillX();
		overview.add("12").expandX().fillX();
		
		overview.row();
		overview.add(new Label("Scoutships", skin)).expandX().fillX();
		overview.add("3").expandX().fillX();
		
		overview.add(new Label("Battleships", skin)).expandX().fillX();
		overview.add("7").expandX().fillX();
		
		overview.row();
		overview.add().expandX().fillX();
		overview.add().expandX().fillX();
		overview.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		overview.add("1").expandX().fillX();
		
		overviewWrapper.add("Fleet Overview").colspan(3).left().expandX();
		overviewWrapper.row().top();                 
		overviewWrapper.add(overviewScroll).expand().colspan(3).fill().top();
		overviewWrapper.row().top().expandX();
		overviewWrapper.add(buildButton).right().top().width(GameScene.SCREEN_WIDTH/6);
		overviewWrapper.add(moveButton).top().width(GameScene.SCREEN_WIDTH/6);
		overviewWrapper.add(attackButton).left().top().width(GameScene.SCREEN_WIDTH/6);
		
		overviewWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		overviewWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}
	
	private void createMoveForm() {
		moveFormWrapper = new Table(skin);
		moveForm = new Table(skin);
		
		final ScrollPane formScroll = new ScrollPane(moveForm, skin);
		InputListener formStopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		moveForm.defaults().expandX();
		
		moveForm.row();
		moveForm.add(new Label("Fighters", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox = new TextField("", skin);
		//unitCountTextBox.DigitsOnlyFilter();
		moveForm.add(unitCountTextBox).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		moveForm.add(new Label("Corvettes", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox5 = new TextField("", skin);
		moveForm.add(unitCountTextBox5).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		moveForm.row();
		moveForm.add(new Label("Bombers", skin)).expandX().fillX();
		TextField unitCountTextBox2 = new TextField("", skin);
		moveForm.add(unitCountTextBox2).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		moveForm.add(new Label("Carriers", skin)).expandX().fillX();
		TextField unitCountTextBox4 = new TextField("", skin);
		moveForm.add(unitCountTextBox4).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		moveForm.row();
		moveForm.add(new Label("Scoutships", skin)).expandX().fillX();
		TextField unitCountTextBox3 = new TextField("", skin);
		moveForm.add(unitCountTextBox3).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		moveForm.add(new Label("Battleships", skin)).expandX().fillX();
		TextField unitCountTextBox6 = new TextField("", skin);
		moveForm.add(unitCountTextBox6).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		moveForm.row();
		moveForm.add().expandX().fillX();
		moveForm.add().expandX().fillX();
		
		moveForm.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		TextField unitCountTextBox7 = new TextField("", skin);
		moveForm.add(unitCountTextBox7).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
	
		TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		TextButton submitButton = new TextButton("Move Fleet", skin.get("default", TextButtonStyle.class));
		
		moveFormWrapper.add("Move Fleet").colspan(3).left().expandX();
		moveFormWrapper.row().top();                 
		moveFormWrapper.add(formScroll).expand().colspan(3).fill().top();
		moveFormWrapper.row().top().expandX();
		moveFormWrapper.add(cancelButton).right().top().width(GameScene.SCREEN_WIDTH/6);
		moveFormWrapper.add(clearButton).top().width(GameScene.SCREEN_WIDTH/6);
		moveFormWrapper.add(submitButton).left().top().width(GameScene.SCREEN_WIDTH/6);
		
		moveFormWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		moveFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
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
		buildForm.add(new Label("Fighters", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		final TextField unitCountTextBox = new TextField("", skin);
		//unitCountTextBox.DigitsOnlyFilter();
		buildForm.add(unitCountTextBox).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.add(new Label("Corvettes", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox5 = new TextField("", skin);
		buildForm.add(unitCountTextBox5).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.row();
		buildForm.add(new Label("Bombers", skin)).expandX().fillX();
		TextField unitCountTextBox2 = new TextField("", skin);
		buildForm.add(unitCountTextBox2).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.add(new Label("Carriers", skin)).expandX().fillX();
		TextField unitCountTextBox4 = new TextField("", skin);
		buildForm.add(unitCountTextBox4).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.row();
		buildForm.add(new Label("Scoutships", skin)).expandX().fillX();
		TextField unitCountTextBox3 = new TextField("", skin);
		buildForm.add(unitCountTextBox3).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.add(new Label("Battleships", skin)).expandX().fillX();
		TextField unitCountTextBox6 = new TextField("", skin);
		buildForm.add(unitCountTextBox6).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		buildForm.row();
		buildForm.add().expandX().fillX();
		buildForm.add().expandX().fillX();
		
		buildForm.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		TextField unitCountTextBox7 = new TextField("", skin);
		buildForm.add(unitCountTextBox7).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		TextButton submitButton = new TextButton("Build Units", skin.get("default", TextButtonStyle.class));
		
		submitButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				buildUnits(Integer.parseInt(unitCountTextBox.getText()));
				
				return true;
			}

		});
		
		buildFormWrapper.add("Build Units").colspan(3).left().expandX();
		buildFormWrapper.row().top();                 
		buildFormWrapper.add(formScroll).expand().colspan(3).fill().top();
		buildFormWrapper.row().top().expandX();
		buildFormWrapper.add(cancelButton).right().top().width(GameScene.SCREEN_WIDTH/6);
		buildFormWrapper.add(clearButton).top().width(GameScene.SCREEN_WIDTH/6);
		buildFormWrapper.add(submitButton).left().top().width(GameScene.SCREEN_WIDTH/6);
		
		buildFormWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		buildFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}
	
	private void createAttackForm() {
		attackFormWrapper = new Table(skin);
		attackForm = new Table(skin);
		
		final ScrollPane formScroll = new ScrollPane(attackForm, skin);
		InputListener formStopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		attackForm.defaults().expandX();
		
		attackForm.row();
		attackForm.add(new Label("Fighters", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox = new TextField("", skin);
		//unitCountTextBox.DigitsOnlyFilter();
		attackForm.add(unitCountTextBox).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		attackForm.add(new Label("Corvettes", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		TextField unitCountTextBox5 = new TextField("", skin);
		attackForm.add(unitCountTextBox5).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		attackForm.row();
		attackForm.add(new Label("Bombers", skin)).expandX().fillX();
		TextField unitCountTextBox2 = new TextField("", skin);
		attackForm.add(unitCountTextBox2).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		attackForm.add(new Label("Carriers", skin)).expandX().fillX();
		TextField unitCountTextBox4 = new TextField("", skin);
		attackForm.add(unitCountTextBox4).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		attackForm.row();
		attackForm.add(new Label("Scoutships", skin)).expandX().fillX();
		TextField unitCountTextBox3 = new TextField("", skin);
		attackForm.add(unitCountTextBox3).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		attackForm.add(new Label("Battleships", skin)).expandX().fillX();
		TextField unitCountTextBox6 = new TextField("", skin);
		attackForm.add(unitCountTextBox6).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		attackForm.row();
		attackForm.add().expandX().fillX();
		attackForm.add().expandX().fillX();
		
		attackForm.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		TextField unitCountTextBox7 = new TextField("", skin);
		attackForm.add(unitCountTextBox7).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		TextButton submitButton = new TextButton("Attack", skin.get("default", TextButtonStyle.class));
		
		attackFormWrapper.add("Attack").colspan(3).left().expandX();
		attackFormWrapper.row().top();                 
		attackFormWrapper.add(formScroll).expand().colspan(3).fill().top();
		attackFormWrapper.row().top().expandX();
		attackFormWrapper.add(cancelButton).right().top().width(GameScene.SCREEN_WIDTH/6);
		attackFormWrapper.add(clearButton).top().width(GameScene.SCREEN_WIDTH/6);
		attackFormWrapper.add(submitButton).left().top().width(GameScene.SCREEN_WIDTH/6);
		
		attackFormWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		attackFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
	}

	
	private void buildUnits(int x) {
		// TODO Auto-generated method stub
		int tmp[] = {x};
		myGame.curOrbitalBody.m_Fleet = new Fleet(myGame.curOrbitalBody,tmp);
		
	}
	
}
