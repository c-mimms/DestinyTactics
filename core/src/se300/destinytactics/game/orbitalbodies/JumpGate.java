package se300.destinytactics.game.orbitalbodies;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;


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
		this.setY((GameScene.SCREEN_HEIGHT/5) + (int)(Utility.random.nextInt(YEDGEEXCLUSION-GameScene.SCREEN_HEIGHT/5) +1));
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

	@Override
	public void mineLevelUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getMineLevel() {
		// TODO Auto-generated method stub
		return null;
	}


}//end JumpGate