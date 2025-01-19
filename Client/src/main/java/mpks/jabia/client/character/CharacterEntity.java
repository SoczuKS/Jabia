package mpks.jabia.client;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

public class CharacterEntity extends Entity {
    public CharacterEntity() {
    }

    public CharacterActionComponent getCharacterActionComponent() {
        return getComponent(CharacterActionComponent.class);
    }

    public void goToPosition(int x, int y) {
        getComponent(AStarMoveComponent.class).stopMovementAt(x, y);
    }
}
