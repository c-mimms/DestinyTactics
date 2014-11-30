package se300.destinytactics;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GalaxyListItem extends Table {
	
	int galaxyID;
	String galaxy;
	int sectors;
	int minPlayers;
	int maxPlayers;
	int alliances;
	int minOrbitalBodies;
	int maxOrbitalBodies;
	
	public GalaxyListItem() {
		super();
	}
	
	public void update(){
		add(new Label(galaxy, DestinyTactics.skin2)).align(Align.left).expandX().left();
	}
}
