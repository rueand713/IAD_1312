package com.randerson.entities;

import java.util.HashMap;

import com.randerson.entities.Particle.ParticleType;
import com.randerson.entities.SubAtomic.ParticleClass;

public class PlayerHandler {

	protected int SCORE = 0;
	protected int LIFETIME_SCORE = 0;
	protected int BONUS = 0;
	protected int[] TIME = {0, 0};
	protected float MULTIPLIER = 1;
	protected float MAX_ELECTRONS = 1;
	protected float MAX_PROTONS = 1;
	protected float MAX_NEUTRONS = 1;
	protected String NAME = "Player 1";
	protected int multiplierTime = 0;
	protected SubAtomic[] SELECTED;
	protected HashMap<String, Achievement> ACHIEVEMENTS;
	
	public PlayerHandler(int time, int multiplier)
	{
		// init the global player values
		TIME[0] = TIME[1] = time;
		MULTIPLIER = multiplier;
		SELECTED = new SubAtomic[2];
		ACHIEVEMENTS = new HashMap<String, Achievement>();
		
		// set the available achievements
		ACHIEVEMENTS.put("reached_level_5", new Achievement("Making Progress", "Reached Level 5"));
		ACHIEVEMENTS.put("reached_level_10", new Achievement("No Letting Up", "Reached Level 10"));
		ACHIEVEMENTS.put("reached_level_25", new Achievement("25 Down", "Reached Level 25"));
		ACHIEVEMENTS.put("reached_level_50", new Achievement("Halftime", "Reached Level 50"));
		ACHIEVEMENTS.put("reached_level_75", new Achievement("Fusion Mogul", "Reached Level 75"));
		ACHIEVEMENTS.put("reached_level_100", new Achievement("Fusion Master", "Reached Level 100"));
		ACHIEVEMENTS.put("earned_10k_points", new Achievement("100 Squared", "Earned 10,000 Points"));
		ACHIEVEMENTS.put("earned_50k_points", new Achievement("50 Stacks", "Earned 50,000 Points"));
		ACHIEVEMENTS.put("earned_200k_points", new Achievement("Silver Status", "Earned 200,000 Points"));
		ACHIEVEMENTS.put("earned_500k_points", new Achievement("Gold Status", "Earned 500,000 Points"));
		ACHIEVEMENTS.put("earned_1m_points", new Achievement("A Millie", "Earned 1,000,000 Points"));
		ACHIEVEMENTS.put("joined_leaderboard", new Achievement("True Competitor", "Signed Up For Leaderboards"));
		ACHIEVEMENTS.put("reached_5x_multiplier", new Achievement("Quintuple", "Obtained A 5x Multiplier"));
		ACHIEVEMENTS.put("failed_to_try", new Achievement("For Quitters Everywhere", "Finished A Game With 0 Points"));
	}
	
	// method for setting the score
	public void setScore(int score)
	{
		SCORE = score;
	}
	
	// method for returning the current score
	public int getScore()
	{
		return SCORE;
	}
	
	// method for setting the overall score obtained throughout all sessions
	public void setLifeTimeScore(int score)
	{
		LIFETIME_SCORE = score;
	}
	
	// method for retrieving player's total score
	public int getLifetimeScore()
	{
		return LIFETIME_SCORE;
	}
	
	// method for getting the achievements object
	public HashMap<String, Achievement> getAchievements()
	{
		return ACHIEVEMENTS;
	}
	
	// method for setting data to the achievements object
	public void addAchievement(String key, Achievement achievement)
	{
		// set the name to its earned state
		ACHIEVEMENTS.put(key, achievement);
	}
	
	// method for adding a full achievement map
	public void setAchievements(HashMap<String, Achievement> achievements)
	{
		ACHIEVEMENTS = achievements;
	}
	
	// method for setting the name
	public void setName(String name)
	{
		NAME = name;
	}
	
	// method for returning the name
	public String getName()
	{
		return NAME;
	}
	
	// method for returning the score multiplier
	public float getMultiplier()
	{
		return MULTIPLIER;
	}
	
