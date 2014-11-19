package se300.destinytactics;

import se300.destinytactics.game.mapgen.Utility;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameListItem extends HorizontalGroup {

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
		this.setX(DestinyTactics.SCREEN_WIDTH/2);
		this.setY(Utility.random.nextInt(DestinyTactics.SCREEN_HEIGHT));
		
		/*
		 * Whole class needs to be created. Each item is one item in the game lobby list of games. A row in 
		 * the scrolling table listing the game paramters. When clicked the list item should create a window on
		 * the right half of screen with game details.
		 */

		
	}
	
	public void update(){
		this.addActor(new Label("Players " + minPlayers, DestinyTactics.skin2));
		this.addActor(new Label("Create Date " + createDate, DestinyTactics.skin2));
		this.setX(DestinyTactics.SCREEN_WIDTH/2 - this.getWidth());
		
	}
	
	public String toString(){
		return createDate;
	}

}
