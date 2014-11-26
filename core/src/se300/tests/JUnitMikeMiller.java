package se300.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fighter;
import se300.destinytactics.game.orbitalbodies.Planet;

public class JUnitMikeMiller {

	@BeforeClass
	public static void setupBC() {
		System.out.println("Setting up before class.");
	}
	
	@AfterClass
	public static void breakAC() {
		System.out.println("Breaking down after class.");
	}
	
	@Before
	public void beforeMethod() {
		System.out.println("Entering method...");
	}
	
	@After
	public void afterMethod() {
		System.out.println("Exiting method...");
	}
	
	@Test
	public void testDecrementBuildTime() {
		System.out.println("Testing: decrement build time.");
		Fighter fighter = new Fighter();
		fighter.decrementBuildTime();
		assertEquals(fighter.stats.buildTime - 1, fighter.getBuildTime());		
	}
	
	@Test 
	public void testHangaring() {
		Fighter fighter = new Fighter();
		fighter.setHangared(true);
		assertTrue(fighter.getHangared());
	}
	
}
