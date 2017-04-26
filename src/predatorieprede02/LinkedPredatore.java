package predatorieprede02;

/**
 * The LinkedPredatore class extends the LinkedOrganism class. It encapsulates a Predatore object to make it part of:
 * - a LinkedOrganisms linked list with the active Predatore objects;
 * - a PlayingField object with the active playing field.
 * It contains methods that allow the list and the field to interact with the inner Predatore object indirectly.
 * It also have methods that interact with the list and with the playing field.
 * This way, no direct interaction between the Predatore object on the one hand, and the list and field on the other,
 * should ever be necessary.
 * 
 * @author Rocco Barbini
 * @see LinkedOrganism
 * @see LinkedOrganisms
 * @see PlayingField
 * @see Predatore
 */
public class LinkedPredatore extends LinkedOrganism {

	// Private fields

		private Predatore self; // Overloaded from LinkedOrganism
	
	// Constructors

		public LinkedPredatore() {
			this(null, -1, -1, null); // Negative positions imply that the organism hasn't been placed on the field
		}
		
		public LinkedPredatore(Predatore predatore, int posX, int posY, PlayingField field){
			this(predatore, posX, posY, field, null);
		}
		
		public LinkedPredatore(Predatore predatore, int posX, int posY, PlayingField field, LinkedOrganisms list) {
			super(predatore, posX, posY, field, list);
			// setSelf(predatore);
		}
	
	// Accessors
	
		/**
		 * @return the encapsulated Predatore object
		 */
		public Predatore getSelf() {
			return self;
		}
	
		/**
		 * @param self the self to set
		 */
		private void setSelf(Predatore self) {
			this.self = self;
		}

	// Public methods

		/**
		 * Manages the action that a Predatore is allowed to do during a turn:
		 * - it chooses a move;
		 * - if it is valid, it changes the position accordingly and it updates the field;
		 * - it manages food and starvation;
		 * - if it's time to do it, it spawns a new Predatore and it adds it to the list and to the field.
		 * If the Predatore dies of starvation, it removes the LinkedPredatore from the field and from the list.
		 * 
		 * @throws Exception Invalid move. This is a critical error and it should cause the program to stop.
		 * @throws Exception Invalid spawn. This is a critical error and it should cause the program to stop.
		 */
		public void act() throws Exception{
			Organismo [] grid;
			int posX = getPosX(), posY = getPosY();
			int dest, newX = posX, newY = posY;
			Predatore pup;
			LinkedPredatore lPup;
			PlayingField field = getField();
			String errorMessage;
			
			// Move
			grid = field.lookAround(posX, posY);
			dest = self.chooseMove(grid);
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
					errorMessage = "Invalid move: " + dest + "! Should have been 1, 3, 5 or 7.";
					throw new Exception(errorMessage);
				}
				if(newX < 0 || newY < 0){
					errorMessage = "Invalid move: " + dest + "! X or Y coordinate lower than 0.";
					throw new Exception(errorMessage);
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
					errorMessage = "Invalid move: " + dest + " is not null or an instance of Preda!";
					throw new Exception(errorMessage);
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
					errorMessage = "Invalid spawn: " + dest + "! Should have been 1, 3, 5 or 7.";
					throw new Exception(errorMessage);
				}
				pup = self.copy();
				lPup = new LinkedPredatore(pup, newX, newY, field);
				getList().add(lPup);
				field.spawn(newX, newY, lPup);
			}
		}
		
		/**
		 * Reveals the encapsulated Predatore object in a safe way.
		 * @return	a Predatore that is a safe copy of the occupant
		 */
		public Predatore reveal(){ // Overloaded to be sure that the type is right
			return self.copy();
		}

}
