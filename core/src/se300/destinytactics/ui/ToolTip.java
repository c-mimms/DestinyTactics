package se300.destinytactics.ui;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sun.javafx.scene.paint.GradientUtils.Point;

public class ToolTip extends ClickListener  {

	TextField toolTip;
	Skin skin = new Skin(Gdx.files.internal("data/Holo-dark-hdpi.json"), new TextureAtlas("data/Holo-dark-hdpi.atlas"));

	int controlState;
	String name;
	java.awt.Point position;
	int numBodies;
	
	public ToolTip() {
		
	}
	
	public ToolTip(Sector sector) {
		name = sector.getName();
		numBodies = sector.getNumBodies();
		controlState = sector.getState();
		position = sector.getPos();
		
		toolTip = new TextField(name, skin); 
	}
	
	
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		hoverOn();
	}
	
	public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		hoverOff();
	}
	
	
	public void hoverOn() {
		System.out.println(name);
		System.out.println(numBodies);
		System.out.println(controlState);
		System.out.println(position);
	}
	
	public void hoverOff() {
		
	}
	
	
}
