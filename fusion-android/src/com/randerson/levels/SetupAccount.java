package com.randerson.levels;

import java.io.IOException;
import java.io.InputStream;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;
import com.randerson.fusion.ApplicationDefaults;
import com.randerson.fusion.GameInitializer;
import com.randerson.fusion.IOManager;
import com.randerson.fusion.R;
import com.randerson.kinvey.AccountEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SetupAccount extends Activity {

	String username = "";
	String password = "";
	String country = "";
	String email = "";
	String[] countries;
	
	Client kinveyClient;
	AsyncAppData<AccountEntity> accountEntity;
	ApplicationDefaults defaults;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set the window and orientation states
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
		
		// init the prefs object
		defaults = new ApplicationDefaults(getApplicationContext());
		
		// verify the object is valid
		if (defaults != null)
		{
			// load up the country codes
			loadAsset();
			
			boolean isSetup = defaults.getData().getBoolean("firstRun", false);
			boolean networkPlay = defaults.getData().getBoolean("networkplay", true);
			
			// check if the game has been setup previously
			if (isSetup && networkPlay)
			{
				// create an intent to start the game
				Intent accountData = new Intent(getApplicationContext(), GameInitializer.class);
				
				// verify the game intent is valid
				if (accountData != null)
				{
					// set the account data objects
					username = defaults.getData().getString("username", null);
					password = defaults.getData().getString("password", null);
					email = defaults.getData().getString("email", null);
					country = defaults.getData().getString("country", null);
					
					// store the data in the intent
					accountData.putExtra("username", username);
					accountData.putExtra("password", password);
					accountData.putExtra("email", email);
					accountData.putExtra("country", country);
					accountData.putExtra("setup", true);
					accountData.putExtra("networkplay", true);
					
					// start the game
					startActivity(accountData);
				}
			}
			else if (isSetup == true && networkPlay == false)
			{
				// user elected not to use leaderboards
				noNetworkPlay();
			}
			else if (isSetup == false && networkPlay == true)
			{
				
				// get the app creds
				String secret = getResources().getString(com.randerson.fusion.R.string.app_secret);
				String key = getResources().getString(com.randerson.fusion.R.string.app_key);
				
				// initialize the client and appData objects
				kinveyClient = new Client.Builder(key, secret, this.getApplicationContext()).build();
				accountEntity = kinveyClient.appData("Accounts", AccountEntity.class);
				
				// get a refernce to the menu buttons
				Button doneButton = (Button) findViewById(R.id.doneButton);
				Button cancelButton = (Button) findViewById(R.id.cancelButton);
				Spinner spinner = (Spinner) findViewById(R.id.country);
				
				if (spinner != null)
				{
					// create an ArrayAdapter object
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
					
					// set the spinner dropdown resource
					adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
					
					// set the adapter
					spinner.setAdapter(adapter);
				}
				
				// verify the done button is valid
				if (doneButton != null)
				{
					// set the click listener
					doneButton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							// check if there is an network connection
					        boolean hasConnection = IOManager.getConnectionStatus(getApplicationContext());
							
							if (hasConnection)
							{
								// call method to try to setup account
								setupAccount();
							}
							else
							{
								// create toast object
								Toast msg = Toast.makeText(getApplicationContext(), "Enable network to sign-up or cancel to continue.", Toast.LENGTH_SHORT);
								
								// verify the object is not null
								if (msg != null)
								{
									// show the msg
									msg.show();
								}
							}
						}
					});
				}
				
				// verify the cancel button is valid
				if (cancelButton != null)
				{
					// set the click listener
					cancelButton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							// user elected to not sign up for leaderboards
							defaults.setBool("networkplay", false);
							noAccountPlay();
						}
					});
				}	
			}
			else if (isSetup == false && networkPlay == false)
			{
				// continue without leaderboards
				noAccountPlay();
			}
		}
		else
		{
			// if for some reason defaults won't validate continue without network play
			noAccountPlay();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	public void setupAccount()
	{
		// get references to the editTexts in layout
		EditText usernameField = (EditText) findViewById(R.id.username);
		EditText passwordField = (EditText) findViewById(R.id.password);
		EditText emailField = (EditText) findViewById(R.id.email);
		Spinner countryField = (Spinner) findViewById(R.id.country);
		
		// verify the username field is valid
		if (usernameField != null)
		{
			// get the username text
			username = usernameField.getText().toString();
		}
		
		// verify the password object is valid
		if (passwordField != null)
		{
			// get the password text
			password = passwordField.getText().toString();
		}
		
		// verify the email object is valid
		if (emailField != null)
		{
			// get the password text
			email = emailField.getText().toString();
		}
		
		// verify the country object is valid
		if (countryField != null)
		{
			// get the password text
			country = (String) countries[countryField.getSelectedItemPosition()];
		}
		
		// verify that all required fields have been submitted
		if (username.length() >3 && password.length() >4 && country.length() <4 && email.length() >8)
		{
			// create a new user account
			kinveyClient.user().create(username, password, new KinveyUserCallback() {
				
				@Override
				public void onSuccess(User arg0) {
					
					// create a success toast
					Toast msg = Toast.makeText(getApplicationContext(), "User account created", Toast.LENGTH_SHORT);
					
					// verify the msg is valid and show it
					if (msg != null)
					{
						msg.show();
					}
					
					// save user data to kinvey
					storeAccount();
				}
				
				@Override
				public void onFailure(Throwable arg0) {
					
					// create an error toast
					Toast msg = Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT);
					
					Log.e("Sign Up Error", arg0.getMessage());
					
					// verify the msg is valid and show it
					if (msg != null)
					{
						msg.show();
					}
				}
			});
		}
				
	}
	
	public void storeAccount()
	{
		// create an account entity object with the new user data
		AccountEntity account = new AccountEntity();
		
		// verify the new account object is valid
		if (account != null)
		{
			// set the account info
			account.set("username", username);
			account.set("password", password);
			account.set("email", email);
		}
		
		// attempt to save the data to kinvey server
		accountEntity.save(account, new KinveyClientCallback<AccountEntity>() {
			
			@Override
			public void onSuccess(AccountEntity arg0) {
				
				// create a success toast
				Toast msg = Toast.makeText(getApplicationContext(), "Account registered!", Toast.LENGTH_SHORT);
				
				// verify the msg is valid and show it
				if (msg != null)
				{
					msg.show();
				}
				
				// create an intent to start the game
				Intent accountData = new Intent(getApplicationContext(), GameInitializer.class);
				
				// verify the game intent is valid
				if (accountData != null)
				{
					accountData.putExtra("username", username);
					accountData.putExtra("password", password);
					accountData.putExtra("email", email);
					accountData.putExtra("setup", true);
					
					startActivity(accountData);
				}
			}
			
			@Override
			public void onFailure(Throwable arg0) {

				// create a success toast
				Toast msg = Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT);
				
				// verify the msg is valid and show it
				if (msg != null)
				{
					msg.show();
				}
				
				// play without leaderboards
				noAccountPlay();
			}
		});
	}
	
	public void noAccountPlay()
	{
		// create an intent to start the game
		Intent accountData = new Intent(getApplicationContext(), GameInitializer.class);
		
		// verify the game intent is valid
		if (accountData != null)
		{
			accountData.putExtra("setup", false);
			accountData.putExtra("networkplay", false);
			
			startActivity(accountData);
		}
	}
	
	public void noNetworkPlay()
	{
		// create an intent to start the game
		Intent accountData = new Intent(getApplicationContext(), GameInitializer.class);
		
		// verify the game intent is valid
		if (accountData != null)
		{
			// set the account data objects
			username = defaults.getData().getString("username", null);
			password = defaults.getData().getString("password", null);
			email = defaults.getData().getString("email", null);
			country = defaults.getData().getString("country", null);
			
			// store the data in the intent
			accountData.putExtra("username", username);
			accountData.putExtra("password", password);
			accountData.putExtra("email", email);
			accountData.putExtra("country", country);
			accountData.putExtra("setup", true);
			accountData.putExtra("networkplay", false);
			
			startActivity(accountData);
		}
	}
	
	public void loadAsset()
	{
		// create a new input stream to read in the country codes
		InputStream reader = getResources().openRawResource(R.raw.countries);	
		
		if (reader != null)
		{
			try {
				
				// set the initial objects for reading data from the in stream
				String content = "";
				StringBuffer buffer = new StringBuffer();
				byte[] contentBytes = new byte[1024];
				int readBytes = 0;
				
				// read the bytes in the stream, setting them and creating
				// a new string from those read bytes, finally appending the string to the buffer
				while ((readBytes = reader.read(contentBytes)) != -1)
				{
					String chars = new String(contentBytes, 0, readBytes);
					
					buffer.append(chars);
				}
				
				// create a string from the buffer
				content = buffer.toString();
				
				// split the created content string on each newline
				countries = content.split("\n");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
