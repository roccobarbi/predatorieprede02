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
		LinkedOrganisms list = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		Predatore pup = new Predatore("noname", 'o', "predatore", 0, 100, 4);
		LinkedPredatore predatore = new LinkedPredatore(pup, 0, 0, field, list);
		Organismo filler = new Organismo();
		LinkedOrganism lFiller = new LinkedOrganism(filler, 1, 0, field, list);
		assertTrue("predatore was not created where it should be", field.getOccupant(0, 0) == predatore);
		assertTrue("predatore has a wrong move probability", field.getOccupant(0, 0).reveal().getMoveProbability() == 100);
		assertTrue("predatore is not of type Predatore", field.getOccupant(0, 0).reveal() instanceof Predatore);
		assertTrue("predatore has a wrong move initialDaysUntilStarve", ((Predatore)field.getOccupant(0, 0).reveal()).getInitialDaysUntilStarve() == 4);
		assertTrue("lFiller was not created where it should be", field.getOccupant(1, 0) == lFiller);
		predatore.act();
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
