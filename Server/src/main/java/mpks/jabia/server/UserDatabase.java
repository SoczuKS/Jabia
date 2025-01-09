package mpks.jabia.server;

import mpks.jabia.common.User;

import java.sql.*;

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

    User getUser(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                return new User(username, password);
            }
        }
        return null;
    }

    User registerUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
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
