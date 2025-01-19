package mpks.jabia.client.ui;

import com.almasb.fxgl.inventory.Inventory;
import com.almasb.fxgl.inventory.ItemStack;
import com.almasb.fxgl.ui.FXGLScrollPane;
import javafx.collections.ListChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mpks.jabia.client.MouseGestures;
import mpks.jabia.common.Item;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InventoryView extends Parent {
    private Inventory<Item> inventory;
    private int widthInCells;
    private int heightInCells;
    private Pane contentPane;
    private FXGLScrollPane scrollPane;
    private Group itemGroup;
    private ListChangeListener<ItemStack<Item>> listener;
    private MouseGestures mouseGestures;

    public InventoryView(Inventory<Item> inventory, int widthInCells, int heightInCells) {
        this.inventory = inventory;
        this.widthInCells = widthInCells;
        this.heightInCells = heightInCells;
        this.contentPane = new Pane();
        this.scrollPane = new FXGLScrollPane(contentPane);
        this.itemGroup = new Group();
        this.mouseGestures = new MouseGestures(itemGroup);

        Rectangle background = new Rectangle(40.0 * widthInCells, (double) inventory.getCapacity() / widthInCells * 40.0);
        background.setArcHeight(15.0);
        background.setArcWidth(15.0);
        background.setStroke(Color.AQUA);
        background.setStrokeWidth(1.5);

        scrollPane.setPrefSize(background.getWidth() + background.getStrokeWidth() * 2 + 10.0, 40 * heightInCells);
        scrollPane.setHbarPolicy(FXGLScrollPane.ScrollBarPolicy.NEVER);

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/ui/item_frame.png")));
        contentPane.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, null)));
        contentPane.setPrefSize(background.getWidth(), background.getHeight());
        contentPane.setMaxHeight(background.getHeight());

        getChildren().add(scrollPane);

        for (int y = 0; y < inventory.getCapacity() / widthInCells; y++) {
            for (int x = 0; x < widthInCells; x++) {
                ItemPane itemPane = new ItemPane();
                itemPane.setTranslateX(x * 40.0 + 3.0);
                itemPane.setTranslateY(y * 40.0 + 3.0);

                mouseGestures.makeDraggable(itemPane, (_) -> swap(itemPane));

                itemPane.setOnMouseClicked(e -> {
                    if (itemPane.isEmpty()) {
                        return;
                    }

                    if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                        var item = itemPane.getItemStack().getUserItem();
                        onItemClicked(item);
                    }
                });

                itemGroup.getChildren().add(itemPane);
            }
        }

        contentPane.getChildren().add(itemGroup);

        listener = change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (var item : change.getAddedSubList()) {
                        addItem(item);
                    }
                } else if (change.wasRemoved()) {
                    for (var stack : change.getRemoved()) {
                        getItemPanes().stream()
                                .filter(it -> it.getItemStack() == stack)
                                .forEach(it -> it.setItemStack(null));
                    }
                }
            }
        };

        inventory.itemsProperty().forEach(this::addItem);
        inventory.itemsProperty().addListener(listener);
    }

    public List<ItemPane> getItemPanes() {
        return itemGroup.getChildren().stream()
                .map(node -> (ItemPane) node)
                .collect(Collectors.toList());
    }

    public double getLayoutWidth() {
        return scrollPane.getPrefWidth();
    }

    public double getLayoutHeight() {
        return scrollPane.getPrefHeight();
    }

    public void sort() {
        getItemPanes().forEach(pane -> pane.setItemStack(null));

        List<ItemStack<Item>> sortedItems = inventory.itemsProperty().stream()
                .sorted(Comparator.comparing(stack -> stack.getUserItem().getName()))
                .toList();

        for (int i = 0; i < sortedItems.size(); i++) {
            getItemPanes().get(i).setItemStack(sortedItems.get(i));
        }
    }

    private void onItemClicked(Item item) {

    }

    private void swap(ItemPane pane) {
        ItemPane closestPane = getItemPanes().stream()
                .min(Comparator.comparingDouble(it ->
                        new Point2D(it.getLayoutX() + it.getTranslateX(), it.getLayoutY() + it.getTranslateY())
                                .distance(new Point2D(pane.getLayoutX() + pane.getTranslateX(), pane.getLayoutY() + pane.getTranslateY()))
                )).orElse(null);

        if (closestPane == null) {
            return;
        }

        pane.relocate(0.0, 0.0);
        if (closestPane == pane) {
            return;
        }

        var itemStack = pane.getItemStack();

        pane.setItemStack(null);
        pane.setItemStack(closestPane.getItemStack());
        closestPane.setItemStack(null);
        closestPane.setItemStack(itemStack);
    }

    private ItemPane getNextFreeSlot() {
        return getItemPanes().stream().filter(ItemPane::isEmpty).findFirst().orElse(null);
    }

    private void addItem(ItemStack<Item> itemStack) {
        ItemPane itemPane = getNextFreeSlot();
        if (itemPane != null) {
            itemPane.setItemStack(itemStack);
        }
    }
}
