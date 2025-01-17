package mpks.jabia.client;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static mpks.jabia.client.EntityType.PORTAL;
import static mpks.jabia.client.EntityType.WALKABLE;

public class JabiaEntityFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return null;
    }

    @Spawns("other_player")
    public Entity newOtherPlayer(SpawnData data) {
        return null;
    }

    @Spawns("npc")
    public Entity newNpc(SpawnData data) {
        return null;
    }

    @Spawns("monster")
    public Entity newMonster(SpawnData data) {
        return null;
    }

    @Spawns("portal")
    public Entity newPortal(SpawnData data) {
        double width = ((Number) data.get("width")).doubleValue();
        double height = ((Number) data.get("height")).doubleValue();

        Rectangle2D interactionCollisionBox = new Rectangle2D(data.getX() + 32, data.getY() + 32, width - 32, height - 32);
        return entityBuilder(data).type(PORTAL).bbox(new HitBox(BoundingShape.box(width, height)))
                .with("interactionCollisionBox", interactionCollisionBox)
                //TODO:.with(new PortalComponent(data.get("mapName"), data.get("toX"), data.get("toY")))
                .build();
    }

    @Spawns("walkable")
    public Entity newWalkable(SpawnData data) {
        double width = ((Number) data.get("width")).doubleValue();
        double height = ((Number) data.get("height")).doubleValue();

        Entity entity = entityBuilder(data).type(WALKABLE).bbox(new HitBox(BoundingShape.box(width, height)))
                .onClick((e) -> {
                    // TODO: Fill
                }).build();
        entity.getViewComponent().addChild(new Rectangle(width, height, Color.TRANSPARENT));
        return entity;
    }

    @Spawns("item")
    public Entity newItem(SpawnData data) {
        return null;
    }

    @Spawns("cell_selection")
    public Entity newCellSelection(SpawnData data) {
        return null;
    }
}
