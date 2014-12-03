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

	public static String[] names = {"Haven", "Firaxis", "Vista", "Omicron", "Gemini", "Zephyr",
			"Mon Tuatha", "Thraxis", "Goliath", "Calypso", "Dirk", "Pirrano", "G'Meyers", 
			"Greystrom", "Tande", "Pelou", "Luthien", "Vaire", "Niocia", "Aldan", "Cerberi", "Cygni", "Adonis",
			"Vorcia", "Hooni", "Shalim", "Qadesh", "Moisu", "Arietis", "Alkonost", "Sicia", "Tian-Mu", "Doradus",
			"Astarte", "Maenali", "M'Glani", "Herculis", "Ceres", "Serpentis", "Draconis", "Utumno", "Phaeton",
			"Judur", "Avlel", "Ninkasi", "Coru", "Nahar", "Semargl", "Oman", "Vaalbara", "Q'Sauri", "H'Necri",
			"Oceanus", "Vorcia", "Nioni", "Vaire", "Furi", "Atlantis", "Tethys", "Skirnir", "Alkonost", "Orionis",
			"Aegle", "Grakha", "Charon", "Tarandi", "L'Mada", "M'Inon", "Bolthor", "Geminorum", "Oberon", "Lona",
			"Gula", "Hyrokkin", "Thyoph"
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