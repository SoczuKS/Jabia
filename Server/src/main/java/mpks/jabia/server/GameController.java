package mpks.jabia.server;

import mpks.jabia.common.User;

import java.io.IOException;
import java.sql.SQLException;

public class GameController {
    private final UserDatabase userDatabase;
    private final Logger logger;

    GameController(Logger logger) {
        this.logger = logger;
        userDatabase = new UserDatabase(logger);
    }

    public void connectDatabase() throws SQLException {
        userDatabase.connect();
    }

    public User login(String username, String password) throws SQLException, IOException {
        var user = userDatabase.getUser(username);

        if (user == null) {
            logger.write("User not found. Registering user.");
            return userDatabase.registerUser(username, password);
        }

        boolean passwordMatch = user.password().equals(password);
        if (!passwordMatch) {
            logger.write("Incorrect password.");
            return null;
        }

        return user;
    }
}
