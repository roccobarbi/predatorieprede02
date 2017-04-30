package predatorieprede02;

/**
 * The LinkedOrganisms class implements the infrastructure for a LinkedList composed by LinkedOrganism elements
 * or by classes derived from it through inheritance.
 * It contains an "act" method that calls iteratively the act method of its members, which makes it unnecessary
 * for the external program to manage this detail.
 * 
 * @author Rocco Barbini
 * @see LinkedOrganisms
 * @see LinkedPredatore
 */
public class LinkedOrganisms {
	
	// Constructors
	
		public LinkedOrganisms() {
			this.first = null;
			this.last = null;
			this.length = 0;
		}

	// private members
		
		private LinkedOrganism first;
		private LinkedOrganism last;
		private int length;

	// Accessors
		
		/**
		 * @return the current length of the linked list
		 */
		public int getLength(){
			return length;
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
				catch(Exception e){ // TODO an error is generated here: DEBUG and correct
					System.out.println("WHILE the first element of LinkedOrganisms " + this + " was trying to act...");
					System.out.println("CRITICAL ERROR: " + e);
					System.out.println("Stopping execution!");
					System.exit(0);
				}
				for(int i = 1; i < length; i++){
					current = current.getNext();
					try{
						if(!(current instanceof LinkedOrganism)){ // Safety check to avoid unhandled exceptions
							throw new Exception("Null element: tried to access element " + i + " but it is null");
						}
						current.act();
					}
					catch(Exception e){
						System.out.println("WHILE one element of LinkedOrganisms " + this + " was trying to act...");
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
			if(!isHere(organismo)){
				if (length == 0) {
					first = organismo;
				} else if (length == 1) {
					organismo.setPrev(first);
					first.setNext(organismo);
					last = organismo;
				} else {
					organismo.setPrev(last);
					last.setNext(organismo);
					last = organismo;
				}
				if(organismo.getList() != this) organismo.setList(this);
			}
			length++;
		}
		
		/**
		 * Removes the references to a specific LinkedOrganism from the linked list.
		 * @param organismo
		 */
		public void remove(LinkedOrganism organismo){
			if(isHere(organismo)){
				if(organismo == first){
					if(length > 1){
						first = organismo.getNext();
						first.setPrev(null);
					} else {
						first = null;
					}
				} else if (organismo == last) {
					if(length > 1){
						last = organismo.getPrev();
						last.setNext(null);
					} else {
						last = null;
					}
				} else {
					organismo.getPrev().setNext(organismo.getNext());
					organismo.getNext().setPrev(organismo.getPrev());
				}
				length--;
			} else {
				// TODO manage the exception (or throw it)
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
					i++;
				}
			}
			return output;
		}
		
		/**
		 * Override of the standard method toString
		 */
		public String toString(){
			return ("Linked List of LinkedOrganism (or any inherited class) with length " + length + ".");
		}

}
