package mpks.jabia.client;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mpks.jabia.client.character.*;
import mpks.jabia.client.components.CellSelectionComponent;
import mpks.jabia.common.User;

import static com.almasb.fxgl.dsl.FXGLForKtKt.animationBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static mpks.jabia.client.EntityType.*;

public class JabiaEntityFactory implements EntityFactory {
    private final Game game;

    JabiaEntityFactory(Game game) {
        this.game = game;
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        data.put("cellX", 0);
        data.put("cellY", 0);

        CharacterEntity player = (CharacterEntity) newCharacter(data);

        player.setType(PLAYER);
        player.addComponent(new PlayerComponent());
        player.addComponent(new PlayerWorldComponent());
        player.addComponent(new IrremovableComponent());
        player.addComponent(new AnimationComponent("/assets/textures/characters/players/local_player.png"));
        player.getViewComponent().getParent().setMouseTransparent(true);

        return player;
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
        Rectangle view = new Rectangle(32, 32, null);
        view.setStroke(Color.BLACK);

        animationBuilder()
                .repeatInfinitely()
                .autoReverse(true)
                .interpolator(Interpolators.CIRCULAR.EASE_OUT())
                .animate(view.strokeProperty())
                .from(Color.color(0.0, 0.0, 0.0, 0.7))
                .to(Color.color(1.0, 0.84313726, 0.0, 0.7))
                .buildAndPlay();

        Entity entity = entityBuilder(data)
                .type(CELL_SELECTION)
                .view(view)
                .zIndex(3500)
                .with(new CellSelectionComponent(game.gameplay))
                .with(new IrremovableComponent())
                .build();

        entity.getViewComponent().getParent().setMouseTransparent(true);

        return entity;
    }

    private Entity newCharacter(SpawnData data) {
        CharacterEntity characterEntity = new CharacterEntity();
        data.getData().forEach(characterEntity::setProperty);

        characterEntity.setLocalAnchor(new Point2D(32, 54));
        characterEntity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(64, 64)));

        characterEntity.addComponent(new CollidableComponent(true));
        characterEntity.addComponent(new StateComponent());
        characterEntity.addComponent(new CharacterEffectComponent());
        characterEntity.addComponent(new CellMoveComponent(32,32,32 * 4.0));
        characterEntity.addComponent(new AStarMoveComponent<>(new LazyValue<>(() -> game.gameplay.getCurrentMap().getGrid())));
        characterEntity.addComponent(new CharacterActionComponent());
        characterEntity.addComponent(new CharacterComponent(data));

        characterEntity.goToPosition(data.get("cellX"), data.get("cellY"));

        animationBuilder().fadeIn(characterEntity).buildAndPlay();

        return characterEntity;
    }
}
