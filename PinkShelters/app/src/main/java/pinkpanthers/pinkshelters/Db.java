package pinkpanthers.pinkshelters;

import android.app.ActivityManager;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Db implements DBI {
    private Connection conn;

    /**
     * Create connection to DB.
     * <p>
     * In the event of a connection error
     * it will retry 10 times and blow up
     * the application after that.
     *
     * @param username Database username
     * @param password Database password
     * @param database Database to use
     */
    public Db(String username, String password, String database) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Properties connProperties = new Properties();
            connProperties.setProperty("tcpKeepAlive", "true");
            connProperties.setProperty("autoReconnect", "true");
            connProperties.setProperty("maxReconnects", "3");
            connProperties.setProperty("user", username);
            connProperties.setProperty("password", password);
            this.conn = DriverManager.getConnection("jdbc:mariadb://timbess.net:3306/pinkpanther", connProperties);
        } catch (SQLException e) {
            String errMsg = logSqlException(e);
            throw new RuntimeException(errMsg);
        }
    }

    private String logSqlException(SQLException e) {
        String errMsg = "Error connecting to DB: " + e.toString();
        Log.d(Db.class.getName(), errMsg);
        return errMsg;
    }

    @Override
    public Account createAccount(String type, String username, String password, String name, String email) throws UniqueKeyError {
        // These are the only valid options.
        // Program should blow up in the event that a developer
        // accidentally changes these strings. For developers only not for users.
        switch (type) {
            case "Homeless":
                break;
            case "Shelter Volunteer":
                break;
            case "Admin":
                break;
            default:
                throw new RuntimeException("You have attempted to createAccount an invalid user type. " +
                        "This should not be possible if the UI is designed correctly.");
        }
        // ?'s are replace in the prepared statement when the query runs
        String sql = "INSERT INTO accounts " +
                "(`type`, `username`, `password`, `name`, `email`, `account_state`, `shelter_id`)" +
                " VALUES " +
                "(?, ?, ?, ?, ?, 'active', NULL)";

        int id;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, type);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, name);
            stmt.setString(5, email);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("Failed to retried user id from DB. This should never occur.");
            }
            id = rs.getInt(1);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                throw new UniqueKeyError("Username already exists: " + username);
            }
            String errMsg = logSqlException(e);
            throw new RuntimeException(errMsg);
        }


        // Create Java object after inserting into database
        // and retrieving the row id.
        Account newUser;
        switch (type) {
            case "Homeless":
                newUser = new Homeless(username, password, name, "active", email, id);
                break;
            case "Shelter Volunteer":
                newUser = new Volunteer(username, password, name, "active", email, id);
                break;
            case "Admin":
                newUser = new Admin(username, password, name, "active", email, id);
                break;
            default:
                throw new RuntimeException("You have attempted to createAccount an invalid user type. " +
                        "This should not be possible if the UI is designed correctly.");
        }
        return newUser;
    }

    @Override
    public Account getAccountByUsername(String username) throws NoSuchUserException {
        Account newUser;
        String sql = "SELECT id, type, username, password, name, email, account_state, shelter_id " +
                "FROM accounts " +
                "WHERE username = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("type");
                int id = rs.getInt("id");
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String accountState = rs.getString("account_state");
                int shelter_id = rs.getInt("shelter_id");

                switch (userType) {
                    case ("Homeless"):
                        newUser = new Homeless(userName, password, name, accountState, email, id);
                        ((Homeless) newUser).setShelterId(shelter_id);
                        // need to check if assignment is set (if professor wants to keep it)
                        break;
                    case ("Shelter Volunteer"):
                        newUser = new Volunteer(userName, password, name, accountState, email, id);
                        ((Volunteer) newUser).setShelterID(shelter_id);
                        break;
                    case ("Admin"):
                        newUser = new Admin(userName, password, name, accountState, email, id);
                        // admin doesn't need any shelter id
                        break;
                    default:
                        throw new NoSuchUserException("Failed to select an account by username, " +
                                "this should never happen");
                }
            } else {
                throw new NoSuchUserException("Account with " + username + " doesn't exist");
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Selecting account by username failed: " +
                    e.toString()); // so we can log sql message too
        }

        return newUser;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        Account newUser = null;
        String sql = "SELECT id, type, username, password, name, email, account_state, shelter_id " +
                "FROM accounts";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String userType = rs.getString("type");
                int id = rs.getInt("id");
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String accountState = rs.getString("account_state");
                int shelter_id = rs.getInt("shelter_id");

                switch (userType) {
                    case ("Homeless"):
                        newUser = new Homeless(userName, password, name, accountState, email, id);
                        ((Homeless) newUser).setShelterId(shelter_id);
                        // need to check if assignment is set (if professor wants to keep it)
                        break;
                    case ("Shelter Volunteer"):
                        newUser = new Volunteer(userName, password, name, accountState, email, id);
                        ((Volunteer) newUser).setShelterID(shelter_id);
                        break;
                    case ("Admin"):
                        newUser = new Admin(userName, password, name, accountState, email, id);
                        // admin doesn't need any shelter id
                        break;
                }
                accountList.add(newUser);
            }
            return accountList;

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Select all accounts failed: " +
                    e.toString()); // so we can log sql message too
        }
    }

    @Override
    public Shelter createShelter(String shelterName,
                                 String capacity,
                                 String specialNotes,
                                 double latitude,
                                 double longitude,
                                 String phoneNumber,
                                 String restrictions,
                                 String address) {
        String sql = "INSERT INTO shelters " +
                "(`shelter_name`, `capacity`, `special_notes`, `latitude`, `longitude`, `phone_number`, `restrictions`, `address`)" +
                " VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";

        int id;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, shelterName);
            stmt.setString(2, capacity);
            stmt.setString(3, specialNotes);
            stmt.setDouble(4, latitude);
            stmt.setDouble(5, longitude);
            stmt.setString(6, phoneNumber);
            stmt.setString(7, restrictions);
            stmt.setString(8, address);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("Failed to retried user id from DB. This should never occur.");
            }
            id = rs.getInt(1);
        } catch (SQLException e) {
            String errMsg = logSqlException(e);
            throw new RuntimeException(errMsg);
        }


        return new Shelter(id, shelterName, capacity, specialNotes, latitude, longitude, phoneNumber, restrictions, address);
    }

    @Override
    public List<Shelter> getAllShelters() {
        List<Shelter> sheltersList = new ArrayList<>();
        Shelter newShelter = null;
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, phone_number, restrictions, address" +
                " FROM shelters";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String shelterName = rs.getString("shelter_name");
                String capacity = rs.getString("capacity");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String phoneNumber = rs.getString("phone_number");
                String specialNotes = rs.getString("special_notes");
                String restrictions = rs.getString("restrictions");
                String address = rs.getString("address");

                newShelter = new Shelter(id, shelterName, capacity, specialNotes, latitude, longitude, phoneNumber, restrictions, address);
                sheltersList.add(newShelter);
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Select all shelters failed: " +
                    e.toString()); // so we can log sql message too
        }

        return sheltersList;
    }

    @Override
    public Shelter getShelterById(int id) throws NoSuchUserException {
        Shelter newShelter = null;
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, phone_number, restrictions, address" +
                " FROM shelters" +
                " WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String shelterName = rs.getString("shelter_name");
                String capacity = rs.getString("capacity");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String phoneNumber = rs.getString("phone_number");
                String specialNotes = rs.getString("special_notes");
                String restrictions = rs.getString("restrictions");
                String address = rs.getString("address");

                newShelter = new Shelter(id, shelterName, capacity, specialNotes, latitude, longitude, phoneNumber, restrictions, address);
            } else {
                throw new NoSuchUserException("Shelter with this " + id + " doesn't exist");
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Selecting account by username failed: " +
                    e.toString()); // so we can log sql message too
        }

        return newShelter;
    }

    @Override
    public List<Shelter> getShelterByRestriction(String restriction) throws NoSuchUserException {
        restriction = String.format("%%%s%%", restriction.toLowerCase());
        List<Shelter> shelterList = new ArrayList<>();
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, phone_number, restrictions, address" +
                " FROM shelters" +
                " WHERE LOWER(restrictions)" +
                " LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, restriction);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String shelterName = rs.getString("shelter_name");
                String capacity = rs.getString("capacity");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String phoneNumber = rs.getString("phone_number");
                String specialNotes = rs.getString("special_notes");
                String restrictions = rs.getString("restrictions");
                String address = rs.getString("address");

                shelterList.add(new Shelter(id, shelterName, capacity, specialNotes,
                        latitude, longitude, phoneNumber, restrictions, address));
            } else {
                throw new NoSuchUserException("There is no shelter that has this restriction: " + restriction);
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Selecting shelter by restrictions failed: " +
                    e.toString()); // so we can log sql message too
        }

        return shelterList;
    }

    @Override
    public List<Shelter> getShelterByName(String shelterName) throws NoSuchUserException {
        shelterName = String.format("%%%s%%", shelterName.toLowerCase());
        List<Shelter> shelterList = new ArrayList<>();
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, phone_number, restrictions, address" +
                " FROM shelters" +
                " WHERE LOWER(shelter_name)" +
                " LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shelterName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                shelterName = rs.getString("shelter_name");
                String capacity = rs.getString("capacity");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String phoneNumber = rs.getString("phone_number");
                String specialNotes = rs.getString("special_notes");
                String restrictions = rs.getString("restrictions");
                String address = rs.getString("address");

                shelterList.add(new Shelter(id, shelterName, capacity, specialNotes,
                        latitude, longitude, phoneNumber, restrictions, address));
            } else {
                throw new NoSuchUserException("There is no shelter that has this restriction: " + shelterName);
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Selecting shelter by name failed: " +
                    e.toString()); // so we can log sql message too
        }

        return shelterList;
    }

}
