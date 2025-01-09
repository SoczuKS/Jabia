package mpks.jabia.server;

import mpks.jabia.common.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase {
    Logger logger;
    private Connection connection;
    private Statement statement;

    UserDatabase(Logger logger) {
        this.logger = logger;
    }

    void connect() throws SQLException {
        createConnection();
        createStatement();
        createDatabase();
    }

    User getUser(String username) throws IOException, SQLException {
        var resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
        if (resultSet.next()) {
            String password = resultSet.getString("password");
            return new User(username, password);
        }
        return null;
    }

    User registerUser(String username, String password) throws SQLException {
        statement.executeUpdate("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')");
        return new User(username, password);
    }

    private void createConnection() throws SQLException {
        String url = "jdbc:sqlite:users.db";
        connection = DriverManager.getConnection(url);
    }

    private void createStatement() throws SQLException {
        statement = connection.createStatement();
    }

    private void createDatabase() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)");
    }
}
