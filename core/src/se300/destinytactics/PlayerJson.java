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
	@SuppressWarnings("rawtypes")
	public ArrayList fleets;

	PlayerJson(int userID, int isHost, String alliance) {
		this.userID = userID;
		this.alliance = alliance;
		this.isHost = isHost;
	}

	public PlayerJson() {
		// TODO Auto-generated constructor stub
	}

	public void update() {

		
		
	}
}

// userID� : 1, �alliance� : �Rebels�, �isHost� : 1