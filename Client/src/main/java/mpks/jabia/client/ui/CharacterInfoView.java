package mpks.jabia.client.ui;

import javafx.scene.layout.Region;
import mpks.jabia.client.character.CharacterEntity;

public class CharacterInfoView extends Region {
    private final CharacterEntity characterEntity;

    public CharacterInfoView(CharacterEntity entity) {
        this.characterEntity = entity;
    }
}
