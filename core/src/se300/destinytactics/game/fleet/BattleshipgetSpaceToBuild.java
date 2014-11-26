package se300.destinytactics.game.fleet;

import static org.junit.Assert.*;

import org.junit.Test;

public class BattleshipgetSpaceToBuild {

	@Test
	public void testSpaceToBuild() {
		Battleship spaceAvailibleTest = new Battleship();
		int result = spaceAvailibleTest.getSpaceToBuild();
		if(result < 0){
			fail("build cannot be less than zero");
		}
		else if(result > 1000){
			fail("build time is taking too long");
			
		}
	
	}

}
