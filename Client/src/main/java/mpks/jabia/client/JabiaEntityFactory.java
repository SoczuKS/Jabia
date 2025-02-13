package mpks.jabia.client;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mpks.jabia.client.character.CharacterEntity;
import mpks.jabia.client.character.component.*;
import mpks.jabia.client.component.CellSelectionComponent;
import org.json.JSONObject;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static mpks.jabia.client.EntityType.*;

public class JabiaEntityFactory implements EntityFactory {
    private final Game game;

    JabiaEntityFactory(Game game) {
        this.game = game;
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        data.put("animationAssetName", "/assets/textures/characters/players/local_player.png");

        CharacterEntity player = (CharacterEntity) newCharacter(data);

        player.setType(PLAYER);
        player.addComponent(new PlayerComponent());
        player.addComponent(new PlayerWorldComponent());
        player.addComponent(new IrremovableComponent());
        player.getViewComponent().getParent().setMouseTransparent(true);

        return player;
    }

    @Spawns("other_player")
    public Entity newOtherPlayer(SpawnData data) {
        String username = ((JSONObject)data.get("otherPlayer")).getString("username");
        data.put("animationAssetName", "/assets/textures/characters/players/other_player.png");

        CharacterEntity otherPlayer = (CharacterEntity) newCharacter(data);

        otherPlayer.setType(OTHER_PLAYER);
        otherPlayer.addComponent(new IDComponent(username, 0));

        return otherPlayer;
    }

    @Spawns("npc")
    public Entity newNpc(SpawnData data) {
        mpks.jabia.common.Entity entity = data.get("npc");
        data.put("animationAssetName", entity.getEntityType() == mpks.jabia.common.EntityType.TRADER ? "/assets/textures/characters/npcs/coragh.png" : "/assets/textures/characters/npcs/charles.png");

        CharacterEntity npc = (CharacterEntity) newCharacter(data);

        npc.setType(NPC);

        npc.addComponent(new IDComponent("NPC", entity.getId()));

        npc.getViewComponent().getParent().setCursor(new ImageCursor(new Image(Objects.requireNonNull(getClass().getResourceAsStream(entity.getEntityType() == mpks.jabia.common.EntityType.TRADER ? "/assets/textures/ui/cursors/chat.png" : "/assets/textures/ui/cursors/question.png"))), GameSettings.cursorSize, GameSettings.cursorSize));
        npc.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            if (game.gameplay.getPlayer().distance(data.get("cellX"), data.get("cellY")) < 2) {
                if (entity.getEntityType() == mpks.jabia.common.EntityType.TRADER) {
                    getNotificationService().pushNotification("TODO: trader");
                } else {
                    getNotificationService().pushNotification("TODO: dialogue");
                }
            }
        });

        return npc;
    }

    @Spawns("chest")
    public Entity newTreasureChest(SpawnData data) {
        int cellX = data.get("cellX");
        int cellY = data.get("cellY");

        AStarCell cell = game.gameplay.getCurrentMap().getGrid().get(cellX, cellY);
        cell.setState(CellState.NOT_WALKABLE);

        return entityBuilder()
                .at(cellX * GameSettings.tileSize, cellY * GameSettings.tileSize)
                .type(CHEST)
                .viewWithBBox(new Texture(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/chest_closed.png")))))
                .collidable()
                .with("cell", cell)
                .onClick(entity -> {
                    if (game.gameplay.getPlayer().distance(cellX, cellY) < 2) {
                        // TODO: collect

                        entity.getViewComponent().clearChildren();
                        entity.getViewComponent().addChild(new Texture(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/chest_open_empty.png")))));
                    }
                })
                .onNotActive(_ -> cell.setState(CellState.WALKABLE))
                .build();
    }

    @Spawns("monster")
    public Entity newMonster(SpawnData data) {
        mpks.jabia.common.Entity entity = data.get("monster");
        data.put("animationAssetName", "/assets/textures/characters/enemies/skeleton_warrior.png");

        CharacterEntity monster = (CharacterEntity) newCharacter(data);

        monster.setType(MONSTER);

        monster.addComponent(new IDComponent("monster", entity.getId()));

        monster.getViewComponent().getParent().setCursor(new ImageCursor(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/ui/cursors/attack.png"))), GameSettings.cursorSize, GameSettings.cursorSize));
        monster.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            if (game.gameplay.getPlayer().distance(data.get("cellX"), data.get("cellY")) < 2) {
                game.informServerAttack(entity.getId());
            }
        });

        return monster;
    }

    @Spawns("walkable")
    public Entity newWalkable(SpawnData data) {
        double width = ((Number) data.get("width")).doubleValue();
        double height = ((Number) data.get("height")).doubleValue();

        Entity entity = entityBuilder(data).type(WALKABLE).bbox(new HitBox(BoundingShape.box(width, height)))
                .onClick(_ -> {
                    int targetX = (int) (FXGL.getInput().getMouseXWorld() / GameSettings.tileSize);
                    int targetY = (int) (FXGL.getInput().getMouseYWorld() / GameSettings.tileSize);

                    getGameWorld().getSingleton(CELL_SELECTION)
                            .getComponent(CellSelectionComponent.class)
                            .onClick();

                    getGameWorld().getSingleton(PLAYER)
                            .getComponent(CharacterActionComponent.class)
                            .orderMove(targetX, targetY);

                    game.informServerMove(targetX, targetY);
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
        Rectangle view = new Rectangle(GameSettings.tileSize, GameSettings.tileSize, null);
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

        characterEntity.setLocalAnchor(new Point2D(GameSettings.spriteSize / 2.0, GameSettings.spriteSize - 10));
        characterEntity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(GameSettings.spriteSize, GameSettings.spriteSize)));

        characterEntity.addComponent(new CollidableComponent(true));
        characterEntity.addComponent(new StateComponent());
        characterEntity.addComponent(new CellMoveComponent(GameSettings.tileSize, GameSettings.tileSize, GameSettings.tileSize * GameSettings.movementSpeed));
        characterEntity.addComponent(new AStarMoveComponent<>(new LazyValue<>(() -> game.gameplay.getCurrentMap().getGrid())));
        characterEntity.addComponent(new AnimationComponent(data.get("animationAssetName")));
        characterEntity.addComponent(new CharacterComponent(data));
        characterEntity.addComponent(new CharacterActionComponent());

        characterEntity.goToPosition(data.get("cellX"), data.get("cellY"));

        animationBuilder().fadeIn(characterEntity).buildAndPlay();

        return characterEntity;
    }
}
