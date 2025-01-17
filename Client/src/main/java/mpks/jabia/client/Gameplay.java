package mpks.jabia.client;

import com.almasb.fxgl.app.services.FXGLAssetLoaderService;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import java.net.MalformedURLException;
import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Gameplay {
    private static final String GAME_MAP = "GAME_MAP";

    public Gameplay() {
    }

    public CharacterEntity getPlayer() {
        return (CharacterEntity) getGameWorld().getSingleton(EntityType.PLAYER);
    }

    public void goToMapWithPosition(String mapName, int x, int y) throws MalformedURLException {
        if (FXGL.getWorldProperties().exists(GAME_MAP)) {
            if (getCurrentMap().getName().equals(mapName)) {
                goToPosition(x, y);
                return;
            }
        }

        var url = Gameplay.class.getResource("/assets/levels/tmx/" + mapName + ".tmx");
        if (url == null) {
            throw new IllegalArgumentException("Map not found: " + mapName);
        }
        var levelLoader = new TMXLevelLoader(true);
        var level = levelLoader.load(url, FXGL.getGameWorld());

        if (FXGL.getWorldProperties().exists(GAME_MAP)) {
            getCurrentMap().exit();
        }

        var newMap = new Map(mapName, level);
        newMap.enter();

        set(GAME_MAP, newMap);

        getPlayer().removeComponent(AStarMoveComponent.class);
        getPlayer().addComponent(new AStarMoveComponent<>(newMap.getGrid()));

        getPlayer().getCharacterActionComponent().orderIdle();
        getPlayer().setPosition(x, y);

        getGameScene().getViewport().setBounds(0, 0, level.getWidth(), level.getHeight());
    }

    public void goToPosition(int x, int y) {
        getPlayer().getCharacterActionComponent().orderIdle();
        getPlayer().goToPosition(x, y);
    }

    public Map getCurrentMap() {
        return geto(GAME_MAP);
    }
}
