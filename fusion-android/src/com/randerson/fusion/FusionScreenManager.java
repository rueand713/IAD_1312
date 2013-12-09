package com.randerson.fusion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.randerson.entities.PlayerHandler;
import com.randerson.levels.ActOne;
import com.randerson.levels.Credits;
import com.randerson.levels.MainMenu;
import com.randerson.levels.SplashScreen;
import com.randerson.levels.Tutorial;

public class FusionScreenManager extends Game {

	// create the screen objects
	public Tutorial tutorial;
	public MainMenu mainMenu;
	public ActOne actOne;
	public Credits credits;
	public SplashScreen splashScreen;
	
	// create the game music and sound objects
	
	// create the game graphics objects
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
	public TextureRegion creditsButton;
	public TextureRegion pauseButton;
	public TextureRegion timeMeterFront;
	public TextureRegion timeMeterBack;
	public Texture bgImage1;
	public Texture splashImage;
	public Texture titleScreen;
	public Texture tutorialImage;
	public Texture creditsImage;
	public Animation particleAnimations;
	public TextureAtlas gameTextures;
	public TextureAtlas uiTextures;
	
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
	
	@Override
	public void create() {
		
		// initialize all of the game scenes
		tutorial = new Tutorial(this);
		mainMenu = new MainMenu(this, false);
		actOne = new ActOne(this);
		credits = new Credits(this);
		splashScreen = new SplashScreen(this);
		
		// show the main screen
		setScreen(mainMenu);
		
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
		mainMenu = new MainMenu(this, true);
		
		// reset the first level objects
		resetAct();
		
		// go to main menu
		setScreen(mainMenu);
	}
	
}