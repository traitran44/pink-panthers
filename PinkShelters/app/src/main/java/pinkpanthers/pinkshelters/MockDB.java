package pinkpanthers.pinkshelters;

/**
 * Created by hdang on 2/19/18.
 */

public class MockDB implements DBI {
    private static int id;

    @Override
    public void create() {
        String userId = idGenerator();


    }

    @Override
    public void update() {

    }

    @Override
    public void get(){

    }

    @Override
    public void delete() {

    }

    private String idGenerator() {
        if () { // user is homeless
            return "HL" + id;
        } else if () { // user is volunteer
            return "VLT" + id;
        } else { // user is admin
            return "AD"+ id;
        }
        id++;
    }
}
