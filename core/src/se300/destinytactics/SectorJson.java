package se300.destinytactics;

public class SectorJson {

	public int galaxyPos;
	public int controlledBy;
	public OrbitalBodyJson[] orbitalbodies;

	public SectorJson(int pos, int cont) {
		galaxyPos = pos;
		controlledBy = cont;
		orbitalbodies = new OrbitalBodyJson[1];
		orbitalbodies[0] = new OrbitalBodyJson(1,1);
	}

}

/*
 * [ { “galaxyPos” : 1, “controlledBy” : 1, “orbitalbodies” [ {“orbit” : 1,
 * “controlledBy” : 1} ] } ]
 */