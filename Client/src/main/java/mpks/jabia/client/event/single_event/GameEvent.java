package mpks.jabia.client.event.single_event;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class GameEvent extends Event {
    private final EventType<? extends GameEvent> eventType;

    public GameEvent(EventType<? extends GameEvent> eventType) {
        super(eventType);
        this.eventType = eventType;
    }

    public EventType<? extends GameEvent> getEventType() {
        return eventType;
    }
}
