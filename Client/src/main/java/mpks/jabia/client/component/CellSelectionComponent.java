package mpks.jabia.client.component;


import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mpks.jabia.client.GameSettings;
import mpks.jabia.client.Gameplay;

import static com.almasb.fxgl.dsl.FXGLForKtKt.animationBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class CellSelectionComponent extends Component {
    private final Gameplay gameplay;

    public CellSelectionComponent(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        int cellX = (int) (getInput().getMouseXWorld() / GameSettings.tileSize);
        int cellY = (int) (getInput().getMouseYWorld() / GameSettings.tileSize);

        AStarGrid grid = gameplay.getCurrentMap().getGrid();

        if (!grid.isWithin(cellX, cellY)) {
            return;
        }

        AStarCell cell = grid.get(cellX, cellY);

        if (cell.isWalkable()) {
            entity.setPosition(cellX * GameSettings.tileSize, cellY * GameSettings.tileSize);
        }
    }

    public void onClick() {
        Rectangle view = new Rectangle(GameSettings.tileSize, GameSettings.tileSize, null);
        view.setStroke(Color.GOLD);

        entity.getViewComponent().addChild(view);

        animationBuilder()
                .onFinished(() -> entity.getViewComponent().removeChild(view))
                .duration(Duration.seconds(0.66))
                .interpolator(Interpolators.SMOOTH.EASE_OUT())
                .scale(view)
                .from(new Point2D(1, 1))
                .to(Point2D.ZERO)
                .buildAndPlay();
    }
}
