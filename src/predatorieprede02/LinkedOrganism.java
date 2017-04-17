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
	
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	public int getPosX(){
		return this.posX;
	}
	
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	public int getPosY(){
		return this.posY;
	}
	
	public void setList(LinkedOrganisms list){
		this.list = list;
	}
	
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
	
	public void kill(){
		field.remove(posX, posY); // Remove the current element from the playing field
		list.remove(this); // Cleanly removes the current element from the list
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
