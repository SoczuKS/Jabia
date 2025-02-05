package mpks.jabia.client.event;

import static com.almasb.fxgl.core.math.FXGLMath.random;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onEvent;
import static com.almasb.fxgl.dsl.FXGLForKtKt.play;

public class EventHandler {
    private static EventHandler instance;

    private EventHandler() {
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler();
        }
        return instance;
    }

    public void initialize() {
        onEvent(Events.ON_ORDERED_MOVE, (_) -> null);
    }
}
