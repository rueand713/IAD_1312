package com.randerson.fusion;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.randerson.entities.PlayerHandler;
import com.randerson.kinvey.LeaderboardEntity;
import com.randerson.levels.ActOne;
import com.randerson.levels.Awards;
import com.randerson.levels.Credits;
import com.randerson.levels.MainMenu;
import com.randerson.levels.SplashScreen;
import com.randerson.levels.Tutorial;
import com.randerson.twitter4j.TwitterAccount;

public class FusionScreenManager extends Game {
	
	// create the screen objects
	public Tutorial tutorial;
	public MainMenu mainMenu;
	public ActOne actOne;
	public Credits credits;
	public SplashScreen splashScreen;
	public Awards achievementScreen;
	
	// create the game graphics objects
	
	// elementary particles
	public TextureRegion upQuark;
	public TextureRegion downQuark;
	public TextureRegion proton;
	public TextureRegion neutron;
	public TextureRegion electron;
	
	// liquids & gases
	public TextureRegion hydrogen;
	public TextureRegion nitrogen;
	public TextureRegion oxygen;
	public TextureRegion fluorine;
	public TextureRegion helium;
	public TextureRegion neon;
	public TextureRegion argon;
	public TextureRegion krypton;
	public TextureRegion xenon;
	public TextureRegion radon;
	public TextureRegion chlorine;
	public TextureRegion mercury;
	public TextureRegion bromine;
	public TextureRegion cesium;
	public TextureRegion francium;
	public TextureRegion gallium;
	
	// solids
	public TextureRegion titanium;
	public TextureRegion iridium;
	public TextureRegion protactinium;
	public TextureRegion thorium;
	public TextureRegion actinium;
	public TextureRegion lutetium;
	public TextureRegion ytterbium;
	public TextureRegion thulium;
	public TextureRegion erbium;
	public TextureRegion holmium;
	public TextureRegion dysprosium;
	public TextureRegion terbium;
	public TextureRegion gadolinium;
	public TextureRegion europium;
	public TextureRegion samarium;
	public TextureRegion neodymium;
	public TextureRegion praseodymium;
	public TextureRegion cerium;
	public TextureRegion lanthanum;
	public TextureRegion cadmium;
	public TextureRegion zinc;
	public TextureRegion gold;
	public TextureRegion silver;
	public TextureRegion copper;
	public TextureRegion platinum;
	public TextureRegion palladium;
	public TextureRegion nickel;
	public TextureRegion rhodium;
	public TextureRegion cobalt;
	public TextureRegion osmium;
	public TextureRegion ruthenium;
	public TextureRegion iron;
	public TextureRegion rhenium;
	public TextureRegion manganese;
	public TextureRegion chromium;
	public TextureRegion tantalum;
	public TextureRegion niobium;
	public TextureRegion hafnium;
	public TextureRegion zirconium;
	public TextureRegion scandium;
	public TextureRegion astatine;
	public TextureRegion polonium;
	public TextureRegion bismuth;
	public TextureRegion lead;
	public TextureRegion thallium;
	public TextureRegion tellurium;
	public TextureRegion molybdenum;
	public TextureRegion antimony;
	public TextureRegion tin;
	public TextureRegion indium;
	public TextureRegion selenium;
	public TextureRegion arsenic;
	public TextureRegion germanium;
	public TextureRegion silicon;
	public TextureRegion aluminum;
	public TextureRegion rubidium;
	public TextureRegion radium;
	public TextureRegion barium;
	public TextureRegion strontium;
	public TextureRegion calcium;
	public TextureRegion magnesium;
	public TextureRegion sodium;
	public TextureRegion beryllium;
	public TextureRegion lithium;
	public TextureRegion tungsten;
	public TextureRegion uranium;
	public TextureRegion vanadium;
	public TextureRegion yttrium;
	public TextureRegion potassium;
	public TextureRegion iodine;
	public TextureRegion sulfur;
	public TextureRegion phosphorus;
	public TextureRegion boron;
	public TextureRegion carbon;
	
	// synthetics
	public TextureRegion rutherfordium;
	public TextureRegion dubnium;
	public TextureRegion seaborgium;
	public TextureRegion bohrium;
	public TextureRegion hassium;
	public TextureRegion meitnerium;
	public TextureRegion darmstadtium;
	public TextureRegion roentgenium;
	public TextureRegion copernicium;
	public TextureRegion ununtrium;
	public TextureRegion flerovium;
	public TextureRegion ununpentium;
	public TextureRegion livermorium;
	public TextureRegion ununseptium;
	public TextureRegion ununoctium;
	public TextureRegion technetium;
	public TextureRegion promethium;
	public TextureRegion neptunium;
	public TextureRegion plutonium;
	public TextureRegion americium;
	public TextureRegion curium;
	public TextureRegion berkelium;
	public TextureRegion californium;
	public TextureRegion einsteinium;
	public TextureRegion fermium;
	public TextureRegion mendelevium;
	public TextureRegion nobelium;
	public TextureRegion lawrencium;
	
