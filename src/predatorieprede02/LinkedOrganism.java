package predatorieprede02;

public class LinkedOrganism {
	
	// private fields
	private LinkedOrganism prev;
	private LinkedOrganism next;
	private Organismo self;
	private int posX;
	private int posY;
	private PlayingField field;
	
	// Accessors
	public void setNext(LinkedOrganism newNext){
		next = newNext;
	}
	
	public void setPrev(LinkedOrganism newPrev){
		prev = newPrev;
	}
	
	public LinkedOrganism getNext(){
		return next; // This must not be a safe copy, as it can be used to operate on the list
	}
	
	public LinkedOrganism getPrev(){
		return prev; // This must not be a safe copy, as it can be used to operate on the list
	}
	
	// Constructors

	public LinkedOrganism() {
		// TODO Auto-generated constructor stub
	}
	
	// Public methods
	
	public void kill(){
		// TODO eliminates the links to self from the field and the linked list
	}
	
	public void act(){
		// TODO chooses a move, changes the position accordingly and updates the links in the field
	}
	
	public void set(){
		// TODO positions the element on the field for the first time
		// MIGHT BE USELESS
	}
	
	public Organismo reveal(){
		//TODO outputs a safe copy of the occupant
		return null;
	}

}
