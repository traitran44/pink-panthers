package pinkpanthers.pinkshelters;
import org.junit.Test;

import pinkpanthers.pinkshelters.Model.Db;

import static org.junit.Assert.*;

/**
 * Created by Trai Tran on 3/30/2018.
 */

public class PinkShelterUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getAllShelters() {
        Db db = new Db("pinkpanther", "PinkPantherReturns!");
//        List<Shelter> shelters = db.getAllShelters();
    }
}
