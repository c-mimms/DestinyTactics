package se300.destinytactics.ui;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import se300.destinytactics.DestinyTactics;

import com.badlogic.gdx.math.Interpolation;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;



public class ToolTip extends Window {

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
	
	public ToolTip(String name, OrbitalBody body) {
		super(name, skin);
		body.sector.galaxy.thisgame.sectorStage.addActor(this); //LOLOL
		String sector = body.sector.getName();
		controlState = body.getState();
		String owner = body.owner.getName();
		int mine = body.getMineLevel();
		float x = body.getX();
		float y = body.getY();
		
		setPosition(x+body.SPRITE_SIZE+10, y-this.getHeight()/2);
		add("Sector: " + sector);
		add("Control State: " + controlState);
		add("Mine Level: " + mine);
		super.addAction(sequence(Actions.alpha(0), Actions.delay(0.3f),Actions.fadeIn(0.4f, Interpolation.fade)));
		
	}
	
}
