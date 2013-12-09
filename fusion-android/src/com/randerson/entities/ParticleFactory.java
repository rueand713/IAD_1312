package com.randerson.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.randerson.entities.Particle.ParticleType;
import com.randerson.fusion.Box2D;
import com.randerson.fusion.FusionScreenManager;

public class ParticleFactory extends BoxActor {

	protected Vector2 COORDS;
	protected World WORLD;
	protected FusionScreenManager MANAGER;
	
	public ParticleFactory(World world, BodyType bodyType, float xOrigin,
			float yOrigin, float restitution, float friction, float density,
			Shape shape) {
		super(world, bodyType, xOrigin, yOrigin, restitution, friction, density, shape);
		
		WORLD = world;
		COORDS = new Vector2(xOrigin, yOrigin);
	}
	
	// method for creating particle elements either randomly or based on a numeric difficulty 1 - 10
	// 1 being the easiest particles 10 being hardest
	public SubAtomic generateParticle(int modifier, PlayerHandler controller)
	{
		// get the fermion bonus values
		int max_protons = (int) controller.getMaxProtons();
		int max_electrons = (int) controller.getMaxElectrons();
		int max_neutrons = (int) controller.getMaxNeutrons();
		
		// get a random integer
		int rand = (int) (Math.random() * 10);
		
		// check if a value has been passed in for the particle determination
		// otherwise particle output will be random
		if (modifier > 0)
		{
			rand = modifier;
		}
		
		// default element to return
		Element element;
		
		// generate an elementary particle (quarks, electron)
		if (rand >= 7)
		{
			// get a random number
			int type = (int) Math.round(Math.random() * 9);
			
			// initialize a base elementary particle
			Particle particle = new Particle(WORLD, Box2D.KINEMATIC_BODY, COORDS.x, COORDS.y, 0.6f, 0.4f, 1f, Box2D.getPolygon(96, 96, new Vector2(47, 48)));
			particle.setManager(MANAGER);
			
			// randomly select the type of elementary particle to set
			if (type > 4)
			{
				particle.setParticle(ParticleType.DownQuark);
			}
			else if (type < 4)
			{
				particle.setParticle(ParticleType.UpQuark);
			}
			
			return particle;
		}
		// generate a fermion (proton / neutron)
		else if (rand <= 5)
		{
			// get a random number
			int type = (int) (Math.random() * 12);
			
			// initialize a particle fermion object
			Particle fermion = new Particle(WORLD, Box2D.KINEMATIC_BODY, COORDS.x, COORDS.y, 0.6f, 0.4f, 1f, Box2D.getPolygon(96, 96, new Vector2(47, 48)));
			fermion.setManager(MANAGER);
			
			// randomly select the type of fermion to set
			if (type < 4)
			{
				fermion.setParticle(ParticleType.Neutron);
				
				// check if the type is in range
				if (type > 2)
				{
					// get a random quantity up to the max
					int quantity = (int) Math.round(Math.random() * max_neutrons);
					
					if (quantity < 1) quantity = 1;
					
					// set the quantity
					fermion.setQuantity(quantity);
				}
			}
			else if (type > 8)
			{
				fermion.setParticle(ParticleType.Proton);
				
				// check if the type is in range
				if (type < 11)
				{
					// get a random quantity up to the max
					int quantity = (int) Math.round(Math.random() * max_protons);
					
					if (quantity < 1) quantity = 1;
					
					// set the quantity
					fermion.setQuantity(quantity);
				}
			}
			else 
			{
				// actually an elementary particle ;)
				fermion.setParticle(ParticleType.Electron);
				
				// check if the type is in range
				if (type == 5 || type == 7)
				{
					// get a random quantity up to the max
					int quantity = (int) Math.round(Math.random() * max_electrons);
					
					if (quantity < 1) quantity = 1;
					
					// set the quantity
					fermion.setQuantity(quantity);
				}
			}
			
			return fermion;
		}
		// generate an element ready to be collected
		else
		{
			element = new Element(WORLD, Box2D.KINEMATIC_BODY, COORDS.x, COORDS.y, 0.6f, 0.4f, 1f, Box2D.getPolygon(96, 96, new Vector2(47, 48)));
			element.setManager(MANAGER);
		}
		
		return element;
	}
	
	public void setManager(FusionScreenManager manager)
	{
		MANAGER = manager;
	}
	
}
