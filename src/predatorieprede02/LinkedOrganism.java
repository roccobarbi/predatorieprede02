package predatorieprede02;

/**
 * The LinkedOrganism class encapsulates an Organismo object to make it part of:
 * - a LinkedOrganisms linked list with the active Predatore objects;
 * - a PlayingField object with the active playing field.
 * It contains methods that allow the list and the field to interact with the inner Organismo object indirectly.
 * It also have methods that interact with the list and with the playing field.
 * This way, no direct interaction between the Organismo object on the one hand, and the list and field on the other,
 * should ever be necessary.
 * 
 * @author Rocco Barbini
 * @see LinkedOrganisms
 * @see LinkedPredatore
 * @see PlayingField
 * @see Predatore
 */
public class LinkedOrganism {
	
	// private fields
		private LinkedOrganism prev; // The preceding element in the linked list, or null
		private LinkedOrganism next; // The following element in the linked list, or null
		private Organismo self; // The inner Organismo object
		private int posX; // The X coordinate in the playing field, or -1
		private int posY; // The Y coordinate in the playing field, or -1
		private PlayingField field; // The playing field, or null
		private LinkedOrganisms list; // The linked list of which the instance is a part, or null
	
	// Constructors

		public LinkedOrganism() {
			this(null, -1, -1, null); // Negative positions imply that the organism hasn't been placed on the field
		}
		
		public LinkedOrganism(Organismo organismo, int posX, int posY, PlayingField field){
			this(organismo, posX, posY, field, null);
		}
		
		/**
		 * Precondition: the playing field MUST have a spawn(int posX, int posY, LinkedOrganism element) method.
		 * 
		 * @param organismo	the instance of Organismo that must be encapsulated
		 * @param posX	the position on the X axis of the playing field
		 * @param posY	the position on the Y axis of the playing field
		 * @param field	the playing field
		 * @param list	the linked list
		 */
		public LinkedOrganism(Organismo organismo, int posX, int posY, PlayingField field, LinkedOrganisms list) {
			this.self = organismo;
			this.posX = posX;
			this.posY = posY;
			this.field = field;
			if(field instanceof PlayingField) field.spawn(posX, posY, this);
			this.setList(list);
		}
		
	// Accessors
		
		/**
		 * Sets the next element in the list
		 * @param newNext	the next LinkedOrganism in the list
		 */
		public void setNext(LinkedOrganism newNext){
			next = newNext;
		}
		
		/**
		 * Sets the previous element in the list
		 * @param newPrev	the previous LinkedOrganism in the list
		 */
		public void setPrev(LinkedOrganism newPrev){
			prev = newPrev;
		}
		
		/**
		 * Returns the address of the next element in the list (NOT a safe copy).
		 * 
		 * @return	the next element in the list
		 */
		public LinkedOrganism getNext(){
			return next; // This must not be a safe copy, as it can be used to operate on the list
		}
		
		/**
		 * Returns the address of the previous element in the list (NOT a safe copy)
		 * 
		 * @return	the previous element in the list
		 */
		public LinkedOrganism getPrev(){
			return prev; // This must not be a safe copy, as it can be used to operate on the list
		}
		
		/**
		 * Sets the current position on the X axis, or -1 for any negative number
		 * 
		 * @param posX	the current position on the X axis
		 */
		public void setPosX(int posX){
			if(posX > -1)
				this.posX = posX;
			else
				this.posX = -1;
		}
		
		/**
		 * Returns the current position on the X axis
		 * 
		 * @return	the current position on the X axis, or -1
		 */
		public int getPosX(){
			return this.posX;
		}
		
		/**
		 * Sets the current position on the y axis
		 * @param posY	the current position on the Y axis
		 */
		public void setPosY(int posY){
			if(posY > -1)
				this.posY = posY;
			else
				this.posY = -1;
		}
		
		/**
		 * Returns the current position on the Y axis
		 * 
		 * @return	the current position on the Y axis, or -1
		 */
		public int getPosY(){
			return this.posY;
		}
		
		/**
		 * Precondition: the linked list has add and isHere methods and they work fine:
		 * - isHere() returns true if the argument is part of the linked list;
		 * - add() takes all the measures needed to add the argument to the list.
		 * 
		 * It sets the linked list. It also calles the add method of the linked list to ensure that the current
		 * instance is actually added to it.
		 * 
		 * @param list	the instance of LinkedOrganisms to which this element must be added
		 */
		public void setList(LinkedOrganisms list){
			this.list = list;
			if(list instanceof LinkedOrganisms && !list.isHere(this)) list.add(this);
		}
		
