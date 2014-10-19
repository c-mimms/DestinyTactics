package se300.destinytactics.mapgen;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se300.destinytactics.logic.MyGame;
import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.Drawable;
import se300.destinytactics.units.Fleet;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:10 PM
 */
public abstract class OrbitalBody extends Actor {

	private int controlState;
	private Fleet fleet;
	private int miningEfficiency = 0;
	private String name;
	protected int orbitRadius;
	protected Sector sector;
	public Fleet m_Fleet;
	public Button m_Button;
	public Random rand;
	
	
	public int spriteIndex;
	public static final int YEDGEEXCLUSION = MyGame.SCREEN_HEIGHT-20;
	public static final int XEDGEEXCLUSION = MyGame.SCREEN_WIDTH-230;
	public static final int SPRITE_SIZE = 50;
	

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

	public OrbitalBody(int radius, Sector sect){
		
		
		orbitRadius = radius;
		setY(rand.nextInt(YEDGEEXCLUSION));
		setX(rand.nextInt(XEDGEEXCLUSION));
		name = Names.newName();
		sector = sect;
		controlState = 0;
		
		setBounds(0,0,SPRITE_SIZE, SPRITE_SIZE);
		
		addListener(new ClickListener(){
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println(name);
		        return true;
		    }
		});
		
		
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

	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.draw(hotBod[spriteIndex], getX(), getY(), SPRITE_SIZE, SPRITE_SIZE);
	}
}// end OrbitalBody