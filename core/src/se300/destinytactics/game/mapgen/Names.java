package se300.destinytactics.game.mapgen;

import java.io.File;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:09 PM
 */
public class Names {

	public static String[] names = {"Randomeous Nameous", "Steveous Jobeous", "Totz Notz Randomz", "Cornify", "Lil' Spoon", "Sauce Boas",
			"Planet 1", "Breaking Bad", "Not A Name", "Name", "Epicus Maximus", "Blue Planet", "Greystrom Meyers", 
			"The Pwnzershriek", "East Germany", "The Snack that Smiles Back", "Strong Steps", "Rolling Stoned"
	};

	public Names(){
	}

	public void finalize() throws Throwable {

	}
	public void close(){

	}

	public String generateName(){
		return "";
	}

	public String getName(){
		return "";
	}

	/**
	 * 
	 * @param f
	 */
	public void loadFile(File f){

	}

	public static String newName() {
		String pick = names[Utility.random.nextInt(names.length)];
		return pick;
	}

}//end Names