		/**
		 * Returns the address of the playing field for this element (NOT a safe copy)
		 * 
		 * @return the field, or null
		 */
		public PlayingField getField() {
			return field;
		}
	
		/**
		 * Sets the playing field for this element
		 * 
		 * @param field	the field to set
		 */
		public void setField(PlayingField field) {
			this.field = field;
		}
	
		/**
		 * Returns the address of the linked list for this element (NOT a safe copy).
		 * 
		 * @return	the current list (not a safe copy), or null.
		 */
		public LinkedOrganisms getList(){
			return this.list;
		}
	
	// Public methods
	
		/**
		 * Cleanly removes the LinkedOrganism from both the linked list and the field
		 * 
		 * Preconditions:
		 * - the playing field has a remove() method that works as intended;
		 * - the linked list has a remove() method that works as intended.
		 * 
		 * Postconditions:
		 * - the field and list variables are null;
		 * - the field position previously occupied by this instance is null;
		 * - this instance can't be found in the linked list.
		 */
		public void kill(){
			if(field instanceof PlayingField){
				field.remove(getPosX(), getPosY()); // Remove the current element from the playing field
				setField(null); // Makes sure that the local link to the playing field is removed
			}
			if(list instanceof LinkedOrganisms){
				list.remove(this); // Cleanly removes the current element from the list
				setList(null); // Makes sure that the local link to the list is removed
			}
		}
		
		/**
		 * Manages the action that an Organismo is allowed to do during a turn:
		 * - it chooses a move;
		 * - if it is valid, it changes the position accordingly and it updates the field;
		 * - if it's time to do it, it spawns a new Predatore and it adds it to the list and to the field.
		 */
		public void act() {
			Organismo [] grid; // Used to look around the current position when choosing moves and spawns
			int dest; // Possible values: -10, -1, 1, 3, 5, 7
			int newX = posX, newY = posY; // Used to manage movement and spawns
			Organismo pup; // Used to manage spawns
			LinkedOrganism lPup; // Used to manage spawns
			String errorMessage; // Used for cleaner exception management
			
			// Move
			grid = field.lookAround(posX, posY); // Receive a description of the sorroundings
			// Decide the direction for the next movement, if any, and manage the enclosed Organismo's age
			dest = self.chooseMove(grid);
			// Check the direction of movement and set the new grid position accordingly
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
					// Perform the movement in the field
					field.move(getPosX(), getPosY(), newX, newY);
					// Check that the movement was performed correctly
					if(field.getOccupant(newX, newY) != this || field.getOccupant(posX, posY) != null){
						errorMessage = "Move failed: the field was not updated!";
						throw new Exception(errorMessage);
					}
					// Store the new position in the private variables
					setPosX(newX);
					setPosY(newY);
				} catch (Exception e){
					field.print();
					System.out.println("Organismo " + this + " at " + posX + ", " + posY);
					System.out.println("CRITICAL EXCEPTION DURING MOVEMENT: " + e);
					System.out.println("SHUTTING DOWN THE APPLICATION!");
					System.exit(0);
				}
			}
			
			// Spawn
			grid = field.lookAround(posX, posY);
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
						throw new Exception("Invalid spawn: " + dest + "! Should have been 1, 3, 5 or 7.");
					}
					// Check: the position can't be negative
					if(newX < 0 || newY < 0){
						errorMessage = "Invalid move: " + dest + "! X or Y coordinate lower than 0.";
						throw new Exception(errorMessage);
					}
					// Create the new Organismo
					pup = self.copy();
					lPup = new LinkedOrganism(pup, newY, newX, field);
					// Add it to the list
					list.add(lPup);
					// Check that the addition to the list was performed correctly
					if(!list.isHere(lPup)){
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
					System.out.println("Organismo " + this + " at " + posX + ", " + posY);
					System.out.println("Trying to spawn Organismo at " + newX + ", " + newY);
					System.out.println("CRITICAL EXCEPTION DURING SPAWN: " + e);
					System.out.println("SHUTTING DOWN THE APPLICATION!");
					System.exit(0);
				}
			}
		}
		
		/**
		 * @return	an Organismo that is a safe copy of the occupant
		 */
		public Organismo reveal(){
			return self.copy();
		}

}
