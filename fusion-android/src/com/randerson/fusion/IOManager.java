/*
 * project 		randerson-common-assets_lib
 * 
 * package 		libs
 * 
 * @author 		Rueben Anderson
 * 
 * date			Jul 27, 2013
 * 
 */

package com.randerson.fusion;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class IOManager {
	
	public class Receiver extends BroadcastReceiver {

		// the default connection status and type for the receiver
		boolean status = false;
		String type = "No Connection";
		Context CONTEXT = null;
		
		// constructor
		public Receiver(Context context)
		{
			CONTEXT = context;
		};
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			ConnectivityManager cm = (ConnectivityManager) CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo network = cm.getActiveNetworkInfo();
			
			// check the network status is connected
			if (network.isConnected())
			{
				// set the bool to true
				status = true;
				
				// set the network type
				type = network.getTypeName();
			}
			else
			{
				// set the bool to false
				status = false;
				
				// set the network type to none
				type = "No Connection";
			}
		} 
		
		// method for returning the network status
		public boolean getStatus()
		{
			// return the network status from the receiver;
			return status;
		}
		
		// method for returning the network type
		public String getType()
		{
			// return the network type
			return type;
		}

	}
	
	static boolean connectionStatus = false;
	static String connectionType = "No Connection";
	public static String CONTENT_JSON = "application/json";
	public static String CONTENT_FORM_URL = "application/x-www-form-urlencoded";
	public static String CONTENT_TEXT = "text/plain";
	public static String CONTENT_XML = "text/xml";
	
	// constructor
	public IOManager(){};
	
	// getter method for retrieving the connectionType
	public static String getConnectionType(Context context)
	{
		// calls the method to check and retrieve the connection data
		fetchConnectionData(context);
				
		// return the connection type
		return connectionType;
	}
	
	// getter method for retrieving the connectionStatus
	public static boolean getConnectionStatus(Context context)
	{
		// calls the method to check and retrieve the connection data
		fetchConnectionData(context);
		
		// return the connection status
		return connectionStatus;
	}
	
	//  method for retrieving the connection details and setting the class fields
	private static void fetchConnectionData(Context context)
	{
		// setup the connectivity manager object
		ConnectivityManager conMngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// instantiate the networkinfo object to hold the connectivity manager active network
		NetworkInfo net411 = conMngr.getActiveNetworkInfo();
		
		// check if the network info object is created
		if (net411 != null)
		{
			// the network info object is created now it checks for a connection
			// if there is a connection the connected and connectionType fields are set to reflect that data
			// otherwise the fields are set to their default values
			if (net411.isConnected())
			{
				connectionStatus = true;
				connectionType = net411.getTypeName();
			}
			else
			{
				connectionStatus = false;
				connectionType = "No Connection";
			}
		}
	}
	
	//  method for making a web request for string data
	public static String makeStringRequest(URL urlString)
	{
		// string object to hold the URL response text
		String responseText = "";
		
		try {
			// create a new URL connection object
			URLConnection connection = urlString.openConnection();
			
			// creates a new buffer input stream with the URL connection object
			BufferedInputStream bfStream = new BufferedInputStream(connection.getInputStream());
			
			// create a new byte array
			byte[] contentBytes = new byte[1024];
			
			// integer to handle the number of bytes read
			int readBytes = 0;
			
			// create a new string buffer object
			StringBuffer buffString = new StringBuffer();
			
			// loop to handle the appending of the string content to buffer
			while ((readBytes = bfStream.read(contentBytes)) != -1)
			{
				// set the response string to the string content read
				responseText = new String(contentBytes, 0, readBytes);
				
				// appends the new string content to the buffer string object
				buffString.append(responseText);
			}
			
			// set the responseText to the full buffered string content in buffer
			responseText = buffString.toString();
			
		} catch (IOException e) {
			Log.e("URL RESPONSE ERROR", "Server failed to respond to request");
		}
		
		// return the response string
		return responseText;
	}
	
	//public static String makePostRequest(String url, String authUrl, JSONObject postData, String authParams)
	public static String makePostRequest(String url, JSONObject postData)
	{
		String responseText = "";
		///String authText = "";
		
		try {

			// create the default http client object
			DefaultHttpClient connection = new DefaultHttpClient();
			
			// get the http client's connection manager
			ClientConnectionManager conManager = connection.getConnectionManager();
			
			// get the http client's credentials provider
			CredentialsProvider credProvider = connection.getCredentialsProvider();
			
			// create a credentials object for the http client
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials("tryouthabodepignardonswe:8AbceoknONhKtHQ86rjAc1Y7");
			
			// set the credentials to the provider
			credProvider.setCredentials(AuthScope.ANY, creds);
			
			// create the http posting object
			HttpPost postRequest = new HttpPost(url);
			
			// create a http string entity to pass the json string 
			StringEntity jsonEntity = new StringEntity(postData.toString());
			
			// pass the http string entity to the post object
			postRequest.setEntity(jsonEntity);
			
			// set the post object headers
			postRequest.setHeader("Accept", IOManager.CONTENT_JSON);
			postRequest.setHeader("Content-type", IOManager.CONTENT_JSON);
			
			// close the previous get request connection
			conManager.closeExpiredConnections();
			
			// make the post request to the connection and capture the http response
		    HttpResponse handler = connection.execute(postRequest);
		    
		    // iterate through the returned headers and log them out
		    for (Header n : handler.getAllHeaders())
		    {
		    	Log.i("Response Header", n.toString());
		    }
		    
		    // log out the status code and message
		    Log.i("Status Code", handler.getStatusLine().toString());
			
		} catch (IOException e)
		{
			Log.e("URL POST ERROR", "Server failed to respond to output request");
			e.printStackTrace();
		}
	
		// return the response string
		return responseText;
	}
	
	public static String makePostRequest(String url, String jsonString)
	{
		String responseText = "";
		///String authText = "";
		
		try {

			// create the default http client object
			DefaultHttpClient connection = new DefaultHttpClient();
			
			// get the http client's connection manager
			ClientConnectionManager conManager = connection.getConnectionManager();
			
			// get the http client's credentials provider
			CredentialsProvider credProvider = connection.getCredentialsProvider();
			
			// create a credentials object for the http client
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials("tryouthabodepignardonswe:8AbceoknONhKtHQ86rjAc1Y7");
			
			// set the credentials to the provider
			credProvider.setCredentials(AuthScope.ANY, creds);
			
			// create the http posting object
			HttpPost postRequest = new HttpPost(url);
			
			// create a http string entity to pass the json string 
			StringEntity jsonEntity = new StringEntity(jsonString);
			
			// pass the http string entity to the post object
			postRequest.setEntity(jsonEntity);
			
			// set the post object headers
			postRequest.setHeader("Accept", IOManager.CONTENT_JSON);
			postRequest.setHeader("Content-type", IOManager.CONTENT_JSON);
			
			// close the previous get request connection
			conManager.closeExpiredConnections();
			
			// make the post request to the connection and capture the http response
		    HttpResponse handler = connection.execute(postRequest);
		    
		    // iterate through the returned headers and log them out
		    for (Header n : handler.getAllHeaders())
		    {
		    	Log.i("Response Header", n.toString());
		    }
		    
		    // log out the status code and message
		    Log.i("Status Code", handler.getStatusLine().toString());
			
		} catch (IOException e)
		{
			Log.e("URL POST ERROR", "Server failed to respond to output request");
			e.printStackTrace();
		}
		
		return responseText;
	}
	
	// method for getting an intent filter
	public static IntentFilter getFilter()
	{
		// return an intent filter
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		
		return filter;
	}
	
}
