package mpks.jabia.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    UserDatabase userDatabase;
    Logger logger;
    Server server;

    ClientHandler(Socket clientSocket, UserDatabase userDatabase, Logger logger, Server server) {
        this.clientSocket = clientSocket;
        this.userDatabase = userDatabase;
        this.logger = logger;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            logger.write("Client connected.");
            clientSocket.setSoTimeout(5 * 60 * 1000);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (server.running) {
                try {
                    String msg = reader.readLine();
                    logger.write("[" + clientSocket.toString() + "] Received: " + msg);
                } catch (SocketTimeoutException e) {
                    logger.write("Client disconnected due to inactivity.");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
