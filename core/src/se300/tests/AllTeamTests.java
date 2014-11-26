package se300.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Battleship;
import se300.destinytactics.game.fleet.Fighter;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.game.orbitalbodies.Planet;

public class AllTeamTests {

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
	
	// Chris Shannon's tests
	@Test
	public void getPlayerScore() {
		Player testPlayer = new Player();
		assertEquals(0, testPlayer.getScore());
	}
	
	@Test
	public void addPlayerResource() {
		Player testPlayer = new Player();
		testPlayer.addResource(25);
		assertEquals(525, testPlayer.getResource());
	}
	
	// Mike Miller's tests
	@SuppressWarnings("static-access")
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
	
	// Rich Simonson's tests
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
	
	// Chris Mimms' tests
}
