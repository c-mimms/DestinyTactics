package se300.destinytactics.mapgen;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se300.destinytactics.orbitalbodies.Planet;
import se300.destinytactics.orbitalbodies.Station;
import se300.destinytactics.ui.Button;
import se300.destinytactics.ui.Drawable;

/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:15 PM
 */
public class Sector extends Drawable {
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

	
	public Sector() {

		sprite =  new Texture("star.png");
		sprite1 =  new Texture("realorbitalbody/sun1.png");
		controlState = 0;
		numBodies = (int)  (Math.random()* 15) + 1;
		bodyList = new OrbitalBody[numBodies];
		posX = (int) (Math.random() * galaxy.getGalaxyWidth());
		posY = (int) (Math.random() * galaxy.getGalaxyHeight());

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

	public void click() {
		
<<<<<<< HEAD
=======
		sectorStage

>>>>>>> origin/GalaxySystemViewDev
	}

	public String getName() {
		return name;
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

<<<<<<< HEAD
	@Override
	public void draw(SpriteBatch batch, float zoomLevel) {
		if(zoomLevel <= 0.201){
			batch.draw(sprite1,getXPos()+100*zoomLevel,getYPos()-100*zoomLevel,400*zoomLevel,800*zoomLevel,0,0,200,400,false,false);
			for (int i = 0; i < numBodies; i++) {
				bodyList[i].drawImage(batch, zoomLevel);
			}
		}
		else{
			batch.draw(sprite,getXPos()-25*zoomLevel,getYPos()-25*zoomLevel,100*zoomLevel,100*zoomLevel,0,0,50,50,false,false);
		}
=======
	public void draw(SpriteBatch batch, float parentAlpha) {
		
			batch.draw(sprite,getXPos(),getYPos(),100,100,0,0,50,50,false,false);
		
>>>>>>> origin/GalaxySystemViewDev
	}
}// end Sector