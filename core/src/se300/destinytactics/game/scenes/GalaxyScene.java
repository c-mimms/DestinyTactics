package se300.destinytactics.game.scenes;

import java.util.ArrayList;
import java.util.List;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.ui.SectorLabel;
import se300.destinytactics.ui.SectorLines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * @author Mimms
 * GalaxyScene extends Stage
 * This class builds the galaxy view that displays sectors and fleets in motion
 */
public class GalaxyScene extends Stage {

	public Image background, gridOverlay;
	public Texture bgimg_galaxy;
	public GameScene myGame;
	public Skin skin;
	private Group sectors, sectorNames;
	private SectorLines sectorLines;
	List<SectorLabel> sectorNameArray;
	ArrayList<Fleet> fleets = new ArrayList<Fleet>();


	public static final int PARALLAX = 10;

	/**
	 * @param vp
	 * @param skin
	 * @param myGame
	 */
	public GalaxyScene(FitViewport vp, Skin skin, GameScene myGame) {

		super(vp);

		this.myGame = myGame;
		this.skin = skin;

		bgimg_galaxy = new Texture("images/galaxies/1494661.jpg");
		//bgimg_galaxy = new Texture("images/galaxies/andromeda-galaxy-15763.jpg");
		//bgimg_galaxy = new Texture("images/galaxies/Chandra-Views-Dwarf-Galaxy-Colliding-with-NGC-1232.jpg");
		
		gridOverlay = new Image(new Texture("images/gridOverlay-ps.png"));
		background = new Image(bgimg_galaxy);

		background.setSize(getWidth() + 4 * PARALLAX, getHeight() + 4
				* PARALLAX);

		this.addActor(background);

		gridOverlay.setFillParent(true);
		gridOverlay.setTouchable(Touchable.disabled);
		this.addActor(gridOverlay);

		sectors = new Group();
		sectorNames = new Group();
		sectorLines = new SectorLines();
		sectorNameArray = new ArrayList<SectorLabel>();
		for (int i = 0; i < GameScene.m_Galaxy.sectors.length; i++) {
			if (GameScene.m_Galaxy.sectors[i] == null)
				break;
			sectors.addActor(GameScene.m_Galaxy.sectors[i]);
			String secName = GameScene.m_Galaxy.sectors[i].getName();
			
			SectorLabel tmpLabel = new SectorLabel(secName, skin);
			sectorNames.addActor(tmpLabel);
			tmpLabel.setX(GameScene.m_Galaxy.sectors[i].getX()
					+ GameScene.m_Galaxy.sectors[i].getWidth() / 2
					- tmpLabel.getWidth() / 2);
			tmpLabel.setAlignment(Align.center);

			if (Utility.random.nextBoolean()) {
				tmpLabel.setY(GameScene.m_Galaxy.sectors[i].getY()
						+ tmpLabel.getHeight() / 2);
			} else {
				tmpLabel.setY(GameScene.m_Galaxy.sectors[i].getY()
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
	 * endTurn in GalaxyScene updates fleets.
	 */
	public void endTurn() {
		
		ArrayList<Fleet> remove = new ArrayList<Fleet>();

		for(Fleet fleet : fleets){
			fleet.moveFleet();
			if(fleet.getDestination() == null){
				remove.add(fleet);
			}
		}
		for(Fleet fleet : remove){
			fleets.remove(fleet);
			fleet.remove();
		}
	}

	public void act(float time) {
		super.act(time);
		int mousex = Gdx.input.getX();
		int mousey = Gdx.input.getY();
		float movex = (mousex - (this.getWidth() / 2)) / (this.getWidth() / 2);
		float movey = -(mousey - (this.getHeight() / 2))
				/ (this.getHeight() / 2);

		background.setPosition(-movex * PARALLAX/4, -movey * PARALLAX/6);
		gridOverlay.setPosition(-movex * PARALLAX / 3, -movey * PARALLAX / 3);
		//sectors.setPosition(-movex * PARALLAX / 2, -movey * PARALLAX / 2);
		//sectorNames.setPosition(movex * PARALLAX / 2, movey * PARALLAX / 2);
		
		for (int i = 0; i < GameScene.m_Galaxy.sectors.length; i++) {
			if (GameScene.m_Galaxy.sectors[i] != null) {
				Sector sector = GameScene.m_Galaxy.sectors[i];
				SectorLabel sectorName = sectorNameArray.get(i);
				sector.setX(sector.getXPos() + (-movex * PARALLAX / 3));
				sector.setY(sector.getYPos() + (-movey * PARALLAX / 3));
				sectorName.setX(sectorName.getOrig_posX() + (-movex * PARALLAX));
				sectorName.setY(sectorName.getOrig_posY() + (-movey * PARALLAX));
			}
		}
		
	}
	
}
