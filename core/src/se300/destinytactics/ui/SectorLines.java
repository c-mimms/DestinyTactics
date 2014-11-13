package se300.destinytactics.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * 
 * @author Chris Shannon
 * Extends Actor in the LibGDX framework.
 * Draws lines connecting the sector label and sector sprite. Lines are redrawn with changes in parallax actor location.
 *
 */
public class SectorLines extends Actor{
	
	private Group sectors, sectorNames;
	private static ShapeRenderer renderer = new ShapeRenderer();
	
	/**
	 * Updates the sectors and sectorNames groups in memory
	 * @param sectors
	 * @param sectorNames
	 */
	public void updateGroups(Group sectors, Group sectorNames) {
		this.sectors = sectors;
		this.sectorNames = sectorNames;
	}
	
	@Override
	/**
	 * LibGDX override. Draws the lines connecting the sector label and sprite.
	 * 
	 * @param batch
	 * @param parentAlpha
	 */
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		
		Actor[] sectorArray = sectors.getChildren().items;
		Actor[] sectorNameArray = sectorNames.getChildren().items;

		renderer.begin(ShapeType.Line);
		for (int i = 0; i < sectorArray.length; i++) {
			if (sectorArray[i] != null) {
				renderer.setColor(Color.valueOf("8fc2fd"));
				//renderer.setColor(Color.CYAN);
				renderer.line(sectorArray[i].getX() + (sectorArray[i].getWidth() / 2),
							  sectorArray[i].getY() + (sectorArray[i].getHeight() / 2),
							  sectorNameArray[i].getX() + (sectorNameArray[i].getWidth() / 2),
							  sectorNameArray[i].getY() + (sectorNameArray[i].getHeight() / 2));
			}
		}
		renderer.end();
		batch.begin();
	}
}
