package predatorieprede02;

public class PlayingField {

	// private fields
	private LinkedOrganism occupant[][]; // A grid of LinkedOrganisms

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
	
	public void spawn(int posX, int posY, Organismo pup){
		// TODO sets an Organismo to a cell
	}
	
	/**
	 * It removes the current occupant from a specific position
	 * @param posX
	 * @param posY
	 */
	public void remove(int posX, int posY){
		occupant[posX][posY] = null;
	}

}
