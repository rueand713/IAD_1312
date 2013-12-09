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

public class Credits implements Screen {

	SpriteBatch batch;
	OrthographicCamera CAMERA;
	FusionScreenManager SCREEN_MANAGER;
	BitmapFont HEADER;
	BitmapFont FONT;
	BitmapFont TITLE_FONT;
	BitmapFont TOUCH_FONT;
	
	public Credits(FusionScreenManager manager)
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
		 batch.draw(SCREEN_MANAGER.creditsImage, 0, 0, SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		 
		 // draws the title text
		 GameManager.drawFont(batch, TITLE_FONT, "CREDITS", (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 150, SCREEN_MANAGER.DEVICE_HEIGHT - 30);
		 
		 // draws the crediting text
		 GameManager.drawFont(batch, HEADER, "Theme & Level Music", 50, SCREEN_MANAGER.DEVICE_HEIGHT - 230);
		 GameManager.drawFont(batch, FONT, "Soundrangers.com", 60, SCREEN_MANAGER.DEVICE_HEIGHT - 280);
		 GameManager.drawFont(batch, HEADER, "Sound Effects", 50, SCREEN_MANAGER.DEVICE_HEIGHT - 380);
		 GameManager.drawFont(batch, FONT, "Finn @ Freesound.org", 60, SCREEN_MANAGER.DEVICE_HEIGHT - 430);
		 GameManager.drawFont(batch, HEADER, "Artwork & Programming", 50, SCREEN_MANAGER.DEVICE_HEIGHT - 530);
		 GameManager.drawFont(batch, FONT, "Rueben Anderson", 60, SCREEN_MANAGER.DEVICE_HEIGHT - 580);
		 
		 // draw the continue text
		 GameManager.drawFont(batch, TOUCH_FONT, "Dual Touch Screen To Continue", (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 180, 75);
		 
		 // end rendering
		 batch.end();
		 
		 CAMERA.update();
		 
		 // check for screen dual touch
		 if (Gdx.input.isTouched(1))
		 {
			 // stops the title music so it can start again
			 SCREEN_MANAGER.titleMusic.stop();
			 
			 // go back to the title screen
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
		TITLE_FONT = GameManager.getBitmapFont(Color.WHITE, 2.25f);
		FONT = GameManager.getBitmapFont(Color.WHITE, 1.5f);
		TOUCH_FONT = GameManager.getBitmapFont(Color.WHITE, 1f);
		HEADER = GameManager.getBitmapFont(Color.WHITE, 1.85f);
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
