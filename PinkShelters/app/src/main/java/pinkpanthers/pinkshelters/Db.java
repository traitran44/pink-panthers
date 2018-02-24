package pinkpanthers.pinkshelters;

import android.os.StrictMode;
import android.util.Log;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Db implements DBI {
    private Connection conn;

    /**
     * Create connection to DB.
     *
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
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return null;
    }
}
