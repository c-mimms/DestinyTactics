package se300.destinytactics.game.mapgen;

import java.io.File;


/**
* <h1>Names</h1>
* Class to assign names to any object that needs a name.
* <p>
*
* @author  Chris Mimms
* @version 1.0
* @since   2014-11-12
*/
public class Names {

	public static String[] names = {"Randomeous Nameous", "Steveous Jobeous", "Totz Notz Randomz", "Cornify", "Lil' Spoon", "Sauce Boas",
			"Planet 1", "Breaking Bad", "Not A Name", "Name", "Epicus Maximus", "Blue Planet", "Greystrom Meyers", 
			"The Pwnzershriek", "East Germany", "The Snack that Smiles Back", "Strong Steps", "Rolling Stoned"
	};


	/**
	 * Load names from a file (unused)
	 * @param f
	 */
	public void loadFile(File f){

	}

	/**
	 * Returns a name from the list of names.
	 * @return
	 */
	public static String newName() {
		String pick = names[Utility.random.nextInt(names.length)];
		return pick;
	}

}//end Names