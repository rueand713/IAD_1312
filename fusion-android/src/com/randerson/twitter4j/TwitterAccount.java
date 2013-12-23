package com.randerson.twitter4j;

import com.randerson.fusion.AndroidExtender;
import com.randerson.fusion.ApplicationDefaults;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class TwitterAccount {

	Activity CONTEXT;
	String accessTokenKey;
	String accessTokenSecret;
	String consumerKey;
	String consumerSecret;
	//RequestToken requestToken;
	//AccessToken accessToken;
	//Twitter tweetMachine;
	String MASTER_SECRET;
	String MASTER_TOKEN;
	ApplicationDefaults defaults;
	
	private static AndroidExtender Android;
	
	/*public TwitterAccount(Activity context)
	{
		// set the class context
		CONTEXT = context;
		
		defaults = new ApplicationDefaults(CONTEXT);
		
		// set the application twitter token and secret
		MASTER_TOKEN = context.getResources().getString(R.string.access_token);
		MASTER_SECRET = context.getResources().getString(R.string.access_token_secret);
		
		// set the application twitter consumer key and secret
		consumerKey = context.getResources().getString(R.string.twiter_key);
		consumerSecret = context.getResources().getString(R.string.twitter_secret);
		
		// setup the twitter object
		tweetMachine = new TwitterFactory().getInstance();
		
		if (tweetMachine != null)
		{
			// get the request token
			getRequestToken();
			
			// set the consumer OAuth consumer values
			tweetMachine.setOAuthConsumer(consumerKey, consumerSecret);
			
			// create an access token for the supplied credentials
			getAccessToken();
			
			// set the OAuth access token
			tweetMachine.setOAuthAccessToken(accessToken);
		}
	}
	
	public void postTweet(String message)
	{
		try {
			// post status message
			tweetMachine.updateStatus(message);
			
			Log.i("Tweet Success", "Tweet Posted");
			
		} catch (TwitterException e) {
			e.printStackTrace();
			
			Log.e("Tweet Exception", e.toString());
		}
	}
	
	@SuppressLint("HandlerLeak")
	private void getRequestToken()
	{	
		Handler requestTokenHandler = new Handler();
		
		requestTokenHandler.post(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					// try to retrieve a request token
					requestToken = tweetMachine.getOAuthRequestToken();
					
				} catch (TwitterException e) {
					
					e.printStackTrace();
					
					Log.e("Token Error", "Error Generating Request/Access Token");
				}
			}
		});
	}
	
	private void getAccessToken()
	{
		// check if there are saved credentials
		accessTokenKey = defaults.getData().getString("access_token_key", null);
		accessTokenSecret = defaults.getData().getString("access_token_secret", null);
		
		// if so load those and use the previously created creds otherwise get new credentials
		if (accessTokenKey != null && accessTokenSecret != null)
		{
			// create an access token from the credentials
			accessToken = new AccessToken(accessTokenKey, accessTokenSecret);
		}
		else
		{
			try {
				
				// try to retrieve an access token using the request token
				accessToken = tweetMachine.getOAuthAccessToken(requestToken);
				
				// check if the token is valid
				if (accessToken != null)
				{
					// retrieve the credential strings and set them to class fields
					accessTokenKey = accessToken.getToken();
					accessTokenSecret = accessToken.getTokenSecret();
					
					// save the credentials
					defaults.setString("access_token_key", accessTokenKey);
					defaults.setString("access_token_secret", accessTokenSecret);
				}
				
			} catch (TwitterException e) {
				e.printStackTrace();
				
				Log.e("Token Error", "Error Generating Access Token");
			}
		}
	}
	*/
	
	public static void webTweet(Activity context, String message)
	{
		Android = (AndroidExtender) context;
		
		Uri uri = Uri.parse("https://twitter.com/intent/tweet?text=\"" + message +"\"");
		
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		
		if (intent != null)
		{
			Android.startIntent(intent);
		}
	}
}