	// method for setting the multiplier
	public void setMultiplier(float multiplier)
	{
		MULTIPLIER = multiplier;
	}
	
	public int getMultiplierTime()
	{
		return multiplierTime;
	}
	
	public void setMultipliertime(int time)
	{
		multiplierTime = time;
	}
	
	// method for returning the percent time to reflect on meter
	public float getMeterLength(float meterLength)
	{
		float meter = (TIME[0] / (float) TIME[1]) * meterLength;
		return meter;
	}
	
	// method for setting the time
	public void updateTime(int time)
	{
		TIME[0] = time;
		
		if (TIME[0] > TIME[1])
		{
			TIME[0] = TIME[1];
		}
	}
	
	public int getBonus()
	{
		return BONUS;
	}
	
	public void updateBonus(int value)
	{
		BONUS = value;
	}
	
	// method for returning the time left
	public int getTime()
	{
		return TIME[0];
	}
	
	// returns the atoms selected
	public SubAtomic[] getSelection()
	{
		return SELECTED;
	}
	
	// adds the particle to the selected array
	public void updateSelection(SubAtomic atom)
	{
		if (SELECTED != null && atom != null && atom.getFixture() != null)
		{
			if (SELECTED[1] == null)
			{
				SELECTED[1] = atom;
				
				atom.setSelected(true);
			}
			else if (SELECTED[0] == null)
			{
				SELECTED[0] = atom;
				
				atom.setSelected(true);
			}
			else if (SELECTED[0] != null && SELECTED[1] != null)
			{
				SubAtomic particleA = SELECTED[1];
				SubAtomic particleB = SELECTED[0];
				
				if (particleA != null && particleA.getFixture() != null)
				{
					if (particleA.getID() != atom.getID() && !particleA.equals(atom))
					{
						// replaces the first selection with the second
						SELECTED[0] = particleA;
						
						// adds the new selection as the second
						SELECTED[1] = atom;
						
						atom.setSelected(true);
					}
				} 
				else if (particleB != null && particleB.getFixture() != null)
				{
					if (particleB.getID() != atom.getID() && !particleB.equals(atom))
					{
						// replaces the first selection with the second
						SELECTED[1] = particleB;
						
						// adds the new selection as the second
						SELECTED[0] = atom;
						
						atom.setSelected(true);
					}
				}
			}
		}
		else if (SELECTED == null)
		{
			resetSelection();
		}
	}
	
	// method to return the number of accumulated neutrons
	public int getMaxNeutrons()
	{
		return (int) MAX_NEUTRONS;
	}
	
	// method to return the numberof accumulated protons
	public int getMaxProtons()
	{
		return (int) MAX_PROTONS;
	}
	
	// method for returning the number of accumulated electrons
	public int getMaxElectrons()
	{
		return (int) MAX_ELECTRONS;
	}
	
