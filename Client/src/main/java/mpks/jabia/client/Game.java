package mpks.jabia.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Game extends GameApplication {
    private final int windowWidth = 1360;
    private final String serverUrl = "localhost";
    MainMenu mainMenu;

    public Game() {

    }

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
    protected void initUI() {
        TextArea usernameTextArea = new TextArea();
        usernameTextArea.setPrefWidth(250);
        usernameTextArea.setPrefHeight(50);
        usernameTextArea.setPromptText("Username");

        TextArea passwordTextArea = new TextArea();
        passwordTextArea.setPrefWidth(250);
        passwordTextArea.setPrefHeight(50);
        passwordTextArea.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        loginButton.setPrefHeight(50);
        loginButton.setOnAction(_ -> {
            String username = usernameTextArea.getText();
            String password = passwordTextArea.getText();
            System.out.println("Username: " + username + ", Password: " + password);
        });

        int uiX = windowWidth / 2 - 125;

        FXGL.addUINode(usernameTextArea, uiX, 100);
        FXGL.addUINode(passwordTextArea, uiX, 175);
        FXGL.addUINode(loginButton, uiX + 75, 250);
    }
}
