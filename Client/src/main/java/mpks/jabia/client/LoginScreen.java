package mpks.jabia.client;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mpks.jabia.common.RequestBuilder;
import mpks.jabia.common.SocketWriter;

import java.net.Socket;

import static mpks.jabia.client.GameSettings.windowWidth;

public class LoginScreen extends SubScene {
    private Game game;
    private boolean waitingForResponse;

    public LoginScreen(Game game) {
        this.game = game;
        this.waitingForResponse = false;
        initUI();
    }

    private void initUI() {
        var usernameTextArea = new TextField();
        usernameTextArea.setPrefWidth(250);
        usernameTextArea.setPrefHeight(50);
        usernameTextArea.setPromptText("Username");

        var passwordTextArea = new PasswordField();
        passwordTextArea.setPrefWidth(250);
        passwordTextArea.setPrefHeight(50);
        passwordTextArea.setPromptText("Password");

        var loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        loginButton.setPrefHeight(50);
        loginButton.setOnAction(_ -> {
            String username = usernameTextArea.getText();
            String password = passwordTextArea.getText();
            System.out.println("Username: " + username + ", Password: " + password);
            authenticate(username, password);
        });

        int uiX = windowWidth / 2 - 125;

        FXGL.addUINode(usernameTextArea, uiX, 100);
        FXGL.addUINode(passwordTextArea, uiX, 175);
        FXGL.addUINode(loginButton, uiX + 75, 250);
    }

    private void authenticate(String username, String password) {
        if (waitingForResponse) {
            return;
        }
        waitingForResponse = true;
        try {
            SocketWriter.write(game.socket, RequestBuilder.buildLoginRequest(username, password));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
