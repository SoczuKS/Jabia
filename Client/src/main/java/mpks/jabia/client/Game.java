package mpks.jabia.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Platform;
import javafx.scene.control.Label;
import mpks.jabia.common.User;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

import static mpks.jabia.client.GameSettings.windowWidth;

public class Game extends GameApplication {
    private final String serverUrl = "localhost";
    public Socket socket = null;
    public User user = null;

    public void run(String[] args) {
        launch(args);
    }

    public void connectToServer() {
        try {
            socket = new Socket(serverUrl, 34527);
            socket.setSoTimeout(10000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(windowWidth);
        settings.setHeightFromRatio(16.0 / 9.0);
        settings.setTitle("Jabia");
        settings.setVersion("1.0.0");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new LoginScreen();
            }
        });
    }

    @Override
    protected void initGame() {
        System.out.println("Game started");
    }

    @Override
    protected void initUI() {
        System.out.println("UI initialized");
    }
}
