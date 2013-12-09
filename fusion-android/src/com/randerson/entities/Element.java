package com.randerson.entities;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Element extends SubAtomic {

	protected int MASS_NUMBER = 1;
	
	public Element(World world, BodyType bodyType, float xOrigin,
			float yOrigin, float restitution, float friction, float density,
			Shape shape) {
		super(world, bodyType, xOrigin, yOrigin, restitution, friction, density, shape);
		
		// set the class type
		setClassType(ParticleClass.Element);
		
		// assign an id value for the object
		ID = hashCode() + (createID(1) + createID(2));
	}
	
	// method for setting the mass number
	public void setMassNumber(int massNumber)
	{
		// verify that the mass number is valid
		// if not set it to hydrogen
		if (massNumber <1 || massNumber > 118)
		{
			massNumber = 1;
		}
		
		// set the mass number
		MASS_NUMBER = massNumber;
		
		// calculate the score
		calculateScore();
		
		determineElement();
	}
	
	// method for recalculating the score
	private void calculateScore()
	{
		setValue(100 * MASS_NUMBER);
	}
	
	// method for setting element by mass number
	private void determineElement()
	{	
		switch(MASS_NUMBER)
		{
		case 1:
			setName("Hydrogen");
			setTexture(MANAGER.hydrogen);
			break;
			
		case 2:
			setName("Helium");
			setTexture(MANAGER.helium);
			break;
			
		case 3:
			setName("Lithium");
			setTexture(MANAGER.lithium);
			break;
			
		case 4:
			setName("Beryllium");
			setTexture(MANAGER.beryllium);
			break;
			
		case 5:
			setName("Boron");
			setTexture(MANAGER.boron);
			break;
			
		case 6:
			setName("Carbon");
			setTexture(MANAGER.carbon);
			break;
			
		case 7:
			setName("Nitrogen");
			setTexture(MANAGER.nitrogen);
			break;
			
		case 8:
			setName("Oxygen");
			setTexture(MANAGER.oxygen);
			break;
			
		case 9:
			setName("Fluorine");
			setTexture(MANAGER.fluorine);
			break;
			
		case 10:
			setName("Neon");
			setTexture(MANAGER.neon);
			break;
			
		case 11:
			setName("Sodium");
			setTexture(MANAGER.sodium);
			break;
			
		case 12:
			setName("Magnesium");
			setTexture(MANAGER.magnesium);
			break;
			
		case 13:
			setName("Aluminum");
			setTexture(MANAGER.aluminum);
			break;
			
		case 14:
			setName("Silicon");
			setTexture(MANAGER.silicon);
			break;
			
		case 15:
			setName("Phosphorus");
			setTexture(MANAGER.phosphorus);
			break;
			
		case 16:
			setName("Sulfur");
			setTexture(MANAGER.sulfur);
			break;
			
		case 17:
			setName("Chlorine");
			setTexture(MANAGER.chlorine);
			break;
			
		case 18:
			setName("Argon");
			setTexture(MANAGER.argon);
			break;
			
		case 19:
			setName("Potassium");
			setTexture(MANAGER.potassium);
			break;
			
		case 20:
			setName("Calcium");
			setTexture(MANAGER.calcium);
			break;
			
		case 21:
			setName("Scandium");
			setTexture(MANAGER.scandium);
			break;
			
		case 22:
			setName("Titanium");
			setTexture(MANAGER.titanium);
			break;
			
		case 23:
			setName("Vanadium");
			setTexture(MANAGER.vanadium);
			break;
			
		case 24:
			setName("Chromium");
			setTexture(MANAGER.chromium);
			break;
			
		case 25:
			setName("Manganese");
			setTexture(MANAGER.manganese);
			break;
			
		case 26:
			setName("Iron");
			setTexture(MANAGER.iron);
			break;
			
		case 27:
			setName("Cobalt");
			setTexture(MANAGER.cobalt);
			break;
			
		case 28:
			setName("Nickel");
			setTexture(MANAGER.nickel);
			break;
			
		case 29:
			setName("Copper");
			setTexture(MANAGER.copper);
			break;
			
		case 30:
			setName("Zinc");
			setTexture(MANAGER.zinc);
			break;
			
		case 31:
			setName("Gallium");
			setTexture(MANAGER.gallium);
			break;
			
		case 32:
			setName("Germanium");
			setTexture(MANAGER.germanium);
			break;
			
		case 33:
			setName("Arsenic");
			setTexture(MANAGER.arsenic);
			break;
			
		case 34:
			setName("Selenium");
			setTexture(MANAGER.selenium);
			break;
			
		case 35:
			setName("Bromine");
			setTexture(MANAGER.bromine);
			break;
			
		case 36:
			setName("Krypton");
			setTexture(MANAGER.krypton);
			break;
			
		case 37:
			setName("Rubidium");
			setTexture(MANAGER.rubidium);
			break;
			
		case 38:
			setName("Strontium");
			setTexture(MANAGER.strontium);
			break;
			
		case 39:
			setName("Yttrium");
			setTexture(MANAGER.yttrium);
			break;
			
		case 40:
			setName("Zirconium");
			setTexture(MANAGER.zirconium);
			break;
			
		case 41:
			setName("Niobium");
			setTexture(MANAGER.niobium);
			break;
			
		case 42:
			setName("Molybdenum");
			setTexture(MANAGER.molybdenum);
			break;
			
		case 43:
			setName("Technetium");
			setTexture(MANAGER.technetium);
			break;
			
		case 44:
			setName("Ruthenium");
			setTexture(MANAGER.ruthenium);
			break;
			
		case 45:
			setName("Rhodium");
			setTexture(MANAGER.rhodium);
			break;
			
		case 46:
			setName("Palladium");
			setTexture(MANAGER.palladium);
			break;
			
		case 47:
			setName("Silver");
			setTexture(MANAGER.silver);
			break;
			
		case 48:
			setName("Cadmium");
			setTexture(MANAGER.cadmium);
			break;
			
		case 49:
			setName("Indium");
			setTexture(MANAGER.indium);
			break;
			
		case 50:
			setName("Tin");
			setTexture(MANAGER.tin);
			break;
			
		case 51:
			setName("Antimony");
			setTexture(MANAGER.antimony);
			break;
			
		case 52:
			setName("Tellurium");
			setTexture(MANAGER.tellurium);
			break;
			
		case 53:
			setName("Iodine");
			setTexture(MANAGER.iodine);
			break;
			
		case 54:
			setName("Xenon");
			setTexture(MANAGER.xenon);
			break;
			
		case 55:
			setName("Cesium");
			setTexture(MANAGER.cesium);
			break;
			
		case 56:
			setName("Barium");
			setTexture(MANAGER.barium);
			break;
			
		case 57:
			setName("Lanthanum");
			setTexture(MANAGER.lanthanum);
			break;
			
		case 58:
			setName("Cerium");
			setTexture(MANAGER.cerium);
			break;
			
		case 59:
			setName("Praseodymium");
			setTexture(MANAGER.praseodymium);
			break;
			
		case 60:
			setName("Neodymium");
			setTexture(MANAGER.neodymium);
			break;
			
		case 61:
			setName("Promethium");
			setTexture(MANAGER.promethium);
			break;
			
		case 62:
			setName("Samarium");
			setTexture(MANAGER.samarium);
			break;
			
		case 63:
			setName("Europium");
			setTexture(MANAGER.europium);
			break;
			
		case 64:
			setName("Gadolinium");
			setTexture(MANAGER.gadolinium);
			break;
			
		case 65:
			setName("Terbium");
			setTexture(MANAGER.terbium);
			break;
			
		case 66:
			setName("Dysprosium");
			setTexture(MANAGER.dysprosium);
			break;
			
		case 67:
			setName("Holmium");
			setTexture(MANAGER.holmium);
			break;
			
		case 68:
			setName("Erbium");
			setTexture(MANAGER.erbium);
			break;
			
		case 69:
			setName("Thulium");
			setTexture(MANAGER.thulium);
			break;
			
		case 70:
			setName("Ytterbium");
			setTexture(MANAGER.ytterbium);
			break;
			
		case 71:
			setName("Lutetium");
			setTexture(MANAGER.lutetium);
			break;
			
		case 72:
			setName("Hafnium");
			setTexture(MANAGER.hafnium);
			break;
			
		case 73:
			setName("Tantalum");
			setTexture(MANAGER.tantalum);
			break;
			
		case 74:
			setName("Tungsten");
			setTexture(MANAGER.tungsten);
			break;
			
		case 75:
			setName("Rhenium");
			setTexture(MANAGER.rhenium);
			break;
			
		case 76:
			setName("Osmium");
			setTexture(MANAGER.osmium);
			break;
			
		case 77:
			setName("Iridium");
			setTexture(MANAGER.iridium);
			break;
			
		case 78:
			setName("Platinum");
			setTexture(MANAGER.platinum);
			break;
			
		case 79:
			setName("Gold");
			setTexture(MANAGER.gold);
			break;
			
		case 80:
			setName("Mercury");
			setTexture(MANAGER.mercury);
			break;
			
		case 81:
			setName("Thallium");
			setTexture(MANAGER.thallium);
			break;
			
		case 82:
			setName("Lead");
			setTexture(MANAGER.lead);
			break;
			
		case 83:
			setName("Bismuth");
			setTexture(MANAGER.bismuth);
			break;
			
		case 84:
			setName("Polonium");
			setTexture(MANAGER.polonium);
			break;
			
		case 85:
			setName("Astatine");
			setTexture(MANAGER.astatine);
			break;
			
		case 86:
			setName("Radon");
			setTexture(MANAGER.radon);
			break;
			
		case 87:
			setName("Francium");
			setTexture(MANAGER.francium);
			break;
			
		case 88:
			setName("Radium");
			setTexture(MANAGER.radium);
			break;
			
		case 89:
			setName("Actinium");
			setTexture(MANAGER.actinium);
			break;
			
		case 90:
			setName("Thorium");
			setTexture(MANAGER.thorium);
			break;
			
		case 91:
			setName("Protactinium");
			setTexture(MANAGER.protactinium);
			break;
			
		case 92:
			setName("Uranium");
			setTexture(MANAGER.uranium);
			break;
			
		case 93:
			setName("Neptunium");
			setTexture(MANAGER.neptunium);
			break;
			
		case 94:
			setName("Plutonium");
			setTexture(MANAGER.plutonium);
			break;
			
		case 95:
			setName("Americium");
			setTexture(MANAGER.americium);
			break;
			
		case 96:
			setName("Curium");
			setTexture(MANAGER.curium);
			break;
			
		case 97:
			setName("Berkelium");
			setTexture(MANAGER.berkelium);
			break;
			
		case 98:
			setName("Californium");
			setTexture(MANAGER.californium);
			break;
			
		case 99:
			setName("Einsteinium");
			setTexture(MANAGER.einsteinium);
			break;
			
		case 100:
			setName("Fermium");
			setTexture(MANAGER.fermium);
			break;
			
		case 101:
			setName("Mendelevium");
			setTexture(MANAGER.mendelevium);
			break;
			
		case 102:
			setName("Nobelium");
			setTexture(MANAGER.nobelium);
			break;
			
		case 103:
			setName("Lawrencium");
			setTexture(MANAGER.lawrencium);
			break;
			
		case 104:
			setName("Rutherfordium");
			setTexture(MANAGER.rutherfordium);
			break;
			
		case 105:
			setName("Dubnium");
			setTexture(MANAGER.dubnium);
			break;
			
		case 106:
			setName("Seaborgium");
			setTexture(MANAGER.seaborgium);
			break;
			
		case 107:
			setName("Bohrium");
			setTexture(MANAGER.bohrium);
			break;
			
		case 108:
			setName("Hassium");
			setTexture(MANAGER.hassium);
			break;
			
		case 109:
			setName("Meitnerium");
			setTexture(MANAGER.meitnerium);
			break;
			
		case 110:
			setName("Darmstadtium");
			setTexture(MANAGER.darmstadtium);
			break;
			
		case 111:
			setName("Roentgenium");
			setTexture(MANAGER.roentgenium);
			break;
			
		case 112:
			setName("Copernicium");
			setTexture(MANAGER.copernicium);
			break;
			
		case 113:
			setName("Ununtrium");
			setTexture(MANAGER.ununtrium);
			break;
			
		case 114:
			setName("Flerovium");
			setTexture(MANAGER.flerovium);
			break;
			
		case 115:
			setName("Ununpentium");
			setTexture(MANAGER.ununpentium);
			break;
			
		case 116:
			setName("Livermorium");
			setTexture(MANAGER.livermorium);
			break;
			
		case 117:
			setName("Ununseptium");
			setTexture(MANAGER.ununseptium);
			break;
			
		case 118:
			setName("Ununoctium");
			setTexture(MANAGER.ununoctium);
			break;
			
			default:
				break;
		}
	}

}
