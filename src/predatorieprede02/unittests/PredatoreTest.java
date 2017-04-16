package predatorieprede02.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import predatorieprede02.Organismo;
import predatorieprede02.Predatore;
import predatorieprede02.Preda;

public class PredatoreTest {

	@Test
	public void testGetInitialDaysUntilStarve() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("Wrong initialDaysUntilStarve from the beginning", predatore0.getInitialDaysUntilStarve() == 4);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong initialDaysUntilStarve after calling reduceDaysUntilStarve twice", predatore0.getInitialDaysUntilStarve() == 4);
	}
	
	@Test
	public void testGetDaysUntilStarve() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("Wrong getDaysUntilStarve from the beginning", predatore0.getDaysUntilStarve() == 4);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong getDaysUntilStarve after calling reduceDaysUntilStarve twice", predatore0.getDaysUntilStarve() == 2);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong getDaysUntilStarve after the beast has starved", predatore0.getDaysUntilStarve() == 0);
	}

	@Test
	public void testGetIsAlive() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("Wrong getIsAlive from the beginning", predatore0.getIsAlive());
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong getIsAlive after calling reduceDaysUntilStarve twice", predatore0.getIsAlive());
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertFalse("Wrong getIsAlive after the beast has starved", predatore0.getIsAlive());
	}

	@Test
	public void testSetIsAlive() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		predatore0.setIsAlive(true);
		assertTrue("Wrong value after setting it true from true", predatore0.getIsAlive());
		predatore0.setIsAlive(false);
		assertFalse("Wrong value after setting it false from true", predatore0.getIsAlive());
		predatore0.setIsAlive(true);
		assertFalse("Wrong value after setting it true from false", predatore0.getIsAlive());
	}

	@Test
	public void testReduceDaysUntilStarve() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong value after calling reduceDaysUntilStarve twice", predatore0.getDaysUntilStarve() == 2);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong value after calling reduceDaysUntilStarve four times", predatore0.getDaysUntilStarve() == 0);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		assertTrue("Wrong value after calling reduceDaysUntilStarve seven times", predatore0.getDaysUntilStarve() == 0);
	}

	@Test
	public void testResetDaysUntilStarve() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.reduceDaysUntilStarve();
		predatore0.resetDaysUntilStarve();
		assertTrue("Wrong value after resetting daysUntilStarve", predatore0.getDaysUntilStarve() == 4);
	}
	
	@Test
	public void testChooseMove() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		Organismo organismo = new Organismo();
		Preda preda = new Preda();
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = organismo;
		grid[7] = preda;
		assertTrue("Did not move to the available prey", predatore0.chooseMove(grid) == 7);
		for(int i = 0; i < 8; i++) grid[i] = null;
		int dest = predatore0.chooseMove(grid);
		assertTrue("Did not move to any available, empty cell", dest == 7 || dest == 5 || dest == 3 || dest == 1);
		for(int i = 0; i < 8; i++) grid[i] = organismo;
		dest = predatore0.chooseMove(grid);
		assertFalse("Did move to a non-empty, non-prey cell", dest == 7 || dest == 5 || dest == 3 || dest == 1);
	}

}
