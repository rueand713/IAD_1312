package com.randerson.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.randerson.fusion.FusionScreenManager;
import com.randerson.fusion.GameManager;

public class Tutorial implements Screen {

	SpriteBatch batch;
	OrthographicCamera CAMERA;
	FusionScreenManager SCREEN_MANAGER;
	BitmapFont FONT;
	
	// class constructor
	public Tutorial(FusionScreenManager manager)
	{
		SCREEN_MANAGER = manager;
	}
	
	@Override
	public void render(float delta) {

		// clear the screen
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				 
		 // begin rendering the sprites/textures
		 batch.setProjectionMatrix(CAMERA.combined);
		 batch.begin();
		 
		 // draw the bg image
		 batch.draw(SCREEN_MANAGER.tutorialImage, 0, 0, SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		 
		 // draws the title text
		 GameManager.drawFont(batch, FONT, "Dual Touch Screen To Continue", (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 150, (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.26f));
		 
		 batch.end();
		 
		 if (Gdx.input.isTouched(1))
		 {
			 SCREEN_MANAGER.titleMusic.stop();
			 
			 SCREEN_MANAGER.setScreen(SCREEN_MANAGER.mainMenu);
		 }
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch(1024, 2);
		CAMERA = GameManager.getCamera(SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		FONT = GameManager.getBitmapFont(Color.WHITE, 2.0f);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}