package mpks.jabia.client;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mpks.jabia.common.RequestBuilder;
import mpks.jabia.common.SocketWriter;
import mpks.jabia.common.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static mpks.jabia.client.GameSettings.windowWidth;

public class LoginScreen extends FXGLMenu {
    private boolean waitingForResponse;

    public LoginScreen() {
        super(MenuType.MAIN_MENU);
        this.waitingForResponse = false;
        initUI();
    }

    private void initUI() {
        int uiX = windowWidth / 2 - 125;

        var usernameTextArea = new TextField();
        usernameTextArea.setPrefWidth(250);
        usernameTextArea.setPrefHeight(50);
        usernameTextArea.setPromptText("Username");
        usernameTextArea.setTranslateX(uiX);
        usernameTextArea.setTranslateY(100);

        var passwordTextArea = new PasswordField();
        passwordTextArea.setPrefWidth(250);
        passwordTextArea.setPrefHeight(50);
        passwordTextArea.setPromptText("Password");
        passwordTextArea.setTranslateX(uiX);
        passwordTextArea.setTranslateY(175);

        var loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        loginButton.setPrefHeight(50);
        loginButton.setOnAction(_ -> {
            String username = usernameTextArea.getText();
            String password = passwordTextArea.getText();
            System.out.println("Username: " + username + ", Password: " + password);
            authenticate(username, password);
        });
        loginButton.setTranslateX(uiX + 75);
        loginButton.setTranslateY(250);

        getContentRoot().getChildren().addAll(usernameTextArea, passwordTextArea, loginButton);
    }

    private void authenticate(String username, String password) {
        if (waitingForResponse) {
            return;
        }
        waitingForResponse = true;
        try {
            Game game = (Game) FXGL.getApp();
            game.connectToServer();
            SocketWriter.write(game.socket, RequestBuilder.buildLoginRequest(username, password));
            new Thread(() -> {
                try {
                    var reader = new BufferedReader(new InputStreamReader(game.socket.getInputStream()));
                    String response = reader.readLine();
                    JSONObject json = new JSONObject(response);

                    if (json.getString("type").equals("response") && json.getString("action").equals("login")) {
                        if (json.getString("status").equals("success")) {
                            game.user = new User(json.getJSONObject("user"));
                            Platform.runLater(this::fireNewGame);
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                } finally {
                    waitingForResponse = false;
                }
            }).start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
