package mpks.jabia.client.character.component;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import javafx.geometry.Point2D;
import mpks.jabia.client.character.CharacterEntity;
import mpks.jabia.client.event.single_event.OnOrderedMoveEvent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.fire;

public class CharacterActionComponent extends Component {
    private CharacterEntity attackTarget;
    private CharacterEntity dialogueTarget;
    private Entity pickupTarget;
    private Point2D moveTarget;
    private StateComponent stateComponent;
    private CharacterEntity characterEntity;
    private AnimationComponent animationComponent;
    private CellMoveComponent cellMoveComponent;

    @SuppressWarnings("unchecked")
    public AStarMoveComponent<?> getAStarMoveComponent() {
        return (AStarMoveComponent<AStarCell>) entity.getComponent(AStarMoveComponent.class);
    }

    public void orderIdle() {
        stateComponent.changeStateToIdle();
    }

    public void orderMove(int x, int y) {
        fire(new OnOrderedMoveEvent(characterEntity, x, y));

        reset();
        stateComponent.changeState(moveState);
        move(x, y);
    }

    @Override
    public void onAdded() {
        characterEntity = (CharacterEntity) entity;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        if (stateComponent.isIdle()) {
            if (animationComponent.isFacingLeft()) {
                animationComponent.loopIdleLeft();
            } else if (animationComponent.isFacingRight()) {
                animationComponent.loopIdleRight();
            } else if (animationComponent.isFacingUp()) {
                animationComponent.loopIdleUp();
            } else if (animationComponent.isFacingDown()) {
                animationComponent.loopIdleDown();
            }

            return;
        }
        if (cellMoveComponent.isMovingDown()) {
            animationComponent.loopWalkDown();
        } else if (cellMoveComponent.isMovingUp()) {
            animationComponent.loopWalkUp();
        } else if (cellMoveComponent.isMovingLeft()) {
            animationComponent.loopWalkLeft();
        } else if (cellMoveComponent.isMovingRight()) {
            animationComponent.loopWalkRight();
        }
    }

    private void move(int x, int y) {
        moveTarget = new Point2D(x, y);
        getAStarMoveComponent().moveToCell(x, y);
        stateComponent.changeState(moveState);
    }

    private void reset() {
        attackTarget = null;
        dialogueTarget = null;
        pickupTarget = null;
        moveTarget = null;
    }

    private final EntityState moveState = new EntityState() {
        @Override
        public void onUpdate(double timePerFrame) {
            if (getAStarMoveComponent().isAtDestination()) {
                // identify other situations like pickup/attack/dialogue
                stateComponent.changeStateToIdle();
            }
        }
    };
}
