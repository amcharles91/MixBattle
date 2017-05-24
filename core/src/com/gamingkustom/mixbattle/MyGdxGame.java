package com.gamingkustom.mixbattle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class MyGdxGame extends ApplicationAdapter {
    public enum State {FALLING, JUMPING, RUNNING, STANDING};
    public State currentState;
    public State previousState;
    private Animation shadowRun;
    private Animation shadowStand;
	SpriteBatch batch;
	Texture background;
    Texture[] flappy;
	private TextureAtlas atlus;
    TextureAtlas.AtlasRegion shadowReg;
    TextureAtlas.AtlasRegion sonicReg;
    TextureRegion sonic;
    TextureRegion shadow;
    Sprite shadowstan;
    private int gameState = 0;
    private float w,h;
    private int flapState = 0;
    private int cur;
    float sY = 0;
    float vel = 0;
    float gravity = 2;
    private float stateTimer;
    private boolean runningRight;
    Array<TextureRegion> frames;

    @Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        cur = 1;

		batch = new SpriteBatch();
		background = new Texture("bg.png");
        flappy = new Texture[2];
        flappy[0] = new Texture("bird.png");
        flappy[1] = new Texture("bird2.png");
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        //Texture Atlast Messing with
        atlus = new TextureAtlas(Gdx.files.internal("shadow.atlas"));
        Gdx.app.debug("atlus", "The atlus location or name is =" + atlus.getRegions().toString());

        shadowReg = atlus.findRegion("shadowani");
        sonicReg = atlus.findRegion("sonicani");

        shadow = new TextureRegion(shadowReg.getTexture(),0,0, 35,40);
        shadowstan = new Sprite(shadow, 0,0, 35,40);
        shadowstan.setBounds(0,0,35,40);

        frames = new Array<TextureRegion>();
        for(int i = 0; i <5; i++){
            frames.add(new TextureRegion(shadowReg.getTexture(), i * 35,0, 35, 40));
            Gdx.app.debug("frames", "i is =" + i);
        }
        shadowRun = new Animation(0.1f, frames);
        sY =((h/2)-frames.get(cur).getRegionWidth());
        //frames.clear();
	}


	@Override
	public void render () {

        if(gameState != 0) {

            if(Gdx.input.justTouched()){
                vel = -30;
            }

            if(sY > 0 || vel < 0) {

                vel = vel + gravity;
                sY -= vel;
            }

        }else{

            if(Gdx.input.justTouched()){
                Gdx.app.log("touch", "Shadow was touched dirty");
                gameState =1;
            }
        }

        if(flapState < 9){
            cur = 0;
            flapState++;
        }else if(flapState < 18){
            cur = 1;
            flapState++;
        }else if(flapState < 27){
            cur=2;
            flapState++;
        }else if(flapState < 36){
            cur=3;
            flapState++;
        }else if(flapState < 45){
            cur=4;
            flapState++;
        }else{
            cur = 1;
            flapState=0;
        }

        batch.begin();
        batch.draw(background, 0, 0, w, h);
        //batch.draw(flappy[flapState], ((w/2)-(flappy[flapState].getWidth()/2)),
        //      ((h/2)-(flappy[flapState].getHeight()/2)));

        batch.draw(frames.get(cur), ((w / 2) - frames.get(cur).getRegionWidth()),
                sY, 128, 152);


        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
