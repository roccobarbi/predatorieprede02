package predatorieprede02;

public class Preda extends Organismo {

	public Preda() {
		this("no name");
	}

	public Preda(String name) {
		this(name, 'o');
	}

	public Preda(String name, char representation) {
		this(name, representation, "generic preda");
	}

	public Preda(String name, char representation, String species) {
		this(name, representation, species, 3);
	}

	public Preda(String name, char representation, String species, int nextOffspring) {
		this(name, representation, species, nextOffspring, 50);
	}

	public Preda(String name, char representation, String species, int nextOffspring, int moveProbability) {
		super(name, representation, species, nextOffspring, moveProbability);
	}
	
	/*
	 *  TODO: in the future, I might want to create differents species that react differently
	 *  to other species, that look for food (if there exists some food for preys) and that
	 *  try to avoid predators.
	 */

}
