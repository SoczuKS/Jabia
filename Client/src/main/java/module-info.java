module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires com.almasb.fxgl.all;

    opens mpks.jabia to javafx.fxml;
    exports mpks.jabia;
}