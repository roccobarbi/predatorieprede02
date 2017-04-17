package predatorieprede02;

public class LinkedOrganisms {

	// private members
	private LinkedOrganism first;
	private LinkedOrganism last;
	private int length;

	// Accessors
	
	public int getLength(){
		return length;
	}
	
	// Constructors
	
	public LinkedOrganisms() {
		this.first = null;
		this.last = null;
		this.length = 0;
	}
	
	// public methods
	/**
	 * Loops through all its elements and makes them act.
	 */
	public void act(){
		LinkedOrganism current = first;
		if(current instanceof LinkedOrganism){
			try{
				current.act();
			}
			catch(Exception e){
				System.out.println("Critical error: " + e);
				System.out.println("Stopping execution!");
				System.exit(0);
			}
			for(int i = 1; i < length; i++){
				current = current.getNext();
				try{
					current.act();
				}
				catch(Exception e){
					System.out.println("Critical error: " + e);
					System.out.println("Stopping execution!");
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * adds a new LinkedOrganism to the list
	 * @param organismo	the new LinkedOrganism that needs to be added to the list
	 */
	public void add(LinkedOrganism organismo){
		last.setNext(organismo);
		last = organismo;
		organismo.setList(this);
		length++;
	}
	
	/**
	 * Removes the references to a specific LinkedOrganism from the linked list.
	 * @param organismo
	 */
	public void remove(LinkedOrganism organismo){
		if(organismo == first){ // I'm checking if the same address is referenced
			if(length > 1){
				first = organismo.getNext();
				first.setPrev(null);
			} else {
				first = null;
			}
			length--;
		} else if (organismo == last) { // I'm checking if the same address is referenced
			if(length > 1){
				last = organismo.getPrev();
				last.setNext(null);
			} else {
				last = null;
			}
			length--;
		} else if(isHere(organismo)){
			organismo.getPrev().setNext(organismo.getNext());
			organismo.getNext().setPrev(organismo.getPrev());
			length--;
		}
	}
	
	/**
	 * Looks for a specific instance of LinkedOrganism in the current linked list.
	 * @param	organismo	the LinkedOrganism that we're looking for
	 * @return	true if it is in the list, false otherwise
	 */
	public boolean isHere(LinkedOrganism organismo){
		boolean output = false;
		if(length > 0){
			int i = 0;
			LinkedOrganism checking = first;
			while(!output && i < length){
				if(checking == organismo){ // This is not an error: I'm checking if the same address is referenced
					output = true;
				}
				if(checking.getNext() != null){
					checking = checking.getNext();
				}
			}
		}
		return output;
	}
	
	/**
	 * Override of the standard method toString
	 */
	public String toString(){
		return ("Linked List of Organismo (or any inherited class) with length " + length + ".");
	}

}
