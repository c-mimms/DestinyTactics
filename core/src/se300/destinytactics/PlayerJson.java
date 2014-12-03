package se300.destinytactics;

import java.util.ArrayList;

/**
 * Class to load a player from a JSON string.
 * @author Chris
 *
 */
public class PlayerJson {

	public int gameID;
	public int userID;
	public String alliance;
	public int isHost;
	public long totalMetal;
	public int statusID;
	public long totalFuel;
	public int playerID;
	public String lastTurnDate;
	public int turnOrder;
	@SuppressWarnings("rawtypes")
	public ArrayList fleets;

	PlayerJson(int gameID, int userID, int isHost, String alliance) {
		this.gameID = gameID;
		this.userID = userID;
		this.alliance = alliance;
		this.isHost = isHost;
	}

	public PlayerJson() {
		this.gameID = 0;
		this.userID = 0;
		this.alliance = "";
		this.isHost = 0;
	}

	public void update() {

		
		
	}
}

// userID� : 1, �alliance� : �Rebels�, �isHost� : 1