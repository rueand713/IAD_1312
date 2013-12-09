package com.randerson.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.randerson.entities.PlayerHandler;
import com.randerson.fusion.FusionScreenManager;
import com.randerson.fusion.GameManager;

public class SplashScreen implements Screen {

	public FusionScreenManager SCREEN_MANAGER;
	public PlayerHandler CONTROLLER;
	BitmapFont TITLE_FONT;
	BitmapFont HEADER_FONT;
	BitmapFont FONT;
	OrthographicCamera CAMERA;
	SpriteBatch batch;
	public boolean gameOver = false;
	public String titleText = "LEVEL COMPLETE";
	
	public SplashScreen (FusionScreenManager manager)
	{
		SCREEN_MANAGER = manager;
	}
	
	public void setController(PlayerHandler controller)
	{
		CONTROLLER = controller;
	}
	
	@Override
	public void render(float delta) {

		// clear the screen
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// setup the spritebatch to begin rendering
		batch.setProjectionMatrix(CAMERA.combined);
		batch.begin();
		
		// draw the splash screen bg image
		batch.draw(SCREEN_MANAGER.splashImage, 0, 0, SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		
		// draw the fonts to the screen
		GameManager.drawFont(batch, TITLE_FONT, titleText, (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 150, SCREEN_MANAGER.DEVICE_HEIGHT - 100);
		GameManager.drawFont(batch, HEADER_FONT, "Max Electron Level: " + CONTROLLER.getMaxElectrons(), 50, SCREEN_MANAGER.DEVICE_HEIGHT - 200);
		GameManager.drawFont(batch, HEADER_FONT, "Max Proton Level: " + CONTROLLER.getMaxProtons(), 50, SCREEN_MANAGER.DEVICE_HEIGHT - 250);
		GameManager.drawFont(batch, HEADER_FONT, "Max Neutron Level: " + CONTROLLER.getMaxNeutrons(), 50, SCREEN_MANAGER.DEVICE_HEIGHT - 300);
		GameManager.drawFont(batch, HEADER_FONT, "Multiplier: " + ((int) CONTROLLER.getMultiplier()), 50, SCREEN_MANAGER.DEVICE_HEIGHT - 350);
		GameManager.drawFont(batch, HEADER_FONT, "Total Score: " + CONTROLLER.getScore(), 50, SCREEN_MANAGER.DEVICE_HEIGHT - 400);
		GameManager.drawFont(batch, FONT, "Dual Touch Screen To Continue", (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 150, 100);
		
		// end the batch
		batch.end();
		
		// check for a touch event
		if (Gdx.input.isTouched(1))
		{
			if (gameOver == true)
			{
				// stop the level music
				SCREEN_MANAGER.sceneMusic.stop();
				
				// reset vars
				SCREEN_MANAGER.resetGame();
			}
			else if (gameOver == false)
			{
				// reset the gameplay level
				SCREEN_MANAGER.resetAct();
				
				// start the next level
				SCREEN_MANAGER.setScreen(SCREEN_MANAGER.actOne);
			}
		}
		
		CAMERA.update();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch(1024, 2);
		TITLE_FONT = GameManager.getBitmapFont(Color.WHITE, 2.0f);
		HEADER_FONT = GameManager.getBitmapFont(Color.WHITE, 1.5f);
		FONT = GameManager.getBitmapFont(Color.WHITE, 1.0f);
		CAMERA = GameManager.getCamera(SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		
		if (gameOver)
		{
			titleText = "GAME OVER";
		}
		else
		{
			titleText = "LEVEL COMPLETE";
		}
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
