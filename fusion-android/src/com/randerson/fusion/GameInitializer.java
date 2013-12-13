package com.randerson.fusion;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.randerson.levels.Leaderboard;

public class GameInitializer extends AndroidApplication implements AndroidExtender {
    
	String username = "";
	String password = "";
	String email = "";
	String country = "";
	
	boolean hasConnection = false;
	boolean leaderboards = false;
	boolean networkPlay = false;
	ApplicationDefaults defaults;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // create a new instance of the app prefs object
    	defaults = new ApplicationDefaults(getApplicationContext());
    	
    	// check if there is an network connection
        hasConnection = IOManager.getConnectionStatus(getApplicationContext());
        
        Intent accountData = getIntent();
        
        if (accountData != null)
        {
        	// verify that the bundle object is valid
        	Bundle bundle = accountData.getExtras();
        	
        	// verify there are valid extras
        	if (bundle != null && bundle.containsKey("setup") && bundle.getBoolean("setup") == true)
        	{
        		// get the passed in account data
        		username = bundle.getString("username");
        		password = bundle.getString("password");
        		country = bundle.getString("country");
        		email = bundle.getString("email");
        		networkPlay = bundle.getBoolean("networkplay");
        		boolean setup = bundle.getBoolean("setup");
        		
        		// verify the defaults are created properly
        		// and save the user application data if it does not exist
        		if (defaults != null && defaults.getData().getBoolean("firstRun", false))
        		{
        			defaults.setBool("firstRun", setup);
        			defaults.setString("username", username);
        			defaults.setString("password", password);
        			defaults.setString("country", country);
        			defaults.setString("email", email);
        		}
        		
        		// verify that leaderboards can and or should be enabled
        		if (setup == true && hasConnection == true && networkPlay == true)
        		{
        			leaderboards = true;
        		}
        	}
        }
        
        // check the status of the leaderboard option
        if (leaderboards == false)
		{
			// inform user that leaderboards are off
			Toast msg = Toast.makeText(getApplicationContext(), "Leaderboards have been disabled.",  Toast.LENGTH_SHORT);
        	
        	if (msg != null)
        	{
        		msg.show();
        	}
		}
        
        // call method to initialize the game
		startGame();
    }
	
	public void startGame()
	{
		// setup the application configuration
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useGL20 = true;
        
        // get the context
        Context context = this.getApplicationContext();
        
        // get the app creds
        String secret = context.getResources().getString(com.randerson.fusion.R.string.app_secret);
        String key = context.getResources().getString(com.randerson.fusion.R.string.app_key);
        
        // create creds object
        HashMap<String, String> creds = new HashMap<String, String>();
        creds.put("appKey", key);
        creds.put("appSecret", secret);
        
        // set the leaderboard android view
        Intent leaderboardView = new Intent(getApplicationContext(), Leaderboard.class);
        
        // init the game object
		initialize(new FusionScreenManager(this, creds, leaderboards, leaderboardView), cfg);
	}

	// method for starting android views
	@Override
	public void startIntent(Intent intent) {

		// verify the intent is valid
		if (intent != null)
		{
			startActivity(intent);
		}
	}

	@Override
	public void showToast(String message) {

		Toast msg = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
		
		if (msg != null)
		{
			msg.show();
		}
	}
	
	
}