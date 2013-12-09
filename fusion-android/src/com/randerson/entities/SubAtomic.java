package com.randerson.entities;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class SubAtomic extends Actor {

	public enum ParticleClass
	{
		Element("Element"), Fermion("Fermion"), ElementaryParticle("ElementaryParticle");
		
		private String classType;
		
		private ParticleClass(String classType)
		{
			this.classType = classType;
		}
		
		public String getClassType()
		{
			return classType;
		}
	}
	
	protected int QUANTITY = 1;
	protected int X_SPEED;
	protected int Y_SPEED;
	protected int MAX_X_SPEED;
	protected int MAX_Y_SPEED;
	protected String TYPE;
	
	protected boolean SELECTED = false;
	
	public SubAtomic(World world, BodyType bodyType, float xOrigin,
			float yOrigin, float restitution, float friction, float density,
			Shape shape) {
		super(world, bodyType, xOrigin, yOrigin, restitution, friction, density, shape);
	}
	
	// method for setting the quantity of elements
	public void setQuantity(int quantity)
	{
		QUANTITY = quantity;
	}
	
	// method for returning the quantity
	public int getQuantity()
	{
		return QUANTITY;
	}
	
	public void setClassType(ParticleClass classType)
	{
		TYPE = classType.getClassType();
	}
	
	public String getClassType()
	{
		return TYPE;
	}
	
	public void destroy()
	{
		TYPE = null;
		
		// destroy the super data
		super.destroy();
	}
	
	public void setSelected(boolean selected)
	{
		SELECTED = selected;
	}
	
	public boolean getSelected()
	{
		return SELECTED;
	}

}
