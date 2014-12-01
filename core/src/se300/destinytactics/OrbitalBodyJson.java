package se300.destinytactics;

import java.util.ArrayList;

import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.orbitalbodies.OrbitalBody;

public class OrbitalBodyJson {

	public int orbit;
	public int sectorID;
	public int orbitalBodyID;
	public int fuelLevel;
	public int metalLevel;
	public int shipyardLevel;
	@SuppressWarnings("rawtypes")
	public ArrayList buildqueues;
	public int controlledBy;

	public OrbitalBodyJson(int orbit, int controlledBy) {
		this.orbit = orbit;
		this.controlledBy = controlledBy;
	}

	public OrbitalBodyJson() {
		// TODO Auto-generated constructor stub
	}

	public void update(int sectorIndex) {

		int orbitalBodyIndex = orbit - 1;
		System.out.println("(update) sector: " + sectorIndex + " orbitalBody: " + orbitalBodyIndex);
		Sector sector = GameScene.m_Galaxy.sectors[sectorIndex];
		OrbitalBody orbitalBody = sector.bodyList[orbitalBodyIndex];
		orbitalBody.setShipyardLevel(shipyardLevel);
		
	}
}
/*
 * {�orbit� : 1, �controlledBy� : 1} ] }
 */