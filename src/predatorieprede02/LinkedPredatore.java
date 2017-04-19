package predatorieprede02;

public class LinkedPredatore extends LinkedOrganism {

	// Private fields

	private Predatore self; // Overloaded
	
	// Accessors
	
	/**
	 * @return the self
	 */
	public Predatore getSelf() {
		return self;
	}

	/**
	 * @param self the self to set
	 */
	private void setSelf(Predatore self) {
		if(self instanceof Predatore) // To avoid that a null value overwrites a legitimate one
			this.self = self;
	}
	
	// Constructors

	public LinkedPredatore() {
		this(null, -1, -1, null); // Negative positions imply that the organism hasn't been placed on the field
	}
	
	public LinkedPredatore(Predatore predatore, int posX, int posY, PlayingField field){
		this(predatore, posX, posY, field, null);
	}
	
	public LinkedPredatore(Predatore predatore, int posX, int posY, PlayingField field, LinkedOrganisms list) {
		super(predatore, posX, posY, field, list);
		setSelf(predatore);
	}

	// Public methods
	public void act(){ // Overloaded
		// TODO
	}
	
	/**
	 * Outputs a safe copy of the occupant.
	 * @return	a Predatore that is an exact copy of the occupant
	 */
	public Predatore reveal(){ // Overloaded to be sure that the type is right
		return self.copy();
	}

}
