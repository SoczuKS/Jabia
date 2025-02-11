package mpks.jabia.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerListener implements Runnable {
    private final Game game;

    ServerListener(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (game.running) {
            try {
                var reader = new BufferedReader(new InputStreamReader(game.socket.getInputStream()));
                String message = reader.readLine();
                JSONObject json = new JSONObject(message);
                handleMessage(json);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void handleMessage(JSONObject json) {
        if (json.getString("type").equals("request")) {
            handleRequest(json);
        }
    }

    private void handleRequest(JSONObject json) {
        String action = json.getString("action");
        switch (action) {
            case "playerJoin" -> game.addNewOtherPlayer(json);
        }
    }
}
