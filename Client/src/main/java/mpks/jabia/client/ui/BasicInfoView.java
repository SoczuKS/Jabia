package mpks.jabia.client.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import mpks.jabia.client.character.CharacterEntity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class BasicInfoView extends Parent {
    private final CharacterEntity characterEntity;
    private final CharacterInfoView characterInfoView;
    private final MinimizeButton minimizeButton;

    public BasicInfoView(CharacterEntity entity) {
        this.characterEntity = entity;
        this.characterInfoView = new CharacterInfoView(this.characterEntity);
        this.minimizeButton = new MinimizeButton("C", 15, 440 - 25, 0, -(440 - 25) + 25, this);

        Pane uiPane = new Pane();

        Rectangle border = new Rectangle((180 * 2 + 3), (440 + 5));
        border.setStrokeWidth(2.0);
        border.setArcWidth(10.0);
        border.setArcHeight(10.0);

        var borderShape = Shape.union(border, new Circle((180 * 2 + 3 - 30), 0.0, 30.0));
        borderShape.setFill(Color.rgb(25, 25, 25, 0.8));
        borderShape.setStroke(Color.WHITE);
        borderShape.setScaleX(-1.0);
        borderShape.setScaleY(-1.0);

        Text text = getUIFactoryService().newText("", Color.WHITE, 12.0);
        text.textProperty().set(characterEntity.getUsername());

        VBox vBox = new VBox(5.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(characterInfoView, new Separator(), uiPane, text);

        getChildren().addAll(borderShape, vBox, minimizeButton);
    }

    public MinimizeButton getMinimizeButton() {
        return minimizeButton;
    }
}
