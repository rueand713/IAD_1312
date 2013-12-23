package com.randerson.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.randerson.entities.PlayerHandler;
import com.randerson.fusion.FusionScreenManager;
import com.randerson.fusion.GameManager;

public class MainMenu implements Screen {

	SpriteBatch batch;
	FusionScreenManager SCREEN_MANAGER;
	OrthographicCamera CAMERA;
	Vector3 TOUCH_POSITION;
	int buttonHeight;
	int buttonWidth;
	int lbuttonWidth;
	int lbuttonHeight;
	boolean touched = false;
	Rectangle achievementsButton;
	Rectangle playButton;
	Rectangle credsButton;
	Rectangle helpButton;
	Rectangle leaderboardButton;
	Rectangle leaderboardToggleButton;
	TextureRegion leaderboard;
	boolean RESET = false;
	boolean networkplay;
	boolean disableTouch = true;
	int disableCount = 60;
	
	
	// class constructor
	public MainMenu(FusionScreenManager manager)
	{
		SCREEN_MANAGER = manager;
	}
	
	@Override
	public void render(float delta) {
		
		 // clear the screen
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		 // check if networkplay is on or off
		 if (SCREEN_MANAGER.defaults != null)
		 {
			 networkplay = SCREEN_MANAGER.defaults.getData().getBoolean("networkplay", false);
			 
			 // set the texture to the networkplay on or off
			 if (networkplay)
			 {
				 leaderboard = SCREEN_MANAGER.leaderboardEnabled;
			 }
			 else
			 {
				 leaderboard = SCREEN_MANAGER.leaderboardDisabled;
			 }
		 }
		 
		// begin rendering
		batch.setProjectionMatrix(CAMERA.combined);
		batch.begin();
		batch.draw(SCREEN_MANAGER.titleScreen, 0, 0, SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		batch.draw(SCREEN_MANAGER.playButton, playButton.x, playButton.y, playButton.width, playButton.height);
		batch.draw(SCREEN_MANAGER.leaderboardButton, leaderboardButton.x, leaderboardButton.y, leaderboardButton.width, leaderboardButton.height);
		batch.draw(SCREEN_MANAGER.helpButton, helpButton.x, helpButton.y, helpButton.width, helpButton.height);
		batch.draw(SCREEN_MANAGER.creditsButton, credsButton.x, credsButton.y, credsButton.width, credsButton.height);
		batch.draw(SCREEN_MANAGER.achievementsButton, achievementsButton.x, achievementsButton.y, achievementsButton.width, achievementsButton.height);
		
		if (leaderboard != null)
		{
			batch.draw(leaderboard, leaderboardToggleButton.x, leaderboardToggleButton.y, leaderboardToggleButton.width, leaderboardToggleButton.height);
		}
		
		batch.end();
		
		CAMERA.update();
		
		// check for a screen touch
		if (Gdx.input.isTouched(0) || Gdx.input.justTouched())
		{
			if (disableTouch == false)
			{
				TOUCH_POSITION = GameManager.getTouchVector(0);
				CAMERA.unproject(TOUCH_POSITION);
				
				// set the bool for tracking a touch true
				touched = true;
				
				// reset the counter
				disableCount = 60;
			}
		}
		else
		{
			//  no touch set the touch tracking bool to false
			touched = false;
		}
		
		// check if a touch was registered to check for button click
		if (touched && disableTouch == false)
		{
			// disable touch events
			disableTouch = true;
			
			// create a vector2 from the touch position
			Vector2 touch = GameManager.getVect2(TOUCH_POSITION);
			
			// check if the user touched any of the buttons
			if (GameManager.overlaps(touch, playButton, true))
			{
				if (SCREEN_MANAGER.titleMusic != null)
				{
					// begin playing the menu sound
					SCREEN_MANAGER.titleMusic.stop();
				}
				
				SCREEN_MANAGER.setScreen(SCREEN_MANAGER.actOne);
			}
			else if (GameManager.overlaps(touch, helpButton, true))
			{
				SCREEN_MANAGER.setScreen(SCREEN_MANAGER.tutorial);
			}
			else if (GameManager.overlaps(touch, credsButton, true))
			{
				SCREEN_MANAGER.setScreen(SCREEN_MANAGER.credits);
			}
			else if (GameManager.overlaps(touch, leaderboardButton, true))
			{
				// get the leaderboard data and display it
				fetchLeaderboard();
			}
			else if (GameManager.overlaps(touch, achievementsButton, true))
			{
				SCREEN_MANAGER.setScreen(SCREEN_MANAGER.achievementScreen);
			}
			else if (GameManager.overlaps(touch, leaderboardToggleButton, true))
			{
				if (SCREEN_MANAGER.defaults != null)
				{
					CharSequence message = "";
					
					if (SCREEN_MANAGER.defaults.getData().getBoolean("networkplay", false))
					{
						// if it is true already, toggle it to false
						SCREEN_MANAGER.defaults.setBool("networkplay", false);
						
						// set the message
						message = "Leaderboards have been disabled";
					}
					else
					{
						// if it is false already, toggle it to true
						SCREEN_MANAGER.defaults.setBool("networkplay", true);
						
						// set the message
						message = "Leaderboards have been enabled";
					}
					
					
					// create a new toast to inform the user
					SCREEN_MANAGER.Android.showToast(message);
				}
			}
		}
		
		// decrement the counter
		if (disableCount > 0)
		{
			disableCount--;
		}
		else
		{
			// re enable touch events
			disableTouch = false;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		// get the device width and height
		SCREEN_MANAGER.DEVICE_HEIGHT = GameManager.getHeight(true);
		SCREEN_MANAGER.DEVICE_WIDTH = GameManager.getWidth(true);
		
		// init the camera
		CAMERA = GameManager.getCamera(SCREEN_MANAGER.DEVICE_WIDTH, SCREEN_MANAGER.DEVICE_HEIGHT);
		
		// create the sprite batch
		batch = new SpriteBatch();
		
		// get the button sizes proportionate to the device screen
		buttonWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.4f);
		buttonHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.125f);
		lbuttonWidth = (int) (SCREEN_MANAGER.DEVICE_WIDTH * 0.1f);
		lbuttonHeight = (int) (SCREEN_MANAGER.DEVICE_HEIGHT * 0.1f);
		
		// setup the screen buttons boundaries
		playButton = new Rectangle(20, 200 + buttonHeight, buttonWidth, buttonHeight);
		credsButton = new Rectangle(SCREEN_MANAGER.DEVICE_WIDTH - (buttonWidth + 20), 200 + buttonHeight, buttonWidth, buttonHeight);
		leaderboardButton = new Rectangle(20, 100 + buttonHeight, buttonWidth, buttonHeight);
		helpButton = new Rectangle(SCREEN_MANAGER.DEVICE_WIDTH - (buttonWidth + 20), 100 + buttonHeight, buttonWidth, buttonHeight);;
		leaderboardToggleButton = new Rectangle(SCREEN_MANAGER.DEVICE_WIDTH - (lbuttonWidth + 20), 0, lbuttonWidth, lbuttonHeight);
		achievementsButton = new Rectangle(20, buttonHeight, buttonWidth, buttonHeight);
		
		// create the touch object
		TOUCH_POSITION = new Vector3();
		
		// verify that the assets have not been previously loaded
		if (SCREEN_MANAGER.gameLoaded == false)
		{
			// load up all of the games assets in one swoop
			// load the atlases and textures
			SCREEN_MANAGER.CONTROLLER = new PlayerHandler(9000, 1);
			SCREEN_MANAGER.titleScreen = GameManager.getTexture("TitleScreen.png");
			SCREEN_MANAGER.tutorialImage = GameManager.getTexture("Help_Screen.png");
			SCREEN_MANAGER.bgImage1 = GameManager.getTexture("Bg1.png");
			SCREEN_MANAGER.splashImage = GameManager.getTexture("Splash_Screen.png");
			SCREEN_MANAGER.creditsImage = GameManager.getTexture("Credits_Screen.png");
			SCREEN_MANAGER.gameTextures = GameManager.getAtlas("Textures.atlas");
			SCREEN_MANAGER.uiTextures = GameManager.getAtlas("UI.atlas");
			SCREEN_MANAGER.awardTextures = GameManager.getAtlas("Achievements.atlas");
			SCREEN_MANAGER.blankAcheivement = SCREEN_MANAGER.awardTextures.findRegion("Not_Earned");
			SCREEN_MANAGER.quitterAward = SCREEN_MANAGER.awardTextures.findRegion("Quitter");
			SCREEN_MANAGER.reached5xMultiplier = SCREEN_MANAGER.awardTextures.findRegion("Multiplier");
			SCREEN_MANAGER.joinedLeaderboard = SCREEN_MANAGER.awardTextures.findRegion("Signed_Up");
			SCREEN_MANAGER.reachedLevel5 = SCREEN_MANAGER.awardTextures.findRegion("Level5");
			SCREEN_MANAGER.reachedLevel10 = SCREEN_MANAGER.awardTextures.findRegion("Level10");
			SCREEN_MANAGER.reachedLevel25 = SCREEN_MANAGER.awardTextures.findRegion("Level25");
			SCREEN_MANAGER.reachedLevel50 = SCREEN_MANAGER.awardTextures.findRegion("Level50");
			SCREEN_MANAGER.reachedLevel75 = SCREEN_MANAGER.awardTextures.findRegion("Level75");
			SCREEN_MANAGER.reachedLevel100 = SCREEN_MANAGER.awardTextures.findRegion("Level100");
			SCREEN_MANAGER.earned10k = SCREEN_MANAGER.awardTextures.findRegion("10k");
			SCREEN_MANAGER.earned50k = SCREEN_MANAGER.awardTextures.findRegion("50k");
			SCREEN_MANAGER.earned200k = SCREEN_MANAGER.awardTextures.findRegion("200k");
			SCREEN_MANAGER.earned500k = SCREEN_MANAGER.awardTextures.findRegion("500k");
			SCREEN_MANAGER.earned1m = SCREEN_MANAGER.awardTextures.findRegion("1m");
			SCREEN_MANAGER.leaderboardButton = SCREEN_MANAGER.uiTextures.findRegion("Leaderboard_Button");
			SCREEN_MANAGER.achievementsButton = SCREEN_MANAGER.uiTextures.findRegion("Award_Button");
			SCREEN_MANAGER.backButton = SCREEN_MANAGER.uiTextures.findRegion("Back_Button");
			SCREEN_MANAGER.playButton = SCREEN_MANAGER.uiTextures.findRegion("Play_Button");
			SCREEN_MANAGER.helpButton = SCREEN_MANAGER.uiTextures.findRegion("Help_Button");
			SCREEN_MANAGER.creditsButton = SCREEN_MANAGER.uiTextures.findRegion("Credits_Button");
			SCREEN_MANAGER.shareButton = SCREEN_MANAGER.uiTextures.findRegion("Share_Button");
			SCREEN_MANAGER.pauseButton = SCREEN_MANAGER.uiTextures.findRegion("Pause_Button");
			SCREEN_MANAGER.timeMeterBack = SCREEN_MANAGER.uiTextures.findRegion("Time_Meter_Back");
			SCREEN_MANAGER.timeMeterFront = SCREEN_MANAGER.uiTextures.findRegion("Time_Meter_Front");
			SCREEN_MANAGER.leaderboardEnabled = SCREEN_MANAGER.gameTextures.findRegion("Yes_Leaderboard");
			SCREEN_MANAGER.leaderboardDisabled = SCREEN_MANAGER.gameTextures.findRegion("No_Leaderboard");
			SCREEN_MANAGER.proton = SCREEN_MANAGER.gameTextures.findRegion("Proton");
			SCREEN_MANAGER.neutron = SCREEN_MANAGER.gameTextures.findRegion("Neutron");
			SCREEN_MANAGER.electron = SCREEN_MANAGER.gameTextures.findRegion("Electron");
			SCREEN_MANAGER.upQuark = SCREEN_MANAGER.gameTextures.findRegion("Up_Quark");
			SCREEN_MANAGER.downQuark = SCREEN_MANAGER.gameTextures.findRegion("Down_Quark");
			SCREEN_MANAGER.hydrogen = SCREEN_MANAGER.gameTextures.findRegion("Hydrogen");
			SCREEN_MANAGER.nitrogen = SCREEN_MANAGER.gameTextures.findRegion("Nitrogen");
			SCREEN_MANAGER.oxygen = SCREEN_MANAGER.gameTextures.findRegion("Oxygen");
			SCREEN_MANAGER.fluorine = SCREEN_MANAGER.gameTextures.findRegion("Fluorine");
			SCREEN_MANAGER.helium = SCREEN_MANAGER.gameTextures.findRegion("Helium");
			SCREEN_MANAGER.neon = SCREEN_MANAGER.gameTextures.findRegion("Neon");
			SCREEN_MANAGER.argon = SCREEN_MANAGER.gameTextures.findRegion("Argon");
			SCREEN_MANAGER.krypton = SCREEN_MANAGER.gameTextures.findRegion("Krypton");
			SCREEN_MANAGER.xenon = SCREEN_MANAGER.gameTextures.findRegion("Xenon");
			SCREEN_MANAGER.radon = SCREEN_MANAGER.gameTextures.findRegion("Radon");
			SCREEN_MANAGER.chlorine = SCREEN_MANAGER.gameTextures.findRegion("Chlorine");
			SCREEN_MANAGER.mercury = SCREEN_MANAGER.gameTextures.findRegion("Mercury");
			SCREEN_MANAGER.bromine = SCREEN_MANAGER.gameTextures.findRegion("Bromine");
			SCREEN_MANAGER.cesium = SCREEN_MANAGER.gameTextures.findRegion("Cesium");
			SCREEN_MANAGER.francium = SCREEN_MANAGER.gameTextures.findRegion("Francium");
			SCREEN_MANAGER.gallium = SCREEN_MANAGER.gameTextures.findRegion("Gallium");
			SCREEN_MANAGER.titanium = SCREEN_MANAGER.gameTextures.findRegion("Titanium");
			SCREEN_MANAGER.iridium = SCREEN_MANAGER.gameTextures.findRegion("Iridium");
			SCREEN_MANAGER.molybdenum = SCREEN_MANAGER.gameTextures.findRegion("Molybdenum");
			SCREEN_MANAGER.protactinium = SCREEN_MANAGER.gameTextures.findRegion("Protactinium");
			SCREEN_MANAGER.thorium = SCREEN_MANAGER.gameTextures.findRegion("Thorium");
			SCREEN_MANAGER.actinium = SCREEN_MANAGER.gameTextures.findRegion("Actinium");
			SCREEN_MANAGER.lutetium = SCREEN_MANAGER.gameTextures.findRegion("Lutetium");
			SCREEN_MANAGER.ytterbium = SCREEN_MANAGER.gameTextures.findRegion("Ytterbium");
			SCREEN_MANAGER.thulium = SCREEN_MANAGER.gameTextures.findRegion("Thulium");
			SCREEN_MANAGER.erbium = SCREEN_MANAGER.gameTextures.findRegion("Erbium");
			SCREEN_MANAGER.holmium = SCREEN_MANAGER.gameTextures.findRegion("Holmium");
			SCREEN_MANAGER.dysprosium = SCREEN_MANAGER.gameTextures.findRegion("Dysprosium");
			SCREEN_MANAGER.terbium = SCREEN_MANAGER.gameTextures.findRegion("Terbium");
			SCREEN_MANAGER.gadolinium = SCREEN_MANAGER.gameTextures.findRegion("Gadolinium");
			SCREEN_MANAGER.europium = SCREEN_MANAGER.gameTextures.findRegion("Europium");
			SCREEN_MANAGER.samarium = SCREEN_MANAGER.gameTextures.findRegion("Samarium");
			SCREEN_MANAGER.neodymium = SCREEN_MANAGER.gameTextures.findRegion("Neodymium");
			SCREEN_MANAGER.praseodymium = SCREEN_MANAGER.gameTextures.findRegion("Praseodymium");
			SCREEN_MANAGER.cerium = SCREEN_MANAGER.gameTextures.findRegion("Cerium");
			SCREEN_MANAGER.lanthanum = SCREEN_MANAGER.gameTextures.findRegion("Lanthanum");
			SCREEN_MANAGER.cadmium = SCREEN_MANAGER.gameTextures.findRegion("Cadmium");
			SCREEN_MANAGER.zinc = SCREEN_MANAGER.gameTextures.findRegion("Zinc");
			SCREEN_MANAGER.gold = SCREEN_MANAGER.gameTextures.findRegion("Gold");
			SCREEN_MANAGER.silver = SCREEN_MANAGER.gameTextures.findRegion("Silver");
			SCREEN_MANAGER.platinum = SCREEN_MANAGER.gameTextures.findRegion("Platinum");
			SCREEN_MANAGER.palladium = SCREEN_MANAGER.gameTextures.findRegion("Palladiium");
			SCREEN_MANAGER.copper = SCREEN_MANAGER.gameTextures.findRegion("Copper");
			SCREEN_MANAGER.nickel = SCREEN_MANAGER.gameTextures.findRegion("Nickel");
			SCREEN_MANAGER.rhodium = SCREEN_MANAGER.gameTextures.findRegion("Rhodium");
			SCREEN_MANAGER.cobalt = SCREEN_MANAGER.gameTextures.findRegion("Cobalt");
			SCREEN_MANAGER.osmium = SCREEN_MANAGER.gameTextures.findRegion("Osmium");
			SCREEN_MANAGER.ruthenium = SCREEN_MANAGER.gameTextures.findRegion("Ruthenium");
			SCREEN_MANAGER.iron = SCREEN_MANAGER.gameTextures.findRegion("Iron");
			SCREEN_MANAGER.rhenium = SCREEN_MANAGER.gameTextures.findRegion("Rhenium");
			SCREEN_MANAGER.manganese = SCREEN_MANAGER.gameTextures.findRegion("Manganese");
			SCREEN_MANAGER.chromium = SCREEN_MANAGER.gameTextures.findRegion("Chromium");
			SCREEN_MANAGER.tantalum = SCREEN_MANAGER.gameTextures.findRegion("Tantalum");
			SCREEN_MANAGER.niobium = SCREEN_MANAGER.gameTextures.findRegion("Niobium");
			SCREEN_MANAGER.hafnium = SCREEN_MANAGER.gameTextures.findRegion("Hafnium");
			SCREEN_MANAGER.zirconium = SCREEN_MANAGER.gameTextures.findRegion("Zirconium");
			SCREEN_MANAGER.scandium = SCREEN_MANAGER.gameTextures.findRegion("Scandium");
			SCREEN_MANAGER.astatine = SCREEN_MANAGER.gameTextures.findRegion("Astatine");
			SCREEN_MANAGER.polonium = SCREEN_MANAGER.gameTextures.findRegion("Polonium");
			SCREEN_MANAGER.bismuth = SCREEN_MANAGER.gameTextures.findRegion("Bismuth");
			SCREEN_MANAGER.lead = SCREEN_MANAGER.gameTextures.findRegion("Lead");
			SCREEN_MANAGER.thallium = SCREEN_MANAGER.gameTextures.findRegion("Thallium");
			SCREEN_MANAGER.tellurium = SCREEN_MANAGER.gameTextures.findRegion("Tellurium");
			SCREEN_MANAGER.antimony = SCREEN_MANAGER.gameTextures.findRegion("Antimony");
			SCREEN_MANAGER.tin = SCREEN_MANAGER.gameTextures.findRegion("Tin");
			SCREEN_MANAGER.indium = SCREEN_MANAGER.gameTextures.findRegion("Indium");
			SCREEN_MANAGER.selenium = SCREEN_MANAGER.gameTextures.findRegion("Selenium");
			SCREEN_MANAGER.arsenic = SCREEN_MANAGER.gameTextures.findRegion("Arsenic");
			SCREEN_MANAGER.germanium = SCREEN_MANAGER.gameTextures.findRegion("Germanium");
			SCREEN_MANAGER.silicon = SCREEN_MANAGER.gameTextures.findRegion("Silicon");
			SCREEN_MANAGER.aluminum = SCREEN_MANAGER.gameTextures.findRegion("Aluminum");
			SCREEN_MANAGER.rubidium = SCREEN_MANAGER.gameTextures.findRegion("Rubidium");
			SCREEN_MANAGER.radium = SCREEN_MANAGER.gameTextures.findRegion("Radium");
			SCREEN_MANAGER.barium = SCREEN_MANAGER.gameTextures.findRegion("Barium");
			SCREEN_MANAGER.strontium = SCREEN_MANAGER.gameTextures.findRegion("Strontium");
			SCREEN_MANAGER.calcium = SCREEN_MANAGER.gameTextures.findRegion("Calcium");
			SCREEN_MANAGER.magnesium = SCREEN_MANAGER.gameTextures.findRegion("Magnesium");
			SCREEN_MANAGER.sodium = SCREEN_MANAGER.gameTextures.findRegion("Sodium");
			SCREEN_MANAGER.beryllium = SCREEN_MANAGER.gameTextures.findRegion("Beryllium");
			SCREEN_MANAGER.lithium = SCREEN_MANAGER.gameTextures.findRegion("Lithium");
			SCREEN_MANAGER.tungsten = SCREEN_MANAGER.gameTextures.findRegion("Tungsten");
			SCREEN_MANAGER.uranium = SCREEN_MANAGER.gameTextures.findRegion("Uranium");
			SCREEN_MANAGER.vanadium = SCREEN_MANAGER.gameTextures.findRegion("Vanadium");
			SCREEN_MANAGER.yttrium = SCREEN_MANAGER.gameTextures.findRegion("Yttrium");
			SCREEN_MANAGER.potassium = SCREEN_MANAGER.gameTextures.findRegion("Potassium");
			SCREEN_MANAGER.iodine = SCREEN_MANAGER.gameTextures.findRegion("Iodine");
			SCREEN_MANAGER.sulfur = SCREEN_MANAGER.gameTextures.findRegion("Sulfur");
			SCREEN_MANAGER.phosphorus = SCREEN_MANAGER.gameTextures.findRegion("Phosphorus");
			SCREEN_MANAGER.boron = SCREEN_MANAGER.gameTextures.findRegion("Boron");
			SCREEN_MANAGER.carbon = SCREEN_MANAGER.gameTextures.findRegion("Carbon");
			SCREEN_MANAGER.promethium = SCREEN_MANAGER.gameTextures.findRegion("Promethium");
			SCREEN_MANAGER.neptunium = SCREEN_MANAGER.gameTextures.findRegion("Neptunium");
			SCREEN_MANAGER.plutonium = SCREEN_MANAGER.gameTextures.findRegion("Plutonium");
			SCREEN_MANAGER.americium = SCREEN_MANAGER.gameTextures.findRegion("Americium");
			SCREEN_MANAGER.curium = SCREEN_MANAGER.gameTextures.findRegion("Curium");
			SCREEN_MANAGER.berkelium = SCREEN_MANAGER.gameTextures.findRegion("Berkelium");
			SCREEN_MANAGER.californium = SCREEN_MANAGER.gameTextures.findRegion("Californium");
			SCREEN_MANAGER.einsteinium = SCREEN_MANAGER.gameTextures.findRegion("Einsteinium");
			SCREEN_MANAGER.fermium = SCREEN_MANAGER.gameTextures.findRegion("Fermium");
			SCREEN_MANAGER.mendelevium = SCREEN_MANAGER.gameTextures.findRegion("Mendelevium");
			SCREEN_MANAGER.nobelium = SCREEN_MANAGER.gameTextures.findRegion("Nobelium");
			SCREEN_MANAGER.lawrencium = SCREEN_MANAGER.gameTextures.findRegion("Lawrencium");
			SCREEN_MANAGER.rutherfordium = SCREEN_MANAGER.gameTextures.findRegion("Rutherfordium");
			SCREEN_MANAGER.dubnium = SCREEN_MANAGER.gameTextures.findRegion("Dubnium");
			SCREEN_MANAGER.seaborgium = SCREEN_MANAGER.gameTextures.findRegion("Seaborgium");
			SCREEN_MANAGER.bohrium = SCREEN_MANAGER.gameTextures.findRegion("Bohrium");
			SCREEN_MANAGER.hassium = SCREEN_MANAGER.gameTextures.findRegion("Hassium");
			SCREEN_MANAGER.meitnerium = SCREEN_MANAGER.gameTextures.findRegion("Meitnerium");
			SCREEN_MANAGER.darmstadtium = SCREEN_MANAGER.gameTextures.findRegion("Darmstadtium");
			SCREEN_MANAGER.roentgenium = SCREEN_MANAGER.gameTextures.findRegion("Roentgenium");
			SCREEN_MANAGER.copernicium = SCREEN_MANAGER.gameTextures.findRegion("Copernicium");
			SCREEN_MANAGER.livermorium = SCREEN_MANAGER.gameTextures.findRegion("Livermorium");
			SCREEN_MANAGER.technetium = SCREEN_MANAGER.gameTextures.findRegion("Technetium");
			SCREEN_MANAGER.flerovium = SCREEN_MANAGER.gameTextures.findRegion("Flerovium");
			SCREEN_MANAGER.ununtrium = SCREEN_MANAGER.gameTextures.findRegion("Ununtrium");
			SCREEN_MANAGER.ununseptium = SCREEN_MANAGER.gameTextures.findRegion("Ununseptium");
			SCREEN_MANAGER.ununoctium = SCREEN_MANAGER.gameTextures.findRegion("Ununoctium");
			SCREEN_MANAGER.ununpentium = SCREEN_MANAGER.gameTextures.findRegion("Ununpentium");
			
			// load up the game sounds
			SCREEN_MANAGER.sceneMusic = GameManager.getMusic("BGM.mp3");
			SCREEN_MANAGER.titleMusic = GameManager.getMusic("Title_Sound.mp3");
			SCREEN_MANAGER.alarmSound = GameManager.getSound("Time_Alarm.mp3");
			SCREEN_MANAGER.bonusSound = GameManager.getSound("Bonus_Sound.mp3");
			SCREEN_MANAGER.clickSound = GameManager.getSound("Menu_Click.mp3");
			SCREEN_MANAGER.particleSound = GameManager.getSound("Particle_Combine.mp3");
			SCREEN_MANAGER.levelComplete = GameManager.getSound("Level_Complete.mp3");
			
			// set the gameloaded bool to true
			SCREEN_MANAGER.gameLoaded = true;
			
			loadPlayerData();
		}
		
		if (SCREEN_MANAGER.titleMusic != null)
		{
			// begin playing the menu sound
			SCREEN_MANAGER.titleMusic.setLooping(true);
			SCREEN_MANAGER.titleMusic.play();
		}
		
		// to ease accidental touching events
		disableTouch = true;
		disableCount = 90;
		
		// check if networkplay is on or off
		 if (SCREEN_MANAGER.defaults != null)
		 {
			 networkplay = SCREEN_MANAGER.defaults.getData().getBoolean("networkplay", false);
			 
			 // set the texture to the networkplay on or off
			 if (networkplay)
			 {
				 leaderboard = SCREEN_MANAGER.leaderboardEnabled;
			 }
			 else
			 {
				 leaderboard = SCREEN_MANAGER.leaderboardDisabled;
			 }
		 }
	}
	
