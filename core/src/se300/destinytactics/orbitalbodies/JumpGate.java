package se300.destinytactics.orbitalbodies;

import se300.destinytactics.logic.Utility;
import se300.destinytactics.mapgen.OrbitalBody;
import se300.destinytactics.mapgen.Sector;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:06 PM
 */
public class JumpGate extends OrbitalBody {

	public JumpGate(int radius, Sector sector){
		super(radius,sector);
		type = Utility.random.nextInt(2);
		
		System.out.println(type);
		this.setY(Utility.random.nextInt(YEDGEEXCLUSION));
		this.setX(XEDGEEXCLUSION-150*orbitRadius);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	/**
	 * 1
	 * 
	 * @param gate
	 */
	public int getDistance(JumpGate gate){
		return 1;
	}

	@Override
	public void getMiningEfficiency() {
		// TODO Auto-generated method stub
	}


}//end JumpGate