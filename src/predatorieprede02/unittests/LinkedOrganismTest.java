package predatorieprede02.unittests;

import static org.junit.Assert.*;
import predatorieprede02.*;

import org.junit.Test;

public class LinkedOrganismTest {

	@Test
	public void testKill() {
		LinkedOrganisms list = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		Organismo pup = new Organismo();
		LinkedOrganism organismo = new LinkedOrganism(pup, 0, 0, field, list);
		assertTrue("organismo has not been successfully added to field.", field.getOccupant(0, 0) == organismo);
		assertTrue("organismo has not been successfully added to list.", list.isHere(organismo));
		organismo.kill();
		assertFalse("organismo has not been successfully removed from field.", field.getOccupant(0, 0) == organismo);
		assertFalse("organismo has not been successfully removed from list.", list.isHere(organismo));
	}

	@Test
	public void testAct() {
		LinkedOrganisms list = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		Preda pup = new Preda("noname", 'o', "preda", 0, 100);
		LinkedOrganism organismo = new LinkedOrganism(pup, 0, 0, field, list);
		Organismo filler = new Organismo();
		LinkedOrganism lFiller = new LinkedOrganism(filler, 1, 0, field, list);
		assertTrue("organismo was not created where it should be", field.getOccupant(0, 0) == organismo);
		assertTrue("organismo has a wrong move probability", field.getOccupant(0, 0).reveal().getMoveProbability() == 100);
		assertTrue("organismo is not of type Preda", field.getOccupant(0, 0).reveal() instanceof Preda);
		assertTrue("lFiller was not created where it should be", field.getOccupant(1, 0) == lFiller);
		try {
			organismo.act();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		assertTrue("organismo has not moved", field.getOccupant(0, 0) != organismo);
		assertTrue("organismo has not spawned", field.getOccupant(0, 0) != null || field.getOccupant(1, 1) != null || field.getOccupant(0, 2) != null);
		assertTrue("organismo has not spawned a Prey", (field.getOccupant(0, 0) != null && field.getOccupant(0, 0).reveal() instanceof Preda) ||
				(field.getOccupant(1, 1) != null && field.getOccupant(1, 1).reveal() instanceof Preda) ||
				(field.getOccupant(0, 2) != null && field.getOccupant(0, 2).reveal() instanceof Preda));
	}

	@Test
	public void testReveal() {
		LinkedOrganisms list = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		Preda pup = new Preda("noname", 'o', "preda", 0, 100);
		LinkedOrganism organismo = new LinkedOrganism(pup, 0, 0, field, list);
		assertTrue("did not reveal an object of the right type", organismo.reveal() instanceof Preda);
		assertTrue("did not reveal a copy, but the original object", organismo.reveal() != pup);
	}

}
