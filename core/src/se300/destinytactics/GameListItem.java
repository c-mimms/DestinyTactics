package se300.destinytactics;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameListItem extends Table {

	int minPlayers;
	int playerCount;
	int galaxyID;
	int createdBy;
	String createDate;
	int gameID;
	int alliances;
	int maxPlayers;
	int maxOrbitalBodies;
	int minOrbitalBodies;
	String galaxy;
	int sectors;
	String hostUserID;

	public GameListItem() {
		// TODO Auto-generated constructor stub
//		this.setX(DestinyTactics.SCREEN_WIDTH/2);
//		this.setY(Utility.random.nextInt(DestinyTactics.SCREEN_HEIGHT));
		
		/*
		 * Whole class needs to be created. Each item is one item in the game lobby list of games. A row in 
		 * the scrolling table listing the game paramters. When clicked the list item should create a window on
		 * the right half of screen with game details.
		 */
		super();
		
	}
	
	public void update(){
		add(new Label("" + gameID, DestinyTactics.skin2)).space(10);
		add(new Label(playerCount + " / " + maxPlayers, DestinyTactics.skin2));
		left();
	}
	
	public String toString(){
		return createDate;
	}

}
