package predatorieprede02;

/**
 * The Predatore class implements an Organismo that is specialised at moving into Preda organisms to eat
 * them. It moves into Preda organisms whenever possible and it dies if it doesn't eat for a certain
 * number of turns (3 by default). The default spawn time is 8 turns.
 * 
 * @author Rocco Barbini
 * @see Organismo
 * @see Preda
 */
public class Predatore extends Organismo {
	
	// Private fields
	
		private int daysUntilStarve;
		private final int initialDaysUntilStarve;
		private boolean isAlive;

	// Constructors
	
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
		
		/**
		 * It creates a copy of another Predatore.
		 * @param parent	another Predatore
		 */
		public Predatore(Predatore parent){
			this(parent.getName(), parent.getRepresentation(), parent.getSpecies(), parent.getInitialNextOffspring(), parent.getMoveProbability(), parent.getInitialDaysUntilStarve());
		}
	
	// Accessors

		/**
		 * This field can only be reset when the Predatore chooses a move on a Preda.
		 * Any other move choice will reduce it by one.
		 * When it reaches zero, the Predatore is killed.
		 * 
		 * @return the current value for daysUntilStarve
		 */
		public int getDaysUntilStarve() {
			return daysUntilStarve;
		}
	
		/**
		 * The value to which daysUntilStarve can be reset.
		 * 
		 * @return the constant initialDaysUntilStarve for the current instance of Predatore
		 */
		public int getInitialDaysUntilStarve() {
			return initialDaysUntilStarve;
		}
		
		/**
		 * Checks if the Predatore is alive or dead.
		 * 
		 * @return true if alive, false if dead
		 */
		public boolean getIsAlive(){
			return isAlive;
		}
		
		/**
		 * This value can only be set to false, unless it is set by a constructor.
		 * If the value is true and the argument is false, it sets isAlive to false. Otherwise it does nothing.
		 * 
		 * @param isAlive	boolean value that defines the Predatore as alive or dead
		 */
		public void setIsAlive(boolean isAlive){
			if(this.isAlive == true){
				this.isAlive = isAlive;
			}
		}
		
	// Private support methods
		
		// Reduces the value for daysUntilStarve and kills the animal if needed
		private int reduceDaysUntilStarve(){
			if(daysUntilStarve > 0) // To avoid negative values, which would make no sense
				daysUntilStarve--;
			if(daysUntilStarve < 1) // To correct any errors that might have happened
				setIsAlive(false);
			return getDaysUntilStarve();
		}
	
		// It resets the daysUntilStarve.
		// This method should only be called when eating.
		private void resetDaysUntilStarve(){
			daysUntilStarve = initialDaysUntilStarve;
		}
		
	// Public methods
		
		/**
		 * It overloads the chooseMove() method for Organismo to account for the need to eat.
		 * @param	grid	an array of 8 Organisms or null representing the surrounding area (an organism can be used as filler for unmovable positions)
		 * @return	the direction where it should move (1 up, 3 right, 5 down, 7 left) or -1 if none is found, or -10 if the beast is dead	
		 */
		public int chooseMove(Organismo [] grid){
			
			int available = 0;
			int destination = 0;
			int move = -10; // Default: the beast is dead and should be removed or managed
			
			if(getIsAlive()){
				move = -1; // Default if the beast is alive: no move.
				// Odd cells in the grid are those where the predator can move
				for(int i = 1; i < 8; i += 2){ 
					if(grid[i] == null) available++;
					if(grid[i] instanceof Preda){ // Prey found, let's go for the kill!
						resetDaysUntilStarve();
						move = i;
						break; // No need to check any other direction for this lazy, naive predator.
					}
				}
				if(move == -1){ // No prey was found
					reduceDaysUntilStarve(); // Unless a Preda is found, the beast is one day closer to starvation
					// If there are available cells, chose one and move there
					if(available > 0){ // No prey has been found AND there are empty cells
						destination = (int)(Math.random() * available); // 0, 1, 2 or 3
						for(int i = 1; i < 8 && destination >= 0; i += 2){ 
							if(destination == 0) move = i;
							if(grid[i] == null) destination--;
						}
					}
				}
				
				increaseAge(); // If the beast was alive at the beginning of the turn, a turn has passed
			}
			
			// Manage starvation and age
			if (getDaysUntilStarve() < 1) {
				move = -10; // The beast is dead, long live the beast!
			}
			
			return move;
		}
		
		/**
		 * Copies the current Predatore.
		 * @return	a safe copy of the current Predatore.
		 */
		public Predatore copy(){
			return new Predatore(this);
		}
		
		/**
		 * Checks if a Predatore object has the same species, moveProbability, initialNextOffspring and
		 * initialDaysUntilStarve as another.
		 * Differences in name, age and nextOffspring are not taken into account.
		 * 
		 * @return true if they are equal based on the parameters above, false otherwise
		 */
		public boolean equals(Object obj){
			boolean output = false;
			Predatore pred;
			if(obj instanceof Predatore){
				pred = (Predatore) obj;
				if (pred.getSpecies() == getSpecies() &&
						pred.getInitialNextOffspring() == getInitialNextOffspring() &&
						pred.getMoveProbability() == getMoveProbability() &&
						pred.getInitialDaysUntilStarve() == getInitialDaysUntilStarve()) {
					output = true;
				}
			}
			return output;
		}
}
