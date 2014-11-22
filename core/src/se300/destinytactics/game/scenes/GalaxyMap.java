package se300.destinytactics.game.scenes;

import java.util.List;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Assets;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;
import se300.destinytactics.ui.SectorLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * <h1>Galaxy Map</h1> Modal window with a map of the galaxy that can be pulled
 * up at any time.
 * 
 * <p>
 * Not currently used.
 *
 * @author Chris Mimms
 * @version 0.1
 * @since 2014-11-12
 */
public class GalaxyMap extends Window {

	private static final float SCALE = 0.8f;

	public static Texture[] planets;
	public Stage calledFrom;
	public Image gridOverlay;
	public GameScene myGame;
	public Skin skin;
	private Group sectors, sectorNames, planet;
	List<SectorLabel> sectorNameArray;

	private static Texture sprite1 = new Texture(
			Gdx.files.internal("realorbitalbody/SectorIcon.png"), true);

	// Nearly exact copy of GalaxyScene, for now.
	public GalaxyMap(Skin skin, GameScene myGame, Stage stage) {
		
		super("Galaxy", skin);
		calledFrom = stage;
		this.myGame = myGame;
		this.skin = skin;
		this.setSize(DestinyTactics.SCREEN_WIDTH * SCALE,
				DestinyTactics.SCREEN_HEIGHT * SCALE);
		this.setPosition(10, 10);
		setGalaxy();

		planets = Assets.getPlanetTypes();
		for (int i = 0; i < planets.length; i++) {
			planets[i].setFilter(TextureFilter.MipMapLinearLinear,
					TextureFilter.Linear);
		}

	}

	/**
	 * Change map to show selected sector.
	 * 
	 * @param copy
	 */
	protected void switchSector(Sector copy) {

		this.clearChildren();
		planet = new Group();

		TextButton back = new TextButton("Back", skin);
		back.setX(0);
		back.setY(this.getHeight()-back.getHeight() - 30);
		back.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				setGalaxy();
				return true;
			}
		});
		this.addActor(back);


		TextButton cancel = new TextButton("Cancel", skin);
		cancel.setX(back.getWidth());
		cancel.setY(this.getHeight()-back.getHeight() - 30);
		cancel.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				remove();
				setGalaxy();
				return true;
			}
		});
		this.addActor(cancel);
		
		//System.out.println(copy.getName());
		for (OrbitalBody body : copy.bodyList) {
			final OrbitalBody copyBody = body;

			Image tmp = new Image(planets[body.type]);
			tmp.setSize(OrbitalBody.SPRITE_SIZE, OrbitalBody.SPRITE_SIZE);
			tmp.setName(copy.getName());
			tmp.addListener(new ClickListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					chooseBody(copyBody);
					return true;
				}
			});
			tmp.setScale(SCALE);
			tmp.setPosition(copyBody.getX() * SCALE, copyBody.getY() * SCALE);
			planet.addActor(tmp);
		}
		this.addActor(planet);

	}

	protected void chooseBody(OrbitalBody copyBody) {
		// TODO Auto-generated method stub
		//System.out.println(copyBody.getName());
		Fleet tmp = myGame.curOrbitalBody.getFleet();
		tmp.remove();
		tmp.setDestination(copyBody);
		myGame.galaxyStage.addActor(tmp);
		myGame.galaxyStage.fleets.add(tmp);

		this.remove();
		setGalaxy();

	}

	protected void setGalaxy() {

		this.clearChildren();

		TextButton cancel = new TextButton("Cancel", skin);
		cancel.setX(0);
		cancel.setY(this.getHeight()-cancel.getHeight() - 30);
		cancel.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				remove();
				setGalaxy();
				return true;
			}
		});
		this.addActor(cancel);
		
		
		sectors = new Group();
		sectorNames = new Group();
		for (int i = 0; i < GameScene.m_Galaxy.sectors.length; i++) {

			// Create sectors
			final Sector copy = GameScene.m_Galaxy.sectors[i];
			Image tmp = new Image(sprite1);
			tmp.setName(copy.getName());
			tmp.addListener(new ClickListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switchSector(copy);
					return true;
				}
			});
			tmp.setScale(SCALE);
			tmp.setPosition(copy.getX() * SCALE, copy.getY() * SCALE);
			sectors.addActor(tmp);

			// Create Labels
			String secName = copy.getName();
			SectorLabel tmpLabel = new SectorLabel(secName, skin);
			sectorNames.addActor(tmpLabel);
			tmpLabel.setScale(SCALE);
			tmpLabel.setAlignment(Align.center);
			tmpLabel.setX(tmp.getX(Align.center) - tmpLabel.getWidth() / 2);
			tmpLabel.setY(tmp.getY() + 20);

		}
		this.addActor(sectors);
		this.addActor(sectorNames);

	}

	public void act(float time) {
		super.act(time);
	}

}
