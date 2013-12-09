package com.randerson.entities;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.randerson.fusion.FusionScreenManager;

// the base object class all unique objects will extend from this one
public class Actor extends BoxActor {

	protected int ID;
	protected int VALUE;
	protected String NAME;
	protected FusionScreenManager MANAGER;
	
	public Actor(World world, BodyType bodyType, float xOrigin, float yOrigin,
			float restitution, float friction, float density, Shape shape) {
		super(world, bodyType, xOrigin, yOrigin, restitution, friction, density, shape);
	}
	
	// method for creating an id
	public int createID(int n)
	{
		int id = 0;
		
		id = (int) Math.random() * (n * 100000000);
		
		// verify that the id is greater than 0
		if (id == 0)
		{
			// try getting another id assigned
			id = getID();
		}
		
		return id;
	}
	
	// method for returning the id
	public int getID()
	{
		return ID;
	};
	
	// method for returning the value
	public int getValue()
	{
		return VALUE;
	}
	
	// method for setting a new value
	public void setValue(int value)
	{
		VALUE = value;
	}
	
	// method for setting the name
	public void setName(String name)
	{
		NAME = name;
	}
	
	// method for getting the name
	public String getName()
	{
		return NAME;
	}
	
	public void setManager(FusionScreenManager manager)
	{
		MANAGER = manager;
	}
	
	public void destroy()
	{
		// nullify current class data
		NAME = null;
		MANAGER = null;
		
		// destory the super data
		super.destroy();
	}
}
