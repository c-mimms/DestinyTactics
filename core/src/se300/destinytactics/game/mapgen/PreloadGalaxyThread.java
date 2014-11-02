package se300.destinytactics.game.mapgen;

import se300.destinytactics.GameScene;

public class PreloadGalaxyThread extends Thread {

    public void run() {
		Galaxy m_Galaxy = new Galaxy(GameScene.GALAXY_WIDTH, GameScene.GALAXY_HEIGHT, GameScene.NUMBER_SECTORS, null);
    }
    
}