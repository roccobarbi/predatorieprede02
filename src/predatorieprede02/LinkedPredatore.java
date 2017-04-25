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

	/**
	 * Chooses a move, changes the position accordingly and updates the field.
	 * After the move, if this is appropriate, it spawns a new Predatore.
	 * If the Predatore dies of starvation, it removes the LinkedPredatore from the field and from the list.
	 * @throws Exception Invalid move. This is a critical error and it should cause the program to stop.
	 */
	public void act() throws Exception{
		Organismo [] grid;
		int posX = getPosX(), posY = getPosY();
		int dest, newX = posX, newY = posY;
		Predatore pup;
		LinkedPredatore lPup;
		PlayingField field = getField();
		
		// Move
		grid = field.lookAround(posX, posY);
		dest = self.chooseMove(grid);
		if(dest > -1){
			System.out.println(dest + " : " + grid[dest]); // DEBUG: remove after debugging
			switch(dest){
			case 1:
				newY--;
				break;
			case 3:
				newX++;
				break;
			case 5:
				newY++;
				break;
			case 7:
				newX--;
				break;
			default:
				throw new Exception("Invalid move: " + dest + "! Should have been 1, 3, 5 or 7.");
			}
			if(field.getOccupant(newX, newY) == null){
				field.move(posX, posY, newX, newY);
				setPosX(newX);
				setPosY(newY);
			} else if(field.getOccupant(newX, newY).reveal() instanceof Preda){
				field.getOccupant(newX, newY).kill();
				field.move(posX, posY, newX, newY);
				setPosX(newX);
				setPosY(newY);
			} else {
				throw new Exception("Invalid move: " + dest + " is not an instance of Preda!");
			}
		}
		if (!self.getIsAlive()) { // If the beast is dead, it kills the beast
			kill();
		}
		
		// Spawn
		grid = field.lookAround(posX, posY);
		dest = self.chooseSpawn(grid);
		if(dest > -1){
			switch(dest){
			case 1:
				newY--;
				break;
			case 3:
				newX++;
				break;
			case 5:
				newY++;
				break;
			case 7:
				newX--;
				break;
			default:
				throw new Exception("Invalid spawn: " + dest + "! Should have been 1, 3, 5 or 7.");
			}
			pup = self.copy();
			lPup = new LinkedPredatore(pup, newX, newY, field);
			getList().add(lPup);
			field.spawn(newX, newY, lPup);
		}
	}
	
	/**
	 * Outputs a safe copy of the occupant.
	 * @return	a Predatore that is an exact copy of the occupant
	 */
	public Predatore reveal(){ // Overloaded to be sure that the type is right
		return self.copy();
	}

}
