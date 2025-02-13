package mpks.jabia.server;

import mpks.jabia.common.NetConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final Logger logger;
    private final GameController gameController;
    private final ExecutorService executorService;
    List<ClientHandler> clientHandlers;
    boolean running;

    Server() throws IOException {
        logger = new Logger();
        running = false;
        gameController = new GameController(logger);
        logger.write("Server ready.");
        clientHandlers = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(1000);
    }

    void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(NetConfig.SERVER_PORT)) {
            gameController.connectDatabase();
            running = true;
            logger.write("Server started.");

            while (running) {
                try {
                    clientHandlers.add(new ClientHandler(serverSocket.accept(), gameController, logger, this));
                    executorService.execute(clientHandlers.getLast());
                } catch (IOException e) {
                    logger.write("Error occurred: " + e.getMessage());
                    running = false;
                }
            }

        } catch (SQLException e) {
            logger.write("Error occurred: " + e.getMessage());
        }
    }

    public void broadcast(String message, String exceptOfUsername) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.user != null && !clientHandler.user.getUsername().equals(exceptOfUsername)) {
                clientHandler.sendMessage(message);
            }
        }
    }

    public void broadcast(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }
}
