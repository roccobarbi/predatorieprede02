package predatorieprede02.unittests;

import static org.junit.Assert.*;
import predatorieprede02.*;

import org.junit.Test;

public class LinkedPredatoreTest {

	@Test
	public void testKill() {
		LinkedOrganisms list = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		Predatore pup = new Predatore();
		LinkedPredatore predatore = new LinkedPredatore(pup, 0, 0, field, list);
		assertTrue("predatore has not been successfully added to field.", field.getOccupant(0, 0) == predatore);
		assertTrue("predatore has not been successfully added to list.", list.isHere(predatore));
		predatore.kill();
		assertFalse("predatore has not been successfully removed from field.", field.getOccupant(0, 0) == predatore);
		assertFalse("predatore has not been successfully removed from list.", list.isHere(predatore));
	}

	@Test
	public void testAct() {
		// Set up List, Field, Predatore, LinkedPredatore and filler Organismo
		PlayingField field = new PlayingField();
		Predatore pup = new Predatore("noname", 'X', "predatore", 0, 100, 4);
		LinkedPredatore predatore = new LinkedPredatore(pup, 0, 0, field); // Top left corner
		Organismo filler = new Organismo();
		LinkedOrganism lFiller = new LinkedOrganism(filler, 1, 0, field); // To the right of the LinkedPredatore
		// Basic checks
		assertTrue("predatore was not created where it should be", field.getOccupant(0, 0) == predatore);
		assertTrue("predatore has a wrong move probability", field.getOccupant(0, 0).reveal().getMoveProbability() == 100);
		assertTrue("predatore is not of type Predatore", field.getOccupant(0, 0).reveal() instanceof Predatore);
		assertTrue("predatore has a wrong move initialDaysUntilStarve", ((Predatore)field.getOccupant(0, 0).reveal()).getInitialDaysUntilStarve() == 4);
		assertTrue("lFiller was not created where it should be", field.getOccupant(1, 0) == lFiller);
		field.print(); // Visual check before acting
		predatore.act(); // MOVE AND SPAWN
		field.print(); // Visual check after acting
		// Visual check: the field should have been updated properly
			Organismo grid[] = field.lookAround(predatore.getPosX(), predatore.getPosY());
			for(int i = 0; i < grid.length; i++) System.out.println(i + " : " + grid[i]);
		// Predatore can only move to 0,1
			assertFalse("predatore is still at origin", field.getOccupant(0, 0) == predatore);
			assertTrue("predatore has not moved to adjacent cell", field.getOccupant(0, 1) == predatore);
			assertFalse("predatore has moved to occcupied cell (not Preda)", field.getOccupant(1, 0) == predatore);
		// Predatore can only move to 0,1, so it can only spawn to (0,0), (1,1) or (0,2)
			assertTrue("predatore in (0,1) has not spawned", field.getOccupant(0, 0) != null || field.getOccupant(1, 1) != null || field.getOccupant(0, 2) != null);;
			assertTrue("predatore has not spawned a Predatore", (field.getOccupant(0, 0) != null && field.getOccupant(0, 0).reveal() instanceof Predatore) ||
					(field.getOccupant(1, 1) != null && field.getOccupant(1, 1).reveal() instanceof Predatore) ||
					(field.getOccupant(0, 2) != null && field.getOccupant(0, 2).reveal() instanceof Predatore));
	}

	@Test
	public void testReveal() {
		LinkedOrganisms list = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		Predatore pup = new Predatore();
		LinkedPredatore predatore = new LinkedPredatore(pup, 0, 0, field, list);
		assertTrue("did not reveal an object of the right type", predatore.reveal() instanceof Predatore);
		assertTrue("did not reveal a copy, but the original object", predatore.reveal() != pup);
	}

}
