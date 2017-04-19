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
	public Organismo[] lookAround(int posX, int posY){
		Organismo output[] = {null, null, null, null, null, null, null, null};
		// TODO provides an array of 8 Organisms around a cell
		return output;
	}
	
	public void move(int fromX, int fromY, int toX, int toY){
		// TODO moves the content of a cell to another
	}
	
	public void spawn(int posX, int posY, LinkedOrganism pup){
		// TODO sets a LinkedOrganism to a cell
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
