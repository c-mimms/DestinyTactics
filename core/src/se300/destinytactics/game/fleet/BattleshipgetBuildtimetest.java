package se300.destinytactics.game.fleet;

import static org.junit.Assert.*;

import org.junit.Test;

public class BattleshipgetBuildtimetest {

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


