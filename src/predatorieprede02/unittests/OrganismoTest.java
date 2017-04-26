package predatorieprede02.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import predatorieprede02.Organismo;

public class OrganismoTest {
	
	@Test
	public void testSetMoveProbability() {
		Organismo organismo = new Organismo("organismo", 'g', "generic organism", 3, 80);
		assertTrue("Correct initial move probability was ignored", organismo.getMoveProbability() == 80);
		organismo.setMoveProbability(100);
		assertTrue("Correct move probability of 100 was ignored", organismo.getMoveProbability() == 100);
		organismo.setMoveProbability(0);
		assertTrue("Correct move probability of 0 was ignored", organismo.getMoveProbability() == 0);
		organismo.setMoveProbability(101);
		assertTrue("Incorrect move probability greater than 100 was accepted", organismo.getMoveProbability() == 100);
		organismo.setMoveProbability(-1);
		assertTrue("Incorrect move probability lower than 0 was accepted", organismo.getMoveProbability() == 0);
	}

	@Test
	public void testGetInitialNextOffspring() {
		Organismo organismo = new Organismo("organismo", 'g', "generic organism", 5, 80);
		assertTrue("getInitialNextOffspring did not return expected value at the start", organismo.getInitialNextOffspring() == 5);
		organismo.loopNextOffspring();
		assertTrue("getInitialNextOffspring did not return expected value after looping", organismo.getInitialNextOffspring() == 5);
	}
	
	@Test
	public void testLoopNextOffspring() {
		Organismo organismo = new Organismo("organismo", 'g', "generic organism", 4, 80);
		assertTrue("getNextOffspring did not return expected value at the start", organismo.getNextOffspring() == 4);
		organismo.loopNextOffspring();
		organismo.loopNextOffspring();
		assertTrue("getNextOffspring did not return expected value after looping", organismo.getNextOffspring() == 2);
		organismo.loopNextOffspring();
		organismo.loopNextOffspring();
		organismo.loopNextOffspring();
		assertTrue("getNextOffspring did not return initial value after complete loop", organismo.getNextOffspring() == 4);
	}

	@Test
	public void testIncreaseAge() {
		Organismo organismo = new Organismo("organismo", 'g', "generic organism", 4, 80);
		assertTrue("initial age was not zero", organismo.getAge() == 0);
		organismo.increaseAge();
		organismo.increaseAge();
		assertTrue("after two increases, the age was not 2", organismo.getAge() == 2);
	}

	@Test
	public void testChooseMove() {
		Organismo organismo = new Organismo("organismo", 'g', "generic organism", 4, 100);
		int moved = 0, dest = -1;
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = null;
		for(int i = 0; i < 100; i++){
			dest = organismo.chooseMove(grid);
			assertTrue("invalid move greater than 7", dest < 8);
			assertTrue("invalid move lower than -1", dest > -2);
			assertTrue("invalid move on even destination", dest % 2 == 1);
			assertTrue("organismo did not move with empty grid and probability of 100", dest > -1);
		}
		organismo = new Organismo("organismo", 'g', "generic organism", 4, 50);
		for(int i = 0; i < 200; i++){
			if(organismo.chooseMove(grid) > -1) moved++;
		}
		assertTrue("moved less than 40% of the times with probability of 50", moved > 80);
		assertTrue("moved more than 60% of the times with probability of 50", moved < 120);
	}

	@Test
	public void testChooseSpawn() {
		Organismo organismo = new Organismo("organismo", 'g', "generic organism", 0, 100);
		int dest = -1;
		Organismo [] grid = new Organismo [8];
		for(int i = 0; i < 8; i++) grid[i] = null;
		for(int i = 0; i < 100; i++){
			dest = organismo.chooseSpawn(grid);
			assertTrue("invalid spawn greater than 7", dest < 8);
			assertTrue("invalid spawn lower than -1", dest > -2);
			assertTrue("invalid spawn on even destination", dest % 2 == 1);
			assertTrue("organismo did not spawn with empty grid and intialNextOffspring of 0", dest > -1);
		}
		organismo = new Organismo("organismo", 'g', "generic organism", 2, 50);
		dest = organismo.chooseSpawn(grid);
		assertTrue("organismo spawned when it shouldn't have", dest == -1);
		dest = organismo.chooseSpawn(grid);
		dest = organismo.chooseSpawn(grid);
		assertTrue("organismo did not spawn when it should have", dest > -1);
	}

}
