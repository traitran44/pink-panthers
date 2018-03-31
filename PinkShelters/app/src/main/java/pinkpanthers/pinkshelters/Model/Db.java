package pinkpanthers.pinkshelters.Model;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Db implements DBI {
    private Connection conn;
    public static Account activeAccount;

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
                "(?, ?, ?, ?, ?, 'active', 0)";

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
        String sql = "SELECT id, type, username, password, name, email, account_state," +
                " shelter_id, family_members, restriction_match  " +
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
                        ((Homeless) newUser).setFamilyMemberNumber(rs.getInt("family_members"));
                        String match = rs.getString("restriction_match");
                        if (match != null) {
                            List<String> newMatch = Arrays.asList(match.split(" "));
                            ((Homeless) newUser).setRestrictionsMatch(newMatch);
                        } else {
                            ((Homeless) newUser).setRestrictionsMatch(null);
                        }
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
        String sql = "SELECT id, type, username, password, name, email, account_state," +
                " shelter_id, family_members, restriction_match " +
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
                        ((Homeless) newUser).setFamilyMemberNumber(rs.getInt("family_members"));
                        String match = rs.getString("restriction_match");
                        if (match != null) {
                            List<String> newMatch = Arrays.asList(match.split(" "));
                            ((Homeless) newUser).setRestrictionsMatch(newMatch);
                        } else {
                            ((Homeless) newUser).setRestrictionsMatch(null);
                        }
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
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, " +
                "longitude, phone_number, restrictions, address, occupancy, update_capacity" +
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
                newShelter.setOccupancy(rs.getInt("occupancy"));
                newShelter.setUpdate_capacity(rs.getInt("update_capacity"));
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
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, " +
                "phone_number, restrictions, address, update_capacity, occupancy" +
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
                newShelter.setOccupancy(rs.getInt("occupancy"));
                newShelter.setUpdate_capacity(rs.getInt("update_capacity"));
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
    public List<Shelter> getShelterByName(String shelterName) throws NoSuchUserException {
        shelterName = String.format("%%%s%%", shelterName.toLowerCase());
        List<Shelter> shelterList = new ArrayList<>();
        String sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, " +
                "phone_number, restrictions, address, occupancy, update_capacity" +
                " FROM shelters" +
                " WHERE LOWER(shelter_name)" +
                " LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shelterName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    int id = rs.getInt("id");
                    shelterName = rs.getString("shelter_name");
                    String capacity = rs.getString("capacity");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
                    String phoneNumber = rs.getString("phone_number");
                    String specialNotes = rs.getString("special_notes");
                    String restrictions = rs.getString("restrictions");
                    String address = rs.getString("address");

                    Shelter shelter = new Shelter(id, shelterName, capacity, specialNotes,
                            latitude, longitude, phoneNumber, restrictions, address);
                    shelter.setOccupancy(rs.getInt("occupancy"));
                    shelter.setUpdate_capacity(rs.getInt("update_capacity"));
                    shelterList.add(shelter);
                } while (rs.next());
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

    @Override
    public List<Shelter> getShelterByRestriction(String restriction) throws NoSuchUserException {
        List<Shelter> shelterList = new ArrayList<>();
        String sql_column;
        String sql;
        switch (restriction.toLowerCase()) {
            case ("men"):
            case ("women"):
            case ("non_binary"):
                sql_column = "gender_restrictions";
                break;
            default:
                sql_column = "age_restrictions";
                break;
        }

        if (sql_column.equals("age_restrictions")) {
            restriction = String.format("%%%s%%", restriction);
            sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, " +
                    "phone_number, restrictions, address, occupancy, update_capacity" +
                    " FROM shelters" +
                    " WHERE " + sql_column +
                    " LIKE ? ";
        } else {
            sql = "SELECT id, shelter_name, capacity, special_notes, latitude, longitude, " +
                    "phone_number, restrictions, address, occupancy, update_capacity" +
                    " FROM shelters" +
                    " WHERE " + sql_column + " = ? ";
        }
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, restriction.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    int id = rs.getInt("id");
                    String shelterName = rs.getString("shelter_name");
                    String capacity = rs.getString("capacity");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
                    String phoneNumber = rs.getString("phone_number");
                    String specialNotes = rs.getString("special_notes");
                    String restrictions = rs.getString("restrictions");
                    String address = rs.getString("address");

                    Shelter shelter = new Shelter(id, shelterName, capacity, specialNotes,
                            latitude, longitude, phoneNumber, restrictions, address);
                    shelter.setOccupancy(rs.getInt("occupancy"));
                    shelter.setUpdate_capacity(rs.getInt("update_capacity"));
                    shelterList.add(shelter);
                } while (rs.next());
            } else {
                throw new NoSuchUserException("There is no shelter that has this " + sql_column + ": " + restriction);
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Selecting shelter by restriction failed: " +
                    e.toString()); // so we can log sql message too
        }

        return shelterList;
    }


    @Override
    public void updateShelterOccupancy(int shelterId, int occupancy) throws SQLException, NoSuchUserException {
        String sql = "UPDATE shelters " +
                "SET occupancy = ? " +
                "WHERE id = ?";
        PreparedStatement updatedOccupancy = null;
        try {
            conn.setAutoCommit(false);
            updatedOccupancy = conn.prepareStatement(sql);
            updatedOccupancy.setInt(1, occupancy);
            updatedOccupancy.setInt(2, shelterId);
            int rowUpdated = updatedOccupancy.executeUpdate();

            if (rowUpdated == 1) {// only one row is supposed to be updated
                conn.commit();
            } else {
                throw new NoSuchUserException("The shelter with id: " + shelterId + " doesn't exist");
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Updating shelter\'s occupancy failed: " +
                    e.toString()); // so we can log sql message too
        } finally {
            if (updatedOccupancy != null) {
                updatedOccupancy.close();
            }
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void updateAccount (Account user) throws SQLException, NoSuchUserException {
        String sql = "UPDATE accounts " +
                "SET password = ?, " +
                "name = ?, " +
                "email = ?, " +
                "account_state = ?, " +
                "shelter_id = ?, " +
                "family_members = ?, " +
                "restriction_match = ? " +
                "WHERE id = ?";
        PreparedStatement updatedAccount = null;
        try {
            conn.setAutoCommit(false);
            updatedAccount = conn.prepareStatement(sql);
            updatedAccount.setString(1, user.getPassword());
            updatedAccount.setString(2, user.getName());
            updatedAccount.setString(3, user.getEmail());
            updatedAccount.setString(4, user.getAccountState());
            if (user instanceof Homeless) {
                updatedAccount.setInt(5, ((Homeless) user).getShelterId());
                updatedAccount.setInt(6, ((Homeless) user).getFamilyMemberNumber());
                String match = TextUtils.join(" ", ((Homeless) user).getRestrictionsMatch());
                updatedAccount.setString(7, match);
            } else {
                updatedAccount.setInt(5, 0);
                updatedAccount.setInt(6, 0);
                updatedAccount.setString(7, "");
            }
            updatedAccount.setInt(8, user.getUserId());
            int updatedRow = updatedAccount.executeUpdate();
            if (updatedRow == 1) {
                conn.commit();
            } else {
                throw new NoSuchUserException("The account with id: " + user.getUserId() + " doesn't exist");
            }

        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException("Updating account information by account id ("
                    + user.getUserId() + "failed: " +
                    e.toString()); // so we can log sql message too
        } finally {
            if (updatedAccount != null) {
                updatedAccount.close();
            }
            conn.setAutoCommit(true);
        }
    }
}
