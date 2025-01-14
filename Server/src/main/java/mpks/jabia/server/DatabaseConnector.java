package mpks.jabia.server;

import mpks.jabia.common.Inventory;
import mpks.jabia.common.User;
import org.json.JSONObject;

import java.sql.*;

public class DatabaseConnector {
    Logger logger;
    private Connection connection;
    private Statement statement;

    DatabaseConnector(Logger logger) {
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
                int id = resultSet.getInt("id");
                int level = resultSet.getInt("level");
                int intelligence = resultSet.getInt("intelligence");
                int strength = resultSet.getInt("strength");
                int agility = resultSet.getInt("agility");
                int vitality = resultSet.getInt("vitality");
                int luck = resultSet.getInt("luck");
                int freeSkillPoints = resultSet.getInt("freeSkillPoints");
                int hp = resultSet.getInt("hp");
                int mana = resultSet.getInt("mana");
                int defence = resultSet.getInt("defence");
                int magicResistance = resultSet.getInt("magicResistance");
                Inventory inventory = new Inventory(new JSONObject(resultSet.getString("inventory")));
                return new User(id, username, password, level, intelligence, strength, agility, vitality, luck, freeSkillPoints, hp, mana, defence, magicResistance, inventory);
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
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new User(id, username, password);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    private void createConnection() throws SQLException {
        String url = "jdbc:sqlite:jabia.db";
        connection = DriverManager.getConnection(url);
    }

    private void createStatement() throws SQLException {
        statement = connection.createStatement();
    }

    private void createDatabase() throws SQLException {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS users (");
        query.append("id INTEGER PRIMARY KEY,");
        query.append("username TEXT NOT NULL,");
        query.append("password TEXT NOT NULL,");
        query.append("level INTEGER DEFAULT ").append(User.DEFAULT_LEVEL).append(",");
        query.append("intelligence INTEGER DEFAULT ").append(User.DEFAULT_INTELLIGENCE).append(",");
        query.append("strength INTEGER DEFAULT ").append(User.DEFAULT_STRENGTH).append(",");
        query.append("agility INTEGER DEFAULT ").append(User.DEFAULT_AGILITY).append(",");
        query.append("vitality INTEGER DEFAULT ").append(User.DEFAULT_VITALITY).append(",");
        query.append("luck INTEGER DEFAULT ").append(User.DEFAULT_LUCK).append(",");
        query.append("freeSkillPoints INTEGER DEFAULT ").append(User.DEFAULT_FREE_SKILL_POINTS).append(",");
        query.append("hp INTEGER DEFAULT ").append(User.DEFAULT_HP).append(",");
        query.append("mana INTEGER DEFAULT ").append(User.DEFAULT_MANA).append(",");
        query.append("defence INTEGER DEFAULT ").append(User.DEFAULT_DEFENCE).append(",");
        query.append("magicResistance INTEGER DEFAULT ").append(User.DEFAULT_MAGIC_RESISTANCE).append(",");
        query.append("inventory TEXT DEFAULT '").append(User.DEFAULT_INVENTORY.toJSON().toString()).append("')");
        statement.executeUpdate(query.toString());
    }
}
