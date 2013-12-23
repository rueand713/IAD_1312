package com.randerson.entities;

public class Achievement {

	public boolean obtained = false;
	private String name = "";
	private String description = "";
	
	public Achievement(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
}
