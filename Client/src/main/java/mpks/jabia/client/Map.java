package mpks.jabia.client;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityWorldListener;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static mpks.jabia.client.EntityType.PORTAL;
import static mpks.jabia.client.EntityType.WALKABLE;

public class Map implements EntityWorldListener {
    private final String name;
    private final Level level;
    private AStarGrid grid = null;

    public Map(String mapName, Level level) {
        this.name = mapName;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public AStarGrid getGrid() {
        return grid;
    }

    public void enter() {
        getGameWorld().addWorldListener(this);
        getGameWorld().setLevel(level);

        grid = AStarGrid.fromWorld(FXGL.getGameWorld(), level.getWidth(), level.getHeight(), 32, 32, type -> {
            if (type == WALKABLE || type == PORTAL) {
                return CellState.WALKABLE;
            }
            return CellState.NOT_WALKABLE;
        });

        getGameWorld().getEntitiesFiltered(it -> it.isType("TiledMapLayer"))
                .forEach(entity -> {
                    //TODO: entity.addComponent(new TiledMapLayerOptimizerComponent());
                });
    }

    public void exit() {
        getGameWorld().removeWorldListener(this);
    }

    @Override
    public void onEntityAdded(Entity entity) {
    }

    @Override
    public void onEntityRemoved(Entity entity) {
    }
}
