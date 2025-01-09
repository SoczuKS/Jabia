module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires com.almasb.fxgl.all;
    requires Common;
    requires org.json;
    requires org.jetbrains.annotations;

    opens mpks.jabia.client to javafx.fxml, com.almasb.fxgl.core;
    exports mpks.jabia.client;
}