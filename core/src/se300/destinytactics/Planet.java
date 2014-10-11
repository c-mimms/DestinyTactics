package se300.destinytactics;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:11 PM
 */
public class Planet extends OrbitalBody implements canBuildFleets, canBuildDefense {

	private int miningEfficiency2;
	private int miningEfficieny1;
	private int recourse;
	private int recourse2;
	private Structure structure[];
	private String type;
	public Structure m_Structure;

	public Planet(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param sector
	 */
	public Planet(Sector sector){

	}

	public String getType(){
		return "";
	}

	public void incrementMining(){

	}

	public void getLevel(){

	}

	public void incrementLevel(){

	}

	public void getLevel(){

	}

	public void incrementLevel(){

	}
}//end Planet