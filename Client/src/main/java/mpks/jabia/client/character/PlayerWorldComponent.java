package mpks.jabia.client.character;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.inventory.Inventory;
import com.almasb.fxgl.ui.MDIWindow;
import javafx.scene.layout.Pane;
import mpks.jabia.client.ui.StorageView;

public class PlayerWorldComponent extends Component {
    private StorageView storageView;
    private MDIWindow storageWindow;

    public PlayerWorldComponent() {
        storageView = new StorageView(new Inventory<>(100));
        storageWindow = new MDIWindow("Storage");
        storageWindow.setCloseable(false);
        storageWindow.setManuallyResizable(false);
        storageWindow.setMinimizable(false);
        storageWindow.setMovable(true);
        storageWindow.setContentPane(new Pane(storageView));
        storageWindow.setPrefSize(storageView.getInventoryView().getLayoutWidth(), storageView.getInventoryView().getLayoutHeight() + 25.0);
    }

    boolean isStorageOpen() {
        return storageView.getScene() != null;
    }
}
