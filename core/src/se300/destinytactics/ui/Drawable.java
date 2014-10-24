package se300.destinytactics.ui;

import se300.destinytactics.mapgen.Names;

import java.awt.image.BufferedImage;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:58 PM
 */
public abstract class Drawable {

	public Graphics g;
	public BufferedImage image;
	public Names namer;
	public boolean visibleByPlayer;
	public Names m_Names;
	public Texture sprite;

	public Drawable(){

	}

	public void finalize() throws Throwable {

	}
	public abstract void drawImage(SpriteBatch batch, float zoomLevel);
}//end Drawable