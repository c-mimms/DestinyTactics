package se300.destinytactics.game.orbitalbodies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Names;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.Drawable;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public abstract class OrbitalBody extends Actor {

	public static Galaxy galaxy;
	private int controlState;
	private Fleet fleet;
	private int miningEfficiency = 0;
	private String name;
	protected int orbitRadius;
	public Sector sector;
	public Fleet m_Fleet;
	public Button m_Button;
	public Random rand;
	public int type;
	

	// Import all planet textures
	public static Texture[] hotBod = {
			new Texture("realorbitalbody/activatedgate.png"),
			new Texture("realorbitalbody/deactivatedgate.png"),
			new Texture("realorbitalbody/gasplanet1.png"),
			new Texture("realorbitalbody/gasplanet2.png"),
			new Texture("realorbitalbody/lifeplanet1.png"),
			new Texture("realorbitalbody/lifeplanet2.png"),
			new Texture("realorbitalbody/moon1.png"),
			new Texture("realorbitalbody/moon2.png"),
			new Texture("realorbitalbody/rockplanet1.png"),
			new Texture("realorbitalbody/rockplanet2.png"),
			new Texture("realorbitalbody/station1.png"),
			new Texture("realorbitalbody/station2.png") };
	
	public static final int SPRITE_SIZE = 65;
	public static final int YEDGEEXCLUSION = GameScene.SCREEN_HEIGHT-SPRITE_SIZE-25; //-25 for tool bar
	public static final int XEDGEEXCLUSION = GameScene.SCREEN_WIDTH-275;
	public Texture texture;
	public GameScene thisgame;

	public OrbitalBody(int radius, Sector sect){
		
		
		orbitRadius = radius;
		name = Names.newName();
		sector = sect;
		controlState = 0;
		galaxy = sect.galaxy;
		thisgame = sect.thisgame;
		
		setBounds(0,0,SPRITE_SIZE, SPRITE_SIZE);
		
		addListener(new ClickListener(){
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("Listen Clicker added... calling switchToPlanetView()");
		        switchToPlanetView();
		        return true;
		    }
		});
		
		
	}
	
	public void switchToPlanetView(){
		System.out.println("In switchToPlanetView()...");
		galaxy.thisgame.switchToPlanetView(this);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void click() {

	}

	/**
	 * 
	 * @param body
	 */
	public int getDistance(OrbitalBody body) {
		int bodyPos = sector.getNumBodies() - orbitRadius;
		int otherBodyPos = body.getSector().getNumBodies() - body.getPos();
		int sectorDist = sector.getDistance(body.getSector());
		return bodyPos + otherBodyPos + sectorDist;
	}

	public String getName() {
		return name;
	}

	public abstract void getMiningEfficiency();

	public Fleet getFleet() {
		return fleet;
	}

	public Sector getSector() {
		return sector;
	}

	public int getPos() {
		return orbitRadius;
	}
	
	public int getState() {
		return controlState;
	}

	public int getType() {
		return type;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.draw(hotBod[type], getX(), getY(), SPRITE_SIZE, SPRITE_SIZE);
	}
}// end OrbitalBody