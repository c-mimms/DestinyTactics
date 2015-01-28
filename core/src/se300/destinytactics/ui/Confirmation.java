package se300.destinytactics.ui;

import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Battleship;
import se300.destinytactics.game.fleet.Bomber;
import se300.destinytactics.game.fleet.Carrier;
import se300.destinytactics.game.fleet.Corvette;
import se300.destinytactics.game.fleet.Dreadnaught;
import se300.destinytactics.game.fleet.Fighter;
import se300.destinytactics.game.fleet.Scout;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * This class creates a popup to display successful or unsuccessful building of ships
 * @author Mike
 *
 */
public class Confirmation extends Dialog{
	GameScene myGame;

	public Confirmation(String title, Skin skin, int F, int Cor, int Car, int B, int S, int Bat, int D, Stage stage) {
		super(title, skin);
		isVisible();
		Table table = new Table();
		table.setVisible(true);
		
		if(F != 0){
			Label fighter = new Label("Fighters: " + F+ " Turns: " + Fighter.stats.buildTime, skin);
			table.add(fighter);
			table.row();
		}
		if(Cor != 0){
			Label corvette = new Label("Corvettes: " + Cor + " Turns: " + Corvette.stats.buildTime, skin);
			table.add(corvette);
			table.row();
		}
		if(B != 0){
			Label bomber = new Label("Bombers: " + B + " Turns: " + Bomber.stats.buildTime, skin);
			table.add(bomber);
			table.row();
		}
		if(S != 0){
			Label scout = new Label("Scoutships: " + S + " Turns: " + Scout.stats.buildTime, skin);
			table.add(scout);
			table.row();
		}
		if(Car != 0){
			Label carrier = new Label("Carriers: " + Car + " Turns: " + Carrier.stats.buildTime, skin);
			table.add(carrier);
			table.row();
		}
		if(Bat != 0){
			Label battleship = new Label("Battleships: " + Bat + " Turns: " + Battleship.stats.buildTime, skin);
			table.add(battleship);
			table.row();
		}
		if(D != 0){
			Label dreadnaught = new Label("Dreadnaughts: " + D + " Turns: " + Dreadnaught.stats.buildTime, skin);
			table.add(dreadnaught);
			table.row();
		}
		
		
		add(table);
		row();
		button("OKAY");
		
		/*
		.addListener(new ClickListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				destroy();
				return true;
			}
			
		});
		*/
		show(stage);
		
		System.out.println("Fighter: " + F + " Corvette: " + Cor + " Scout: " + S + " Bomber: " + B + " Carrier: " + Car + " Battleship: " + Bat + " Dread: " + D);
		
	}
	
	public void destroy() {
		this.remove();
	}

}
