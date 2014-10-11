package se300.destinytactics;


/**
 * @author John
 * @version 1.0
 * @created 10-Oct-2014 5:49:15 PM
 */
public class Sector extends Quadtree {

	private int controlState;
	private String name;
	private OrbitalBody OrbitalBodies[];
	private int posX;
	private int posY;
	public OrbitalBody m_OrbitalBody;
	public Button m_Button;

	public Sector(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public void click(){

	}

	public String getName(){
		return "";
	}

	public Point getPos(){
		return null;
	}

	public int getState(){
		return 0;
	}
}//end Sector