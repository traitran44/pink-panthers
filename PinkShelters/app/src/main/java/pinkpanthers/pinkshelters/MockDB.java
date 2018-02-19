package pinkpanthers.pinkshelters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hdang on 2/19/18.
 */

public class MockDB implements DBI {
    private static int id = 2018;
    private static Map<String, Account> accounts = new HashMap<>(); //stores username and account
    private String userType;
    private Account newUser;
    private String userName;
    private String password;
    private String accountState;
    private String email;
    private String name;


    public MockDB(String name, String email, String userName, String password, String userType) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userType = userType;

    }

    @Override
    public boolean create() {
        // validate
        if (accounts.containsKey(userName)) { //available username or not
            return false;
        }
        String userId = idGenerator();
        if (userType.equals("Homeless")) {
            newUser = new Homeless(userName, password, name, "unlocked", email, userId);
        } else if (userType.equals("Volunteer")) {
            newUser = new Volunteer(userName, password, name, "unlocked", email, userId);
        } else {
            newUser = new Admin(userName, password, name, "unlocked", email, userId);
        }
        accounts.put(userName, newUser);
        return true;



    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public void get() {

    }

    @Override
    public void delete() {

    }

    private String idGenerator() {
        id++;
        if (userType.equals("Homeless")) { // user is homeless
            return "HL" + id;
        } else if (userType.equals("Volunteer")) { // user is volunteer
            return "VLT" + id;
        } else { // user is admin
            return "AD" + id;
        }
    }

    public static Map getAccounts() {
        return accounts;
    }
}
