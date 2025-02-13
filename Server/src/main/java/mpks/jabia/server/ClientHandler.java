package mpks.jabia.server;

import mpks.jabia.common.RequestBuilder;
import mpks.jabia.common.ResponseBuilder;
import mpks.jabia.common.SocketWriter;
import mpks.jabia.common.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import static mpks.jabia.common.RequestBuilder.buildMonsterDefeatedRequest;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    GameController gameController;
    Logger logger;
    Server server;
    User user = null;
    boolean clientConnected = true;
    int x;
    int y;

    ClientHandler(Socket clientSocket, GameController gameController, Logger logger, Server server) {
        this.clientSocket = clientSocket;
        this.gameController = gameController;
        this.logger = logger;
        this.server = server;
        x = gameController.worldInfo.getSpawnPointX();
        y = gameController.worldInfo.getSpawnPointY();
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
                break;

            case "move":
                x = json.getInt("x");
                y = json.getInt("y");
                server.broadcast(json.toString(), user.getUsername());
                break;

            case "attack":
                var id = json.getInt("id");
                gameController.killMonster(id);
                server.broadcast(buildMonsterDefeatedRequest(id));
                break;

            case "monsterDefeated":
                server.broadcast(json.toString());
                break;
        }
    }

    private void processLogin(JSONObject json) throws IOException, SQLException {
        String username = json.getString("username");
        String password = json.getString("password");

        user = gameController.login(username, password);

        JSONArray currentUsers = new JSONArray();
        for (ClientHandler clientHandler : server.clientHandlers) {
            if (clientHandler.user != null && !clientHandler.user.getUsername().equals(username)) {
                JSONObject user = new JSONObject();
                user.put("username", clientHandler.user.getUsername());
                user.put("x", clientHandler.x);
                user.put("y", clientHandler.y);
                currentUsers.put(user);
            }
        }

        String response = ResponseBuilder.buildLoginResponse(user, gameController.getWorldInfo(), currentUsers);
        SocketWriter.write(clientSocket, response);
        logger.write(clientSocket, "Sent: " + response);

        if (user != null) {
            server.broadcast(RequestBuilder.buildPlayerJoinRequest(user.getUsername(), x, y), user.getUsername());
        }
    }

    public void sendMessage(String message) {
        try {
            SocketWriter.write(clientSocket, message);
            logger.write(clientSocket, "Sent: " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
