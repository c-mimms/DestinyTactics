package se300.destinytactics;

import java.util.ArrayList;

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
	public ArrayList fleets;

	PlayerJson(int user, int host, String alliance) {
		userID = user;
		this.alliance = alliance;
		isHost = host;
	}

	public PlayerJson() {
		// TODO Auto-generated constructor stub
	}
}

// userID” : 1, “alliance” : “Rebels”, “isHost” : 1