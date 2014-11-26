package se300.destinytactics;

import java.util.ArrayList;

import se300.destinytactics.game.mapgen.Sector;

public class SectorJson {

	public int galaxyPos;
	public int controlledBy;
	public int gameID;
	public int sectorID;
	public ArrayList<OrbitalBodyJson> orbitalBodies;

	public SectorJson(int pos, int cont) {
		galaxyPos = pos;
		controlledBy = cont;
		orbitalBodies = new ArrayList<OrbitalBodyJson>();
	}

	public SectorJson() {
		// TODO Auto-generated constructor stub
		orbitalBodies = new ArrayList<OrbitalBodyJson>();
	}

	public void update() {
		
		int sectorIndex = galaxyPos - 1;
		Sector me = GameScene.m_Galaxy.sectors[sectorIndex];
		
		me.controlState = controlledBy;
		
		for( OrbitalBodyJson body : orbitalBodies){
			body.update(sectorIndex);
		}
		
		
	}

}

/*
 * [ { �galaxyPos� : 1, �controlledBy� : 1, �orbitalbodies� [ {�orbit� : 1,
 * �controlledBy� : 1} ] } ]
 */