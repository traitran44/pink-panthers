package pinkpanthers.pinkshelters;
import org.junit.Test;

import pinkpanthers.pinkshelters.Model.Db;

import static org.junit.Assert.*;

/**
 * Test arithmetic function: addition
 *
 */
public class PinkShelterUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    /**
     * Get all shelters saved in database
     *
     */

    @Test
    public void getAllShelters() {
        Db db = new Db("pinkpanther", "PinkPantherReturns!");
//        List<Shelter> shelters = db.getAllShelters();
    }
}
