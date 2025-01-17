package mpks.jabia.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import mpks.jabia.common.User;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static mpks.jabia.client.GameSettings.windowWidth;
import static mpks.jabia.common.NetConfig.SERVER_ADDRESS;

public class Game extends GameApplication {
    public Socket socket = null;
    public User user = null;
    public Gameplay gameplay = null;

    public void run(String[] args) {
        launch(args);
    }

    public void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, 34527);
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
        settings.setVersion("0.0.1");
        //settings.getCSSList().add("jabia.css");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public @NotNull FXGLMenu newMainMenu() {
                return new LoginScreen();
            }

            @Override
            public @NotNull LoadingScene newLoadingScene() {
                return new LoadingScreen();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {

    }

    @Override
    protected void onPreInit() {
        EventHandler.getInstance().initialize();
    }

    @Override
    protected void initGame() {
        gameplay = new Gameplay();

        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new JabiaEntityFactory());

        //CharacterEntity userCharacter = (CharacterEntity) spawn("player");
        //spawn("cellSelection");

        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setZoom(1.5);
        //getGameScene().getViewport().bindToEntity(userCharacter, (double) getAppWidth() / 2, (double) getAppHeight() / 2);

        try {
            gameplay.goToMapWithPosition("map", 2, 6);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initUI() {
        getGameScene().setUIMouseTransparent(false);
        getGameScene().setCursor(image("ui/cursors/main.png"), new Point2D(52.0, 10.0));

        getGameScene().addUINodes(
                new BasicInfoView(),
                new PlayerInventoryView()
        );
    }

    @Override
    protected void initInput() {
//        onKeyDown(KeyCode.C, "ToggleBasicView", () ->
//                getGameScene().getUINodes().stream()
//                        .filter(node -> node instanceof BasicInfoView)
//                        .forEach(node -> ((BasicInfoView) node).getMinimizeButton().onClick()));
//
//        onKeyDown(KeyCode.I, "ToggleInventoryView", () ->
//                getGameScene().getUINodes().stream()
//                        .filter(node -> node instanceof PlayerInventoryView)
//                        .forEach(node -> ((PlayerInventoryView) node).getMinimizeButton().onClick()));
    }

    @Override
    protected void onUpdate(double timePerFrame) {
        //currentMap.onUpdate(timePerFrame);
    }
}
