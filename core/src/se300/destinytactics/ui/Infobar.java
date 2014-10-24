package se300.destinytactics.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Infobar {
	
private Stage stage;
private Skin skin;
private Table table;
private List button1, button2;
private TextArea texarea;


public void create(){
	stage = new Stage();
	Gdx.input.setInputProcessor(stage);
	TextArea textarea = new TextArea("Example text area", skin );
	
	Table table = new Table();
	stage.addActor(table);
	Skin skin = new Skin();
	table.setSize(260, 195);
	table.setPosition(190, 142);
	
	List button1 = new List(skin, "list1");
	List button2 = new List(skin, "List2");
}
public void render(){
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	stage.act(Gdx.graphics.getDeltaTime());
	stage.draw();
	
}
public void resize(int width, int height){
	stage.getViewport().update(width, height, true);
	
}
public void dispose(){
	stage.dispose();
}
}
