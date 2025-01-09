package mpks.jabia.server;

import mpks.jabia.common.ResponseBuilder;
import mpks.jabia.common.SocketWriter;
import mpks.jabia.common.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    GameController gameController;
    Logger logger;
    Server server;
    User user = null;
    boolean clientConnected = true;

    ClientHandler(Socket clientSocket, GameController gameController, Logger logger, Server server) {
        this.clientSocket = clientSocket;
        this.gameController = gameController;
        this.logger = logger;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            logger.write(clientSocket, "Client connected.");
            clientSocket.setSoTimeout(5 * 60 * 1000);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (server.running && clientConnected) {
                try {
                    String msg = reader.readLine();
                    logger.write(clientSocket, "Received: " + msg);
                    processMessage(msg);
                } catch (SocketTimeoutException e) {
                    logger.write(clientSocket, "Client disconnected due to inactivity.");
                    break;
                }
            }
        } catch (SocketException e) {
            clientConnected = false;
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            clientSocket.close();
            logger.write(clientSocket, "Client handler finished.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(String msg) throws IOException, SQLException {
        // Process the message
        JSONObject json = new JSONObject(msg);
        switch (json.getString("type")) {
            case "request":
                processRequest(json);
        }
    }

    private void processRequest(JSONObject json) throws IOException, SQLException {
        switch (json.getString("action")) {
            case "login":
                processLogin(json);
        }
    }

    private void processLogin(JSONObject json) throws IOException, SQLException {
        String username = json.getString("username");
        String password = json.getString("password");

        user = gameController.login(username, password);
        String response = user != null ? ResponseBuilder.buildLoginResponse(true) : ResponseBuilder.buildLoginResponse(false);
        SocketWriter.write(clientSocket, response);
        logger.write(clientSocket, "Sent: " + response);
    }
}
