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
	private Table container, overviewWrapper, buildFormWrapper, overview,
			buildForm;
	public Skin skin;

	public Label mineStuff, gasStuff, shipyardStuff;

	// Public Methods
	public Infrastructure(Skin skin, final GameScene myGame) {
		this.skin = skin;
		this.myGame = myGame;
		mineStuff = new Label("Lv: 0. Cost: 25. RPT: 100", skin);
		gasStuff = new Label("0", skin);
		shipyardStuff = new Label("0", skin);

		// Build the overview and forms
		createOverview();
		createBuildForm();

		// assemble the container
		container = new Table(skin);
		container.add("INFRASTRUCTURE").expandX().top()
				.height(GameScene.SCREEN_HEIGHT * 1 / 10);
		container.row();
		container.add(getOverview()).expandX().top()
				.height(GameScene.SCREEN_HEIGHT * 3 / 10);
		container.row();
		container.add(getBuildForm()).expandX().top()
				.height(GameScene.SCREEN_HEIGHT * 3 / 10);

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
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return false;
			}
		};

		overview.defaults().expandX();

		overview.row();
		overview.add(new Label("Shipyard", skin)).width(
				GameScene.SCREEN_WIDTH / 4);
		overview.add(shipyardStuff).width(GameScene.SCREEN_WIDTH / 4);

		overview.row();
		overview.add(new Label("Gas Mining Facilities", skin)).width(
				GameScene.SCREEN_WIDTH / 4);
		overview.add(gasStuff).width(GameScene.SCREEN_WIDTH / 4);

		overview.row();
		overview.add(new Label("Refinery", skin)).width(
				GameScene.SCREEN_WIDTH / 4);
		overview.add(mineStuff).width(GameScene.SCREEN_WIDTH / 4);

		overviewWrapper.add("Infrastructure Overview").left().expandX();
		overviewWrapper.row().top();
		overviewWrapper.add(overviewScroll).expand().fill().top();

		overviewWrapper.setWidth(GameScene.SCREEN_WIDTH / 2);
		overviewWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3 / 10);
	}

	private void createBuildForm() {
		buildFormWrapper = new Table(skin);
		buildForm = new Table(skin);

		final ScrollPane formScroll = new ScrollPane(buildForm, skin);
		InputListener formStopTouchDown = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return false;
			}
		};

		buildForm.defaults().expandX();

		/*
		 * Below creates the click-able "Shipyard" button, which also updates
		 * the related interface
		 */
		buildForm.row();
		buildForm.add(new Label("Shipyard", skin)).width(GameScene.SCREEN_WIDTH / 4).expandX().fillX();
		TextButton upgradeButton_Shipyard = new TextButton("Upgrade", skin.get("default", TextButtonStyle.class));
		upgradeButton_Shipyard.addListener(new InputListener() { // BUTTON CLICKABLE
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (myGame.curOrbitalBody.spentTurn == false) {
					myGame.curOrbitalBody.shipyardLevelUp();
					myGame.curOrbitalBody.spendTurn();
					shipyardStuff.setText("Lv: "
							+ myGame.curOrbitalBody.getShipyardLevel()
							+ ". Cost: "
							+ myGame.curOrbitalBody.getShipyardCost()
							+ ". Size: "
							+ myGame.curOrbitalBody.getShipyardSize());
				} else {

				}
				return true;

			}
		});
		buildForm.add(upgradeButton_Shipyard).width(GameScene.SCREEN_WIDTH / 4)
				.expandX().fillX();
		// End

		/*
		 * Below creates the click-able "Other Resource" button, which currently
		 * wastes space
		 */
		buildForm.row();
		buildForm.add(new Label("Other Resource", skin)).width(GameScene.SCREEN_WIDTH / 4).expandX().fillX();
		TextButton upgradeButton_GasMining = new TextButton("Upgrade HERE-aahhh", skin.get("default", TextButtonStyle.class));
		upgradeButton_GasMining.addListener(new InputListener() { // BUTTON CLICK-ABLE
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if (myGame.curOrbitalBody.spentTurn == false) {
							
						} else {
							
						}
						return true;
					}
				});
		buildForm.add(upgradeButton_GasMining).width(GameScene.SCREEN_WIDTH / 4).expandX().fillX();
		// End

		/*
		 * Below creates the click-able "Mine" button, which also updates the
		 * related interface.
		 */
		buildForm.row();
		buildForm.add(new Label("Mine", skin))
				.width(GameScene.SCREEN_WIDTH / 4).expandX().fillX();
		TextButton upgradeButton_Refinery = new TextButton("Upgrade", skin.get(
				"default", TextButtonStyle.class));
		upgradeButton_Refinery.addListener(new InputListener() { // Button Click-able
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if (myGame.curOrbitalBody.spentTurn == false) {
							myGame.curOrbitalBody.mineLevelUp();
							myGame.curOrbitalBody.spendTurn();
							mineStuff.setText("Lv: "
									+ myGame.curOrbitalBody.getMineLevel()
									+ ". Cost: "
									+ myGame.curOrbitalBody.getMineCost()
									+ ". RPT: "
									+ myGame.curOrbitalBody.getRPT());
						} else {
							System.out.println("Turn spent");
						}
						return true;
					}
				});
		buildForm.add(upgradeButton_Refinery).width(GameScene.SCREEN_WIDTH / 4)
				.expandX().fillX();
		// End

		buildFormWrapper.add("Build Fleet").left().expandX();
		buildFormWrapper.row().top();
		buildFormWrapper.add(formScroll).expand().fill().top();
		buildFormWrapper.row().top().expandX();

		buildFormWrapper.setWidth(GameScene.SCREEN_WIDTH / 2);
		buildFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3 / 10);
	}

}
