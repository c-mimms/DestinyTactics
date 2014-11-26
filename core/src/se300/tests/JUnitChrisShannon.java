package se300.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Fighter;
import se300.destinytactics.game.orbitalbodies.Planet;

public class JUnitChrisShannon {

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
	
}
