package se300.destinytactics.game.scenes;

import se300.destinytactics.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.Viewport;

@SuppressWarnings("unused")
public class OldInfoBar extends Stage{

	private Table container1, overviewWrapper, infobarFormWrapper, overview, infobarform;
	private Label label1, label2;
	private TextField txt1, txt2;
	public Skin skin;
	
	
	
	public OldInfoBar(Viewport vp, final GameScene myGame){
		super(vp);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		createOverview();
		createinfobarform();
		
		container1 = new Table(skin);
		container1.add("INFO BAR").expandX().bottom().height(GameScene.SCREEN_HEIGHT * 2/10);
		container1.row();
		
		container1.setFillParent(true);
		this.addActor(container1);
		
		
	}
	public Table getInfobar(){
		return container1;
	}
	public Table getOverview(){
	return overviewWrapper;	
	}
	public Table getInoform(){
	return infobarform;	
	}
	public void createOverview(){
		overviewWrapper = new Table(skin);
		overview = new Table(skin);
		
		//yeah its copied, watcha gonna do bout it, tried it my way didnt work, still dont work
		final ScrollPane overviewScroll = new ScrollPane(overview, skin);
		InputListener stopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		
		overview.defaults().expandX();
		
		overview.row();
		overview.add(new Label("label1", skin)).width(GameScene.SCREEN_WIDTH/4);
		
		overview.row();
		overview.add(new Label("label2", skin)).width(GameScene.SCREEN_WIDTH/4);
		
		overviewWrapper.add("Onfo bar").bottom().fillX();
		overviewWrapper.row().top();
		
		overviewWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		overviewWrapper.setHeight(GameScene.GALAXY_HEIGHT * 3/10);
	}
	
	public void createinfobarform() {
		infobarFormWrapper = new Table(skin);
		infobarform = new Table(skin);
		final ScrollPane formScroll = new ScrollPane(infobarform, skin);
		InputListener formStopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};
		
		
		infobarform.defaults().expandX();
		
		infobarform.row();
		infobarform.add(new Label("label1", skin)).width(GameScene.SCREEN_WIDTH/8).expandX().fillX();
		
		infobarform.row();
		infobarform.add(new Label("label2", skin)).width(GameScene.SCREEN_WIDTH/4).expandX().fillX();
		
		infobarFormWrapper.add("label1").bottom().expandX();
		infobarFormWrapper.row().top();                 
		infobarFormWrapper.add(formScroll).expand().fill().top();
		infobarFormWrapper.row().bottom().expandX();
		
		infobarFormWrapper.setWidth(GameScene.SCREEN_WIDTH/2);
		infobarFormWrapper.setHeight(GameScene.SCREEN_HEIGHT * 3/10);
		
	}
		
	
		
}

	
