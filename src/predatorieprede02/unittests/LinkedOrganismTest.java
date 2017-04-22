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
		fail("Not yet implemented");
	}

	@Test
	public void testReveal() {
		fail("Not yet implemented");
	}

}