	public void fetchLeaderboard()
	{
		// turn off the sounds
		SCREEN_MANAGER.titleMusic.stop();
		
		// show the leaderboard
		SCREEN_MANAGER.Android.startIntent(SCREEN_MANAGER.leaderboardView);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		
		if (SCREEN_MANAGER.titleMusic != null)
		{
			// begin playing the menu sound
			SCREEN_MANAGER.titleMusic.pause();
		}
	}

	@Override
	public void resume() {

		if (SCREEN_MANAGER.titleMusic != null)
		{
			// begin playing the menu sound
			SCREEN_MANAGER.titleMusic.play();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void reset()
	{
		// reset the player data object
		SCREEN_MANAGER.CONTROLLER = new PlayerHandler(9000, 1);
		
		// reload the achievements
		loadPlayerData();
	}
	
	public void loadPlayerData()
	{
		// load the saved achievement data
		boolean lv5 = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_5", false);
		boolean lv10 = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_10", false);
		boolean lv25 = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_25", false);
		boolean lv50 = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_50", false);
		boolean lv75 = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_75", false);
		boolean lv100 = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_100", false);
		boolean _10k = SCREEN_MANAGER.defaults.getData().getBoolean("earned_10k_points", false);
		boolean _50k = SCREEN_MANAGER.defaults.getData().getBoolean("earned_50k_points", false);
		boolean _200k = SCREEN_MANAGER.defaults.getData().getBoolean("earned_200k_points", false);
		boolean _500k = SCREEN_MANAGER.defaults.getData().getBoolean("earned_500k_points", false);
		boolean _1m = SCREEN_MANAGER.defaults.getData().getBoolean("earned_1m_points", false);
		boolean _5x = SCREEN_MANAGER.defaults.getData().getBoolean("reached_level_100", false);
		boolean signedUp = SCREEN_MANAGER.defaults.getData().getBoolean("joined_leaderboard", false);
		boolean quitter = SCREEN_MANAGER.defaults.getData().getBoolean("failed_to_try", false);
		
		// set the data to the controller
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_level_5").obtained = lv5;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_level_10").obtained = lv10;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_level_25").obtained = lv25;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_level_50").obtained = lv50;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_level_75").obtained = lv75;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_level_100").obtained = lv100;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("earned_10k_points").obtained = _10k;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("earned_50k_points").obtained = _50k;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("earned_200k_points").obtained = _200k;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("earned_500k_points").obtained = _500k;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("earned_1m_points").obtained = _1m;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("reached_5x_multiplier").obtained = _5x;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("joined_leaderboard").obtained = signedUp;
		SCREEN_MANAGER.CONTROLLER.getAchievements().get("failed_to_try").obtained = quitter;
		
		// load the lifetime score
		int score = SCREEN_MANAGER.defaults.getData().getInt("lifetime_score", 0);
		
		// set the score to the controller
		SCREEN_MANAGER.CONTROLLER.setLifeTimeScore(score);
	}

}
