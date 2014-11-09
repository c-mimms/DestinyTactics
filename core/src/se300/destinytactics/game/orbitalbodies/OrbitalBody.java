package se300.destinytactics.game.orbitalbodies;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Assets;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Names;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.ToolTip;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public abstract class OrbitalBody extends Actor {

	public static Galaxy galaxy;
	protected int controlState;
	public Player owner;
	public Fleet fleet;
	public String name;
	protected int orbitRadius;
	public Sector sector;
	//public 
	public Fleet m_Fleet;
	public Button m_Button;
	public Random rand;
	public static int test ; 
	public int type;
	public static Texture[] planets;

	public static Texture circles[] = {
			new Texture(Gdx.files.internal("images/blueSelect.png")),
			new Texture(Gdx.files.internal("images/redSelect.png")) };

	public Image myCircle = new Image(circles[0]);
	
	public static final int SPRITE_SIZE = 40;
	public static final int YEDGEEXCLUSION = GameScene.SCREEN_HEIGHT-GameScene.SCREEN_HEIGHT/10-SPRITE_SIZE; 
	public static final int XEDGEEXCLUSION = GameScene.SCREEN_WIDTH-275;
	public Texture texture;
	public GameScene myGame;
	public OrbitalBody body;
	
	boolean hovering;
	ToolTip toolTip;
	
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
		owner = GameScene.localPlayer;
		myCircle.setOrigin(Align.center);
		myCircle.setSize(50, 50);
		myCircle.setPosition(getX()+30, getY()+30);
		
		

		setY((GameScene.SCREEN_HEIGHT/5) + (int)(Utility.random.nextInt(YEDGEEXCLUSION-GameScene.SCREEN_HEIGHT/5) +1));
		setX(XEDGEEXCLUSION-150*orbitRadius);

		
		setHeight(SPRITE_SIZE);
		setWidth(SPRITE_SIZE);
		setBounds(0,0,SPRITE_SIZE, SPRITE_SIZE);
		
		addListener(new ClickListener(){
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			
		        switchToPlanetView();
		        return true;
		    }
		    
		    public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				hoverOn();
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				//System.out.println("Exit at " + x + ", " + y);
				hoverOff();
			}
			
		});
		
		
		
		
	}
	
	/**
	 * Run when mouse hovers on planet
	 */
	public void hoverOn() {	
		if(toolTip ==null){
		toolTip = new ToolTip(name, this);
		}
		this.getStage().addActor(toolTip);
		toolTip.addAction(sequence(Actions.alpha(0), Actions.delay(0.3f),Actions.fadeIn(0.4f, Interpolation.fade)));
		
		hovering = true;
	}

	/**
	 * Run when mouse hovers off of planet
	 */
	public void hoverOff() {
		hovering = false;
		toolTip.remove();
//		if(this.getStage()!=null){
//		this.getStage().mouseMoved(0, 1);
//		}
	}
	
	
	public void switchToPlanetView(){
		owner = sector.galaxy.thisgame.localPlayer;
		this.getStage().mouseMoved(20, 20);
		sector.galaxy.thisgame.switchToPlanetView(this);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}


	/**
	 * Finds distance between any two orbital bodies.
	 * @param body
	 */
	public int getDistance(OrbitalBody body) {
		int bodyPos = sector.getNumBodies() - orbitRadius;
		int otherBodyPos = body.getSector().getNumBodies() - body.getPos();
		int sectorDist = sector.getDistance(body.getSector());
		return bodyPos + otherBodyPos + sectorDist;
	}

	/**
	 * Returns name of body
	 */
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
		
		if(m_Fleet != null){
			myCircle.draw(batch, parentAlpha);
		}
		batch.draw(planets[type], getX(), getY(), SPRITE_SIZE, SPRITE_SIZE);
		
	}
	
	public void act(float time){
		super.act(time);
	}
	
	//I guess abstract?
	public abstract void mineLevelUp();
	public abstract Integer getMineLevel();
	//The Methods below relate to the shipyard implemented
	
	
}// end OrbitalBody