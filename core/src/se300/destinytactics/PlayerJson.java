package se300.destinytactics;

public class PlayerJson {
	
	public int userID;
	public String alliance;
	public int isHost;
	
	PlayerJson(int user, int host, String alliance){
		userID = user;
		this.alliance = alliance;
		isHost = host;
	}

}



//userID” : 1, “alliance” : “Rebels”, “isHost” : 1