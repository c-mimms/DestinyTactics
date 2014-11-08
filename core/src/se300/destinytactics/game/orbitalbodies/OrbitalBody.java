package se300.destinytactics.game.orbitalbodies;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Assets;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Names;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.ui.Button;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public abstract class OrbitalBody extends Actor {

	public static Galaxy galaxy;
	protected int controlState;
	public Player owner;
	private Fleet fleet;
	private String name;
	protected int orbitRadius;
	public Sector sector;
	//public 
	public Fleet m_Fleet;
	public Button m_Button;
	public Random rand;
	public static int test ; 
	public int type;
	private static String spriteLib = "realorbitalbody";
	private static String imagelib = "images/orbitalbodies/planets";
	public static Texture[] planets;
	// Import all planet textures
//	public static Texture[] hotBod = {
//			new Texture(Gdx.files.internal(spriteLib + "/activatedgate.png"),true),
//			new Texture(Gdx.files.internal(spriteLib + "/deactivatedgate.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/life1.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/life1.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/red1.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/red2.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/redWater1.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/redWater2.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/water1.png"),true),
//			new Texture(Gdx.files.internal(imagelib + "/water2.png"),true),
//			new Texture(Gdx.files.internal(spriteLib + "/station1.png"),true),
//			new Texture(Gdx.files.internal(spriteLib + "/station2.png"),true) };
//	
	
	public static final int SPRITE_SIZE = 40;
	public static final int YEDGEEXCLUSION = GameScene.SCREEN_HEIGHT-GameScene.SCREEN_HEIGHT/10-SPRITE_SIZE; 
	public static final int XEDGEEXCLUSION = GameScene.SCREEN_WIDTH-275;
	public Texture texture;
	public GameScene myGame;
	public OrbitalBody body;
	
	//Mining Variable Initialization

	
	public OrbitalBody(int radius, Sector sect){		
		
		planets = Assets.getPlanetTypes();
		for(int i = 0; i < planets.length; i++){
			planets[i].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		}
		orbitRadius = radius;
		name = Names.newName();
		sector = sect;
		controlState = 0;
		//galaxy = sector.galaxy;
		controlState = 1;
		owner = new Player();
		
		setBounds(0,0,SPRITE_SIZE, SPRITE_SIZE);
		
		addListener(new ClickListener(){
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        switchToPlanetView();
		        return true;
		    }
		});
		
		
		
		
	}
	
	public void switchToPlanetView(){
		owner = sector.galaxy.thisgame.localPlayer;
		sector.galaxy.thisgame.switchToPlanetView(this);
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
	
	
	
	public int getSpriteSize() {
		return SPRITE_SIZE;
	}
	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.draw(planets[type], getX(), getY(), SPRITE_SIZE, SPRITE_SIZE);
	}
	
	public void act(float time){
	}
	
	//I guess abstract?
	public abstract void mineLevelUp();
	public abstract Integer getMineLevel();
	//The Methods below relate to the shipyard implemented
	
	
}// end OrbitalBody