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
	
	public Predatore(Predatore parent){
		this(parent.getName(), parent.getRepresentation(), parent.getSpecies(), parent.getInitialNextOffspring(), parent.getMoveProbability(), parent.getInitialDaysUntilStarve());
	}
	
	// Accessors

	/**
	 * This field can only be reset by eat() or reduced by any other function with reduceDaysUntilStarve()
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
	 * Reduces the value for daysUntilStarve and kills the animal if needed
	 * @return	the days until starve
	 */
	public int reduceDaysUntilStarve(){
		if(daysUntilStarve < 1) // To correct any errors that might have happened
			isAlive = false;
		else
			daysUntilStarve--;
		return getDaysUntilStarve();
	}
	/**
	 * It resets the daysUntilStarve. This method should only be called by eat()
	 */
	public void resetDaysUntilStarve(){
		daysUntilStarve = initialDaysUntilStarve;
	}
	
	// Public methods
	
	/**
	 * It overloads the chooseMove() method for Organismo  account for the need to eat.
	 * @param	grid	an array of 8 Organisms or null representing the surrounding area (an organism can be used as filler for unmovable positions)
	 * @return	the direction where it should move (1 up, 3 right, 5 down, 7 left) or -1 if none is found, or -10 if the beast is dead	
	 */
	public int chooseMove(Organismo [] grid){
		int available = 0;
		int destination = 0;
		int move = -10; // Default: the beast is dead and should be removed or managed
		if(isAlive){
			move = -1; // New default: no move.
			// Odd cells in the grid are those where the predator can move
			for(int i = 1; i < 8; i += 2){ 
				if(grid[i] == null) available++;
				if(grid[i] instanceof Preda){ // Prey found, let's go for the kill!
					resetDaysUntilStarve();
					move = i;
					break; // No need to check any other direction for this lazy, naive predator.
				}
			}
			// If there are available cells, chose one and move there
			if(available > 0 && move == -1){ // No prey has been found AND there are empty cells
				destination = (int)(Math.random() * available); // 0, 1, 2 or 3
				for(int i = 1; i < 8 && destination >= 0; i += 2){ 
					if(destination == 0) move = i;
					if(grid[i] == null) destination--;
				}
				if(reduceDaysUntilStarve() == 0){ // If no prey was found, the beast is one day closer to starvation
					move = -10; // The beast is dead, long live the beast!
				}
			}
		}
		return move;
	}
	
	/**
	 * @return	an exact copy of the current Predatore
	 */
	public Predatore copy(){
		return new Predatore(this);
	}

}
