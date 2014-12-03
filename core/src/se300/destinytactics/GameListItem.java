package se300.destinytactics;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 
 * @author Chris
 * An item to populate the game list.
 *
 */
public class GameListItem extends Table {

	int minPlayers;
	int playerCount;
	int galaxyID;
	int galaxySeed;
	int createdBy;
	String createDate;
	String username;
	String status;
	int gameID;
	int alliances;
	CharSequence maxPlayers;
	int maxOrbitalBodies;
	int minOrbitalBodies;
	String galaxy;
	CharSequence sectors;
	String hostUserID;
	public int turnPlayerID;

	public GameListItem() {
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
		String allowsAlliances = alliances == 1 ? "Yes" : "No";
		add(new Label("" + username, DestinyTactics.skin2)).align(Align.left).expandX().space(75).center();
		add(new Label("" + allowsAlliances, DestinyTactics.skin2)).align(Align.center).expandX().space(75).center();
		add(new Label(playerCount + " / " + maxPlayers, DestinyTactics.skin2)).align(Align.center).expandX().space(75).center();
		add(new Label("" + status, DestinyTactics.skin2)).align(Align.right).expandX().space(75).center();
		//left();
	}
	
	public String toString(){
		return createDate;
	}

}
