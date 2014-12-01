package se300.destinytactics;

import java.util.ArrayList;

import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

public class GameJson {

	public int gameID;
	public long galaxySeed;
	public int roundsCompleted;
	public int createdBy;
	public int turnPlayerID;
	public int galaxyID;
	public String createDate;
	public String status;
	public ArrayList<PlayerJson> players;
	public ArrayList<SectorJson> sectors;
	public String[] alliances = { "test" };
	public PlayerJson turnPlayer;

	public GameJson(long galaxySeed) {
		this.galaxySeed = galaxySeed;
		Galaxy gal = GameScene.m_Galaxy;
		roundsCompleted = 0;
		galaxyID = 1;
		createdBy = MultiplayerScreen.userID;

		players = new ArrayList<PlayerJson>();
		sectors = new ArrayList<SectorJson>(gal.sectors.length);
		for(int i = 0; i< gal.sectors.length; i++){
			Sector sect = gal.sectors[i];
			SectorJson tmpSector = new SectorJson();
			tmpSector.controlledBy = sect.controlState;
			tmpSector.galaxyPos = i + 1;
			
			System.out.println("(new) orbitalbodies in sector: " + sect.bodyList.length);
			
			for(OrbitalBody bod : sect.bodyList){
				OrbitalBodyJson tmpOrbitalBody = new OrbitalBodyJson();
				tmpOrbitalBody.orbit = bod.getPos() + 1;
				//ADD MORE ORBITAL BODY STUFF HERE
				tmpSector.orbitalBodies.add(tmpOrbitalBody);
			}
			sectors.add(tmpSector);
		}
		//ADD LOOP TO ADD ALL PLAYERS
		PlayerJson tmpPlayer = new PlayerJson();
		tmpPlayer.isHost = 1;
		tmpPlayer.userID = MultiplayerScreen.userID;
		players.add(tmpPlayer);
		
		this.turnPlayer = tmpPlayer;
	}

	public GameJson(int gameID, long galaxySeed) {
		this(galaxySeed);
		this.gameID = gameID;
	}
	
	public GameJson() {
		
	}

	public void update(String status, PlayerJson turnPlayer, int roundsCompleted) {
		this.status = status;
		this.roundsCompleted = roundsCompleted;
		this.turnPlayer = turnPlayer;
		this.turnPlayerID = turnPlayer.playerID;
		
		for (PlayerJson player : players) {
			player.update();
		}
		for (SectorJson sector : sectors) {
			sector.update();
		}
	}
	
	public void update(String status) {
		update(this.status, getPlayerByUserID(createdBy), this.roundsCompleted);
	}
	
	public void update(int userID) {
		update(this.status, getPlayerByUserID(userID), this.roundsCompleted);
	}
	
	public void update(){
		update(this.status, getPlayerByUserID(createdBy), this.roundsCompleted);
	}
	
	private PlayerJson getPlayerByUserID(int userID) {
		PlayerJson selectedPlayer = new PlayerJson();
		for (PlayerJson player : players) {
			if (player.userID == userID) {
				selectedPlayer = player;
			}
		} 
		return selectedPlayer;
	}
	
	public void setNextPlayer() {
		PlayerJson nextPlayer = new PlayerJson();
		PlayerJson firstPlayer = new PlayerJson();
		
		for (PlayerJson player : players) {
			if (turnPlayer.turnOrder < player.turnOrder) {
				nextPlayer = player;
			}
			if (player.turnOrder == 1) {
				firstPlayer = player;
			}
		}
		
		if (nextPlayer.gameID == 0) {
			nextPlayer = firstPlayer;
		}
		
	}
}



// {"message":"Game loaded.","gameObj":{"galaxySeed":"","players":[],"createdBy":"","galaxyID":"","createDate":""
// + "","gameID":"","status":"","sectors":[],"roundsCompleted":""}}
/*
 * { 'galaxyID' : 1, 'galaxySeed' : '35kn573lknd6kl3n67' 'alliances' :
 * ['Rebels', 'Empire'], 'players' : [ {'userID' : 1, 'alliance' : 'Rebels',
 * 'isHost' : 1} ], 'sectors' [ { 'galaxyPos' : 1, 'controlledBy' : 1,
 * 'orbitalbodies' [ {'orbit' : 1, 'controlledBy' : 1} ] } ] }
 */