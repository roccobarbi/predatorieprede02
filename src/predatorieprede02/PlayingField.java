package predatorieprede02;

/**
 * The PlayingField class defines a playing field, which can be imagined as a bidimensional array of width X and height Y.
 * 
 * The implementation of this class, or any class derived from it by inheritance, is irrelevant as long as these characteristics are kept:
 * - x describes the horizontal axis, from left to right;
 * - y describes the vertical axis, from top to bottom;
 * - the point (0,0) is at the top left;
 * - xLength describes the length (cardinality) of the x axis;
 * - yLength describes the length (cardinality) of tye y axis;
 * - all coordinates are passed to the public methods in the intuitive (x, y) order.
 * 
 * Each cell in the PlayingField is occupied by a LinkedOrganism, or it is null. When exporting grids from the PlayingField, any element that is
 * outside the field's boundaries (e.g. the line above the points with y = 0) is represented as an Organismo with a moveProbability of 0, the
 * default name "filler" and the default species "filler". 
 * 
 * @author Rocco Barbini
 * @see LinkedOrganism
 */
public class PlayingField {

	// private fields
	
		private static final int defaultWidth = 20;
		private static final int defaultHeight = 20;
		private int xLength, yLength; // The length of X and Y
		
		// Within the class, the actual coordinates are not set as [x][y] but as [y][x], so that the first index
		// actually represents the height (which is more intuitive to visualise, but less intuitive to code).
		// All methods MUST keep this into account and use the (x, y) coordinates inverted.
		private LinkedOrganism occupant[][]; // A grid of LinkedOrganisms
	
	// Constructors
		
		public PlayingField() {
			this(defaultHeight, defaultWidth);
		}
		
		public PlayingField(int x, int y) {
			this.occupant = new LinkedOrganism[y][x];
			this.xLength = x;
			this.yLength = y;
		}
		
	// Accessors
	
		/**
		 * Returns the occupant of a specific cell, not a safe copy.
		 * @param posX	the x coordinate of the element in the playing field
		 * @param posY	the y coordinate of the element in the playing field
		 * @return	the actual address to the occupant at posX, posY, NOT a safe copy
		 */
		public LinkedOrganism getOccupant(int posX, int posY){
			try{
				if(posX < 0 || posY < 0 || posX >= xLength || posY >= yLength){
					throw new Exception("Coordinates out of bounds.");
				}
			} catch (Exception e){
				System.out.println("Critical error: " + e.getMessage());
				System.out.println("Stopping execution");
				System.exit(0);
			}
			return occupant[posY][posX];
		}
		
		/**
		 * @return the length of the X axis (horizontal)
		 */
		public int getXLength(){
			return xLength;
		}
		
		/**
		 * @return the length of the Y axis (vertical)
		 */
		public int getYLength(){
			return yLength;
		}
	
	// Public methods
	
		/**
		 * Provides a list of Organismo elements (or null) around a specific cell, starting from the top left:
		 *  012
		 *  7X3
		 *  654
		 * Cells "out of boundary" are represented by an Organismo with a moveProbability of 0, the default name "filler" and
		 * the default species "filler".
		 * @param posX
		 * @param posY
		 * @return an array with copies of the 8 occupants around a cell, null if they are empty, or a filler Organism if there are no cells at all.
		 */
		public Organismo[] lookAround(int posX, int posY){
			// TODO Create an overloaded method with the range of vision of the caller. 
			Organismo output[] = new Organismo[8];
			Organismo filler = new Organismo("filler", 'F', "filler", 100, 0);
			
			// Fill the output with null elements
			for(int i = 0; i < output.length; i++) output[i] = null;
			
			try{
				if(posX < 0 || posY < 0 || posX >= xLength || posY >= yLength){
					throw new Exception("Coordinates out of bounds.");
				}
				
				// Add the fillers
				if (posX == 0){
					output[0] = filler;
					output[6] = filler;
					output[7] = filler;
				}
				if (posX == xLength - 1) {
					output[2] = filler;
					output[3] = filler;
					output[4] = filler;
				}
				if (posY == 0){
					output[0] = filler;
					output[1] = filler;
					output[2] = filler;
				}
				if (posY == yLength - 1){
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
			} catch (Exception e){
				System.out.println("Critical error: " + e.getMessage());
				System.out.println("Stopping execution");
				System.exit(0);
			}
			return output;
		}
		
		/**
		 * Moves an occupant from its current position to a new one. The preceding position is nullified.
		 * No checks are made on the validity of the move, the occupant should do them before moving at this stage.
		 * @param fromX
		 * @param fromY
		 * @param toX
		 * @param toY
		 */
		public void move(int fromX, int fromY, int toX, int toY){ // X and Y are inverted in the array
			try{
				if(fromX < 0 || fromY < 0 || fromX >= xLength || fromY >= yLength){
					throw new Exception("Coordinates out of bounds.");
				}
				if(toX < 0 || toY < 0 || toX >= xLength || toY >= yLength){
					throw new Exception("Coordinates out of bounds.");
				}
				occupant[toY][toX] = occupant[fromY][fromX];
				occupant[fromY][fromX] = null;
			} catch (Exception e){
				System.out.println("Critical error: " + e.getMessage());
				System.out.println("Stopping execution");
				System.exit(0);
			}
		}
		
		/**
		 * Adds a new occupant to a specific position.
		 * @param posX
		 * @param posY
		 * @param pup
		 */
		public void spawn(int posX, int posY, LinkedOrganism pup){
			try{
				if(posX < 0 || posY < 0 || posX >= xLength || posY >= yLength){
					throw new Exception("Coordinates out of bounds.");
				}
				occupant[posY][posX] = pup;
			} catch (Exception e){
				System.out.println("Critical error: " + e.getMessage());
				System.out.println("Stopping execution");
				System.exit(0);
			}
		}
		
		/**
		 * Shuffles the playing field, updating the position of each organism on it
		 */
		public void shuffle(){
			LinkedOrganism temp;
			int newN = 0, length = xLength * yLength;
			for(int i = 0; i < length; i++){
				newN = i + (int) (Math.random() * (length - i));
				temp = occupant[i / occupant.length][i % occupant.length];
				occupant[i / occupant.length][i % occupant.length] = occupant[newN / occupant.length][newN % occupant.length];
				occupant[newN / occupant.length][newN % occupant.length] = temp;
				// Update the coordinates stored within each moved element
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
		 * Removes the current occupant from a specific position.
		 * @param posX
		 * @param posY
		 */
		public void remove(int posX, int posY){
			occupant[posY][posX] = null;
		}
		
		/**
		 * Prints the field on screen
		 */
		public void print(){
			char item = ' ';
			System.out.println();
			System.out.print(" ");
			for(int k = 1; k < xLength + 1; k++){
				System.out.print("-");
			}
			System.out.println();
			for(int i = 0; i < yLength; i++){
				System.out.print("|");
				for(int k = 0; k < xLength; k++){
					item = occupant[i][k] != null ? occupant[i][k].reveal().getRepresentation() : ' '; 
					System.out.print(item);
				}
				System.out.print("|");
				System.out.println();
			}
			System.out.print(" ");
			for(int k = 1; k < occupant[0].length + 1; k++){
				System.out.print("-");
			}
			System.out.println();
		}

}
