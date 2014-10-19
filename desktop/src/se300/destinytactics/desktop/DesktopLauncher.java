package se300.destinytactics.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import se300.destinytactics.logic.MyGame;

import se300.destinytactics.logic.DestinyTactics;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Destiny Tactics";
	      config.width = 1200;
	      config.height = 800;
		new LwjglApplication(new MyGame(), config);
	}
}
