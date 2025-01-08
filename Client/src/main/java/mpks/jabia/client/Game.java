package mpks.jabia.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

import static mpks.jabia.client.GameSettings.windowWidth;

public class Game extends GameApplication {
    private final String serverUrl = "localhost";
    Socket socket;

    public void run(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(windowWidth);
        settings.setHeightFromRatio(16.0 / 9.0);
        settings.setTitle("Jabia");
        settings.setVersion("1.0.0");
    }

    @Override
    protected void initGame() {
        try {
            socket = new Socket(serverUrl, 34527);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(() -> FXGL.getSceneService().pushSubScene(new LoginScreen(this)));
    }

    @Override
    protected void initUI() {

    }
}
