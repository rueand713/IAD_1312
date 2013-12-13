package com.randerson.levels;

import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;
import com.randerson.entities.PlayerHandler;
import com.randerson.fusion.FusionScreenManager;
import com.randerson.fusion.GameManager;
import com.randerson.kinvey.LeaderboardEntity;

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
	boolean makeRequest;
	int highscore = 0;
	int disableCount = 90;
	boolean disableTouch = true;
	
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
		
		// when the touch is ready show the message
		if (disableTouch == false)
		{
			GameManager.drawFont(batch, FONT, "Dual Touch Screen To Continue", (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 150, 100);
		}
		
		// end the batch
		batch.end();
		
		// check for a touch event
		if (Gdx.input.isTouched(1))
		{
			if (disableTouch == false)
			{
				// check if this is a game over splash screen or level complete screen
				if (gameOver == true && makeRequest == true)
				{
					if (SCREEN_MANAGER.defaults != null)
					{
						// save the player highest score (in the event of comms error this can be uploaded)
						highscore = SCREEN_MANAGER.defaults.getData().getInt("highscore", 0);
						
						// compare the current score with any saved high score
						// if the current one is greater save it as the high score
						if (CONTROLLER.getScore() > highscore)
						{
							highscore = CONTROLLER.getScore();
							
							SCREEN_MANAGER.defaults.setInt("highscore", highscore);
						}
					}
					else
					{
						// set the temp high score
						highscore = CONTROLLER.getScore();
					}
					
					// set the request bool to false
					makeRequest = false;
					
					// update the leaderboard and end game
					updateLeaderboard();
				}
				else if (gameOver == false)
				{
					// reset the gameplay level
					SCREEN_MANAGER.resetAct();
					
					// start the next level
					SCREEN_MANAGER.setScreen(SCREEN_MANAGER.actOne);
				}
				
				// disable the touch
				disableTouch = true;
				disableCount = 90;
			}
		}
		
		CAMERA.update();
		
		// decrement the touch disable counter
		if (disableCount > 0)
		{
			disableCount--;
		}
		else 
		{
			// re enable touch
			disableTouch = false;
		}
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
		
		// kinvey tracking request bool
		makeRequest = true;
		
		// set touch disabling objects
		disableCount = 90;
		disableTouch = true;
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
	
	public void updateLeaderboard()
	{
		if (SCREEN_MANAGER.leaderboardsEnabled == true)
		{
			// create a new query object
			Query userQuery = SCREEN_MANAGER.kinveyClient.query();
			
			// verify the query object is valid
			if (userQuery != null)
			{
				// set the leaderboard query object by username
				// to fetch the current user score for evaluation
				userQuery.equals("username", SCREEN_MANAGER.defaults.getData().getString("username", null));
				
				// retreive current user leaderboard data
				SCREEN_MANAGER.leaderboard.get(userQuery, new KinveyListCallback<LeaderboardEntity>() {
					
					@Override
					public void onSuccess(LeaderboardEntity[] arg0) {
						
						if (arg0 != null && arg0.length > 0)
						{
							// get the entity score unit
							int score = arg0[0].score;
							
							// if the new score is greater it should replace
							// the previous score
							if (score < highscore)
							{
								// update the previous score with new one
								updateScore(arg0[0].id);
							}
							else 
							{
								// start a new session
								newGame();
							}
						}
						else
						{
							// add the new score
							updateScore(null);
						}
					}
					
					@Override
					public void onFailure(Throwable arg0) {

						// create a the error message
						Toast msg = Toast.makeText(SCREEN_MANAGER.CONTEXT, "Database Communication Error", Toast.LENGTH_SHORT); 
						
						// verify the object is valid and show it
						if (msg != null)
						{
							msg.show();
						}
						
						newGame();
					}
				});
			}
		}
		else
		{
			newGame();
		}
		
	}
	
	// method for starting new game session
	public void newGame()
	{
		// stop the level music
		SCREEN_MANAGER.sceneMusic.stop();
		
		// reset vars
		SCREEN_MANAGER.resetGame();
		
		// go to main menu
		SCREEN_MANAGER.setScreen(SCREEN_MANAGER.mainMenu);
	}
	
	// method for overwritting leaderboard score
	public void updateScore(String id)
	{
		// create a new leaderboard object
		LeaderboardEntity leaderboardEntry = new LeaderboardEntity();
		
		// verify the object is created properly
		if (leaderboardEntry != null)
		{
			// set the user data to the leaderbard object
			leaderboardEntry.set("username", SCREEN_MANAGER.defaults.getData().getString("username", null));
			leaderboardEntry.set("score", highscore);
			leaderboardEntry.set("country", SCREEN_MANAGER.defaults.getData().getString("country", null));
			leaderboardEntry.set("level", SCREEN_MANAGER.LEVEL);
			
			// check if this is an update or new entry
			if (id != null)
			{
				leaderboardEntry.set("_id", id);
			}
		}
		
		// attempt to save the leaderboard data
		SCREEN_MANAGER.leaderboard.save(leaderboardEntry, new KinveyClientCallback<LeaderboardEntity>() {
			
			@Override
			public void onSuccess(LeaderboardEntity arg0) {
				
				// create a new success message
				Toast msg = Toast.makeText(SCREEN_MANAGER.CONTEXT, "New High Score!", Toast.LENGTH_SHORT); 
				
				// verify the object is valid and show it
				if (msg != null)
				{
					msg.show();
				}
						
				// start the new session
				newGame();
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				
				// create a new failure message
				Toast msg = Toast.makeText(SCREEN_MANAGER.CONTEXT, "Leaderboard error", Toast.LENGTH_SHORT);
				
				// verify the object is valid and show it
				if (msg != null)
				{
					msg.show();
				}
				
				// start the new session
				newGame();
			}
		});
	}

}
