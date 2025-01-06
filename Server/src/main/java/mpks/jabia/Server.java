package mpks.jabia;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final Logger logger;
    private final UserDatabase userDatabase;
    private ServerSocket serverSocket;
    boolean running;
    final int socketPort;
    private final ExecutorService executorService;

    Server() throws IOException {
        socketPort = 33581;
        logger = new Logger();
        userDatabase = new UserDatabase(logger);
        logger.write("Server ready.");
        executorService = Executors.newFixedThreadPool(1000);
    }

    void start() throws IOException {
        try {
            userDatabase.connect();
            serverSocket = new ServerSocket(socketPort);
            running = true;
            logger.write("Server started.");

            while (running) {
                try {
                    executorService.execute(new ClientHandler(serverSocket.accept(), userDatabase, logger));
                } catch (IOException e) {
                    logger.write("Error occurred: " + e.getMessage());
                    running = false;
                }
            }
        } catch (SQLException e) {
            logger.write("Error occurred: " + e.getMessage());
        }

    }
}
