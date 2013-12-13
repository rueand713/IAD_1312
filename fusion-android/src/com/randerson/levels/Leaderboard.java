package com.randerson.levels;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.randerson.fusion.ApplicationDefaults;
import com.randerson.fusion.R;
import com.randerson.kinvey.LeaderboardEntity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Leaderboard extends Activity {

	public enum queryType
	{
		Score_Ascending(1, "Score_Ascending"), Score_Descending(2, "Score_Descending"), Country_Alphabetical(3, "Country_Alphabetical"), Country_Reverse(4, "Country_Reverse"), Level_Ascending(5, "Level_Ascending"), 
		Level_Descending(6, "Level_Descending"), Score_Greater(7, "Score_Greater"), Score_Lesser(8, "Score_Lesser"), User_Alphabetical(9, "User_Alphabetical"), User_Reverse(10, "User_Reverse");
		
		int id;
		String query;
		
		private queryType(int value, String type)
		{
			id = value;
			query = type;
		};
		
		public int getId()
		{
			return id;
		}
		
		public String getQuery()
		{
			return query;
		}
	}
	
	Client kinveyClient;
	AsyncAppData<LeaderboardEntity> leaderboardEntity;
	queryType userQuery;
	Query query;
	ApplicationDefaults defaults;
	int highscore = 0;
	Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// set the window and orientation states
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.leaderboard);
		
		// init the defaults
		defaults = new ApplicationDefaults(getApplicationContext());
		
		// get the user highscore
		if (defaults != null && defaults.getData().getInt("highscore", 0) > 0)
		{
			highscore = defaults.getData().getInt("highscore", 0);
		}
		
		// set the default query
		userQuery = queryType.Score_Descending;
		
		// setup the kinvey client and appData objects
		kinveyClient = new Client.Builder(getResources().getString(R.string.app_key), getResources().getString(R.string.app_secret), getApplicationContext()).build();
		leaderboardEntity = kinveyClient.appData("Leaderboards", LeaderboardEntity.class);
		
		spinner = (Spinner) findViewById(R.id.querySpinner);
		Button backButton = (Button) findViewById(R.id.backBtn);
		Button refreshButton = (Button) findViewById(R.id.refreshBtn);
		
		if (spinner != null)
		{
			String[] queries = {queryType.Level_Ascending.getQuery(), queryType.Level_Descending.getQuery(), queryType.Score_Ascending.getQuery(), 
								queryType.Score_Descending.getQuery(), queryType.Score_Greater.getQuery(), queryType.Score_Lesser.getQuery(),
								queryType.Country_Alphabetical.getQuery(), queryType.Country_Reverse.getQuery(), queryType.User_Alphabetical.getQuery(), queryType.User_Reverse.getQuery()};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, queries);
			spinner.setAdapter(adapter);
		}
		
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
		
		
		// calls method to retreive all records from backend
		fetchKinveyData();
	}
	
	
	public void fetchKinveyData()
	{
		query = kinveyClient.query();
		int index = spinner.getSelectedItemPosition();
		
		switch(index)
		{
		case 0:
			userQuery = queryType.Level_Ascending;
			break;
			
		case 1:
			userQuery = queryType.Level_Descending;
			break;
			
		case 2:
			userQuery = queryType.Score_Ascending;
			break;
			
		case 3:
			userQuery = queryType.Score_Descending;
			break;
			
		case 4:
			userQuery = queryType.Score_Greater;
			break;
			
		case 5:
			userQuery = queryType.Score_Lesser;
			break;
			
		case 6:
			userQuery = queryType.Country_Alphabetical;
			break;
			
		case 7:
			userQuery = queryType.Country_Reverse;
			break;
			
		case 8:
			userQuery = queryType.User_Alphabetical;
			break;
			
		case 9:
			userQuery = queryType.User_Reverse;
			break;
			
			default:
				userQuery = queryType.Level_Ascending;
				break;
		}
		
		final TableLayout table = (TableLayout) findViewById(R.id.scoreTable);
		
		// verify the table is valid
		if (table != null)
		{
			// create the call back method
			KinveyListCallback<LeaderboardEntity> kvc = new KinveyListCallback<LeaderboardEntity>() {
				
				@Override
				public void onSuccess(LeaderboardEntity[] arg0) {
					
					// empty the table
					table.removeAllViews();
					
					// verify the returned object is valid
					if (arg0 != null && arg0.length > 0)
					{
						
						// create a new table row for the user data
						TableRow titleRow = new TableRow(getApplicationContext());
						
						// verify the row is valid object
						if (titleRow != null)
						{
							// create new textviews to hold the user element data
							TextView title_name = new TextView(getApplicationContext());
							TextView title_country = new TextView(getApplicationContext());
							TextView title_level = new TextView(getApplicationContext());
							TextView title_score = new TextView(getApplicationContext());
							
							// set the text and styling to the text views
							title_name.setText("PLAYER");
							title_name.setTextAppearance(getApplicationContext(), R.style.TitleStyle);
							title_name.setPadding(10, 5, 10, 5);
							title_country.setText("COUNTRY");
							title_country.setTextAppearance(getApplicationContext(), R.style.TitleStyle);
							title_country.setPadding(10, 5, 10, 5);
							title_level.setText("LEVEL");
							title_level.setTextAppearance(getApplicationContext(), R.style.TitleStyle);
							title_level.setPadding(10, 5, 10, 5);
							title_score.setText("SCORE");
							title_score.setTextAppearance(getApplicationContext(), R.style.TitleStyle);
							title_score.setPadding(10, 5, 10, 5);
							
							// add the titles to the row
							titleRow.addView(title_name);
							titleRow.addView(title_country);
							titleRow.addView(title_level);
							titleRow.addView(title_score);
							
							// add the row to the table as the title row
							table.addView(titleRow);
						}
						
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
								user_name.setTextAppearance(getApplicationContext(), R.style.TextStyle);
								user_name.setPadding(6, 6, 6, 6);
								user_country.setText(country);
								user_country.setTextAppearance(getApplicationContext(), R.style.TextStyle);
								user_country.setPadding(6, 6, 6, 6);
								user_level.setText("" + level);
								user_level.setTextAppearance(getApplicationContext(), R.style.TextStyle);
								user_level.setPadding(6, 6, 6, 6);
								user_score.setText("" + score);
								user_score.setTextAppearance(getApplicationContext(), R.style.TextStyle);
								user_score.setPadding(6, 6, 6, 6);
								
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
			};	// end kinvey call back setting
			
			// set the query data object
			if (userQuery == queryType.Level_Ascending)
			{
				query.addSort("level", SortOrder.ASC);
			}
			else if (userQuery == queryType.Level_Descending)
			{
				query.addSort("level", SortOrder.DESC);
			}
			else if (userQuery == queryType.Score_Ascending)
			{
				query.addSort("score", SortOrder.ASC);
			}
			else if (userQuery == queryType.Score_Descending)
			{
				query.addSort("score", SortOrder.DESC);
			}
			else if (userQuery == queryType.Country_Alphabetical)
			{
				query.addSort("country", SortOrder.ASC);
			}
			else if (userQuery == queryType.Country_Reverse)
			{
				query.addSort("country", SortOrder.DESC);
			}
			else if (userQuery == queryType.Score_Greater)
			{
				query.greaterThan("score", highscore);
			}
			else if (userQuery == queryType.Score_Lesser)
			{
				query.lessThan("score", highscore);
			}
			else if (userQuery == queryType.User_Alphabetical)
			{
				query.addSort("username", SortOrder.ASC);
			}
			else if (userQuery == queryType.User_Reverse)
			{
				query.addSort("username", SortOrder.DESC);
			}
			
			// make the query request
			leaderboardEntity.get(query, kvc);
		}
	}
	
}
