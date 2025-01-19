package mpks.jabia.client.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.inventory.ItemStack;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mpks.jabia.common.Item;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class ItemPane extends StackPane {
    private Text text;
    private ItemStack<Item> itemStack;

    public ItemPane() {
        text = getUIFactoryService().newText("", Color.WHITE, 12.0);
        itemStack = null;
        setAlignment(Pos.BOTTOM_RIGHT);
        setPickOnBounds(true);
        setCursor(Cursor.HAND);
        text.setStrokeWidth(1.5);
        getChildren().add(text);
    }

    public void setItemStack(ItemStack<Item> itemStack) {
        if (itemStack == null) {
            if (this.itemStack != null) {
                getChildren().removeFirst();
                text.textProperty().unbind();
                text.setText("");
            }
        } else {
            Texture view = FXGL.texture(itemStack.getUserItem().getTextureName());
            getChildren().add(view);
            text.textProperty().bind(itemStack.quantityProperty().asString());
//            setOnTooltipHover { t: TooltipView ->
//                    t.setItem(value.userItem)
//            }
        }

        this.itemStack = itemStack;
    }

    ItemStack<Item> getItemStack() {
        return itemStack;
    }

    public boolean isEmpty() {
        return itemStack == null;
    }
}
