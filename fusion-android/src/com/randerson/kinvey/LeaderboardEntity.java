package com.randerson.kinvey;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class LeaderboardEntity extends GenericJson {

	@Key("_id")
	public String id;
	
	@Key("username")
	public String username;
	
	@Key("country")
	public String country;
	
	@Key("score")
	public int score;
	
	@Key("level")
	public int level;
	
	public LeaderboardEntity(){};
	
}
