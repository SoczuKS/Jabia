package mpks.jabia.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import mpks.jabia.client.character.CharacterEntity;
import mpks.jabia.client.event.EventHandler;
import mpks.jabia.client.screen.LoadingScreen;
import mpks.jabia.client.screen.LoginScreen;
import mpks.jabia.client.ui.BasicInfoView;
import mpks.jabia.client.ui.PlayerInventoryView;
import mpks.jabia.common.User;
import mpks.jabia.common.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static mpks.jabia.client.GameSettings.windowWidth;
import static mpks.jabia.common.NetConfig.SERVER_ADDRESS;

public class Game extends GameApplication {
    public Socket socket = null;
    public User user = null;
    public Gameplay gameplay = null;
    public WorldInfo worldInfo = null;
    CharacterEntity userCharacter = null;

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
        super.initGameVars(vars);
    }

    @Override
    protected void onPreInit() {
        EventHandler.getInstance().initialize();
    }

    @Override
    protected void initGame() {
        gameplay = new Gameplay();

        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new JabiaEntityFactory(this));

        SpawnData spawnData = new SpawnData();
        spawnData.put("user", user);

        userCharacter = (CharacterEntity) spawn("player", spawnData);
        spawn("cell_selection");

        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setZoom(1.0);
        getGameScene().getViewport().bindToEntity(userCharacter, (double) getAppWidth() / 2, (double) getAppHeight() / 2);

        try {
            gameplay.goToMapWithPosition("map", 2, 6);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionCollectible(EntityType.PLAYER, EntityType.ITEM, (entity) -> {

        });

        FXGL.onCollisionCollectible(EntityType.PLAYER, EntityType.TREASURE_CHEST, (entity) -> {

        });
    }

    @Override
    protected void initUI() {
        getGameScene().setUIMouseTransparent(false);
        Image cursor = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/ui/cursors/main.png")));
        getGameScene().setCursor(cursor, new Point2D(52.0, 10.0));

        getGameScene().addUINodes(
                new BasicInfoView(userCharacter),
                new PlayerInventoryView(userCharacter)
        );
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.C, "ToggleBasicView", () -> {
            getGameScene().getUINodes().stream()
                    .filter(node -> node instanceof BasicInfoView)
                    .forEach(node -> ((BasicInfoView) node).getMinimizeButton().onClick());
            return null;
        });

        onKeyDown(KeyCode.I, "ToggleInventoryView", () -> {
            getGameScene().getUINodes().stream()
                    .filter(node -> node instanceof PlayerInventoryView)
                    .forEach(node -> ((PlayerInventoryView) node).getMinimizeButton().onClick());
            return null;
        });
    }

    @Override
    protected void onUpdate(double timePerFrame) {
        gameplay.getCurrentMap().onUpdate(timePerFrame);
    }
}
