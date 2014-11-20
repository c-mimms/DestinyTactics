package se300.destinytactics;

import java.util.ArrayList;

import se300.destinytactics.game.fleet.Ship;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

public class OrbitalBodyJson {

	public int orbit;
	public int sectorID;
	public int orbitalBodyID;
	public int fuelLevel;
	public int metalLevel;
	public int shipyardLevel;
	public ArrayList buildqueues;
	public int controlledBy;

	public OrbitalBodyJson(int i, int j) {
		orbit = i;
		controlledBy = j;
	}

	public OrbitalBodyJson() {
		// TODO Auto-generated constructor stub
	}

	public void update(int sector) {

		
		OrbitalBody me = GameScene.m_Galaxy.sectors[sector].bodyList[orbit];
		me.setShipyardLevel(shipyardLevel);
		
	}
}
/*
 * {“orbit” : 1, “controlledBy” : 1} ] }
 */