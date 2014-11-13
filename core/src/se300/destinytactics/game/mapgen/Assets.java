package se300.destinytactics.game.mapgen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;


/**
* <h1>Asset Loader</h1>
* Class to preload all the assets required by the game.
* <p>
*
* @author  Chris Mimms
* @version 1.0
* @since   2014-11-12
*/
public class Assets {
	public static AssetManager manager = new AssetManager();

	/**
	 * Queue all items to be loaded
	 */
	public static void queueLoading() {

		//Create parameter to use mipmaps when loading images
		TextureLoader.TextureParameter mipmaps = new TextureLoader.TextureParameter();
		mipmaps.minFilter = TextureFilter.Linear;
		mipmaps.genMipMaps = true;

		// Load all sector sun images
		String imagePath = "images/orbitalbodies/suns";
		manager.load(imagePath + "/blueSun.png", Texture.class);
		manager.load(imagePath + "/neonSun.png", Texture.class);
		manager.load(imagePath + "/orangeSun.png", Texture.class);
		manager.load(imagePath + "/redSun.png", Texture.class);
		manager.load(imagePath + "/tealSun.png", Texture.class);
		manager.load(imagePath + "/yellowSun.png", Texture.class);

		// Import all planet textures
		String spriteLib = "realorbitalbody";
		String imagelib = "images/orbitalbodies/planets";
		manager.load(spriteLib + "/activatedgate.png", Texture.class,mipmaps);
		manager.load(spriteLib + "/deactivatedgate.png", Texture.class,mipmaps);
		manager.load(imagelib + "/life1.png", Texture.class, mipmaps);
		manager.load(imagelib + "/life1.png", Texture.class, mipmaps);
		manager.load(imagelib + "/red1.png", Texture.class, mipmaps);
		manager.load(imagelib + "/red2.png", Texture.class, mipmaps);
		manager.load(imagelib + "/redWater1.png", Texture.class, mipmaps);
		manager.load(imagelib + "/redWater2.png", Texture.class, mipmaps);
		manager.load(imagelib + "/water1.png", Texture.class, mipmaps);
		manager.load(imagelib + "/water2.png", Texture.class, mipmaps);
		manager.load(spriteLib + "/station1.png", Texture.class, mipmaps);
		manager.load(spriteLib + "/station2.png", Texture.class, mipmaps);

	}

	/**
	 * Returns array of planet images
	 * 
	 * @return All loaded planet images.
	 */
	public static Texture[] getPlanetTypes() {
		String spriteLib = "realorbitalbody";
		String imagelib = "images/orbitalbodies/planets";
		Texture[] ret = {
				manager.get(spriteLib + "/activatedgate.png"),
				manager.get(spriteLib + "/deactivatedgate.png"),
				manager.get(imagelib + "/life1.png"),
				manager.get(imagelib + "/life1.png"),
				manager.get(imagelib + "/red1.png"),
				manager.get(imagelib + "/red2.png"),
				manager.get(imagelib + "/redWater1.png"),
				manager.get(imagelib + "/redWater2.png"),
				manager.get(imagelib + "/water1.png"),
				manager.get(imagelib + "/water2.png"),
				manager.get(spriteLib + "/station1.png"),
				manager.get(spriteLib + "/station2.png") };
		return ret;
	}

	/**
	 * Return sun texture array
	 * 
	 * @return All loaded sun images.
	 */
	public static Texture[] getSunTypes() {
		String imagePath = "images/orbitalbodies/suns";
		Texture[] ret = {
				manager.get(imagePath + "/blueSun.png"),
				manager.get(imagePath + "/neonSun.png"),
				manager.get(imagePath + "/orangeSun.png"),
				manager.get(imagePath + "/redSun.png"),
				manager.get(imagePath + "/tealSun.png"),
				manager.get(imagePath + "/yellowSun.png") };
		return ret;
	}

	/**
	 * Loads assets, returns true when assets are loaded
	 * 
	 * @return True if all assets have finished loading.
	 */
	
	public static boolean update() {
		return manager.update();
	}
	
	/**
	 * Blocks program execution until all assets are loaded.
	 */
	public static void finish() {
		 manager.finishLoading();
	}
}