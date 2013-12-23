package com.randerson.levels;

import java.util.HashMap;
import android.annotation.SuppressLint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.randerson.entities.Achievement;
import com.randerson.fusion.FusionScreenManager;
import com.randerson.fusion.GameManager;
import com.randerson.twitter4j.TwitterAccount;

@SuppressLint("DefaultLocale")
public class Awards implements Screen {

	FusionScreenManager SCREEN_MANAGER;
	SpriteBatch batch;
	Vector3 TOUCH_POSITION;
	Vector2 touch;
	OrthographicCamera CAMERA;
	BitmapFont HEADER_FONT;
	BitmapFont FONT;
	Rectangle BACK_BUTTON;
	Rectangle SHARE_BUTTON;
	Rectangle[] achievementBoundaries;
	Vector2[] achievementCoords;
	String awardDescription = "";
	HashMap<String, Achievement> achievements;
	int selectedAchievement = 0;
	boolean canShare = false;
	boolean disableTouch = true;
	int disableCount = 60;
	
	public Awards(FusionScreenManager manager)
	{
		SCREEN_MANAGER = manager;
	}
	
	@Override
	public void render(float delta) {
		
		// clear the screen
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 
		 batch.setProjectionMatrix(CAMERA.combined);
		 batch.begin();
		 
		 // draw the bg
		 batch.draw(SCREEN_MANAGER.bgImage1, 0, 0, SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		 
		 // draw the back and share buttons to the screen
		 batch.draw(SCREEN_MANAGER.backButton, BACK_BUTTON.x, BACK_BUTTON.y, BACK_BUTTON.width, BACK_BUTTON.height);
		 
		 if (canShare == true)
		 {
			 batch.draw(SCREEN_MANAGER.shareButton, SHARE_BUTTON.x, SHARE_BUTTON.y, SHARE_BUTTON.width, SHARE_BUTTON.height);
		 }
		 
		 // draw the title and description text to screen
		 GameManager.drawFont(batch, HEADER_FONT, "ACHIEVEMENTS", 20, (SCREEN_MANAGER.DEVICE_HEIGHT - 35));
		 GameManager.drawFont(batch, FONT, awardDescription, 25, (SCREEN_MANAGER.DEVICE_HEIGHT - 95));
		 
		 // draw the achievements to screen as earned or unearned icons
		 // draw the leaderboard signup award
		 if (achievements.get("joined_leaderboard").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.joinedLeaderboard, achievementBoundaries[0].x, achievementBoundaries[0].y, achievementBoundaries[0].width, achievementBoundaries[0].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[0].x, achievementBoundaries[0].y, achievementBoundaries[0].width, achievementBoundaries[0].height);
		 }
		 
		 // draw the multiplier award
		 if (achievements.get("reached_5x_multiplier").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reached5xMultiplier, achievementBoundaries[1].x, achievementBoundaries[1].y, achievementBoundaries[1].width, achievementBoundaries[1].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[1].x, achievementBoundaries[1].y, achievementBoundaries[1].width, achievementBoundaries[1].height);
		 }
		 
