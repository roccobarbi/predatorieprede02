package predatorieprede02;


public class Organismo {
	
	// Private fields
	private String name;
	private String species;
	private int age;
	private char representation; // a character that represents this organism on the screen
	private int nextOffspring;
	private int moveProbability; // The probability that the animal moves (0 to 100)
	
	private final int initialNextOffspring;
	
	// Accessors
	
	/**
	 * @return the moveProbability
	 */
	public int getMoveProbability() {
		return moveProbability;
	}
	/**
	 * precondition: the moveProbability is between 0 and 100, including the extremes.
	 * @param moveProbability the moveProbability to set
	 */
	public void setMoveProbability(int moveProbability) {
		if(moveProbability > 100)
			this.moveProbability = 100;
		else if (moveProbability < 0)
			this.moveProbability = 0;
		else
			this.moveProbability = moveProbability;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * It returns the current age.
	 * The age can only be increased with the increaseAge method.
	 * It is automatically set to 0 when the Organism is created
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @return the species
	 */
	public String getSpecies() {
		return species;
	}
	/**
	 * @param species the species to set
	 */
	public void setSpecies(String species) {
		this.species = species;
	}
	/**
	 * @return the character used to represent this organism on screen
	 */
	public char getRepresentation() {
		return representation;
	}
	/**
	 * @param	representation	the representation to set
	 */
	public void setRepresentation(char representation) {
		this.representation = representation;
	}
	/**
	 * @return the basic nextOffspring value for this Organism
	 */
	public int getInitialNextOffspring() {
		return this.initialNextOffspring;
	}
	/**
	 * It returns the turns to the next offspring.
	 * This value can only be changed using the loopNextOffspring method, which sets its correct behaviour.
	 * @return the current nextOffspring value for this Organism
	 */
	public int getNextOffspring() {
		return this.nextOffspring;
	}
	
	// Constructors
	
	public Organismo(){
		this("noName");
	}
	public Organismo(String name){
		this(name, 'g');
	}
	public Organismo(String name, char representation){
		this(name, representation, "noSpecies");
	}
	public Organismo(String name, char representation, String species){
		this(name, representation, species, 3);
	}
	public Organismo(String name, char representation, String species, int nextOffspring){
		this(name, representation, species, nextOffspring, 50);
	}
	public Organismo(String name, char representation, String species, int nextOffspring, int moveProbability){
		this.name = name;
		this.species = species;
		this.representation = representation;
		this.initialNextOffspring = nextOffspring;
		this.nextOffspring = nextOffspring;
		this.age = 0;
		setMoveProbability(moveProbability);
	}
	
	// Private methods
	/**
	 * It resets nextOffspring to the original value
	 */
	private void resetNextOffspring(){
		nextOffspring = initialNextOffspring;
	}
	
	// Public methods
	/**
	 * If the organism wants to move (50% of the time), it moves in a random direction chosen from the available ones.
	 * @param	grid	an array of 8 Organisms or null representing the surrounding area (an organism can be used as filler for unmovable positions)
	 * @return	the direction where it should move (1 up, 3 right, 5 down, 7 left) or -1
	 */
	public int chooseMove(Organismo[] grid){
		boolean wantsToMove = Math.random() < (moveProbability / 100.0);
		int available = 0;
		int destination = 0;
		int move = -1;
		if(wantsToMove){
			// Odd cells in the grid are those where the organism can move
			for(int i = 1; i < 8; i += 2){ 
				if(grid[i] == null) available++;
			}
			// If there are available cells, chose one and move there
			if(available > 0){
				destination = (int)(Math.random() * available); // 0, 1, 2 or 3
				for(int i = 1; i < 8 && destination >= 0; i += 2){ 
					if(destination == 0) move = i;
					if(grid[i] == null) destination--;
				}
			}
		}
		return move;
	};
	
	/**
	 * Given a grid with the surrounding cels, it decides wether it should spawn and in which direction.
	 * @param	grid	an array of 8 Organisms or null representing the surrounding area (an organism can be used as filler for unmovable positions)
	 * @return	the direction where it should spawn (1 up, 3 right, 5 down, 7 left) or -1
	 */
	public int chooseSpawn(Organismo[] grid){
		int offspring = -1;
		int available = 0;
		int destination = 0;
		if(this.getNextOffspring() == 0){
			for(int i = 1; i < 8; i +=2){
				if(grid[i] == null) available++; // check if the cell is empty and flag it as available
			}
			// If there are available cells, chose one and spawn there
			if(available > 0){
				destination = (int)(Math.random() * available); // 0, 1, 2 or 3
				for(int i = 1; i < 8 && destination >= 0; i += 2){ 
					if(destination == 0) offspring = i;
					if(grid[i] == null) destination--;
				}
			}
		}
		return offspring;
	};
	
	/**
	 * It prints on screen the character that represents this organism.
	 */
	public void print(){
		System.out.print(this.getRepresentation());
	}
	
	/**
	 * It increases the age for this organism
	 */
	public void increaseAge(){
		this.age++;
	}
	
	/**
	 * It loops to the next available value for nextOffspring
	 */
	public void loopNextOffspring(){
		if(nextOffspring < 1) // To correct any errors that might have happened
			resetNextOffspring();
		else
			nextOffspring--;
	}
}
