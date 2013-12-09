package com.randerson.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.randerson.fusion.Box2D;

// master game object class will serve as base of base classes
public class BoxActor {

	protected TextureRegion TEXTURE;
	protected Fixture FIXTURE;
	protected Animation ANIMATION;
	
	// method for setting the physics attributes associated with the world
	public BoxActor(World world, BodyType bodyType, float xOrigin, float yOrigin, float restitution, float friction, float density, Shape shape)
	{	
		// create the body definition with the libgdx rect data
		BodyDef bodydef = Box2D.getBodyDef(bodyType, xOrigin, yOrigin);
		
		// create the body from the definition
		Body body = Box2D.getBody(world, bodydef);
		
		// create the fixture definition from the physics objects set during construction
		FixtureDef fxDef = Box2D.getFixtureDef(shape, density, friction, restitution);
		
		// create a fixture from the body
		FIXTURE = Box2D.getFixture(body, fxDef);
	}
	
	// method for checking collision between two entities using their fixtures
	public boolean overlaps(Fixture fixture, boolean addPadding)
	{
		// get the vector objects associated with this actor and the other supplied
		Vector2 actorPosition = FIXTURE.getBody().getPosition();
		Vector2 otherPosition = fixture.getBody().getPosition();
		
		// set the default collision return value
		boolean isColliding = false;
		
		int padding = 0;
		
		if (addPadding)
		{
			padding = 10;
		}
		
		// set the x and y collision ranges for sprites
		float xRangeMax = actorPosition.x + (padding + TEXTURE.getRegionWidth());
		float xRangeMin = actorPosition.x - padding;
		float yRangeMax = actorPosition.y + (padding + TEXTURE.getRegionHeight());
		float yRangeMin = actorPosition.y - padding;
		
		// check if the two fixtures occupy the same space x & y
		// if they do then a collision is set to return true
		if (otherPosition.x >= xRangeMin && otherPosition.x <= xRangeMax)
		{
			if (otherPosition.y >= yRangeMin && otherPosition.y <= yRangeMax)
			{
				isColliding = true;
			}
		}
		
		return isColliding;
	}
	
	// method for checking collision between two entities using a fixture and rectangle
	public boolean overlaps(Rectangle rectangle, boolean addPadding)
	{
		// get the vector objects associated with this actor and the other supplied
		Vector2 actorPosition = FIXTURE.getBody().getPosition();
		Vector2 otherPosition = new Vector2(rectangle.x, rectangle.y);
		
		// set the default collision return value
		boolean isColliding = false;
		
		int padding = 0;
		
		if (addPadding)
		{
			padding = 10;
		}
		
		// set the x and y collision ranges for sprites
		float xRangeMax = actorPosition.x + (padding + TEXTURE.getRegionWidth());
		float xRangeMin = actorPosition.x - padding;
		float yRangeMax = actorPosition.y + (padding + TEXTURE.getRegionHeight());
		float yRangeMin = actorPosition.y - padding;
		
		// check if the two fixtures occupy the same space x & y
		// if they do then a collision is set to return true
		if (otherPosition.x >= xRangeMin && otherPosition.x <= xRangeMax)
		{
			if (otherPosition.y >= yRangeMin && otherPosition.y <= yRangeMax)
			{
				isColliding = true;
			}
		}
		
		return isColliding;
	}
	
	// method for checking collision between two entities using a fixture and rectangle
	public boolean overlaps(Vector2 otherPosition, boolean addPadding)
	{
		// get the vector objects associated with this actor
		Vector2 actorPosition = FIXTURE.getBody().getPosition();
		
		// set the default collision return value
		boolean isColliding = false;
		
		int padding = 0;
		
		if (addPadding)
		{
			padding = 10;
		}
		
		// set the x and y collision ranges for sprites
		float xRangeMax = actorPosition.x + (padding + TEXTURE.getRegionWidth());
		float xRangeMin = actorPosition.x - padding;
		float yRangeMax = actorPosition.y + (padding + TEXTURE.getRegionHeight());
		float yRangeMin = actorPosition.y - padding;
		
		// check if the two fixtures occupy the same space x & y
		// if they do then a collision is set to return true
		if (otherPosition.x >= xRangeMin && otherPosition.x <= xRangeMax)
		{
			if (otherPosition.y >= yRangeMin && otherPosition.y <= yRangeMax)
			{
				isColliding = true;
			}
		}
		
		return isColliding;
	}
	
	public void addRotation(float angle)
	{
		FIXTURE.getBody().applyAngularImpulse(angle, true);
	}
	
	public void applyForce(boolean applyToCenter, float xForce, float yForce)
	{
		// check whether the force should be applied to the center of the entity
		// apply force to center if so otherwise apply force at x and y
		if (applyToCenter)
		{
			FIXTURE.getBody().applyForceToCenter(xForce, yForce, true);
		}
		else 
		{
			// get the x and y positions
			float x = FIXTURE.getBody().getPosition().x;
			float y = FIXTURE.getBody().getPosition().y; 
			
			// apply the force to the x and y
			FIXTURE.getBody().applyForce(xForce, yForce, x, y, true);
		}
	}
	
	public void applyImpulse(boolean applyToCenter, float xImpulse, float yImpulse)
	{
		// check whether the impulse should be applied to the center of the entity
		// apply impulse to center if so otherwise apply impulse at x and y
		if (applyToCenter)
		{
			float x = FIXTURE.getBody().getLocalCenter().x;
			float y = FIXTURE.getBody().getLocalCenter().y;
			
			FIXTURE.getBody().applyLinearImpulse(xImpulse, yImpulse, x, y, true);
		}
		else 
		{
			// get the x and y positions
			float x = FIXTURE.getBody().getPosition().x;
			float y = FIXTURE.getBody().getPosition().y; 
			
			// apply the force to the x and y
			FIXTURE.getBody().applyForce(xImpulse, yImpulse, x, y, true);
		}
	}
	
	public void applyVelocity(float xVelocity, float yVelocity)
	{
		FIXTURE.getBody().setLinearVelocity(xVelocity, yVelocity);
	}
	
	// method for returning the fixture
	public Fixture getFixture()
	{
		return FIXTURE;
	}
	
	// method for returning the animation
	public TextureRegion getAnimatedFrame(float stateTime, boolean isLooping)
	{
		return ANIMATION.getKeyFrame(stateTime, isLooping);
	}
	
	// method for returning the texture
	public TextureRegion getTexture()
	{
		return TEXTURE;
	}
	
	// method for setting the texture
	public void setTexture(TextureRegion texture)
	{
		TEXTURE = texture;
	}
	
	// method for setting the animation
	public void setAnimation(Animation animation)
	{
		ANIMATION = animation;
	}
	
	// method for returning the fixture body
	public Body getBody()
	{
		return FIXTURE.getBody();
	}
	
	// method for returning the position
	public Vector2 getPosition()
	{
		return FIXTURE.getBody().getPosition();
	}
	
	// method for setting position
	public void setPosition(float x, float y, float angle)
	{
		FIXTURE.getBody().setTransform(x, y, angle);
	}
	
	public void destroy()
	{
		// nullify the objects
		FIXTURE = null;
		TEXTURE = null;
		ANIMATION = null;
	}
	
}
