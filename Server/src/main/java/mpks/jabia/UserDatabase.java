package mpks.jabia;

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
