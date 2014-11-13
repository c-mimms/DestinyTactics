package se300.destinytactics.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * 
 */
public class SectorLabel extends Label{
	
	private float orig_posX, orig_posY;
	
	public SectorLabel(CharSequence text, Skin skin) {
		super(text, skin);
		// TODO Auto-generated constructor stub
	}

	public float getOrig_posX() {
		return orig_posX;
	}

	public void setOrig_posX(float orig_posX) {
		this.orig_posX = orig_posX;
	}

	public float getOrig_posY() {
		return orig_posY;
	}

	public void setOrig_posY(float orig_posY) {
		this.orig_posY = orig_posY;
	}
}
