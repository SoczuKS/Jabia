package mpks.jabia.client.ui;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.animationBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class MinimizeButton extends StackPane {
    private boolean isMinimized;
    private final double minimizedY;
    private final Parent root;
    private final Rectangle background;

    public MinimizeButton(String key, double x, double y, double minimizedX, double minimizedY, Parent root) {
        isMinimized = true;
        this.minimizedY = minimizedY;
        this.root = root;

        background = new Rectangle(30.0, 20.0);
        background.setStroke(Color.WHITE);

        var buttonText = getUIFactoryService().newText(key);

        relocate(x, y);

        root.setTranslateX(minimizedX);
        root.setTranslateY(minimizedY);

        setOnMouseClicked(_ -> onClick());

        getChildren().addAll(background, buttonText);
    }

    void onClick() {
        animationBuilder()
                .duration(Duration.seconds(0.33))
                .translate(root)
                .from(isMinimized ? new Point2D(0.0, minimizedY) : new Point2D(0.0, 0.0))
                .to(isMinimized ? new Point2D(0.0, 0.0) : new Point2D(0.0, minimizedY))
                .buildAndPlay();

        animationBuilder()
                .duration(Duration.seconds(0.33))
                .animate(background.fillProperty())
                .from(Color.GOLD)
                .to(Color.BLACK)
                .buildAndPlay();

        isMinimized = !isMinimized;
    }
}
