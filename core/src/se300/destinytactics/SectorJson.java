package se300.destinytactics;

public class SectorJson {

	public int galaxyPos;
	public int controlledBy;
	public int gameID;
	public int sectorID;
	public OrbitalBodyJson[] orbitalBodies;

	public SectorJson(int pos, int cont) {
		galaxyPos = pos;
		controlledBy = cont;
		orbitalBodies = new OrbitalBodyJson[1];
		orbitalBodies[0] = new OrbitalBodyJson(1, 1);
	}

	public SectorJson() {
		// TODO Auto-generated constructor stub
	}

}

/*
 * [ { “galaxyPos” : 1, “controlledBy” : 1, “orbitalbodies” [ {“orbit” : 1,
 * “controlledBy” : 1} ] } ]
 */