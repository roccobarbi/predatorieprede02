package predatorieprede02;

public class PlayingField {

	// private fields
	
	private static final int defaultWidth = 20;
	private static final int defaultHeight = 20;
	private LinkedOrganism occupant[][]; // A grid of LinkedOrganisms
	
	// Accessors
	
	/**
	 * Returns the occupant of a specific cell, not a safe copy. The inversion of the coordinates is managed by the funcion,
	 * so the caller does not have to bother with it.
	 * @param posX
	 * @param posY
	 * @return	the actual address to the occupant at posX, posY, NOT a safe copy
	 */
	public LinkedOrganism getOccupant(int posX, int posY){
		return occupant[posY][posX];
	}

	public PlayingField() {
		this(defaultHeight, defaultWidth);
	}
	
	public PlayingField(int w, int h) {
		this.occupant = new LinkedOrganism[h][w];
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
		// Add the fillers
		if (posX == 0){
			output[0] = filler;
			output[6] = filler;
			output[7] = filler;
		}
		if (posX == occupant[0].length-1) {
			output[2] = filler;
			output[3] = filler;
			output[4] = filler;
		}
		if (posY == 0){
			output[0] = filler;
			output[1] = filler;
			output[2] = filler;
		}
		if (posY == occupant.length-1){
			output[4] = filler;
			output[5] = filler;
			output[6] = filler;
		}
		// Check everything else
		for(int i = 0; i < output.length; i++){
			// IF not filler, fill it
			if(output[i] != filler){
				switch(i){
				case 0:
					output[i] = occupant[posY - 1][posX - 1] == null ? null : occupant[posY - 1][posX - 1].reveal();
					break;
				case 1:
					output[i] = occupant[posY - 1][posX] == null ? null : occupant[posY - 1][posX].reveal();
					break;
				case 2:
					output[i] = occupant[posY - 1][posX + 1] == null ? null : occupant[posY - 1][posX + 1].reveal();
					break;
				case 3:
					output[i] = occupant[posY][posX + 1] == null ? null : occupant[posY][posX + 1].reveal();
					break;
				case 4:
					output[i] = occupant[posY + 1][posX + 1] == null ? null : occupant[posY + 1][posX + 1].reveal();
					break;
				case 5:
					output[i] = occupant[posY + 1][posX] == null ? null : occupant[posY + 1][posX].reveal();
					break;
				case 6:
					output[i] = occupant[posY + 1][posX - 1] == null ? null : occupant[posY + 1][posX - 1].reveal();
					break;
				case 7:
					output[i] = occupant[posY][posX - 1] == null ? null : occupant[posY][posX - 1].reveal();
					break;
				}
			}
		}
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
	public void move(int fromY, int fromX, int toY, int toX){ // X and Y are inverted in the array
		occupant[toY][toX] = occupant[fromY][fromX];
		occupant[fromY][fromX] = null;
	}
	
	/**
	 * Adds a new occupant to a specific position
	 * @param posX
	 * @param posY
	 * @param pup
	 */
	public void spawn(int posY, int posX, LinkedOrganism pup){
		occupant[posY][posX] = pup;
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
	
	/**
	 * Prints the field on screen
	 */
	public void print(){
		char item = ' ';
		System.out.println();
		for(int i = 0; i < occupant.length; i++){
			for(int k = 0; k < occupant[i].length; k++){
				item = occupant[i][k] != null ? occupant[i][k].reveal().getRepresentation() : ' '; 
				System.out.print(item);
			}
			System.out.println();
		}
	}

}
