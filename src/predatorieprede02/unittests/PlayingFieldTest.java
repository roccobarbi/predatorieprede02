package predatorieprede02.unittests;

import static org.junit.Assert.*;
import predatorieprede02.*;

import org.junit.Test;

import predatorieprede02.PlayingField;

public class PlayingFieldTest {

	@Test
	public void testGetOccupant() {
		PlayingField field = new PlayingField();
		LinkedOrganism occupant = field.getOccupant(0, 0);
		assertTrue("newly created field is not null", occupant == null);
		assertFalse("newly created field is instanceof LinkedOrganism", occupant instanceof LinkedOrganism);
		LinkedOrganism newOccupant = new LinkedOrganism();
		field.spawn(19, 19, newOccupant);
		occupant = field.getOccupant(19, 19);
		assertTrue("address of new occupant is different in and out of the field", occupant == field.getOccupant(19, 19));
		assertTrue("new occupant is not instanceof LinkedOrganism", field.getOccupant(19, 19) instanceof LinkedOrganism);
	}

	@Test
	public void testLookAround() {
		PlayingField field = new PlayingField();
		Organismo organismo = new Organismo();
		LinkedOrganism pup = new LinkedOrganism(organismo, 1, 1, field);
		Organismo [] grid = field.lookAround(0, 0);
		assertFalse("slot out of the playing field is null", grid[0] == null);
		assertFalse("slot out of the playing field is null", grid[2] == null);
		assertFalse("slot out of the playing field is null", grid[6] == null);
		assertTrue("good slot is not null", grid[3] == null);
		assertTrue("occupied slot is null", grid[4] != null);
		assertTrue("occupied slot does not contain Organismo", grid[4] instanceof Organismo);
		
		// Create a 1x1 field and look around: there should be no nulls.
			field = new PlayingField(1, 1);
			grid = field.lookAround(0, 0);
			int fieldsNull = 0;
			for(int i = 0; i < grid.length; i++) if(grid[i] == null) fieldsNull++;
			assertTrue("there are null fields out of bound", fieldsNull == 0);
		
		// Create a 3x3 field and look around the center: all fields should be null.
			field = new PlayingField(3, 3);
			grid = field.lookAround(1, 1);
			fieldsNull = 0;
			for(int i = 0; i < grid.length; i++) if(grid[i] == null) fieldsNull++;
			assertTrue("good fields are not null", fieldsNull == grid.length);
	}

	@Test
	public void testMove() {
		PlayingField field = new PlayingField();
		Organismo organismo = new Organismo();
		LinkedOrganism pup = new LinkedOrganism(organismo, 1, 1, field);
		// Basic checks
		assertTrue("occupant is not at source", pup == field.getOccupant(1, 1));
		assertTrue("designated destination is not null", field.getOccupant(1, 0) == null);
		field.move(1, 1, 1, 0);
		assertTrue("occupant is not at destination", pup == field.getOccupant(1, 0));
		assertTrue("source is not null", field.getOccupant(1, 1) == null);
	}

	@Test
	public void testSpawn() {
		PlayingField field = new PlayingField();
		LinkedOrganism newOccupant = new LinkedOrganism();
		field.spawn(19, 19, newOccupant);
		assertTrue("address of new occupant is different in and out of the field", field.getOccupant(19, 19) == newOccupant);
		assertTrue("new occupant is not instanceof LinkedOrganism", field.getOccupant(19, 19) instanceof LinkedOrganism);
	}

	@Test
	public void testShuffle() {
		PlayingField field = new PlayingField(2, 2);
		LinkedOrganism newOccupant = new LinkedOrganism();
		field.spawn(1, 1, newOccupant);
		field.shuffle();
		int found = 0;
		for (int i = 0; i < 2; i++) {
			for (int k = 0; k < 2; k++){
				if (field.getOccupant(i, k) == newOccupant)
					found++;
			}
		}
		assertTrue("item not found", found == 1);
		assertTrue("item found more than once", found == 1);
	}

	@Test
	public void testRemove() {
		PlayingField field = new PlayingField();
		LinkedOrganism newOccupant = new LinkedOrganism();
		field.spawn(19, 19, newOccupant);
		assertTrue("address of new occupant is different in and out of the field", field.getOccupant(19, 19) == newOccupant);
		assertTrue("new occupant is not instanceof LinkedOrganism", field.getOccupant(19, 19) instanceof LinkedOrganism);
		field.remove(19, 19);
		assertTrue("removed occupant is not null", field.getOccupant(19, 19) == null);
	}

}
