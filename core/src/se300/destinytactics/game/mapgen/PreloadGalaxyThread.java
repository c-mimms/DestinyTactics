package se300.destinytactics.game.mapgen;

import se300.destinytactics.GameScene;

/**
* <h1>Galaxy Loader</h1>
* Class to generate a galaxy in a seperate thread, before the game is started. 
* <p>
* This class is currently unused.
*
* @author  Chris Mimms
* @version 0.1
* @since   2014-11-12
* @deprecated
*/
public class PreloadGalaxyThread extends Thread {

    public void run() {
		@SuppressWarnings("unused")
		Galaxy m_Galaxy = new Galaxy(GameScene.GALAXY_WIDTH, GameScene.GALAXY_HEIGHT, GameScene.NUMBER_SECTORS, null);
    }
    
}