	// method for checking for matches in the selected items list
	public int calculateSelectionMatches()
	{
		int SCORE = 0; 
		
		// verify that the selection list is full
		if (SELECTED != null)
		{
			// create subatomic class objects from the indexes
			SubAtomic particleA = (SubAtomic) SELECTED[0];
			SubAtomic particleB = (SubAtomic) SELECTED[1];
			
			// verify the objects are valid
			if (particleA != null && particleB != null)
			{
				if (particleA.getFixture() != null && particleB.getFixture() != null)
				{
					// get the string type names for each object
					String particleAName = particleA.getName();
					String particleBName = particleB.getName();
					
					// checks that the class of particle A and B match
					if (particleA.getClassType().equals(ParticleClass.ElementaryParticle.getClassType()) &&
							particleB.getClassType().equals((ParticleClass.ElementaryParticle.getClassType())))
					{
						// check if the names are the same
						if (particleAName.equals(particleBName))
						{
							// adds the score without multiplier bonus
							SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity()));
						}	// check if the names are not the same
						else if (!particleAName.equals(particleBName))
						{
							// adds the score with multiplier bonus
							SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity())) * (int) MULTIPLIER;
						}
					}	// check if the class of particle B matches with that of A
					else if (particleA.getClassType().equals(ParticleClass.Fermion.getClassType()) &&
						particleB.getClassType().equals(ParticleClass.Fermion.getClassType()))
					{
						// check if the names are not the same
						if (!particleAName.equals(particleBName))
						{
							// adds the score with multiplier bonus
							SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity())) * (int) MULTIPLIER;
						}	// check if the names are the same
						else if (particleAName.equals(particleBName))
						{
							// adds the score without multiplier bonus
							SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity()));
						}
					}		// checks if the two selected are both elements but not the same element
					else if (particleA.getClassType().equals(ParticleClass.Element.getClassType()) && 
							particleB.getClassType().equals(ParticleClass.Element.getClassType()))
					{
						// check if the names are not the same
						if (!particleAName.equals(particleBName))
						{
							// adds the score with multiplier bonus
							SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity())) * (int) (MULTIPLIER * 2);
						}
							// check if the names are the same
						else if (particleAName.equals(particleBName))
						{
							// adds the score without multiplier bonus
							SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity()));
						}
					}		// checks if the particles are the same type of particle
					else if (particleAName.equals(particleBName))
					{
						// adds the score but not with a bonus multiplier
						SCORE = ((particleA.getValue() * particleA.getQuantity()) + (particleB.getValue() * particleB.getQuantity()));
					}
				}
			}
		}	// check if only one object is in the list
		else if (SELECTED[0] != null && SELECTED[1] == null)
		{
			// create an element object from the selection
			SubAtomic elementA = (SubAtomic) SELECTED[0];
			
			// verify the object is not null
			if (elementA != null && elementA.getFixture() != null)
			{
				// checks if the selected element is indeed an element
				if (elementA.getClassType().equals(ParticleClass.Element.getClassType()))
				{
					// adds the score without multiplier bonus
					SCORE = (elementA.getValue() * elementA.getQuantity());
				}
			}
		}
		else if (SELECTED == null || SELECTED[0] == null && SELECTED[1] == null)
		{
			resetSelection();
		}
		
		// check for a rewarded score to signal a valid combination
		// and then check to determine the what fermions get a bonus to their max
		if (SCORE > 0)
		{
			String particleAName = SELECTED[0].getName();
			String particleBName = SELECTED[1].getName();
			
			// check if the objects are the same
			// if they are only a single bonus is awarded otherwise a bonus for each
			// particle is awarded
			if (particleAName.equals(particleBName))
			{
				// compare the particle A name to determine what bonus to increase
				if (particleAName.equals(ParticleType.Electron.getName()))
				{
					MAX_ELECTRONS += 0.25f;
				}
				else if (particleAName.equals(ParticleType.Neutron.getName()))
				{
					MAX_NEUTRONS += 0.25f;
				}
				else if (particleAName.equals(ParticleType.Proton.getName()))
				{
					MAX_PROTONS += 0.25f;
				}
			}
			else
			{
				// compare the particle A name to determine what bonus to increase
				if (particleAName.equals(ParticleType.Electron.getName()))
				{
					MAX_ELECTRONS += 0.25f;
				}
				else if (particleAName.equals(ParticleType.Neutron.getName()))
				{
					MAX_NEUTRONS += 0.25f;
				}
				else if (particleAName.equals(ParticleType.Proton.getName()))
				{
					MAX_PROTONS += 0.25f;
				}
				
				// compare the particle B name to determine what bonus to increase
				if (particleBName.equals(ParticleType.Electron))
				{
					MAX_ELECTRONS += 0.25f;
				}
				else if (particleBName.equals(ParticleType.Neutron))
				{
					MAX_NEUTRONS += 0.25f;
				}
				else if (particleBName.equals(ParticleType.Proton))
				{
					MAX_PROTONS += 0.25f;
				}
			}
		}
		
		return SCORE;
	}
	
	// method for resetting the array list
	public void resetSelection()
	{
		SELECTED = new SubAtomic[2];
	}
	
}
