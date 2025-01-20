package mpks.jabia.client.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import mpks.jabia.client.character.CharacterEntity;

public class PlayerInventoryView extends Parent {
    private MinimizeButton minimizeButton;
    private CharacterEntity player;

    public PlayerInventoryView(CharacterEntity player) {
        this.player = player;
        relocate(FXGL.getAppWidth() - 200.0, FXGL.getAppHeight() - 240.0);

        Rectangle border = new Rectangle(200.0 * 2 + 3, 240.0 + 5);
        border.setStrokeWidth(2.0);
        border.setArcWidth(10.0);
        border.setArcHeight(10.0);

        Shape borderShape = Shape.union(border, new Circle((200.0 * 2 + 3 - 30), 0.0, 30.0));
        borderShape.setFill(Color.rgb(25, 25, 25, 0.8));
        borderShape.setStroke(Color.WHITE);
        borderShape.setTranslateX(-200.0 - 3);

        EquipmentView equipmentView = new EquipmentView(player);
        equipmentView.setTranslateX(-200.0);

        //InventoryView inventoryView = new InventoryView(player.getInventory(), 5, 6); TODO: Fix this
        //inventoryView.onItemClicked = this::onItemClicked

        //SortButton sortButton = new SortButton(inventoryView)
        //sortButton.setTranslateX(-40.0);
        //sortButton.setTranslateY(5.0);

        minimizeButton = new MinimizeButton("I", 200.0 - 46.0, -22.0, 0.0, 240.0, this);

        getChildren().addAll(borderShape, equipmentView, /*inventoryView, sortBtn,*/ minimizeButton);
    }
}
