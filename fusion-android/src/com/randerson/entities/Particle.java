package com.randerson.entities;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Particle extends SubAtomic {

	// enum type representation for the particles
	public enum ParticleType {
		Proton(75, "Proton"), Neutron(50, "Neutron"), Electron(25, "Electron"), UpQuark(15, "UpQuark"), DownQuark(10, "DownQuark");
	
		private int value;
		private String name;
		
		// method for setting enum value and name
		private  ParticleType(int value, String name)
		{
			this.value = value;
			this.name = name;
		}
		
		// enum method for getting the particles value
		public int getValue()
		{
			return this.value;
		}
		
		// enum method for getting the particles name
		public String getName()
		{
			return this.name;
		}
	
	};
	
	public Particle(World world, BodyType bodyType, float xOrigin,
			float yOrigin, float restitution, float friction, float density,
			Shape shape) {
		super(world, bodyType, xOrigin, yOrigin, restitution, friction, density, shape);
		
		// assign an id value for the object
		ID = hashCode() + (createID(1) + createID(2));
	}
	
	// method for setting the particle type
	public void setParticle(ParticleType particle)
	{
		// set the score based on the particle
		setValue(particle.getValue());
		
		// set the particle name
		setName(particle.getName());
		
		// set the particle texture and class type
		if (particle == ParticleType.Electron)
		{
			setTexture(MANAGER.electron);
			setClassType(ParticleClass.Fermion);
		}
		else if (particle == ParticleType.Proton)
		{
			setTexture(MANAGER.proton);
			setClassType(ParticleClass.Fermion);
		}
		else if (particle == ParticleType.Neutron)
		{
			setTexture(MANAGER.neutron);
			setClassType(ParticleClass.Fermion);
		}
		else if (particle == ParticleType.DownQuark)
		{
			setTexture(MANAGER.downQuark);
			setClassType(ParticleClass.ElementaryParticle);
		}
		else if (particle == ParticleType.UpQuark)
		{
			setTexture(MANAGER.upQuark);
			setClassType(ParticleClass.ElementaryParticle);
		}
	}

}
