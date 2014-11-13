package se300.destinytactics.game.scenes;

import java.util.ArrayList;
import java.util.List;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.ui.SectorLabel;
import se300.destinytactics.ui.SectorLines;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
* <h1>Galaxy Map</h1>
* Modal window with a map of the galaxy that can be pulled up at any time.
* 
* <p>
* Not currently used.
*
* @author  Chris Mimms
* @version 0.1
* @since   2014-11-12
* @deprecated
*/
public class GalaxyMap extends Window {

	public static final int PARALLAX = 10;
	
	
	public Image gridOverlay;
	public GameScene myGame;
	public Skin skin;
	private Group sectors, sectorNames;
	private SectorLines sectorLines;
	List<SectorLabel> sectorNameArray;
	private static Texture sprite1 = new Texture(
			Gdx.files.internal("realorbitalbody/SectorIcon.png"), true);

	//Nearly exact copy of GalaxyScene, for now.
	public GalaxyMap(Skin skin, GameScene myGame) {

		super("Galaxy", skin);

		this.myGame = myGame;
		this.skin = skin;

		gridOverlay = new Image(new Texture("images/gridOverlay-ps.png"));

		gridOverlay.setFillParent(true);
		gridOverlay.setTouchable(Touchable.disabled);
		this.addActor(gridOverlay);

		sectors = new Group();
		sectorNames = new Group();
		sectorLines = new SectorLines();
		sectorNameArray = new ArrayList<SectorLabel>();
		for (int i = 0; i < myGame.m_Galaxy.sectors.length; i++) {
			final Sector copy = myGame.m_Galaxy.sectors[i];
			Image tmp = new Image(sprite1);
			tmp.setName(copy.getName());
			tmp.addListener(new ClickListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switchSector(copy);
					return true;
				}

			});
			tmp.setPosition(copy.getX(), copy.getY());
			sectors.addActor(tmp);
			
			String secName = copy.getName();
			SectorLabel tmpLabel = new SectorLabel(secName, skin);
			sectorNames.addActor(tmpLabel);
			tmpLabel.setX(myGame.m_Galaxy.sectors[i].getX()
					+ myGame.m_Galaxy.sectors[i].getWidth() / 2
					- tmpLabel.getWidth() / 2);
			tmpLabel.setAlignment(Align.center);

			if (Utility.random.nextBoolean()) {
				tmpLabel.setY(myGame.m_Galaxy.sectors[i].getY()
						+ tmpLabel.getHeight() / 2);
			} else {
				tmpLabel.setY(myGame.m_Galaxy.sectors[i].getY()
						- tmpLabel.getHeight());
			}

			tmpLabel.setOrig_posX(tmpLabel.getX());
			tmpLabel.setOrig_posY(tmpLabel.getY());
			sectorNameArray.add(tmpLabel);
		}

		this.addActor(sectors);
		this.addActor(sectorNames);
		this.addActor(sectorLines);

		sectorLines.updateGroups(sectors, sectorNames);

	}

	/**
	 * Change map to show selected sector.
	 * @param copy
	 */
	protected void switchSector(Sector copy) {
		// TODO Auto-generated method stub

	}

	public void act(float time) {
		super.act(time);
		int mousex = Gdx.input.getX();
		int mousey = Gdx.input.getY();
		float movex = (mousex - (this.getWidth() / 2)) / (this.getWidth() / 2);
		float movey = -(mousey - (this.getHeight() / 2))
				/ (this.getHeight() / 2);

		gridOverlay.setPosition(-movex * PARALLAX / 3, -movey * PARALLAX / 3);
		// sectors.setPosition(-movex * PARALLAX / 2, -movey * PARALLAX / 2);
		// sectorNames.setPosition(movex * PARALLAX / 2, movey * PARALLAX / 2);

		for (int i = 0; i < myGame.m_Galaxy.sectors.length; i++) {

			if (myGame.m_Galaxy.sectors[i] != null) {
				Sector sector = myGame.m_Galaxy.sectors[i];
				SectorLabel sectorName = sectorNameArray.get(i);
				sector.setX(sector.getXPos() + (-movex * PARALLAX / 3));
				sector.setY(sector.getYPos() + (-movey * PARALLAX / 3));
				sectorName
						.setX(sectorName.getOrig_posX() + (-movex * PARALLAX));
				sectorName
						.setY(sectorName.getOrig_posY() + (-movey * PARALLAX));
			}
		}

	}

}
