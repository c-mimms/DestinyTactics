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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GalaxyScene extends Stage {

	public Image background, gridOverlay;
	public Texture bgimg_galaxy;
	public GameScene myGame;
	public Skin skin;
	Fleet test, test2, test3;
	private Group sectors, sectorNames;
	private SectorLines sectorLines;
	List<SectorLabel> sectorNameArray;


	public static final int PARALLAX = 10;

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
		for (int i = 0; i < myGame.m_Galaxy.sectors.length; i++) {
			if (myGame.m_Galaxy.sectors[i] == null)
				break;
			sectors.addActor(myGame.m_Galaxy.sectors[i]);
			String secName = myGame.m_Galaxy.sectors[i].getName();
			
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
		
		test = new Fleet(myGame.m_Galaxy.sectors[0].bodyList[0],null);
		test2 = new Fleet(myGame.m_Galaxy.sectors[1].bodyList[0],null);
		test3 = new Fleet(myGame.m_Galaxy.sectors[2].bodyList[0],null);
		
		this.addActor(test);
		test.setColor(new Color(1, 0, 0, 1));
		test.setDestination(myGame.m_Galaxy.sectors[6].bodyList[0]);

		this.addActor(test2);
		test2.setColor(new Color(0, 0, 1, 1));
		test2.setDestination(myGame.m_Galaxy.sectors[7].bodyList[0]);

		this.addActor(test3);
		test3.setColor(new Color(0, 1, 0, 1));
		test3.setDestination(myGame.m_Galaxy.sectors[7].bodyList[0]);
		
		test.moveFleet();
		test2.moveFleet();
		test3.moveFleet();

	}

	public void endTurn() {
		test.moveFleet();
		if (test.getDestination() == null) {
			test.setDestination(myGame.m_Galaxy.sectors[Utility.random
					.nextInt(20)].bodyList[0]);
		}
		test2.moveFleet();
		if (test2.getDestination() == null) {
			test2.setDestination(myGame.m_Galaxy.sectors[Utility.random
					.nextInt(20)].bodyList[0]);
		}
		test3.moveFleet();
		if (test3.getDestination() == null) {
			test3.setDestination(myGame.m_Galaxy.sectors[Utility.random
					.nextInt(20)].bodyList[0]);
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
		
		for (int i = 0; i < myGame.m_Galaxy.sectors.length; i++) {
			if (myGame.m_Galaxy.sectors[i] != null) {
				Sector sector = myGame.m_Galaxy.sectors[i];
				SectorLabel sectorName = sectorNameArray.get(i);
				sector.setX(sector.orig_posX + (-movex * PARALLAX / 3));
				sector.setY(sector.orig_posY + (-movey * PARALLAX / 3));
				sectorName.setX(sectorName.getOrig_posX() + (-movex * PARALLAX));
				sectorName.setY(sectorName.getOrig_posY() + (-movey * PARALLAX));
			}
		}
		
	}
	
}
