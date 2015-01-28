package se300.destinytactics.game.scenes;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Battleship;
import se300.destinytactics.game.fleet.Bomber;
import se300.destinytactics.game.fleet.Carrier;
import se300.destinytactics.game.fleet.Corvette;
import se300.destinytactics.game.fleet.Dreadnaught;
import se300.destinytactics.game.fleet.Fighter;
import se300.destinytactics.game.fleet.Scout;
import se300.destinytactics.ui.Confirmation;
import se300.destinytactics.ui.ToolTip;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * 
 * @author Shannon
 * 
 * FleetCommand creates a UI for all things fleet.
 *
 */
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class FleetCommand {
	private Table container, overviewWrapper, moveFormWrapper, buildFormWrapper, attackFormWrapper, overview, moveForm, buildForm, attackForm;
	private TextButton buildButton, moveButton, attackButton;
	public Skin skin;
	public GameScene myGame;
	
	//Labels for number of ships
	public Label fighterNum, corvetteNum, bomberNum, carrierNum, scoutNum, battleNum, dreadNum;
	
	//Hovering Tooltip
	boolean hovering;
	ToolTip toolTip;
	public String name;
	
	
	
	// Public Methods
	/**
	 * Constructor takes skin and mygame as parameters. 
	 * Combines Overview, Build, Move, and Attack forms. 
	 * Buttons switch between all.
	 * @param skin
	 * @param myGame
	 */
	public FleetCommand(Skin skin,  GameScene myGame) {
		this.skin = skin;
		this.myGame = myGame;
		
		// Create management buttons (displayed within overview)
		buildButton = new TextButton("Build Units", skin.get("default", TextButtonStyle.class));
		moveButton = new TextButton("Move Fleet", skin.get("default", TextButtonStyle.class));
		attackButton = new TextButton("Attack", skin.get("default", TextButtonStyle.class));
		
		//Initialize Nums
		fighterNum = new Label("0" + " (0 queued)", skin);
		corvetteNum = new Label("0" + " (0 queued)", skin);
		bomberNum = new Label("0" + " (0 queued)", skin);
		carrierNum = new Label("0" + " (0 queued)", skin);
		scoutNum = new Label("0" + " (0 queued)", skin);
		battleNum = new Label("0" + " (0 queued)", skin);
		dreadNum = new Label("0" + " (0 queued)", skin);
		
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
	
	/**
	 * Gets fleet command container
	 * @return container
	 */
	public Table getFleetCommand() {
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
	 * Gets moveFormWrapper
	 * @return moveFormWrapper
	 */
	public Table getMoveForm() {
		return moveFormWrapper;
	}
	
	/**
	 * Gets buildFormWrapper
	 * @return buildFormWrapper
	 */
	public Table getBuildForm() {
		return buildFormWrapper;
	}
	
	/**
	 * Gets attackFormWrapper
	 * @return attackFormWrapper
	 */
	public Table getAttackForm() {
		return attackFormWrapper;
	}
	
	/**
	 * Switches out Move, Build, and Attack forms
	 * @param formType
	 */
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
	/**
	 * Gets current form
	 * @return cell
	 */
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
	
	/**
	 * Creates overview of fleet command. 
	 * Displays number of fleets in orbit and in the build queue
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
		overview.add(new Label("Fighters", skin)).width(GameScene.SCREEN_WIDTH/8);
		overview.add(fighterNum).width(GameScene.SCREEN_WIDTH/8);
		
		overview.add(new Label("Corvettes", skin)).width(GameScene.SCREEN_WIDTH/8);
		overview.add(corvetteNum).width(GameScene.SCREEN_WIDTH/8);
		
		overview.row();
		overview.add(new Label("Bombers", skin)).expandX().fillX();
		overview.add(bomberNum).expandX().fillX();
		
		overview.add(new Label("Carriers", skin)).expandX().fillX();
		overview.add(carrierNum).expandX().fillX();
		
		overview.row();
		overview.add(new Label("Scoutships", skin)).expandX().fillX();
		overview.add(scoutNum).expandX().fillX();
		
		overview.add(new Label("Battleships", skin)).expandX().fillX();
		overview.add(battleNum).expandX().fillX();
		
		overview.row();
		overview.add().expandX().fillX();
		overview.add().expandX().fillX();
		overview.add(new Label("Dreadnaughts", skin)).expandX().fillX();
		overview.add(dreadNum).expandX().fillX();
		
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
	
	/**
	 * Creates move form. Takes strings as input.
	 */
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
		
		//FIGHTERS
		moveForm.row();
		Label fighterMove = new Label("Fighters", skin);
		moveForm.add(fighterMove).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		final TextField unitCountTextBox = new TextField("0", skin);
		unitCountTextBox.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		fighterMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Fighter", unitCountTextBox, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});

		//CORVETTES
		Label corvetteMove = new Label("Corvettes", skin);
		moveForm.add(corvetteMove).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		final TextField unitCountTextBox5 = new TextField("0", skin);
		unitCountTextBox5.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox5).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		corvetteMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Corvette", unitCountTextBox5, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			}
		});
		
		//BOMBERS
		moveForm.row();
		Label bomberMove = new Label("Bombers", skin);
		moveForm.add(bomberMove).expandX().fillX();
		final TextField unitCountTextBox2 = new TextField("0", skin);
		unitCountTextBox2.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox2).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		bomberMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Bomber", unitCountTextBox2, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			}
		});
		
		//CARRIERS
		Label carrierMove = new Label("Carriers", skin);
		moveForm.add(carrierMove).expandX().fillX();
		final TextField unitCountTextBox4 = new TextField("0", skin);
		unitCountTextBox4.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox4).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		carrierMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Carrier", unitCountTextBox4, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			}
		});
		
		//SCOUTSHIPS
		moveForm.row();
		Label scoutMove = new Label("Scoutships", skin);
		moveForm.add(scoutMove).expandX().fillX();
		final TextField unitCountTextBox3 = new TextField("0", skin);
		unitCountTextBox3.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox3).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		scoutMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Scoutship", unitCountTextBox3, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			}
		});
		
		//BATTLESHIPS
		Label battleMove = new Label("Battleships", skin);
		moveForm.add(battleMove).expandX().fillX();
		final TextField unitCountTextBox6 = new TextField("0", skin);
		unitCountTextBox6.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox6).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		battleMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Battleship", unitCountTextBox6, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			}
		});
		
		
		//DREADNAUGHTS
		moveForm.row();
		moveForm.add().expandX().fillX();
		moveForm.add().expandX().fillX();
		
		Label dreadMove = new Label("Dreadnaughts", skin);
		moveForm.add(dreadMove).expandX().fillX();
		final TextField unitCountTextBox7 = new TextField("0", skin);
		unitCountTextBox7.setTextFieldFilter(new DigitsOnlyFilter());
		moveForm.add(unitCountTextBox7).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		dreadMove.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Dreadnaught", unitCountTextBox7, "move");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			}
		});
	
		TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		
		/*
		 * Working Clear Button
		 */
		TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		
		clearButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				clearMoveSelection();
				return true;
			}
			
			public void clearMoveSelection(){
				unitCountTextBox.setText("0");
				unitCountTextBox2.setText("0");
				unitCountTextBox3.setText("0");
				unitCountTextBox4.setText("0");
				unitCountTextBox5.setText("0");
				unitCountTextBox6.setText("0");
				unitCountTextBox7.setText("0");
			}
			
			
		});
		
		/*
		 * Workings Submit Button
		 */
		TextButton submitButton = new TextButton("Move Fleet", skin.get("default", TextButtonStyle.class));
	
		submitButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				moveFleet();
				clearMoveSelection();
				return true;
			}
			
			public void clearMoveSelection(){
				unitCountTextBox.setText("0");
				unitCountTextBox2.setText("0");
				unitCountTextBox3.setText("0");
				unitCountTextBox4.setText("0");
				unitCountTextBox5.setText("0");
				unitCountTextBox6.setText("0");
				unitCountTextBox7.setText("0");
			}
		});
		
		
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
	
	/**
	 * Creates build form. Takes strings as input.
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
		
		//FIGHTERS
		buildForm.row();
		Label fighterBuild = new Label("Fighters", skin);
		buildForm.add(fighterBuild).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		final TextField fighterCount = new TextField("0", skin);
		fighterCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(fighterCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		fighterBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Fighter", fighterCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		//CORVETTES
		Label corvetteBuild = new Label("Corvettes", skin);
		buildForm.add(corvetteBuild).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		final TextField corvetteCount = new TextField("0", skin);
		corvetteCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(corvetteCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		corvetteBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Corvette", corvetteCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		//BOMBERS
		buildForm.row();
		Label bomberBuild = new Label("Bombers", skin);
		buildForm.add(bomberBuild).expandX().fillX();
		final TextField bomberCount = new TextField("0", skin);
		bomberCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(bomberCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		bomberBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Bomber", bomberCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		//CARRIERS
		Label carrierBuild = new Label("Carriers", skin);
		buildForm.add(carrierBuild).expandX().fillX();
		final TextField carrierCount = new TextField("0", skin);
		carrierCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(carrierCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		carrierBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Carrier", carrierCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		//SCOUTSHIPS
		buildForm.row();
		Label scoutBuild = new Label("Scoutships", skin);
		buildForm.add(scoutBuild).expandX().fillX();
		final TextField scoutCount = new TextField("0", skin);
		scoutCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(scoutCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		scoutBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Scoutship", scoutCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		//BATTLESHIPS
		Label battleBuild = new Label("Battleships", skin);
		buildForm.add(battleBuild).expandX().fillX();
		final TextField battleshipCount = new TextField("0", skin);
		battleshipCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(battleshipCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		battleBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Battleship", battleshipCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		//FILLER
		buildForm.row();
		buildForm.add().expandX().fillX();
		buildForm.add().expandX().fillX();
		
		//DREADNAUGHTS
		Label dreadBuild = new Label("Dreadnaughts", skin);
		buildForm.add(dreadBuild).expandX().fillX();
		final TextField dreadCount = new TextField("0", skin);
		dreadCount.setTextFieldFilter(new DigitsOnlyFilter());
		buildForm.add(dreadCount).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		dreadBuild.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn("Dreadnaught", dreadCount, "build");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOff();
			
			}
		});
		
		TextButton cancelButton = new TextButton("Cancel", skin.get("default", TextButtonStyle.class));
		TextButton clearButton = new TextButton("Clear", skin.get("default", TextButtonStyle.class));
		TextButton submitButton = new TextButton("Build Units", skin.get("default", TextButtonStyle.class));
		
		submitButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//buildUnits(Integer.parseInt(fighterCount.getText()));
				//buildForm.act(0.01f);
				for(int i = 0; i < Integer.parseInt(fighterCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Fighter());
				}
				for(int i = 0; i < Integer.parseInt(corvetteCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Corvette());
				}
				for(int i = 0; i < Integer.parseInt(bomberCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Bomber());
				}
				for(int i = 0; i < Integer.parseInt(carrierCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Carrier());
					
				}
				for(int i = 0; i < Integer.parseInt(scoutCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Scout());
					
				}
				for(int i = 0; i < Integer.parseInt(battleshipCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Battleship());
			
				}
				for(int i = 0; i < Integer.parseInt(dreadCount.getText()); i++){
					myGame.curOrbitalBody.addToQueue(new Dreadnaught());
					
				}
				
				//Set each to 0
				fighterCount.setText("0");
				corvetteCount.setText("0");
				bomberCount.setText("0");
				carrierCount.setText("0");
				scoutCount.setText("0");
				battleshipCount.setText("0");
				dreadCount.setText("0");
				
				//Instant update
				if(myGame.curOrbitalBody.hasFleet() == true){
				fighterNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Fighter")
						+" ("
						+myGame.curOrbitalBody.getShips(0)
						+" queued)");
				corvetteNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Corvette")
						+" ("
						+myGame.curOrbitalBody.getShips(1)
						+" queued)");
				bomberNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Bomber")
						+" ("
						+myGame.curOrbitalBody.getShips(2)
						+" queued)");
				carrierNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Carrier")
						+" ("
						+myGame.curOrbitalBody.getShips(3)
						+" queued)");
				scoutNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Scout")
						+" ("
						+myGame.curOrbitalBody.getShips(4)
						+" queued)");
				battleNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Battleship")
						+" ("
						+myGame.curOrbitalBody.getShips(5)
						+" queued)");
				dreadNum.setText(myGame.curOrbitalBody.m_Fleet.getShipCount("Dreadnaught")
						+" ("
						+myGame.curOrbitalBody.getShips(6)
						+" queued)");
				} else {
					fighterNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(0)
							+" queued)");
					corvetteNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(1)
							+" queued)");
					bomberNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(2)
							+" queued)");
					carrierNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(3)
							+" queued)");
					scoutNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(4)
							+" queued)");
					battleNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(5)
							+" queued)");
					dreadNum.setText("0"
							+" ("
							+myGame.curOrbitalBody.getShips(6)
							+" queued)");
				}
				
				//Popup window for all added
				
				Confirmation popup = new Confirmation("Name", skin, myGame.curOrbitalBody.getShips(0), myGame.curOrbitalBody.getShips(1),
						myGame.curOrbitalBody.getShips(3), myGame.curOrbitalBody.getShips(2), myGame.curOrbitalBody.getShips(4), myGame.curOrbitalBody.getShips(5),
						myGame.curOrbitalBody.getShips(6), myGame.curOrbitalBody.getStage());
				myGame.planetUI.addActor(popup);

				popup = null;
				
				
				return true;
			}

		});
		
		cancelButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//Set each to 0
				fighterCount.setText("0");
				corvetteCount.setText("0");
				bomberCount.setText("0");
				carrierCount.setText("0");
				scoutCount.setText("0");
				battleshipCount.setText("0");
				dreadCount.setText("0");
				return true;
			}
		});
		
		clearButton.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//Set each to 0
				fighterCount.setText("0");
				corvetteCount.setText("0");
				bomberCount.setText("0");
				carrierCount.setText("0");
				scoutCount.setText("0");
				battleshipCount.setText("0");
				dreadCount.setText("0");
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
	
	/**
	 * Creates attack form. Takes strings as input.
	 */
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


	
	private void moveFleet(){
		
		if(myGame.curOrbitalBody.m_Fleet !=null){
			
			myGame.planetUI.getDestination();
			
			
		}
		
	}
	

	public void hoverOn(String name, TextField textBox, String purpose) {
		if (toolTip == null) {
			toolTip = new ToolTip(name, textBox, purpose);
		}
		if (purpose == "move")
			this.getMoveForm().addActor(toolTip);
		if (purpose == "build")
			this.getBuildForm().addActor(toolTip);
		if (purpose == "attack")
			this.getAttackForm().addActor(toolTip);
		
		toolTip.addAction(sequence(Actions.alpha(0), Actions.delay(0.3f),
				Actions.fadeIn(0.4f, Interpolation.fade)));

		hovering = true;
	}

	
	public void hoverOff() {
		hovering = false;
		if(toolTip != null)
			toolTip.remove();
		toolTip = null;
	}
	
}
