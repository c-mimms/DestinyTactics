package se300.destinytactics.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import se300.destinytactics.DestinyTactics;
import se300.destinytactics.GameScene;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Destiny Tactics";
	      config.width = 1024;
	      config.height = 640;
		new LwjglApplication(new DestinyTactics(), config);
	}
}