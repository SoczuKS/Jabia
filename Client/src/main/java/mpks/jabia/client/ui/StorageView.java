package mpks.jabia.client.ui;

import com.almasb.fxgl.inventory.Inventory;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mpks.jabia.common.Item;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class StorageView extends Parent {
    private InventoryView inventoryView;
    private Button closeButton;

    public StorageView(Inventory<Item> inventory) {
        inventoryView = new InventoryView(inventory, 20, 4);
        closeButton = getUIFactoryService().newButton("Close");

        closeButton.fontProperty().unbind();
        closeButton.setFont(Font.font(11.0));
        closeButton.setPrefSize(213.0, 13.0);
        closeButton.setOnAction(e -> {/*gameplay.closeStorage()*/});

        VBox vbox = new VBox(inventoryView, closeButton);
        vbox.setAlignment(Pos.TOP_CENTER);

        getChildren().add(vbox);
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }
}
