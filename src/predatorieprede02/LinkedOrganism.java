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
		 */
		public void kill(){
			if(field instanceof PlayingField){
				field.remove(posX, posY); // Remove the current element from the playing field
				field = null; // Makes sure that the local link to the playing field is removed
			}
			if(list instanceof LinkedOrganisms){
				list.remove(this); // Cleanly removes the current element from the list
				list = null; // Makes sure that the local link to the list is removed
			}
		}
		
		/**
		 * Chooses a move, changes the position accordingly and updates the field
		 * @throws Exception Invalid move. This is a critical error and it should cause the program to stop.
		 */
		public void act() throws Exception{
			Organismo [] grid;
			int dest, newX = posX, newY = posY;
			Organismo pup;
			LinkedOrganism lPup;
			
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
					throw new Exception("Invalid move: " + dest + "! Should have been 1, 3, 5 or 7.");
				}
				field.move(posX, posY, newX, newY);
				setPosX(newX);
				setPosY(newY);
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
				lPup = new LinkedOrganism(pup, newY, newX, field);
				list.add(lPup);
				field.spawn(newX, newY, lPup);
			}
		}
		
		/**
		 * @return	an Organismo that is a safe copy of the occupant
		 */
		public Organismo reveal(){
			return self.copy();
		}

}
