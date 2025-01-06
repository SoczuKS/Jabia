package mpks.jabia;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class Main extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1024);
        settings.setHeight(768);
        settings.setTitle("Jabia");
        settings.setVersion("1.0.0");
    }

    public static void main(String[] args) {
        launch(args);
    }
}