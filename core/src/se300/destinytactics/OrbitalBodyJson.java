package se300.destinytactics;

import java.util.ArrayList;

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
}
/*
 * {“orbit” : 1, “controlledBy” : 1} ] }
 */