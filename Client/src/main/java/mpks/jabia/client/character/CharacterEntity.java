package mpks.jabia.client.character;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import mpks.jabia.client.EntityType;
import mpks.jabia.client.character.component.AnimationComponent;
import mpks.jabia.client.character.component.CharacterActionComponent;
import mpks.jabia.client.character.component.CharacterComponent;

public class CharacterEntity extends Entity {
    public CharacterEntity() {
    }

    public CharacterActionComponent getCharacterActionComponent() {
        return getComponent(CharacterActionComponent.class);
    }

    public AnimationComponent getAnimationComponent() {
        return getComponent(AnimationComponent.class);
    }

    public CharacterComponent getCharacterComponent() {
        return getComponent(CharacterComponent.class);
    }

    public void goToPosition(int x, int y) {
        getComponent(AStarMoveComponent.class).stopMovementAt(x, y);
    }

    public boolean isPlayer() {
        return isType(EntityType.PLAYER);
    }

    public String getUsername() {
        return getCharacterComponent().getUsername();
    }
}
