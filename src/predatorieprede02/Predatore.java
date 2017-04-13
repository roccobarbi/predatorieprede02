package predatorieprede02;

public class Predatore extends Organismo {
	
	// Private fields
	private int daysUntilStarve;
	private final int initialDaysUntilStarve;
	private boolean isAlive;

	public Predatore() {
		this("no name");
	}

	public Predatore(String name) {
		this(name, 'X');
	}

	public Predatore(String name, char representation) {
		this(name, representation, "generic predatore");
	}

	public Predatore(String name, char representation, String species) {
		this(name, representation, species, 8);
	}

	public Predatore(String name, char representation, String species, int nextOffspring) {
		this(name, representation, species, nextOffspring, 100);
	}

	public Predatore(String name, char representation, String species, int nextOffspring, int moveProbability) {
		super(name, representation, species, nextOffspring, moveProbability);
		this.initialDaysUntilStarve = 3;
		this.daysUntilStarve = 3;
		isAlive = true;
	}
	
	public Predatore(String name, char representation, String species, int nextOffspring, int moveProbability, int daysUntilStarve) {
		super(name, representation, species, nextOffspring, moveProbability);
		this.initialDaysUntilStarve = daysUntilStarve;
		this.daysUntilStarve = daysUntilStarve;
		isAlive = true;
	}
	
	// Accessors

	/**
	 * This field can only be reset by hunt() or reduced by any other function with reduceDaysUntilStarve()
	 * @return the daysUntilStarve
	 */
	public int getDaysUntilStarve() {
		return daysUntilStarve;
	}

	/**
	 * @return the initialDaysUntilStarve
	 */
	public int getInitialDaysUntilStarve() {
		return initialDaysUntilStarve;
	}
	/**
	 * @return true if alive, false if dead
	 */
	public boolean getIsAlive(){
		return isAlive;
	}
	/**
	 * This value can only be set to false, unless it is set by a constructor.
	 * @param	isAlive	boolean value that defines the Predatore as alive or dead
	 */
	public void setIsAlive(boolean isAlive){
		if(this.isAlive == true){
			this.isAlive = isAlive;
		}
	}
	/**
	 * reduces the value for daysUntilStarve
	 */
	public void reduceDaysUntilStarve(){
		if(daysUntilStarve < 1) // To correct any errors that might have happened
			isAlive = false;
		else
			daysUntilStarve--;
	}
	/**
	 * It resets the daysUntilStarve. This method should only be called by hunt()
	 */
	public void resetDaysUntilStarve(){
		daysUntilStarve = initialDaysUntilStarve;
	}

}
