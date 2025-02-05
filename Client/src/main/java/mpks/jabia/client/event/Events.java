package mpks.jabia.client.event;

import javafx.event.Event;
import javafx.event.EventType;
import mpks.jabia.client.event.single_event.GameEvent;
import mpks.jabia.client.event.single_event.OnOrderedMoveEvent;

public class Events {
    public static final EventType<GameEvent> ANY = new EventType<>(Event.ANY, "ANY");

    public static final EventType<GameEvent> ORDER_ANY = new EventType<>(ANY, "ORDER_ANY");
    public static final EventType<OnOrderedMoveEvent> ON_ORDERED_MOVE = new EventType<>(ORDER_ANY, "ON_ORDERED_MOVE");
}
