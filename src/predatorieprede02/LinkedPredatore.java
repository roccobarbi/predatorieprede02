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
			setSelf(predatore);
		}
	
	// Accessors
	
		/**
		 * Outputs the address to the actual encapsulated Predatore object, not a safe copy.
		 * @return the encapsulated Predatore object
		 */
		private Predatore getSelf() {
			return self;
		}
	
		/**
		 * @param self	the Predatore object to set as self
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
		 */
		public void act(){
			Organismo [] grid;
			int dest, newX = getPosX(), newY = getPosY();
			Predatore pup;
			LinkedPredatore lPup;
			PlayingField field = getField();
			String errorMessage;
			
			// Move
			grid = field.lookAround(getPosX(), getPosY());
			dest = getSelf().chooseMove(grid);
			if(dest > -1){
				try{
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
					
					// Check: the position can't be negative
					if(newX < 0 || newY < 0){
						errorMessage = "Invalid move: " + dest + "! X or Y coordinate lower than 0.";
						throw new Exception(errorMessage);
					}
					// Check: the position can't be too large
					if(newX >= field.getXLength() || newY >= field.getYLength()){
						errorMessage = "Invalid move: " + dest + "! X or Y coordinate greater than top limit.";
						throw new Exception(errorMessage);
					}
					
					// Check if there is a Preda at destination and act accordingly
					if(field.getOccupant(newX, newY) == null){ // There is no Preda
						field.move(getPosX(), getPosY(), newX, newY);
						// Check that the movement was performed correctly
						if(field.getOccupant(newX, newY) != this || field.getOccupant(getPosX(), getPosY()) != null){
							errorMessage = "Move failed (without Preda): the field was not updated!";
							throw new Exception(errorMessage);
						}
						setPosX(newX);
						setPosY(newY);
					} else if(field.getOccupant(newX, newY).reveal() instanceof Preda){
						field.getOccupant(newX, newY).kill();
						field.move(getPosX(), getPosY(), newX, newY);
						// Check that the movement was performed correctly
						if(field.getOccupant(newX, newY) != this || field.getOccupant(getPosX(), getPosY()) != null){
							errorMessage = "Move failed (with Preda): the field was not updated!";
							throw new Exception(errorMessage);
						}
						setPosX(newX);
						setPosY(newY);
					} else {
						errorMessage = "Invalid move: " + dest + " is not null or an instance of Preda!";
						throw new Exception(errorMessage);
					}
				} catch (Exception e) {
					field.print();
					System.out.println("Predatore " + this + " at " + getPosX() + ", " + getPosY());
					System.out.println("CRITICAL EXCEPTION DURING MOVEMENT: " + e);
					System.out.println("SHUTTING DOWN THE APPLICATION!");
					System.exit(0);
				}
			}
			if (!getSelf().getIsAlive()) { // If the beast is dead, it kills the beast
				kill();
			}
			
			// Spawn
			if(self.getIsAlive()){
				grid = field.lookAround(getPosX(), getPosY());
				dest = self.chooseSpawn(grid);
				if(dest > -1){
					try{
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
						// Create the new Predatore
						pup = new Predatore(reveal());
						lPup = new LinkedPredatore(pup, newX, newY, field);
						// Safety check: if there is no list, create a new one
						if(getList() == null){
							LinkedOrganisms list = new LinkedOrganisms();
							setList(list);
						}
						// Add the new Predatore to the list
						getList().add(lPup);
						// Check that the addition to the list was performed correctly
						if(!getList().isHere(lPup)){
							errorMessage = "Spawn failed: the list was not updated!";
							throw new Exception(errorMessage);
						}
						// Spawn it to the field
						field.spawn(newX, newY, lPup);
						// Check that the spawn was performed correctly
						if(field.getOccupant(newX, newY) != lPup){
							errorMessage = "Spawn failed: the field was not updated!";
							throw new Exception(errorMessage);
						}
					} catch (Exception e) {
						System.out.println("Predatore " + this + " at " + getPosX() + ", " + getPosY());
						System.out.println("Trying to spawn Predatore at " + newX + ", " + newY);
						System.out.println("CRITICAL EXCEPTION DURING SPAWN: " + e);
						System.out.println("SHUTTING DOWN THE APPLICATION!");
						System.exit(0);
					}
				}
			}
		}
		
		/**
		 * Reveals the encapsulated Predatore object in a safe way.
		 * @return	a Predatore that is a safe copy of the occupant
		 */
		public Predatore reveal(){ // Overloaded to be sure that the type is right
			return getSelf().copy();
		}

}
