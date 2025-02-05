package mpks.jabia.client.character.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Objects;

import static javafx.util.Duration.seconds;

public class AnimationComponent extends Component {
    private final AnimatedTexture animatedTexture;

    private final AnimationChannel channelIdleDown;
    private final AnimationChannel channelIdleRight;
    private final AnimationChannel channelIdleUp;
    private final AnimationChannel channelIdleLeft;

    private final AnimationChannel channelWalkDown;
    private final AnimationChannel channelWalkRight;
    private final AnimationChannel channelWalkUp;
    private final AnimationChannel channelWalkLeft;

    private final AnimationChannel channelCastDown;
    private final AnimationChannel channelCastRight;
    private final AnimationChannel channelCastUp;
    private final AnimationChannel channelCastLeft;

    private final AnimationChannel channelSlashDown;
    private final AnimationChannel channelSlashRight;
    private final AnimationChannel channelSlashUp;
    private final AnimationChannel channelSlashLeft;

    private final AnimationChannel channelShootDown;
    private final AnimationChannel channelShootRight;
    private final AnimationChannel channelShootUp;
    private final AnimationChannel channelShootLeft;

    private final AnimationChannel channelDeath;
    
    public AnimationComponent(String assetName){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(assetName)));

        channelIdleDown   = new AnimationChannel(image, 9, 64, 64, seconds(1.2), 9 * 10, 9 * 10);
        channelIdleRight  = new AnimationChannel(image, 9, 64, 64, seconds(1.2), 9 * 11, 9 * 11);
        channelIdleUp     = new AnimationChannel(image, 9, 64, 64, seconds(1.2), 9 * 8, 9 * 8);
        channelIdleLeft   = new AnimationChannel(image, 9, 64, 64, seconds(1.2), 9 * 9, 9 * 9);

        channelWalkDown   = new AnimationChannel(image, 9, 64, 64, seconds(0.4), 9 * 10, 9 * 10 + 9 - 1);
        channelWalkRight  = new AnimationChannel(image, 9, 64, 64, seconds(0.5), 9 * 11, 9 * 11 + 9 - 1);
        channelWalkUp     = new AnimationChannel(image, 9, 64, 64, seconds(0.4), 9 * 8, 9 * 8 + 9 - 1);
        channelWalkLeft   = new AnimationChannel(image, 9, 64, 64, seconds(0.5), 9 * 9, 9 * 9 + 9 - 1);

        channelCastDown   = new AnimationChannel(image, 7, 64, 64, seconds(0.7), 7 * 2, 7 * 2 + 7 - 1);
        channelCastRight  = new AnimationChannel(image, 7, 64, 64, seconds(0.7), 7 * 3, 7 * 3 + 7 - 1);
        channelCastUp     = new AnimationChannel(image, 7, 64, 64, seconds(0.7), 0, 7 - 1);
        channelCastLeft   = new AnimationChannel(image, 7, 64, 64, seconds(0.7), 7, 7 + 7 - 1);

        channelSlashDown  = new AnimationChannel(image, 6, 64, 64, seconds(0.6), 6 * 14, 6 * 14 + 6 - 1);
        channelSlashRight = new AnimationChannel(image, 6, 64, 64, seconds(0.6), 6 * 15, 6 * 15 + 6 - 1);
        channelSlashUp    = new AnimationChannel(image, 6, 64, 64, seconds(0.6), 6 * 12, 6 * 12 + 6 - 1);
        channelSlashLeft  = new AnimationChannel(image, 6, 64, 64, seconds(0.6), 6 * 13, 6 * 13 + 6 - 1);

        channelShootDown  = new AnimationChannel(image, 13, 64, 64, seconds(1.2), 13 * 18, 13 * 18 + 13 - 1);
        channelShootRight = new AnimationChannel(image, 13, 64, 64, seconds(1.2), 13 * 19, 13 * 19 + 13 - 1);
        channelShootUp    = new AnimationChannel(image, 13, 64, 64, seconds(1.2), 13 * 16, 13 * 16 + 13 - 1);
        channelShootLeft  = new AnimationChannel(image, 13, 64, 64, seconds(1.2), 13 * 17, 13 * 17 + 13 - 1);

        channelDeath      = new AnimationChannel(image, 6, 64, 64, seconds(1.2), 6 * 20, 6 * 20 + 6 - 1);

        animatedTexture = new AnimatedTexture(channelIdleDown);
        animatedTexture.setPickOnBounds(true);
        animatedTexture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animatedTexture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.setZIndex(entity.getComponent(CellMoveComponent.class).getCellY() + 5000);
    }

    public void playDeath(Runnable onFinished) {
        animatedTexture.playAnimationChannel(channelDeath);
        animatedTexture.setOnCycleFinished(onFinished);
    }

    void playCastUp(Runnable onFinished) {
        animatedTexture.playAnimationChannel(channelCastUp);
        animatedTexture.setOnCycleFinished(onFinished);
    }

    void playCastDown(Runnable onFinished) {
        animatedTexture.playAnimationChannel(channelCastDown);
        animatedTexture.setOnCycleFinished(onFinished);
    }

    void playCastRight(Runnable onFinished) {
        animatedTexture.playAnimationChannel(channelCastRight);
        animatedTexture.setOnCycleFinished(onFinished);
    }

    void playCastLeft(Runnable onFinished) {
        animatedTexture.playAnimationChannel(channelCastLeft);
        animatedTexture.setOnCycleFinished(onFinished);
    }

    void loopIdleUp() {
        animatedTexture.loopNoOverride(channelIdleUp);
    }

    void loopIdleDown() {
        animatedTexture.loopNoOverride(channelIdleDown);
    }

    void loopIdleRight() {
        animatedTexture.loopNoOverride(channelIdleRight);
    }

    void loopIdleLeft() {
        animatedTexture.loopNoOverride(channelIdleLeft);
    }

    void loopWalkUp() {
        animatedTexture.loopNoOverride(channelWalkUp);
    }

    void loopWalkDown() {
        animatedTexture.loopNoOverride(channelWalkDown);
    }

    void loopWalkRight() {
        animatedTexture.loopNoOverride(channelWalkRight);
    }

    void loopWalkLeft() {
        animatedTexture.loopNoOverride(channelWalkLeft);
    }

    void loopAttackUp() {
        animatedTexture.loopAnimationChannel(channelSlashUp);
    }

    void loopAttackDown() {
        animatedTexture.loopAnimationChannel(channelSlashDown);
    }

    void loopAttackRight() {
        animatedTexture.loopAnimationChannel(channelSlashRight);
    }

    void loopAttackLeft() {
        animatedTexture.loopAnimationChannel(channelSlashLeft);
    }

    boolean isFacingUp() {return isIn(channelIdleUp, channelShootUp, channelSlashUp, channelWalkUp, channelCastUp);}

    boolean isFacingDown() {return isIn(channelIdleDown, channelShootDown, channelSlashDown, channelWalkDown, channelCastDown);}

    boolean isFacingRight() {return isIn(channelIdleRight, channelShootRight, channelSlashRight, channelWalkRight, channelCastRight);}

    boolean isFacingLeft() {return isIn(channelIdleLeft, channelShootLeft, channelSlashLeft, channelWalkLeft, channelCastLeft);}

    private boolean isIn(AnimationChannel... channels) {
        return Arrays.stream(channels).anyMatch(channel -> animatedTexture.getAnimationChannel() == channel);
    }
}
