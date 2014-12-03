package se300.destinytactics;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 
 * @author Chris
 * Item to populate the list of galaxy types.
 */
public class GalaxyListItem extends Table {
	
	int galaxyID;
	private String galaxy;
	private int sectors;
	int minPlayers;
	int maxPlayers;
	int alliances;
	int minOrbitalBodies;
	int maxOrbitalBodies;
	
	public GalaxyListItem() {
		super();
	}
	
	public void update(){
		add(new Label(getGalaxy(), DestinyTactics.skin2)).align(Align.left).expandX().left();
	}

	public int getSectors() {
		return sectors;
	}

	public void setSectors(int sectors) {
		this.sectors = sectors;
	}

	public String getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(String galaxy) {
		this.galaxy = galaxy;
	}
}
