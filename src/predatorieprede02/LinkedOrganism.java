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
	 */
	public void act(){
		// TODO chooses a move, changes the position accordingly and updates the links in the field
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
		//TODO outputs a safe copy of the occupant
		return null;
	}

}
