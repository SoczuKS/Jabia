package mpks.jabia.client.ui;

import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mpks.jabia.client.GameSettings;

public class SortButton extends Parent {
    private InventoryView inventoryView;

    public SortButton(InventoryView inventoryView) {
        this.inventoryView = inventoryView;

        Rectangle bg = new Rectangle(GameSettings.tileSize, GameSettings.tileSize);
        bg.setArcWidth(10.0);
        bg.setArcHeight(10.0);
        bg.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.DARKGRAY).otherwise(Color.LIGHTGRAY));

        getChildren().add(bg);

        for (int i = 0; i < 4; i++) {
            Rectangle rect = new Rectangle(5.0, 5.0, Color.BLACK);
            int x = i % 2 + 1;
            int y = i / 2 + 1;

            rect.setTranslateX(x * 9.0);
            rect.setTranslateY(y * 9.0);

            getChildren().add(rect);
        }

        setOnMouseClicked(e -> inventoryView.sort());
    }
}
