package com.randerson.kinvey;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class AccountEntity extends GenericJson {

	@Key("_id")
	public String id;
	
	@Key("username")
	public String username;
	
	@Key("password")
	public String password;
	
	@Key("country")
	public String country;
	
	@Key("email")
	public String email;
	
	public AccountEntity()
	{
		
	}
}
