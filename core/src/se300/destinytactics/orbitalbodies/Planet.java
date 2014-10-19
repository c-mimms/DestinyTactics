package se300.destinytactics.orbitalbodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import se300.destinytactics.mapgen.OrbitalBody;
import se300.destinytactics.mapgen.Sector;
import se300.destinytactics.orbitalbodies.interfaces.canBuildDefense;
import se300.destinytactics.orbitalbodies.interfaces.canBuildFleets;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:11 PM
 */
public class Planet extends OrbitalBody implements canBuildFleets, canBuildDefense {

	private int miningEfficiency2;
	private int miningEfficiency1;
	private int resource;
	private int resource2;
	private Structure structure[];
	private String type;
	public Structure m_Structure;
	public Texture texture;

	public Planet(int radius, Sector sector){
		super(radius,sector);
		miningEfficiency1 = 0;
		miningEfficiency2 = 0;
		resource = (int) Math.random()*1000;
		resource2 = (int) Math.random()*1000;
		
		texture = hotBod[5];
		this.setBounds(0,0,texture.getWidth(), texture.getHeight());
		this.addListener(new InputListener(){
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("down");
		        return true;
		    }

		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("up");
		    }
		});
		
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	public String getType(){
		return type;
	}

	public void incrementMining(){
		miningEfficiency1 ++;
	}

	public void getLevel(){
		
	}

	public void incrementLevel(){
		
	}

	@Override
	public void getMiningEfficiency() {
		// TODO Auto-generated method stub
		
	}

}//end Planet