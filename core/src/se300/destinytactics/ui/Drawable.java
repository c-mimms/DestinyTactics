package se300.destinytactics.ui;

import se300.destinytactics.mapgen.Names;

import java.awt.image.BufferedImage;

import com.badlogic.gdx.Graphics;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:48:58 PM
 */
public class Drawable {

	public Graphics g;
	public BufferedImage image;
	public Names namer;
	public boolean visibleByPlayer;
	public Names m_Names;

	public Drawable(){

	}

	public void finalize() throws Throwable {

	}
	public Graphics drawImage(){
		return null;
	}
}//end Drawable