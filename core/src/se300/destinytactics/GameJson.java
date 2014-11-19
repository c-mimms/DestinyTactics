package se300.destinytactics;

public class GameJson {

	public long galaxySeed;
	public PlayerJson[] players;
	public int createdBy;
	public int galaxyID;
	public String createDate;
	public int gameID;
	public String status;
	public SectorJson[] sectors;
	public String[] alliances = {"Rebels", "Empire"};
	public int roundsCompleted;

	public GameJson(long seed) {
		galaxySeed = seed;
		galaxyID = 1;
		createdBy = 1;
		players = new PlayerJson[1];
		players[0] = new PlayerJson(1, 1, "Rebels");
		sectors = new SectorJson[1];
		sectors[0] = new SectorJson(1,1);

	}
	public GameJson() {
		// TODO Auto-generated constructor stub
	}

}

// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"","createDate":""
// + "","gameID":"","status":"","sectors":[],"roundsCompleted":""}}
/*
 * { “galaxyID” : 1, “galaxySeed” : “35kn573lknd6kl3n67” “alliances” :
 * [“Rebels”, “Empire”], “players” : [ {“userID” : 1, “alliance” : “Rebels”,
 * “isHost” : 1} ], “sectors” [ { “galaxyPos” : 1, “controlledBy” : 1,
 * “orbitalbodies” [ {“orbit” : 1, “controlledBy” : 1} ] } ] }
 */