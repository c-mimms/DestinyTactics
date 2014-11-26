package se300.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import se300.destinytactics.game.fleet.Battleship;

public class JUnitRich {

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
	
	@SuppressWarnings("deprecation")
	@Test
	public void getbuildtimetest() {
		Battleship buildtimetest = new Battleship();
		int result = buildtimetest.getBuildTime();
		if(result < 0){
			fail("build cannot be less than zero");
		}
		if(result > 1){
			fail("build time is taking too long");
			
		}
	
	}

}
