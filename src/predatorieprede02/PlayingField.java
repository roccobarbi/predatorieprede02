package predatorieprede02;

public class PlayingField {

	// private fields
	
	private LinkedOrganism occupant[][]; // A grid of LinkedOrganisms
	
	// Accessors
	
	/**
	 * @param posX
	 * @param posY
	 * @return	the actual address to the occupant at posX, posY, NOT a safe copy
	 */
	public LinkedOrganism getOccupant(int posX, int posY){
		return occupant[posX][posY];
	}

	public PlayingField() {
		// TODO Auto-generated constructor stub
	}
	
	// Public methods
	
	/**
	 * Provides a list of the cells in the immediate vicinity of a specific cell
	 * TODO Create an overloaded method with the range of vision of the caller. 
	 * @param posX
	 * @param posY
	 * @return an array with copies of the 8 occupants around a cell, null if they are empty, or a filler Organism if there are no cells at all.
	 */
	public Organismo[] lookAround(int posX, int posY){
		Organismo output[] = {null, null, null, null, null, null, null, null};
		Organismo filler = new Organismo("filler");
		// TODO provides an array of 8 Organisms around a cell
		return output;
	}
	
	/**
	 * Moves an occupant from its current position to a new one.
	 * No checks are made on the validity of the move, the occupant should do them before moving at this stage.
	 * TODO Implement checks on the validity of the move and manage errors.
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 */
	public void move(int fromX, int fromY, int toX, int toY){
		occupant[toX][toY] = occupant[fromX][fromY];
		occupant[fromX][fromY] = null;
	}
	
	/**
	 * Adds a new occupant to a specific position
	 * @param posX
	 * @param posY
	 * @param pup
	 */
	public void spawn(int posX, int posY, LinkedOrganism pup){
		occupant[posX][posY] = pup;
	}
	
	/**
	 * Shuffles the playing field, updating the position of each organism on it
	 */
	public void shuffle(){
		LinkedOrganism temp;
		int newN = 0, length = occupant.length * occupant[0].length;
		for(int i = 0; i < length; i++){
			newN = i + (int) (Math.random() * (length - i));
			temp = occupant[i / occupant.length][i % occupant.length];
			occupant[i / occupant.length][i % occupant.length] = occupant[newN / occupant.length][newN % occupant.length];
			occupant[newN / occupant.length][newN % occupant.length] = temp;
			if(occupant[i / occupant.length][i % occupant.length] instanceof LinkedOrganism){
				occupant[i / occupant.length][i % occupant.length].setPosX(i / occupant.length);
				occupant[i / occupant.length][i % occupant.length].setPosY(i % occupant.length);
			}
			if (occupant[newN / occupant.length][newN % occupant.length] instanceof LinkedOrganism){
				occupant[newN / occupant.length][newN % occupant.length].setPosX(newN / occupant.length);
				occupant[newN / occupant.length][newN % occupant.length].setPosY(newN % occupant.length);
			}
		}
	}
	
	/**
	 * Removes the current occupant from a specific position
	 * @param posX
	 * @param posY
	 */
	public void remove(int posX, int posY){
		occupant[posX][posY] = null;
	}

}
