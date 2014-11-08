package se300.destinytactics.game.scenes;

import se300.destinytactics.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OrbitalBodyUI extends Stage {

	public GameScene myGame;
	public Table managementInterface;
	public FleetCommand fc;
	public Infrastructure inf;
	public Skin skin;
	public Defense def;
	int edgePadding;
	int buttonPadding = 5;

	public OrbitalBodyUI(Viewport vp, int padding, Skin skin, final GameScene myGame) {

		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;
		fc = new FleetCommand(skin);
		inf = new Infrastructure(skin, myGame);
		def = new Defense(skin);

		managementInterface = new Table();
		managementInterface.add(fc.getFleetCommand()).expand().top();
		managementInterface.setHeight(GameScene.SCREEN_HEIGHT * 7 / 10);
		managementInterface.setWidth(GameScene.SCREEN_WIDTH / 2);
		managementInterface.setY(GameScene.SCREEN_HEIGHT * 2 / 10);
		managementInterface.setX(GameScene.SCREEN_WIDTH / 2 - padding);

		TextButton managefleet = new TextButton("Fleet Command", skin.get(
				"default", TextButtonStyle.class));
		TextButton manageInfrastructure = new TextButton("Infrastructure",
				skin.get("default", TextButtonStyle.class));
		TextButton manageDefense = new TextButton("Defense", skin.get(
				"default", TextButtonStyle.class));

		// Add Click listeners. Changes the loaded form and the toggled button.
		managefleet.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.selectSound.play(myGame.masterVolume);
				setManagementInterface("Fleet");
				return true;
			}
		});

		manageInfrastructure.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.selectSound.play(myGame.masterVolume);
				setManagementInterface("Infrastructure");
				return true;
			}
		});

		manageDefense.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				myGame.selectSound.play(myGame.masterVolume);
				setManagementInterface("Defense");
				return true;
			}
		});

		this.addActor(managefleet);
		this.addActor(manageInfrastructure);
		this.addActor(manageDefense);
		this.addActor(managementInterface);

		managefleet.setX(edgePadding);
		managefleet.setY(managefleet.getHeight() * 9);
		manageInfrastructure.setX(edgePadding);
		manageInfrastructure.setY(managefleet.getHeight() * 8);
		manageDefense.setX(edgePadding);
		manageDefense.setY(managefleet.getHeight() * 7);

	}

	public void setManagementInterface(String formType) {
		Cell cell = getFormCell();

		if (cell.hasActor()) {
			cell.clearActor();

			if (formType == "Fleet") {
				cell.setActor(fc.getFleetCommand());
			} else if (formType == "Infrastructure") {
				cell.setActor(inf.getInfrastructure());
			} else if (formType == "Defense") {
				cell.setActor(def.getDefense());
			}
		}
	}

	// Private Methods
	private Cell getFormCell() {
		Cell cell;

		cell = managementInterface.getCell(fc.getFleetCommand());
		if (cell == null) {
			cell = managementInterface.getCell(inf.getInfrastructure());
		}
		if (cell == null) {
			cell = managementInterface.getCell(def.getDefense());
		}

		return cell;
	}
}
