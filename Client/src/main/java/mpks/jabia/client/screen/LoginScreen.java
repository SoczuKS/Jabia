package mpks.jabia.client.screen;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mpks.jabia.client.Game;
import mpks.jabia.common.RequestBuilder;
import mpks.jabia.common.SocketWriter;
import mpks.jabia.common.User;
import mpks.jabia.common.WorldInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        EventHandler<KeyEvent> enterKeyHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        };

        usernameTextArea.setOnKeyPressed(enterKeyHandler);
        passwordTextArea.setOnKeyPressed(enterKeyHandler);

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

            ExecutorService loginExecutor = Executors.newSingleThreadExecutor();
            LoginResponseHandler loginResponseHandler = new LoginResponseHandler(game.socket);

            try {
                Future<LoginResponseHandler.Result> future = loginExecutor.submit(loginResponseHandler);
                LoginResponseHandler.Result result = future.get();

                if (result.success) {
                    game.user = result.user;
                    game.worldInfo = result.worldInfo;
                    game.otherPlayersInfo = result.otherPlayersInfo;
                    Platform.runLater(this::fireNewGame);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                waitingForResponse = false;
                loginExecutor.shutdown();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private record LoginResponseHandler(Socket socket) implements Callable<LoginResponseHandler.Result> {
        @Override
        public Result call() {
            Result result = new Result(false, null, null, null);

            try {
                var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = reader.readLine();
                JSONObject json = new JSONObject(response);

                if (json.getString("type").equals("response") && json.getString("action").equals("login") && json.getString("status").equals("success")) {
                    result = new Result(true, new User(json.getJSONObject("user")), new WorldInfo(json.getJSONObject("worldInfo")), json.getJSONArray("currentUsers"));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            return result;
        }

        record Result(boolean success, User user, WorldInfo worldInfo, JSONArray otherPlayersInfo) {
        }
    }
}