		 // draw the negative award to screen
		 if (achievements.get("failed_to_try").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.quitterAward, achievementBoundaries[2].x, achievementBoundaries[2].y, achievementBoundaries[2].width, achievementBoundaries[2].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[2].x, achievementBoundaries[2].y, achievementBoundaries[2].width, achievementBoundaries[2].height);
		 }
		 
		// draws the level incremental award to screen
		 if (achievements.get("reached_level_5").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reachedLevel5, achievementBoundaries[3].x, achievementBoundaries[3].y, achievementBoundaries[3].width, achievementBoundaries[3].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[3].x, achievementBoundaries[3].y, achievementBoundaries[3].width, achievementBoundaries[3].height);
		 }
		 
		// draws the level incremental award to screen
		 if (achievements.get("reached_level_10").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reachedLevel10, achievementBoundaries[4].x, achievementBoundaries[4].y, achievementBoundaries[4].width, achievementBoundaries[4].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[4].x, achievementBoundaries[4].y, achievementBoundaries[4].width, achievementBoundaries[4].height);
		 }
		 
		// draws the level incremental award to screen
		 if (achievements.get("reached_level_25").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reachedLevel25, achievementBoundaries[5].x, achievementBoundaries[5].y, achievementBoundaries[5].width, achievementBoundaries[5].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[5].x, achievementBoundaries[5].y, achievementBoundaries[5].width, achievementBoundaries[5].height);
		 }
		 
		// draws the level incremental award to screen
		 if (achievements.get("reached_level_50").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reachedLevel50, achievementBoundaries[6].x, achievementBoundaries[6].y, achievementBoundaries[6].width, achievementBoundaries[6].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[6].x, achievementBoundaries[6].y, achievementBoundaries[6].width, achievementBoundaries[6].height);
		 }
		 
		// draws the level incremental award to screen
		 if (achievements.get("reached_level_75").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reachedLevel75, achievementBoundaries[7].x, achievementBoundaries[7].y, achievementBoundaries[7].width, achievementBoundaries[7].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[7].x, achievementBoundaries[7].y, achievementBoundaries[7].width, achievementBoundaries[7].height);
		 }
		 
		// draws the level incremental award to screen
		 if (achievements.get("reached_level_100").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.reachedLevel100, achievementBoundaries[8].x, achievementBoundaries[8].y, achievementBoundaries[8].width, achievementBoundaries[8].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[8].x, achievementBoundaries[8].y, achievementBoundaries[8].width, achievementBoundaries[8].height);
		 }
		 
		// draws the score incremental award to screen
		 if (achievements.get("earned_10k_points").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.earned10k, achievementBoundaries[9].x, achievementBoundaries[9].y, achievementBoundaries[9].width, achievementBoundaries[9].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[9].x, achievementBoundaries[9].y, achievementBoundaries[9].width, achievementBoundaries[9].height);
		 }
		 
		// draws the score incremental award to screen
		 if (achievements.get("earned_50k_points").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.earned50k, achievementBoundaries[10].x, achievementBoundaries[10].y, achievementBoundaries[10].width, achievementBoundaries[10].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[10].x, achievementBoundaries[10].y, achievementBoundaries[10].width, achievementBoundaries[10].height);
		 }
		 
		// draws the score incremental award to screen
		 if (achievements.get("earned_200k_points").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.earned200k, achievementBoundaries[11].x, achievementBoundaries[11].y, achievementBoundaries[11].width, achievementBoundaries[11].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[11].x, achievementBoundaries[11].y, achievementBoundaries[11].width, achievementBoundaries[11].height);
		 }
		 
		// draws the score incremental award to screen
		 if (achievements.get("earned_500k_points").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.earned500k, achievementBoundaries[12].x, achievementBoundaries[12].y, achievementBoundaries[12].width, achievementBoundaries[12].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[12].x, achievementBoundaries[12].y, achievementBoundaries[12].width, achievementBoundaries[12].height);
		 }
		 
		 // draws the score incremental award to screen
		 if (achievements.get("earned_1m_points").obtained == true)
		 {
			 batch.draw(SCREEN_MANAGER.earned1m, achievementBoundaries[13].x, achievementBoundaries[13].y, achievementBoundaries[13].width, achievementBoundaries[13].height);
		 }
		 else
		 {
			 batch.draw(SCREEN_MANAGER.blankAcheivement, achievementBoundaries[13].x, achievementBoundaries[13].y, achievementBoundaries[13].width, achievementBoundaries[13].height);
		 }
		 
		 batch.end();
		 
		 if (Gdx.input.isTouched() && disableTouch == false)
		 {
			// re initiate the disabled touching
			 disableTouch = true;
			 disableCount = 60;
			 
			 TOUCH_POSITION = GameManager.getTouchVector(0);
			 CAMERA.unproject(TOUCH_POSITION);
			 
			 touch = GameManager.getVect2(TOUCH_POSITION);
			 
			 // check for object collisions for the achievement buttons
			 if (GameManager.overlaps(touch, achievementBoundaries[0], true))
			 {
				 // set the selected achievement id
				 selectedAchievement = 0;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("joined_leaderboard").getName() + " - " + achievements.get("joined_leaderboard").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[1], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 1;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_5x_multiplier").getName() + " - " + achievements.get("reached_5x_multiplier").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[2], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 2;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("failed_to_try").getName() + " - " + achievements.get("failed_to_try").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[3], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 3;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_level_5").getName() + " - " + achievements.get("reached_level_5").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[4], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 4;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_level_10").getName() + " - " + achievements.get("reached_level_10").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[5], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 5;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_level_25").getName() + " - " + achievements.get("reached_level_25").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[6], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 6;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_level_50").getName() + " - " + achievements.get("reached_level_50").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[7], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 7;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_level_75").getName() + " - " + achievements.get("reached_level_75").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[8], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 8;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("reached_level_100").getName() + " - " + achievements.get("reached_level_100").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[9], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 9;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("earned_10k_points").getName() + " - " + achievements.get("earned_10k_points").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[10], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 10;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("earned_50k_points").getName() + " - " + achievements.get("earned_50k_points").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[11], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 11;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("earned_200k_points").getName() + " - " + achievements.get("earned_200k_points").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[12], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 12;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("earned_500k_points").getName() + " - " + achievements.get("earned_500k_points").getDescription();
			 }
			 else if (GameManager.overlaps(touch, achievementBoundaries[13], true))
			 {
				// set the selected achievement id
				 selectedAchievement = 13;
				 
				// validate the sharing
				 canShare = true;
				 
				 // get the achievement name and description
				 awardDescription = achievements.get("earned_1m_points").getName() + " - " + achievements.get("earned_1m_points").getDescription();
			 }
			 else if (GameManager.overlaps(touch, BACK_BUTTON, true))
			 {
				 // check for collision with the back button
				 
				 // invalidate the sharing
				 canShare = false;
				 
				 // stop the music
				 SCREEN_MANAGER.titleMusic.stop();
				 
				 // set the screen
				 SCREEN_MANAGER.setScreen(SCREEN_MANAGER.mainMenu);
			 }
			 else if (GameManager.overlaps(touch, SHARE_BUTTON, true) && canShare == true)
			 {
				 // check for collision with the share button
				 
				 // split the descriptor text for the tweet
				 String[] award = awardDescription.split(" - ");
				 
				 // create the achievement text to be shared
				 String message = "I just received the \"" + award[0] +"\" achievement in Fusion because I " + award[1].toLowerCase();
				 
				 // share the intent via twitter web intent
				TwitterAccount.webTweet(SCREEN_MANAGER.CONTEXT, message);
			 }
			 else
			 {
				 // no valid collision so the selected text should be reset
				 awardDescription = "";
				 
				// invalidate the sharing
				 canShare = false;
			 }
		 }
		 
		 // decrement the counter
		 if (disableCount >0)
		 {
			 disableCount--;
		 }
		 else
		 {
			 // reset the touching
			 disableTouch = false;
		 }
		 
		 // update the camera
		 CAMERA.update();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {

		// get the button size proportionate to the device screen
		int buttonWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.4f);
		int buttonHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.125f);
		int awardButtonWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.125f);
		int awardButtonHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.125f);
		
		// initialize the game objects
		CAMERA = GameManager.getCamera(SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		batch = new SpriteBatch(1024, 2);
		FONT = GameManager.getBitmapFont(Color.GRAY, 1.5f);
		HEADER_FONT = GameManager.getBitmapFont(Color.WHITE, 2.5f);
		BACK_BUTTON = new Rectangle((SCREEN_MANAGER.DEVICE_WIDTH - (buttonWidth + 10)), 0, buttonWidth, buttonHeight);
		SHARE_BUTTON = new Rectangle(10, 0, buttonWidth, buttonHeight);
		achievements = SCREEN_MANAGER.CONTROLLER.getAchievements();
		TOUCH_POSITION = new Vector3();
		achievementBoundaries = new Rectangle[14];
		achievementCoords = new Vector2[14];
		
		int[] xCoords = new int[5];
		int[] yCoords = new int[3];
		
		// iterate over the xCoords array and set the positions
		for (int i = 0; i < 5; i++)
		{
			xCoords[i] = ((awardButtonWidth * i) + (50 * i));
		}
		
		// iterate over the yCoords array and set the positions
		for (int n = 0; n < 3; n++)
		{
			yCoords[n] = ((SCREEN_MANAGER.DEVICE_HEIGHT - 340) - ((awardButtonHeight * n) + (50 * n)));
		}
		
		// set the coords index
		int index = 0;
		
		// iterate over the achievement coords array setting a vector object
		// created dynamically using the x and y coords arrays
		for (int j = 0; j < 3; j++)
		{
			for (int m = 0; m < 5; m++)
			{
				if (index < 14)
				{
					// set the achievementCoords index vector
					achievementCoords[index++] = new Vector2(xCoords[m], yCoords[j]);
				}
			}
		}
		
		
		// iterate over the achievement boundaries array setting the bounding rectangles
		for (int z = 0; z < 14; z++)
		{
			achievementBoundaries[z] = new Rectangle(achievementCoords[z].x, achievementCoords[z].y, awardButtonWidth, awardButtonHeight);
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
