package com.randerson.levels;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.randerson.entities.BoxActor;
import com.randerson.entities.Particle;
import com.randerson.entities.ParticleFactory;
import com.randerson.entities.PlayerHandler;
import com.randerson.entities.SubAtomic;
import com.randerson.fusion.Box2D;
import com.randerson.fusion.FusionScreenManager;
import com.randerson.fusion.GameManager;

public class ActOne implements Screen {

	OrthographicCamera CAMERA;
	FusionScreenManager SCREEN_MANAGER;
	boolean TOUCHED = false;
	boolean MULTITOUCH = false;
	boolean PAUSED = false;
	BitmapFont FONT;
	BitmapFont SUB_FONT;
	Vector3[] TOUCH_POSITION;
	Rectangle pauseButton;
	int pauseButtonWidth;
	int pauseButtonHeight;
	int timeMeterWidth;
	int timeMeterHeight;
	int meterWidth;
	int scoreX;
	int scoreY;
	int timer = 0;
	int selectedItemHeight;
	int selectedItemWidth;
	Vector2[] factoryLocations;
	float backgroundRotation = 0;
	PlayerHandler controller;
	World WORLD;
	SubAtomic[] PARTICLES;
	ParticleFactory[] PARTICLE_FACTORY;
	Box2DDebugRenderer debugger;
	BoxActor leftWall;
	BoxActor rightWall;
	BoxActor topWall;
	BoxActor bottomWall;
	ShapeRenderer shapeRender;
	SpriteBatch batch;
	SpriteBatch batch2;
	int BONUS = 0;
	int TARGET_SCORE;
	
	// class constructor
	public ActOne(FusionScreenManager manager)
	{
		SCREEN_MANAGER = manager;
	}
	
