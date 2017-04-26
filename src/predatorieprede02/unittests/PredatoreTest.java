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
		Organismo organismo = new Organismo();
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = organismo;
		assertTrue("Wrong initialDaysUntilStarve from the beginning", predatore0.getInitialDaysUntilStarve() == 4);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		assertTrue("Wrong initialDaysUntilStarve after moving twice on a grid without any Preda: " + predatore0.getInitialDaysUntilStarve(), predatore0.getInitialDaysUntilStarve() == 4);
	}
	
	@Test
	public void testGetDaysUntilStarve() {
		Predatore predatore0 = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		Organismo organismo = new Organismo();
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = organismo;
		assertTrue("Wrong getDaysUntilStarve from the beginning", predatore0.getDaysUntilStarve() == 4);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		assertTrue("Wrong getDaysUntilStarve after moving twice on a grid without any Preda: " + predatore0.getDaysUntilStarve(), predatore0.getDaysUntilStarve() == 2);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		predatore0.chooseMove(grid);
		assertTrue("Wrong getDaysUntilStarve after the beast has starved: " + predatore0.getDaysUntilStarve(), predatore0.getDaysUntilStarve() == 0);
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
	
	@Test
	public void testGetInitialNextOffspring() {
		Predatore predatore = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("getInitialNextOffspring did not return expected value at the start", predatore.getInitialNextOffspring() == 8);
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = null;
		predatore.chooseSpawn(grid);
		assertTrue("getInitialNextOffspring did not return expected value after choosing a spawn", predatore.getInitialNextOffspring() == 8);
	}
	
	@Test
	public void testLoopNextOffspring() {
		Predatore predatore = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("getNextOffspring did not return expected value at the start", predatore.getNextOffspring() == 8);
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = null;
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		assertTrue("getNextOffspring did not return expected value after choosing 2 spawns", predatore.getNextOffspring() == 6);
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		predatore.chooseSpawn(grid);
		assertTrue("getNextOffspring did not return initial value after a complete loop", predatore.getNextOffspring() == 8);
	}
	
	@Test
	public void testIncreaseAge() {
		Predatore predatore = new Predatore("no-name", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("initial age was not zero", predatore.getAge() == 0);
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = null;
		predatore.chooseMove(grid);
		predatore.chooseMove(grid);
		assertTrue("after two calls to chooseMove, the age was not 2", predatore.getAge() == 2);
		predatore.chooseMove(grid);
		predatore.chooseMove(grid);
		predatore.chooseMove(grid);
		predatore.chooseMove(grid);
		System.out.println(predatore.getAge());
		assertTrue("after starving the beast, the age was not 4", predatore.getAge() == 4);
	}
	
	@Test
	public void testEquals() {
		Predatore pred01 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 4);
		Predatore pred02 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 4);
		assertTrue("Equals returns false when true is appropriate", pred01.equals(pred02));
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = null;
		pred01.chooseMove(grid);
		assertTrue("Equals returns false because age is different", pred01.equals(pred02));
		pred01.chooseSpawn(grid);
		assertTrue("Equals returns false because nextOffspring is different", pred01.equals(pred02));
		pred01 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 4);
		pred02 = new Predatore("predatore01", 'X', "generic Predatore", 5, 100, 4);
		assertFalse("Equals returns true when initialNextOffspring is different", pred01.equals(pred02));
		pred01 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 4);
		pred02 = new Predatore("predatore01", 'X', "generic Predatore", 8, 50, 4);
		assertFalse("Equals returns true when moveProbability is different", pred01.equals(pred02));
		pred01 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 4);
		pred02 = new Predatore("predatore01", 'X', "generic Predatore 002", 8, 50, 4);
		assertFalse("Equals returns true when species is different", pred01.equals(pred02));
		pred01 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 4);
		pred02 = new Predatore("predatore01", 'X', "generic Predatore", 8, 100, 6);
		assertFalse("Equals returns true when initialDaysUntilStarve is different", pred01.equals(pred02));
	}

}
