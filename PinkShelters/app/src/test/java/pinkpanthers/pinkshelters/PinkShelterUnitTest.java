package pinkpanthers.pinkshelters;
import org.junit.Test;

import java.util.List;

import pinkpanthers.pinkshelters.Controller.MapsActivity;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Shelter;

import static org.junit.Assert.*;

/**
 * Created by Trai Tran on 3/30/2018.
 */

public class PinkShelterUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getAllShelters() {
        Db db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        List<Shelter> shelters = db.getAllShelters();
    }
}
