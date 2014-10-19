package se300.destinytactics.mapgen;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import se300.destinytactics.orbitalbodies.Planet;
import se300.destinytactics.orbitalbodies.Station;
import se300.destinytactics.ui.Button;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:15 PM
 */
public class Sector extends Actor {

	public static Galaxy galaxy;
	private int controlState;
	private int numBodies;
	private String name;
	private OrbitalBody bodyList[];
	private int posX;
	private int posY;
	public OrbitalBody m_OrbitalBody;
	public Button m_Button;
	private Texture sprite1;
	private final double EDGE_EXCLUSION = 60;
	private final int SPRITE_SIZE = 50;
	
	public Sector() {

		super.setVisible(true);
		sprite1 =  new Texture(Gdx.files.internal("realorbitalbody/galaxySun.png"));
		controlState = 0;
		numBodies = (int)  (Math.random()* 15) + 1;
		bodyList = new OrbitalBody[numBodies];
		posX = (int) (EDGE_EXCLUSION + (Math.random() * (galaxy.getGalaxyWidth()- 2*EDGE_EXCLUSION)));
		posY = (int) (EDGE_EXCLUSION + (Math.random() * (galaxy.getGalaxyHeight()-2*EDGE_EXCLUSION)));

        setWidth(sprite1.getWidth());
        setHeight(sprite1.getHeight());
        setBounds(0, 0, getWidth(), getHeight());
        
		double stationChance = 0.2;
		for (int i = 0; i < numBodies; i++) {
			if (stationChance > Math.random()) {
				bodyList[i] = new Station(i, this);
				stationChance /= 10;
			} else {
				bodyList[i] = new Planet(i, this);
			}
		}
		name = Names.newName();
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	public String getName() {
		return name;
	}
	
	public void act(){
		System.out.println("ACtin!");
	}

	public Point getPos() {
		return new Point(posX, posY);
	}
	
	public int getXPos() {
		return posX;
	}

	public int getYPos() {
		return posY;
	}
	
	public int getNumBodies() {
		return numBodies;
	}
	
	public int getDistance(Sector sector){
		return (int) sector.getPos().distance(getPos());
	}
	
	public int getState() {
		return controlState;
	}

	public void draw(Batch batch, float parentAlpha) {

		batch.draw(sprite1, getXPos()-SPRITE_SIZE/2, getYPos()-SPRITE_SIZE/2,SPRITE_SIZE,SPRITE_SIZE);

	}
}// end Sector