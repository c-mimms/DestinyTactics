package se300.destinytactics.game.scenes;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import se300.destinytactics.DestinyTactics; 
import se300.destinytactics.GameScene;
import se300.destinytactics.game.fleet.Fleet;
import se300.destinytactics.game.mapgen.Sector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * @author Mike
 *
 * InfoBar extends Stage
 * Creates area on bottom edge of screen for high level info.
 */
@SuppressWarnings({"unused","unchecked","rawtypes"})
public class InfoBar extends Stage{

	public GameScene myGame;
	private Table infobar;
	private Label label1, txt1, txt2, txt3, txt4,txt5, txt6,sectorLabel,playerResources, SelectedFleet, Location;
	public Skin skin;
	TextButton PlayerButton, PlayerButton2, PlayerButton3;
	SelectBox<String> ListButton;
	int edgePadding;
	int buttonPadding = 5;
	String[] fleets = new String[2];
	JComboBox fleetList = new JComboBox(fleets);
	
	
	/**
	 * @param vp
	 * @param padding
	 * @param skin
	 * @param myGame
	 */
	public InfoBar(Viewport vp, int padding, Skin skin, final GameScene myGame){
		super(vp);
		this.myGame = myGame;
		edgePadding = padding;
		this.skin = skin;
		
		infobar = new Table(skin);
		
		label1 = new Label("Information", skin);
		label1.setX(DestinyTactics.SCREEN_WIDTH / 2);
		label1.setY(100);
		label1.setAlignment(Align.center);
		
		ListButton = new SelectBox<String>( skin);
		//ListButton.setItems(myGame.m_Galaxy.m_Sector);
		
		
		PlayerButton = new TextButton("Selected Fleet", skin.get("default",
				TextButtonStyle.class));
		PlayerButton2 = new TextButton("Current game status", skin.get(
				"default", TextButtonStyle.class));
		PlayerButton3 = new TextButton("Fleet Status", skin.get(
				"default", TextButtonStyle.class));
		
		txt1 = new Label("txt1", skin);
		txt2 = new Label("txt2", skin);
		txt3 = new Label("txt3", skin);
		txt4 = new Label("txt4", skin);
		txt5 = new Label("txt5", skin);
		txt6 = new Label("txt6", skin);
		sectorLabel = new Label("sector", skin);
		playerResources = new Label("playerResources", skin);
		SelectedFleet = new Label("SelectedFleet", skin);
		
		infobar.setX(100);
		infobar.setY(PlayerButton.getHeight());
		PlayerButton.setX(0);
        PlayerButton.setY(3*PlayerButton.getHeight());
        PlayerButton2.setX(0);
        PlayerButton2.setY(2*PlayerButton.getHeight());
        PlayerButton3.setX(0);
        PlayerButton3.setY(PlayerButton.getHeight());
        txt1.setX(edgePadding * 10);
        txt1.setY(PlayerButton.getHeight() * 3);
        txt1.setText("Player Resoures:");
        
        fleets[0] = new String("Player 1");
        fleets[1] = new String("Enemy fleet 1");
        ListButton.setX(0);
        ListButton.setY(3*PlayerButton.getHeight());
        ListButton.setItems(fleets);
        
        
        
        final String playerrecource = ("0");
        
        // Switches to Location of Currently selected Fleet
        ListButton.addListener(new ClickListener(){
        	public void changed (ChangeEvent event) {
				int selectedPlayer = ListButton.getSelectedIndex();
				if(selectedPlayer == 0){
					SelectedFleet.setText("not your fleet");
					playerResources.setText(playerrecource);
				}
        		
        	}
        });
        
        ListButton.setSelected("Enemy Fleet 1");

        playerResources.setX(txt1.getX() + txt1.getMinWidth() + buttonPadding);
        playerResources.setY(PlayerButton.getHeight() * 3);
        
        
        SelectedFleet.setX(txt1.getX() + txt1.getMinWidth() + buttonPadding);
        SelectedFleet.setY(PlayerButton.getHeight());
        SelectedFleet.setText("Your fleet");
        
        
        
        txt2.setX(edgePadding * 10);
        txt2.setY(PlayerButton.getHeight() * 2);
        txt2.setText("Sector:");
        
        sectorLabel.setX(txt2.getX() + txt2.getMinWidth() + buttonPadding);
        sectorLabel.setY(txt2.getY());
        sectorLabel.setText("Sector Name");
        
        txt3.setX(edgePadding * 10);
        txt3.setY(PlayerButton.getHeight() * 1);
        txt3.setText("selected fleet");
        txt4.setX(edgePadding * 40);
        txt4.setY(PlayerButton.getHeight() * 3);
        txt4.setText("Travel Speed:");
        txt5.setX(edgePadding * 40);
        txt5.setY(PlayerButton.getHeight() * 2);
        txt5.setText("Destination:");
        txt6.setX(edgePadding * 40);
        txt6.setY(PlayerButton.getHeight() * 1);
        txt6.setText("Location:");
		
		
		/*
		PlayerButton.setX(edgePadding);
		PlayerButton.setY(3 * PlayerButton.getHeight());
		PlayerButton2.setX(edgePadding);
		PlayerButton2.setY(2 * PlayerButton.getHeight());
		PlayerButton3.setX(edgePadding);
		PlayerButton3.setY(PlayerButton.getHeight());
		txt1.setX(DestinyTactics.SCREEN_WIDTH - txt1.getWidth() - edgePadding);
		txt1.setY(edgePadding);
		*/
        
        
        
        
		this.addActor(label1);
		this.addActor(ListButton);
		this.addActor(txt1);
		this.addActor(txt2);
		this.addActor(txt3);
		this.addActor(txt4);
		this.addActor(txt5);
		this.addActor(txt6);
		this.addActor(sectorLabel);
		this.addActor(playerResources);
		this.addActor(SelectedFleet);
		
		
		
	}
	
	/**
	 * Sets name for the sector label
	 * @param name
	 */
	public void setSector(String name){
		sectorLabel.setText(name);
	}
	
	
	@SuppressWarnings("static-access")
	public void act(){
		if(myGame.sectorView){
			setSector(myGame.curSector.getName());
		}
		playerResources.setText("" + myGame.localPlayer.getResource());
	}
	
	public void render(float delta) {

	    Gdx.gl.glClearColor(0, 0, 0, 1);    //sets up the clear color (background color) of the screen.
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  //instructs openGL to actually clear the screen to the newly set clear color.

	    

	    }
			
}
		


	
