package se300.destinytactics.ui;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import se300.destinytactics.DestinyTactics;

import com.badlogic.gdx.math.Interpolation;
import se300.destinytactics.game.mapgen.Sector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;



public class ToolTip extends Window {

	Dialog toolTip;
	static Skin skin = new Skin(Gdx.files.internal("data/Holo-dark-hdpi.json"), new TextureAtlas("data/Holo-dark-hdpi.atlas"));

	int controlState;
	String name;
	java.awt.Point position;
	int numBodies;


	public ToolTip(String name , Sector sector) {
		super(name, skin);
		sector.galaxy.thisgame.galaxyStage.addActor(this);
		numBodies = sector.getNumBodies();
		controlState = sector.getState();
		position = sector.getPos();
		 
		setPosition(position.x+Sector.SPRITE_SIZE+20, position.y-this.getHeight()/2);
		if(position.x > DestinyTactics.SCREEN_WIDTH-200)
			setPosition(position.x-this.getWidth()-20, position.y-this.getHeight()/2);
		add("Orbital Bodies: " + numBodies);
		add("Control State: " + controlState);
		add("Coordinates: " + position.x + "," + position.y);
		
		addAction(sequence(Actions.alpha(0), Actions.delay(0.3f),Actions.fadeIn(0.4f, Interpolation.fade)));
		
	}
	
	
}
