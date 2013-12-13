package com.randerson.levels;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.randerson.fusion.R;
import com.randerson.kinvey.LeaderboardEntity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Leaderboard extends Activity {

	Client kinveyClient;
	AsyncAppData<LeaderboardEntity> leaderboardEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// set the window and orientation states
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.leaderboard);
		
		// setup the kinvey client and appData objects
		kinveyClient = new Client.Builder(getResources().getString(R.string.app_key), getResources().getString(R.string.app_secret), getApplicationContext()).build();
		leaderboardEntity = kinveyClient.appData("Leaderboards", LeaderboardEntity.class);
		
		Button backButton = (Button) findViewById(R.id.backBtn);
		Button refreshButton = (Button) findViewById(R.id.refreshBtn);
		
		// verify the button is valid
		if (refreshButton != null)
		{
			// set the click listener
			refreshButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					// calls method to retreive all scores from backend
					fetchKinveyData();
				}
			});
		}
		
		// verify the back button is valid object
		if (backButton != null)
		{
			// set the listener
			backButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
						
					// end the activity
					finish();
				}
			});
		}
		
		
		// calls method to retreive all scores from backend
		fetchKinveyData();
	}
	
	
	public void fetchKinveyData()
	{
		final TableLayout table = (TableLayout) findViewById(R.id.scoreTable);
		
		// verify the table is valid
		if (table != null)
		{
			leaderboardEntity.get(new KinveyListCallback<LeaderboardEntity>() {
				
				@Override
				public void onSuccess(LeaderboardEntity[] arg0) {
					
					// empty the table
					table.removeAllViews();
					
					// verify the returned object is valid
					if (arg0 != null && arg0.length > 0)
					{
						// loop through each object and add the data to the table
						for (int i = 0; i < arg0.length; i++)
						{
							// grab the individual user elements
							String username = arg0[i].username;
							String country = arg0[i].country;
							int level = arg0[i].level;
							int score = arg0[i].score;
							
							// create a new table row for the user data
							TableRow row = new TableRow(getApplicationContext());
							
							// verify the row is valid object
							if (row != null)
							{
								// create new textviews to hold the user element data
								TextView user_name = new TextView(getApplicationContext());
								TextView user_country = new TextView(getApplicationContext());
								TextView user_level = new TextView(getApplicationContext());
								TextView user_score = new TextView(getApplicationContext());
								
								// set the textview texts that correspond with their data
								user_name.setText(username);
								user_country.setText(country);
								user_level.setText("" + level);
								user_score.setText("" + score);
								
								// add the player elements to the row view
								row.addView(user_name);
								row.addView(user_country);
								row.addView(user_level);
								row.addView(user_score);
								
								// add the row to the table
								table.addView(row);
							}
						}
					}
					else
					{
						// no results returned
						// create a new table row for the user data
						TableRow row = new TableRow(getApplicationContext());
						
						// verify the row is valid object
						if (row != null)
						{
							TextView noResults = new TextView(getApplicationContext());
							
							if (noResults != null)
							{
								noResults.setText("No Results Found");
								
								// add the child elements to their parents
								row.addView(noResults);
								table.addView(row);
							}
						}
					}
				}
				
				@Override
				public void onFailure(Throwable arg0) {
				
					Toast msg = Toast.makeText(getApplicationContext(), "Error retrieving results", Toast.LENGTH_SHORT);
					
					if (msg != null)
					{
						msg.show();
					}
					
				}
			});
		}
	}
	
}
