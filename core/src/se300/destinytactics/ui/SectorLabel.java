package se300.destinytactics.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Chris Shannon
 * Extends Label in the LibGDX framework.
 * Provides additional getter/setter methods for determining the sector label position.
 */
public class SectorLabel extends Label{
	
	private float orig_posX, orig_posY;
	
	/**
	 * SectorLabel contstuctor. Calls the parent Label constructor.
	 * @param text  Label text
	 * @param skin  Skin to use for the screen.
	 */
	public SectorLabel(CharSequence text, Skin skin) {
		super(text, skin);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *  
	 * @return orig_posX
	 */
	public float getOrig_posX() {
		return orig_posX;
	}
	
	/**
	 * 
	 * @param orig_posX
	 */
	public void setOrig_posX(float orig_posX) {
		this.orig_posX = orig_posX;
	}
	
	/**
	 * 
	 * @return orig_posY
	 */
	public float getOrig_posY() {
		return orig_posY;
	}
	
	/**
	 * 
	 * @param orig_posY
	 */
	public void setOrig_posY(float orig_posY) {
		this.orig_posY = orig_posY;
	}
}
