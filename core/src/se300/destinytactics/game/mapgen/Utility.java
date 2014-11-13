package se300.destinytactics.game.mapgen;

import java.util.Random;

/**
* <h1>Utility</h1>
* Utility methods used by all objects, including the random generator.
* <p>
*
* @author  Chris Mimms
* @version 1.0
* @since   2014-11-12
*/
public class Utility {
	
	public static Random random = new Random();
	public static long seed = random.nextLong();
	
	//Set random seed to the current seed
	static{
		random.setSeed(seed);
	}

	/**
	 * Set the random seed.
	 * @param seed
	 */
	public void setSeed(long seed){
		Utility.seed = seed;
		random.setSeed(seed);
	}
	
	/**
	 * Get random seed.
	 * @return Seed used by random generator.
	 */
	public long getSeed(){
		return seed;
	}
	 

}
