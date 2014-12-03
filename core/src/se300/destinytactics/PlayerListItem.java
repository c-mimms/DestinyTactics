package se300.destinytactics;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Item to populate the list of players.
 * @author Chris
 *
 */
public class PlayerListItem extends Table {

	int playerID;
	int userID;
	int rank;
	int gameID;
	String username;
	int turnOrder;
	String alliance;
	String lastTurnDate;
	int isHost;
	int statusID;
	int totalMetal;
	int totalFuel;


	public PlayerListItem() {
		// TODO Auto-generated constructor stub
//		this.setX(DestinyTactics.SCREEN_WIDTH/2);
//		this.setY(Utility.random.nextInt(DestinyTactics.SCREEN_HEIGHT));
		
		/*
		 * Whole class needs to be created. Each item is one item in the game lobby list of games. A row in 
		 * the scrolling table listing the game parameters. When clicked the list item should create a window on
		 * the right half of screen with game details.
		 */
		super();
		
	}
	
	public void update(){
		add(new Label(username, DestinyTactics.skin2)).align(Align.left).expandX().space(85).left();
		add(new Label(alliance, DestinyTactics.skin2)).align(Align.center).expandX().space(85).left();
		add(new Label("" + rank, DestinyTactics.skin2)).align(Align.center).expandX().space(85).right();
	}
	
	public String toString(){
		return lastTurnDate;
	}

}
