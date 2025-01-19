package mpks.jabia.client;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.util.HashMap;
import java.util.function.Consumer;

public class MouseGestures {
    private boolean isDragging = false;
    private HashMap<Node, DragContext> draggableNodes;
    private Node context;

    public MouseGestures(Node context) {
        this.context = context;
        draggableNodes = new HashMap<Node, DragContext>();
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void makeDraggable(Node node) {
        draggableNodes.put(node, new DragContext(node, context));
    }

    public void makeDraggable(Node node, Consumer<Node> onDraggedStopped) {
        draggableNodes.put(node, new DragContext(node, context, onDraggedStopped));
    }

    private class DragContext {
        private double x;
        private double y;
        private Node node;
        private Node context;
        private Consumer<Node> onDraggedStopped;

        DragContext(Node node, Node context) {
            this(node, context, (_) -> {});
        }

        DragContext(Node node, Node context, Consumer<Node> onDraggedStopped) {
            this.node = node;
            this.context = context;
            this.onDraggedStopped = onDraggedStopped;

            node.setOnMousePressed(event -> {
                if (event.getButton() != MouseButton.PRIMARY) {
                    return;
                }

                isDragging = true;
                x = event.getSceneX();
                y = event.getSceneY();
                node.getProperties().put("startLayoutX", node.getLayoutX());
                node.getProperties().put("startLayoutY", node.getLayoutY());
            });

            node.setOnMouseDragged(event -> {
                if (event.getButton() != MouseButton.PRIMARY) {
                    return;
                }

                Node draggingNode = (Node) event.getSource();

                double offsetX = event.getSceneX() - x;
                double offsetY = event.getSceneY() - y;

                draggingNode.setLayoutX(draggingNode.getLayoutX() + offsetX);
                draggingNode.setLayoutY(draggingNode.getLayoutY() + offsetY);

                x = event.getSceneX();
                y = event.getSceneY();
            });

            node.setOnMouseReleased(event -> {
                if (event.getButton() != MouseButton.PRIMARY) {
                    return;
                }

                isDragging = false;
                onDraggedStopped.accept(node);
            });
        }
    }
}
