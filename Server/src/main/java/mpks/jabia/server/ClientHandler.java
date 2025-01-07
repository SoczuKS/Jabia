package mpks.jabia.server;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    UserDatabase userDatabase;
    Logger logger;

    ClientHandler(Socket clientSocket, UserDatabase userDatabase, Logger logger) {
        this.clientSocket = clientSocket;
        this.userDatabase = userDatabase;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            logger.write("Client connected.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