	// hud and atlas objects
	public TextureRegion playButton;
	public TextureRegion helpButton;
	public TextureRegion backButton;
	public TextureRegion shareButton;
	public TextureRegion creditsButton;
	public TextureRegion pauseButton;
	public TextureRegion achievementsButton;
	public TextureRegion timeMeterFront;
	public TextureRegion timeMeterBack;
	public TextureRegion leaderboardEnabled;
	public TextureRegion leaderboardDisabled;
	public TextureRegion leaderboardButton;
	public TextureRegion blankAcheivement;
	public TextureRegion reachedLevel5;
	public TextureRegion reachedLevel10;
	public TextureRegion reachedLevel25;
	public TextureRegion reachedLevel50;
	public TextureRegion reachedLevel75;
	public TextureRegion reachedLevel100;
	public TextureRegion earned10k;
	public TextureRegion earned50k;
	public TextureRegion earned200k;
	public TextureRegion earned500k;
	public TextureRegion earned1m;
	public TextureRegion joinedLeaderboard;
	public TextureRegion quitterAward;
	public TextureRegion reached5xMultiplier;
	public Texture bgImage1;
	public Texture splashImage;
	public Texture titleScreen;
	public Texture tutorialImage;
	public Texture creditsImage;
	public Animation particleAnimations;
	public TextureAtlas gameTextures;
	public TextureAtlas uiTextures;
	public TextureAtlas awardTextures;
	
	// android application context and class reference
	public Activity CONTEXT;
	public HashMap<String, String> APP_CREDS;
	public ApplicationDefaults defaults;
	public boolean leaderboardsEnabled = true;
	public AsyncAppData<LeaderboardEntity> leaderboard;
	public AndroidExtender Android;
	public TwitterAccount Twitter;
	
	// game objects
	public PlayerHandler CONTROLLER;
	public boolean gameLoaded = false;
	public int DEVICE_WIDTH;
	public int DEVICE_HEIGHT;
	public int LEVEL = 1;
	
	public Music sceneMusic;
	public Music titleMusic;
	public Sound alarmSound;
	public Sound bonusSound;
	public Sound clickSound;
	public Sound particleSound;
	public Sound levelComplete;
	
	// Kinvey client
	public Client kinveyClient;
	
	// android objects
	public Intent leaderboardView;
	
	public FusionScreenManager(Activity context, HashMap<String, String> creds, boolean networkPlay, Intent leaderboardScreen)
	{
		CONTEXT = context;
		APP_CREDS = creds;
		
		// setup the twitter account class
		//Twitter = new TwitterAccount(CONTEXT);
		
		// setup the prefs object
		defaults = new ApplicationDefaults(CONTEXT);
		
		// set the leaderboard status
		leaderboardsEnabled = networkPlay;
		
		// set the leaderboard pointer
		leaderboardView = leaderboardScreen;
		
		// set android reference
		Android = (AndroidExtender) context;
		
		// initialize the kinvey client and appData objects
		kinveyClient = new Client.Builder((String) creds.get("appKey"), (String) creds.get("appSecret"), context).build();
		leaderboard = kinveyClient.appData("Leaderboards", LeaderboardEntity.class);
	}
	
	@Override
	public void create() {
		
		// initialize all of the game scenes
		tutorial = new Tutorial(this);
		mainMenu = new MainMenu(this);
		actOne = new ActOne(this);
		credits = new Credits(this);
		splashScreen = new SplashScreen(this);
		achievementScreen = new Awards(this);
		
		if (leaderboardsEnabled == true)
		{
			// sign into kinvey account
			signIn();
		}
		else
		{
			// show the main screen
			setScreen(mainMenu);
		}
	}
	
	public void resetAct()
	{
		actOne = new ActOne(this);
	}
	
	public void resetGame()
	{
		// reset the level to 1
		LEVEL = 1;
		
		// reset the objects in the main screen
		// and reset the player data controller object
		mainMenu.reset();
		
		// reset the first level objects
		resetAct();
	}
	
	public void signIn()
	{
		
		// verify that the prefs object is valid
		if (defaults != null)
		{
			// get the kinvey account credentials
			final String username = defaults.getData().getString("username", null);
			final String password = defaults.getData().getString("password", null);
			
			// verify there are valid credentials
			if (username != null && password != null)
			{
				
				if (kinveyClient.user().isUserLoggedIn() == false)
				{
					// initiate a login request
					kinveyClient.user().login(username, password, new KinveyUserCallback() {
						
						@Override
						public void onSuccess(User arg0) {
							
							// create a new success message
							Toast msg = Toast.makeText(CONTEXT, "User: " + username + "Logged In", Toast.LENGTH_SHORT);
							
							// verify the toast is valid before displaying it
							if (msg != null)
							{
								msg.show();
							}
							
							// show the main screen
							setScreen(mainMenu);
						}
						
						@Override
						public void onFailure(Throwable arg0) {
							
							// create a new fail message
							Toast msg = Toast.makeText(CONTEXT, "Log In Attempt Failed, Leaderboards disabled.", Toast.LENGTH_SHORT);
							
							Log.e("Sign In Error", arg0.getMessage());
							
							// verify the toast is valid before displaying it
							if (msg != null)
							{
								msg.show();
							}
							
							// disable network play
							leaderboardsEnabled = false;
							
							// show the main screen
							setScreen(mainMenu);
						}
					});
				}
				else
				{
					// show the main screen - use is logged in already
					setScreen(mainMenu);
				}
			}
		}
	}

	
}