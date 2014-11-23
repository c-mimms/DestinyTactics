package se300.destinytactics.ui;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;


/**
 * 
 * @author Mike
 * 
 * ToolTip extends Window
 * ToolTip displays relevant information about it's calling object.  *
 */
@SuppressWarnings("unused")
public class ToolTip extends Window {

	static Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	int controlState;
	String name;
	java.awt.Point position;
	int numBodies = 0;
	public int mine;
	public int totalRPT;

	/**
	 * Constructor takes a name and sector as parameters.
	 * Gets number of bodies in a sector.
	 * Gets owner.
	 * Gets position of sector for display purposes. 
	 * @param name
	 * @param sector
	 */
	public ToolTip(String name , Sector sector) {
		//Init
		super(name, skin);
		//sector.galaxy.thisgame.galaxyStage.addActor(this);
		numBodies = sector.getNumBodies();
		controlState = sector.getState();
		position = sector.getPos();
		for(OrbitalBody body : sector.bodyList) {
			totalRPT += body.getRPT();
		}
		
		//Creating labels for scale-able text
		Label nameL = new Label(name, skin);
		Label numBodiesL = new Label("Orbital Bodies: " +  numBodies, skin);
		Label ownerL = new Label("Owner: " + sector.getOwner().getName(), skin);
		Label tRPTL = new Label("RPT: " + totalRPT, skin);
		Label positionL = new Label("Coords: (" + position.x + "," + position.y + ")", skin);
		
		add(numBodiesL).left();
		row();
		left();
		add(ownerL).left();
		row();
		add(tRPTL).left();
		row();
		add(positionL).left();
		
		pack();
		
		setPosition(position.x+Sector.SPRITE_SIZE+20, position.y-this.getHeight()/2);
		if(position.x > DestinyTactics.SCREEN_WIDTH-200)
			setPosition(position.x-this.getWidth()-20, position.y-this.getHeight()/2);
		
	}
	
	/**
	 * Constructor takes name and orbital body as arguments.
	 * @param name
	 * @param body
	 */
	public ToolTip(String name, OrbitalBody body) {
		super(name, skin);
		//body.sector.galaxy.thisgame.sectorStage.addActor(this); //LOLOL
		
		/*
		 * Will display:
		 * Owner
		 * Resources per turn
		 * Mine level
		 * Shipyard level
		 * Shipyard number of ships in queue
		 * Shipyard space available
		 */
		controlState = body.getState();
		String owner = body.owner.getName();
		int RPT = body.getRPT();
		mine = body.getMineLevel();
		int shipyardLv = body.getShipyardLevel();
		int shipyardQueue = body.getBuildQueueSize();
		int spaceAv = body.getShipyardSize();
		float x = body.getX();
		float y = body.getY();
		
		add("Owner: " + owner).left();
		row();
		add("RPT: " + RPT).left();
		row();
		add("Mine Lv: " + mine).left();
		row();
		add("Shipyard Lv: " + shipyardLv).left();
		row();
		add("Ships Queued: " + shipyardQueue).left();
		row();
		add("Available : " + spaceAv).left();
		
		pack();
		setPosition(x+OrbitalBody.SPRITE_SIZE+10, y-this.getHeight()/2 + OrbitalBody.SPRITE_SIZE/2);
	}
	
}
