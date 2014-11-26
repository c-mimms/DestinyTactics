package se300.destinytactics;

import java.util.ArrayList;

import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

public class GameJson {

	public long galaxySeed;
	public ArrayList<PlayerJson> players;
	public int createdBy;
	public int galaxyID;
	public String createDate;
	public int gameID;
	public String status;
	public ArrayList<SectorJson> sectors;
	public String[] alliances = { "test" };
	public int roundsCompleted;

	public GameJson(long seed) {
		galaxySeed = seed;
		Galaxy gal = GameScene.m_Galaxy;
		
		galaxyID = 1;
		createdBy = MultiplayerScreen.userID;
		players = new ArrayList<PlayerJson>();
		sectors = new ArrayList<SectorJson>(gal.sectors.length);
		for(int i = 0; i< gal.sectors.length; i++){
			Sector sect = gal.sectors[i];
			SectorJson tmp = new SectorJson();
			tmp.controlledBy = sect.controlState;
			tmp.galaxyPos = i + 1;
			for(OrbitalBody bod : sect.bodyList){
				OrbitalBodyJson tmp2 = new OrbitalBodyJson();
				tmp2.orbit = bod.getPos() + 1;
				//ADD MORE ORBITAL BODY STUFF HERE
				tmp.orbitalBodies.add(tmp2);
			}
			sectors.add(tmp);
		}
		//ADD LOOP TO ADD ALL PLAYERS
		PlayerJson tmp3 = new PlayerJson();
		tmp3.isHost = 1;
		tmp3.userID = MultiplayerScreen.userID;
		players.add(tmp3);
	}

	public GameJson() {
		// TODO Auto-generated constructor stub
	}

	public void update() {

		for (PlayerJson player : players) {
			player.update();
		}
		for (SectorJson sector : sectors) {
			sector.update();
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