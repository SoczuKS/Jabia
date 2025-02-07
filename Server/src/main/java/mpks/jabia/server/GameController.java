package mpks.jabia.server;

import mpks.jabia.common.User;
import mpks.jabia.common.WorldInfo;

import java.io.IOException;
import java.sql.SQLException;

public class GameController {
    private final DatabaseConnector databaseConnector;
    private final Logger logger;
    private final WorldInfo worldInfo;

    GameController(Logger logger) throws IOException {
        this.logger = logger;
        databaseConnector = new DatabaseConnector(logger);
        worldInfo = new WorldInfo();
    }

    public void connectDatabase() throws SQLException {
        databaseConnector.connect();
    }

    public User login(String username, String password) throws SQLException, IOException {
        var user = databaseConnector.getUser(username);

        if (user == null) {
            logger.write("User not found. Registering user.");
            return databaseConnector.registerUser(username, password);
        }

        boolean passwordMatch = user.getPassword().equals(password);
        if (!passwordMatch) {
            logger.write("Incorrect password.");
            return null;
        }

        return user;
    }

    public WorldInfo getWorldInfo() {
        return worldInfo;
    }
}