	@Override
	public void render(float delta) {

		// ******** SPRITE RENDERING  *********
		
		// clear the screen
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 
		// begin drawing the sprites to the screen even when paused
		 batch.setProjectionMatrix(CAMERA.combined);
		 batch.enableBlending();
		 batch.begin();
		 
		 // draws the scene background		 
		 batch.draw(SCREEN_MANAGER.bgImage1, 0, 0, SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		 
		 if (PARTICLES != null)
		 {
			 for (int i = 0; i < PARTICLES.length; i++)
			 {
				 // gets the current index particle in the array
				 SubAtomic atom = PARTICLES[i];
				 
				 if (atom != null && atom.getFixture() != null)
				 {
					 // retrieve the current position vector
					 Vector2 pos = atom.getPosition();
		
					 // draw the texture to the screen at the coordinates
					 batch.draw(atom.getTexture(), pos.x, pos.y);
					 
					 // draw the particle quantities
					 if (atom.getQuantity() >1)
					 {
					 	GameManager.drawFont(batch, SUB_FONT, "" + atom.getQuantity(), (int) (atom.getPosition().x), (int) (atom.getPosition().y + 15));
					 }
					 
					 // retrieve the velocity speeds and add them to the previous x position
					 float x = pos.x + atom.getBody().getLinearVelocity().x;
					 float y = pos.y + atom.getBody().getLinearVelocity().y;
					 
					 // set the x and y based on the velocity speeds
					 atom.setPosition(x, y, 0);
				 }
			 }
		 }
		 
		 // draws the time meter to the screen
		 batch.draw(SCREEN_MANAGER.timeMeterBack, 0, 0, timeMeterWidth, timeMeterHeight);
		 batch.end();
		 
		 // draw the actual time meter
		 GameManager.drawRectangle(shapeRender, CAMERA, 11, 0, controller.getMeterLength(meterWidth), timeMeterHeight, Color.GRAY);
		 
		 // draw the foreground meter
		 batch2.setProjectionMatrix(CAMERA.combined);
		 batch2.begin();
		 batch2.draw(SCREEN_MANAGER.timeMeterFront, 0, 0, timeMeterWidth, timeMeterHeight);
		 
		 // iterate over the selection object for drawing them to screen
		 for (int n = 0; n < controller.getSelection().length; n++)
		 {
			 SubAtomic particle = (SubAtomic) controller.getSelection()[n];
			 
			 // verify the particle is valid
			 if (particle != null && particle.getFixture() != null)
			 {
				 // set the default position (for selection 1)
				 int x = SCREEN_MANAGER.DEVICE_WIDTH - 128;
				 int y = 10;
				 
				 // check if this is for a second selection
				 if (n == 1)
				 {
					 // set the position for the second selection
					 x = SCREEN_MANAGER.DEVICE_WIDTH - 60;
				 }
				 
				 // draw the texture to the screen
				 batch2.draw(particle.getTexture(), x, y, selectedItemWidth, selectedItemHeight);
			 }
		 }
		 
		 // check if there is a bonus awarded
		 if (BONUS > 0)
		 {
			// draw the bonus message to the screen
			 GameManager.drawFont(batch2, FONT, "Bonus!", SCREEN_MANAGER.DEVICE_WIDTH / 2, SCREEN_MANAGER.DEVICE_HEIGHT / 2);
		 }
		 
		 // draw the score to the screen
		 GameManager.drawFont(batch2, FONT, "SCORE: " + controller.getScore(), scoreX, scoreY);
		 
		 if ((int) controller.getMultiplier() > 1)
		 {
			 // draw the score to the screen
			 GameManager.drawFont(batch2, FONT, ((int) controller.getMultiplier()) + "x", scoreX, scoreY - 45);
		 }
		 
		 // check if the game has been paused
		 if (PAUSED)
		 {
			 // draw the paused text
			 GameManager.drawFont(batch2, FONT, "GAME PAUSED", (SCREEN_MANAGER.DEVICE_WIDTH / 2) - 150, SCREEN_MANAGER.DEVICE_HEIGHT / 2);
		 }
		 
		// draw the pause button to the screen
		 batch2.draw(SCREEN_MANAGER.pauseButton, pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height);
		 batch2.end();
		 
		 
		// *********** TOUCH EVENTS ***********
		 
		 
		 // check if the user has touched the screen
		 if (PAUSED == true && Gdx.input.isTouched(1))
		 {
			 // unpause the game
			 PAUSED = false;
			 
			 // set the touches to false
			 TOUCHED = false;
			 MULTITOUCH = false;
			 
			 for (int n = 0; n < PARTICLES.length; n++)
			 {
				 SubAtomic particle = PARTICLES[n];
				 
				 if (particle != null)
				 {
					// set the particle to be destroyed
					 particle.destroy();
					 PARTICLES[n] = null;
				 }
			 }
			 
		 }								// check if a touch event is true to manipulate game events unless paused
		 else if (PAUSED == false)
		 {
			 if (Gdx.input.isTouched(1))
			 {
				// capture the touch position
				 TOUCH_POSITION[1] = GameManager.getTouchVector(1);
				 CAMERA.unproject(TOUCH_POSITION[1]);
				 
				 MULTITOUCH = true;
				 TOUCHED = false;
			 }
			 else if (Gdx.input.isTouched(0))
			 {
				 // capture the touch position
				 TOUCH_POSITION[0] = GameManager.getTouchVector(0);
				 CAMERA.unproject(TOUCH_POSITION[0]);
				 
				 MULTITOUCH = false;
				 TOUCHED = true;
			 }
			 else
			 {
				 // no longer touched set the touch tracking bool to false
				 TOUCHED = false;
				 MULTITOUCH = false;
			 }
			 
			// check if there is a registered multitouch 
			 if (MULTITOUCH == true)
			 {
				 // get the returned score (if any) for valid matches
				 int reward = controller.calculateSelectionMatches();
				 
				// check if there was a match in the selection
				if (reward > 0)
				{
					// increase the multiplier
					controller.setMultiplier(controller.getMultiplier() + 0.25f);
					
					// set the multiplier time
					controller.setMultipliertime(90);
					
					// play the got points sfx
					if (SCREEN_MANAGER.particleSound != null)
					{
						// play low time sfx
						SCREEN_MANAGER.particleSound.play();
					}
					
					// add the score
					controller.setScore(controller.getScore() + reward);
					
					// add the score to the bonus tracker
					controller.updateBonus(controller.getBonus() + reward);
					
					// check if the player has earned a bonus yet
					if (controller.getBonus() >= 5000)
					{
						// set the bonus timer
						BONUS = 1000;
						
						// play bonus award sfx
						if (SCREEN_MANAGER.bonusSound != null)
						{
							// play low time sfx
							SCREEN_MANAGER.bonusSound.play();
						}
						
						// award the bonus points
						controller.setScore(controller.getScore() + 1000);
						
						// award the bonus time
						controller.updateTime(controller.getTime() + 100);
						
						// reset the bonus tracker
						controller.updateBonus(0);
					}
					
					// add 1 sec to the timer for successful fusion
					controller.updateTime(controller.getTime() + (30 * (int) controller.getMultiplier()));
					
					SubAtomic[] list = controller.getSelection();
					
					if (list != null && list[0] != null)
					{
						// get the id of the first selected particle
						int selectedId1 = list[0].getID();
						
						// init a default id for a second selected particle
						int selectedId2 = 0;
						
						// init a integer array for the first selected particle
						int[] indexes = new int[1];
						
						if (list[1] != null)
						{
							// get the id of the second selected particle
							selectedId2 = list[1].getID();
							
							// re initialize the index array for two selected particles
							indexes = new int[2];
						}
						
						// iterate over the particles object
						for (int i = 0; i < PARTICLES.length; i++)
						{
							// create a reference to the particles within the array
							SubAtomic particle = (SubAtomic) PARTICLES[i];
							
							if (particle != null && particle.getFixture() != null)
							{
								// verify that the current referenced particle's id matches 
								// either the first or second selected ids
								if (particle.getID() == selectedId1)
								{
									// destroy the particle data
									PARTICLES[i].destroy();
									
									// add the index of the current particle to the indexes to remove
									indexes[0] = i;
									
									break;
								}
								else if (particle.getID() == selectedId2)
								{
									// destroy the particles data
									PARTICLES[i].destroy();
									
									// add the index of the current particle to the indexes to remove
									indexes[1] = i;
									
									break;
								}
							}
						}
						
						// iterate over the indexes to remove
						for (int n : indexes)
						{
							// remove the indexes from the particles array
							PARTICLES[n] = null;
						}
					}
					
					// reset the array
					controller.resetSelection();
				}
				
			 }										// checks if a single touch is registered check if it touched an entity
			 else if (TOUCHED == true)
			 {
				 // check if the user 
				 if (GameManager.overlaps(GameManager.getVect2(TOUCH_POSITION[0]), pauseButton, false))
				 {
					 // pause the game
					 PAUSED = true;
					 
					 // iterate over the particles array and store their velocities
					 // and remove the velocities
					 for (int n = 0; n < PARTICLES.length; n++)
					 {
						 SubAtomic particle = PARTICLES[n];
						 
						 // verify the particle object is valid
						 if (particle != null && particle.getFixture() != null)
						 { 
							 // remove the particles velocity
							 PARTICLES[n].applyVelocity(0, 0);
						 }
					 }
				 }
				 else
				 {
					 for (int j = 0; j < PARTICLES.length; j++)
					 {
						 // create reference to particle in array
						 SubAtomic particle = (SubAtomic) PARTICLES[j];
						 
						 // verify the object is valid
						 if (particle != null && particle.getFixture() != null)
						 {
							 // check if the touch overlapped the entity
							 if (particle.overlaps(GameManager.getVect2(TOUCH_POSITION[0]), false))
							 {
							 	// selects the particle
							 	controller.updateSelection(particle);
							 };
						 }
					 }
				 }
			 }
			 
			 // check if there are less than 10 particles generated
			 if (PARTICLES != null && timer % 30 == 0)
			 {
				 
				 boolean hasEmptySpace = false;
				 
				 for (SubAtomic particle : PARTICLES)
				 {
					 // set the space checking bool to true
					 // so that more particles can be generated
					 if (particle == null) hasEmptySpace = true;
				 }
				 
				 // randomly select a factory to generate from
				 int j = (int) Math.round(Math.random() * 3.25f);
				 
				 // modifiers for the vertical and horizontal velocity vectors
				 int xModifier = 1;
				 int yModifier = 1;
				 
				 // set the modifer values to cause the particles to travel away from their origin
				 switch(j)
				 {
				 // top left - move down right
				 case 0:
					 yModifier = -1;
					 break;
					 
				// bottom left - move up right
				 case 1:
					 break;
				
				// top right - move down left
				 case 2:
					 yModifier = -1;
					 xModifier = -1;
					 break;
				
					 
				// bottom right - move up left
				 case 3:
					 xModifier = -1;
					 break;
					 
					 default:
						 break;
				 }
				 
				 // reference a particle factory object from the factory array
				 ParticleFactory pf = PARTICLE_FACTORY[j];
				 
				 Particle particle = null;
				 
				 if (pf != null)
				 {
					 int factoryModifier = SCREEN_MANAGER.LEVEL / 10;
					 
					 if (factoryModifier < 1) factoryModifier = 1;
					 
					// generate a particle from the particle factory
					 particle = (Particle) pf.generateParticle(factoryModifier, controller);
				 }
				 
				 // verify that the object is valid
				 if (particle != null && hasEmptySpace == true)
				 {
					 float deltaTime = GameManager.getDelta(false);
					 
					 // get random x and y forces
					 float yForce = (float) (Math.random() * (120 * yModifier)) * deltaTime;
					 float xForce = (float) (Math.random() * (120 * xModifier)) * deltaTime;
					 
					 // apply the velocities to the particle
					 particle.applyVelocity(xForce, yForce);
					 
					 for (int i = 0; i < PARTICLES.length; i++)
					 {
						 if (PARTICLES[i] == null)
						 {
							// add the particle to the particle tracking array
							 PARTICLES[i] = particle;
							 
							 break;
						 }
					 }
					 
				 }
			 }
			 
			// integer list to hold the particles that have went out of range
			 // and need to be removed to draw fresh particles
			 ArrayList<Integer> outOfBoundsIndexes = new ArrayList<Integer>();
			 
			 // check for all the particles that have gone out of range
			 for (int j = 0; j < PARTICLES.length; j++)
			 {
				 SubAtomic atom = (SubAtomic) PARTICLES[j];
				 
				 if (atom != null && atom.getFixture() != null)
				 {
					 Vector2 pos = atom.getPosition();
					 
					 if (pos.x >= SCREEN_MANAGER.DEVICE_WIDTH + 100 || pos.x < -100)
					 {
						 outOfBoundsIndexes.add(Integer.valueOf(j));
					 }
					 else if (pos.y >= SCREEN_MANAGER.DEVICE_HEIGHT + 100 || pos.y < -100)
					 {
						 outOfBoundsIndexes.add(Integer.valueOf(j));
					 }
				 }
				 else
				 {
					 // adds the null reference to be removed
					 outOfBoundsIndexes.add(Integer.valueOf(j));
				 }
			 }
			 
			 // iterate over the particle list of out of bounds particles
			 for (int n = 0; n < outOfBoundsIndexes.size(); n++)
			 {
				 int index = (int) outOfBoundsIndexes.get(n);
				 
				 if (PARTICLES[index] != null)
				 {
					// verify that the particle is selected
					 if (PARTICLES[index].getSelected())
					 {
						 // check if the ids match with the first selection
						 if (controller.getSelection() != null)
						 {
							 controller.resetSelection();
						 }
					 }
					 
					 // destory object data
					 PARTICLES[index].destroy();
					 
					// remove the value at the index held in the outOfBounds list
					 PARTICLES[index] = null;
				 }
			 }
			 
			// increment the timer
			 timer++;
			 
			// reset the timer
			 if (timer > 150)
			 {
				 timer = 0;
			 }
			 
			 // check if the multiplier time is greater than 0
			 // if so decrement it
			 if (controller.getMultiplierTime() > 0)
			 {
				 controller.setMultipliertime(controller.getMultiplierTime() - 1);
			 }
			 else if ((int) controller.getMultiplier() > 1)
			 {
				 // reset the multiplier
				 controller.setMultiplier((int) controller.getMultiplier() - 1);
				 
				 // if the multiplier falls below 1 reset it to 1
				 if (controller.getMultiplier() <= 0)
				 {
					 controller.setMultiplier(1);
				 }
			 }
			 
			 // decrement bonus message meter
			 if (BONUS > 0)
			 {
				 BONUS -= 50;
			 }
			 
			 // check the decay time
			 if (controller.getTime() < 0)
			 {
				 // set the game to end
				 SCREEN_MANAGER.splashScreen.gameOver = true;
				 
				 // game over
				 SCREEN_MANAGER.setScreen(SCREEN_MANAGER.splashScreen);
				 
			 }		// check if the target score has been reached
			 else if (controller.getScore() >= TARGET_SCORE)
			 {
				 // play level complete sfx
				 SCREEN_MANAGER.levelComplete.play();
				 
				 // increment the level
				 SCREEN_MANAGER.LEVEL++;
				 
				// set the game to end
				 SCREEN_MANAGER.splashScreen.gameOver = false;
				 
				 // show the splash screen
				 SCREEN_MANAGER.setScreen(SCREEN_MANAGER.splashScreen);
			 }
			 else
			 {
				 // set how fast the time is reduced
				 int decayTime = (SCREEN_MANAGER.LEVEL / 10);
				 
				 if (decayTime < 1)
				 {
					 decayTime = 1;
				 }
				 
				// decrement the decayTime
				controller.updateTime(controller.getTime() - decayTime);
				
				if (controller.getTime() <= 300)
				{
					if (controller.getTime() % 30 == 0)
					{
						if (SCREEN_MANAGER.alarmSound != null)
						{
							// play low time sfx
							SCREEN_MANAGER.alarmSound.play();
						}
					}
				}
			 }
			 
		 }
		 
		 // update the camera
		 CAMERA.update();
		 
		// debugger.render(WORLD, CAMERA.combined);
		 
		 // step the world
		 WORLD.step(1/60, 6, 2);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {

		if (SCREEN_MANAGER.sceneMusic != null)
		{
			// begin playing the menu sound
			SCREEN_MANAGER.sceneMusic.setLooping(true);
			SCREEN_MANAGER.sceneMusic.play();
		}
		
		// initialize the camera
		CAMERA = GameManager.getCamera(SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		
		// initialize the touch vector
		TOUCH_POSITION = new Vector3[2];
		
		// create the shaperender object for the time meter
		shapeRender = new ShapeRenderer();
		
		// create the sprite batches
		batch =  batch2 = new SpriteBatch(1024, 2);
		
		pauseButtonWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.07f);
		pauseButtonHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.1f);
		
		// setup the pauseButton frame
		pauseButton = GameManager.getRect(SCREEN_MANAGER.DEVICE_WIDTH - pauseButtonWidth, SCREEN_MANAGER.DEVICE_HEIGHT - pauseButtonHeight, pauseButtonWidth, pauseButtonHeight);
		
		// create the font object
		FONT = GameManager.getBitmapFont(Color.WHITE, 2.0f);
		SUB_FONT = GameManager.getBitmapFont(Color.WHITE, 0.25f);
		
		// set the proportionate height and width for the hud objects 
		timeMeterHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.1f);
		timeMeterWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.7f);
		meterWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.675f);
		selectedItemHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.08f);
		selectedItemWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.05f);
		scoreX = 10;
		scoreY = SCREEN_MANAGER.DEVICE_HEIGHT - 20;
		
		// initialize the list of particles and elements generated in the world
		PARTICLES = new SubAtomic[10];
		
		// initialize the box2d world object
		WORLD = new World(new Vector2(0, -1),  true);
		
		// setup the game controller
		controller = SCREEN_MANAGER.CONTROLLER;
		
		// pass in the controller object
		SCREEN_MANAGER.splashScreen.CONTROLLER = controller;
		
		debugger = new Box2DDebugRenderer();
		
		// initialize the factory locations
		factoryLocations =  new Vector2[] {new Vector2(-96, (SCREEN_MANAGER.DEVICE_HEIGHT + 96)), 
				  new Vector2(-96, -96), 
				  new Vector2((SCREEN_MANAGER.DEVICE_WIDTH + 96), (SCREEN_MANAGER.DEVICE_HEIGHT + 96)), 
				  new Vector2((SCREEN_MANAGER.DEVICE_WIDTH + 96), -96)};
		
		// initialize the particle factory class, for generating the particles
		PARTICLE_FACTORY = new ParticleFactory[4];
		
		for (int n = 0; n < PARTICLE_FACTORY.length; n++)
		{
			// particle factor locations vector
			Vector2 pfLocs = factoryLocations[n];
			
			// create factory object
			ParticleFactory factory = new ParticleFactory(WORLD, Box2D.STATIC_BODY, pfLocs.x, pfLocs.y, 0, 0, 0, Box2D.getPolygon(64, 64, new Vector2(64, 64)));
			factory.setManager(SCREEN_MANAGER);
			
			// add the factory the the factory array
			PARTICLE_FACTORY[n] = factory;
		}
		
		// create the screen boundaries
		leftWall = new BoxActor(WORLD, Box2D.STATIC_BODY, -24, 0, 0, 0, 1, Box2D.getPolygon(64, SCREEN_MANAGER.DEVICE_HEIGHT * 2.5f));
		rightWall = new BoxActor(WORLD, Box2D.STATIC_BODY, (SCREEN_MANAGER.DEVICE_WIDTH + 24), 0, 0, 0, 1, Box2D.getPolygon(64, SCREEN_MANAGER.DEVICE_HEIGHT * 2.5f));
		topWall = new BoxActor(WORLD, Box2D.STATIC_BODY, 0, (SCREEN_MANAGER.DEVICE_HEIGHT + 24), 0, 0, 1, Box2D.getPolygon(((SCREEN_MANAGER.DEVICE_WIDTH * 2.5f) + 96), 64));
		bottomWall = new BoxActor(WORLD, Box2D.STATIC_BODY, 0, -24, 0, 0, 1, Box2D.getPolygon(((SCREEN_MANAGER.DEVICE_WIDTH * 2.5f) + 96), 64));
		
		// set target score
		TARGET_SCORE = (int) ((SCREEN_MANAGER.LEVEL * 1000) * (SCREEN_MANAGER.LEVEL / 2.0f));
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		
		PAUSED = true;
		
		if (SCREEN_MANAGER.sceneMusic != null)
		{
			// begin playing the menu sound
			SCREEN_MANAGER.sceneMusic.pause();
		}
		
	}

	@Override
	public void resume() {
		
		PAUSED = false;
		
		if (SCREEN_MANAGER.sceneMusic != null)
		{
			// begin playing the menu sound
			SCREEN_MANAGER.sceneMusic.play();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
