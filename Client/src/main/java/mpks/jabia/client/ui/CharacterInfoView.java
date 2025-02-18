package mpks.jabia.client.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mpks.jabia.client.character.CharacterEntity;
import mpks.jabia.client.character.component.CharacterComponent;

public class CharacterInfoView extends Region {
    private final CharacterEntity characterEntity;

    public CharacterInfoView(CharacterEntity entity) {
        this.characterEntity = entity;

        generateView();
    }

    public void generateView() {
        CharacterComponent characterComponent = characterEntity.getCharacterComponent();

        VBox attributesBox = new VBox(5.0);

        Text intelligenceText = new Text("Intelligence: " + characterComponent.getUser().getIntelligence());
        intelligenceText.setStroke(Color.WHITE);
        Text strengthText = new Text("Strength: " + characterComponent.getUser().getStrength());
        strengthText.setStroke(Color.WHITE);
        Text agilityText = new Text("Dexterity: " + characterComponent.getUser().getAgility());
        agilityText.setStroke(Color.WHITE);
        Text vitalityText = new Text("Vitality: " + characterComponent.getUser().getVitality());
        vitalityText.setStroke(Color.WHITE);
        Text luckText = new Text("Luck: " + characterComponent.getUser().getLuck());
        luckText.setStroke(Color.WHITE);
        Text defenceText = new Text("Defence: " + characterComponent.getUser().getDefence());
        defenceText.setStroke(Color.WHITE);
        Text magicResistanceText = new Text("Magic Resistance: " + characterComponent.getUser().getMagicResistance());
        magicResistanceText.setStroke(Color.WHITE);

        attributesBox.getChildren().addAll(intelligenceText, strengthText, agilityText, vitalityText, luckText, defenceText, magicResistanceText);

        getChildren().add(new HBox(10.0, attributesBox));
    }
}
