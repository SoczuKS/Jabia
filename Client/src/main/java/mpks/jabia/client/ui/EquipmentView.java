package mpks.jabia.client.ui;

import javafx.scene.Parent;
import mpks.jabia.client.character.CharacterEntity;

public class EquipmentView extends Parent {
    private CharacterEntity player;

    public EquipmentView(CharacterEntity player) {
        this.player = player;
    }
}
