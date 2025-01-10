package mpks.jabia.client;

import com.almasb.fxgl.animation.AnimatedValue;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import javafx.animation.Interpolator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGLForKtKt.animationBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class LoadingScreen extends LoadingScene {
    public LoadingScreen() {
        var text = getUIFactoryService().newText("Loading...", Color.WHITE, 58.0);
        FXGL.centerText(text);

        animationBuilder(this)
                .repeatInfinitely()
                .duration(Duration.seconds(1.66))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .animate(new AnimatedString("", "Loading..."))
                .onProgress(text::setText).buildAndPlay();


        getContentRoot().getChildren().addAll(new Rectangle(FXGLForKtKt.getAppWidth(), FXGLForKtKt.getAppHeight()));
        getContentRoot().getChildren().addAll(text);
    }

    private static class AnimatedString extends AnimatedValue<String> {
        AnimatedString(String startValue, String endValue) {
            super(startValue, endValue);
        }

        @Override
        public String animate(String val1, String val2, double progress, @NotNull Interpolator interpolator) {
            var index = (int) (val2.length() * interpolator.interpolate(0.0, 1.0, progress));
            return val2.substring(0, index);
        }
    }
}
