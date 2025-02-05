module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires com.almasb.fxgl.all;
    requires Common;
    requires org.json;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens mpks.jabia.client to javafx.fxml, com.almasb.fxgl.core;
    exports mpks.jabia.client;
    exports mpks.jabia.client.character;
    opens mpks.jabia.client.character to com.almasb.fxgl.core, javafx.fxml;
    exports mpks.jabia.client.ui;
    opens mpks.jabia.client.ui to com.almasb.fxgl.core, javafx.fxml;
    exports mpks.jabia.client.screen;
    opens mpks.jabia.client.screen to com.almasb.fxgl.core, javafx.fxml;
    exports mpks.jabia.client.character.component;
    opens mpks.jabia.client.character.component to com.almasb.fxgl.core, javafx.fxml;
    exports mpks.jabia.client.event;
    opens mpks.jabia.client.event to com.almasb.fxgl.core, javafx.fxml;
}