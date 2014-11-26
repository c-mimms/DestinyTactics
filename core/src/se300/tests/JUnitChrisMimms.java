package se300.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.Player;
import se300.destinytactics.game.fleet.Fighter;
import se300.destinytactics.game.mapgen.Galaxy;
import se300.destinytactics.game.mapgen.Names;
import se300.destinytactics.game.mapgen.Sector;
import se300.destinytactics.game.mapgen.Utility;
import se300.destinytactics.game.orbitalbodies.Planet;

public class JUnitChrisMimms {

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
	public void testNames() {
		String name = Names.newName();
		//Ensure name is returned and not null
		assertTrue(name!=null);
	}
	
	@Test
	public void testSetRandomSeed() {
		long seed = 9999999;
		Utility.setSeed(seed);
		assertEquals(seed, Utility.getSeed()); //Ensure seed is set correctly.

	}
	
}
