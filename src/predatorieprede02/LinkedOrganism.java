package predatorieprede02;

public class LinkedOrganism {
	
	// private fields
	private LinkedOrganism prev;
	private LinkedOrganism next;
	private Organismo self;
	private int posX;
	private int posY;
	private PlayingField field;
	private LinkedOrganisms list;
	
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
	 * Returns the next element in the list (not a safe copy).
	 * @return	the next element in the list
	 */
	public LinkedOrganism getNext(){
		return next; // This must not be a safe copy, as it can be used to operate on the list
	}
	
	/**
	 * Returns the previous element in the list (not a safe copy)
	 * @return	the previous element in the list
	 */
	public LinkedOrganism getPrev(){
		return prev; // This must not be a safe copy, as it can be used to operate on the list
	}
	
	/**
	 * Sets the current position on the X axis
	 * @param posX	the current position on the X axis
	 */
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	/**
	 * Returns the current position on the X axis
	 * @return	the current position on the X axis
	 */
	public int getPosX(){
		return this.posX;
	}
	
	/**
	 * Sets the current position on the y axis
	 * @param posY	the current position on the Y axis
	 */
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	/**
	 * Returns the current position on the Y axis
	 * @return	the current position on the Y axis
	 */
	public int getPosY(){
		return this.posY;
	}
	
	/**
	 * Sets the list
	 * @param list
	 */
	public void setList(LinkedOrganisms list){
		this.list = list;
	}
	
	/**
	 * @return the field
	 */
	public PlayingField getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(PlayingField field) {
		this.field = field;
	}

	/**
	 * Returns the list.
	 * @return	the current list (not a safe copy)
	 */
	public LinkedOrganisms getList(){
		return this.list;
	}
	
	// Constructors

	public LinkedOrganism() {
		this(null, -1, -1, null); // Negative positions imply that the organism hasn't been placed on the field
	}
	
	public LinkedOrganism(Organismo organismo, int posX, int posY, PlayingField field){
		this(organismo, posX, posY, field, null);
	}
	
	public LinkedOrganism(Organismo organismo, int posX, int posY, PlayingField field, LinkedOrganisms list) {
		this.self = organismo;
		this.posX = posX;
		this.posY = posY;
		this.field = field;
		if(field instanceof PlayingField) field.spawn(posX, posY, this);
		this.list = list;
	}
	
	// Public methods
	
	/**
	 * Cleanly removes the LinkedOrganism from both the linked list and the field
	 */
	public void kill(){
		field.remove(posX, posY); // Remove the current element from the playing field
		list.remove(this); // Cleanly removes the current element from the list
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
			posX = newX;
			posY = newY;
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
			lPup = new LinkedOrganism(pup, newX, newY, field);
			list.add(lPup);
			field.spawn(newX, newY, lPup);
		}
	}
	
	public void set(){
		// TODO positions the element on the field for the first time
		// MIGHT BE USELESS
	}
	
	/**
	 * Outputs a safe copy of the occupant.
	 * @return	an Organismo that is an exact copy of the occupant
	 */
	public Organismo reveal(){
		return self.copy();
	}

}
