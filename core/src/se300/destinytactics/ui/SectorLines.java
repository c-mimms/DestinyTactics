package se300.destinytactics.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;



public class SectorLines extends Actor{
	
	private Group sectors, sectorNames;
	private static ShapeRenderer renderer = new ShapeRenderer();
	
	public void updateGroups(Group sectors, Group sectorNames) {
		this.sectors = sectors;
		this.sectorNames = sectorNames;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		
		Actor[] sectorArray = sectors.getChildren().items;
		Actor[] sectorNameArray = sectorNames.getChildren().items;

		renderer.begin(ShapeType.Line);
		for (int i = 0; i < sectorArray.length; i++) {
			if (sectorArray[i] != null) {
				//renderer.setColor(Color.valueOf("506c8d"));
				renderer.setColor(Color.CYAN);
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
