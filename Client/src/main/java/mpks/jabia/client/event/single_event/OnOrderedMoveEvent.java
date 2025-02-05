package mpks.jabia.client.event.single_event;

import mpks.jabia.client.character.CharacterEntity;
import mpks.jabia.client.event.Events;

public class OnOrderedMoveEvent extends GameEvent {
    public final int x;
    public final int y;
    public final CharacterEntity characterEntity;

    public OnOrderedMoveEvent(CharacterEntity characterEntity, int x, int y) {
        super(Events.ON_ORDERED_MOVE);
        this.characterEntity = characterEntity;
        this.x = x;
        this.y = y;
    }
}
