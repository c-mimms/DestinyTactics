package se300.destinytactics.units.interfaces;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:09 PM
 */
public interface needsHangar {

	public static int hangarCost = 0;
	public static boolean isHangared = false;

	public int getHangarCost();

	public boolean isHangared();

}