package mpks.jabia.client.component;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import mpks.jabia.client.GameSettings;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class TiledMapLayerOptimizerComponent extends Component {
    private final int marginTiles = 5;
    private final int width;
    private final int height;
    private ImageView frontBufferView;
    private ImageView backBufferView;
    private ImageView mapView;

    public TiledMapLayerOptimizerComponent() {
        width = (int) (getAppWidth() / getGameScene().getViewport().getZoom() + GameSettings.tileSize * marginTiles * 2);
        height = (int) (getAppHeight() / getGameScene().getViewport().getZoom() + GameSettings.tileSize * marginTiles * 2);

        WritableImage frontBuffer = new WritableImage(width, height);
        frontBufferView = new ImageView(frontBuffer);

        WritableImage backBuffer = new WritableImage(width, height);
        backBufferView = new ImageView(backBuffer);
    }

    @Override
    public void onAdded() {
        mapView = (ImageView) entity.getViewComponent().getChildren().getFirst();
        entity.getViewComponent().removeChild(mapView);

        entity.getViewComponent().addChild(frontBufferView);

        Point2D viewportOrigin = getGameScene().getViewport().getOrigin();

        int srcX = (int) Math.max((viewportOrigin.getX() - GameSettings.tileSize * marginTiles), 0);
        int srcY = (int) Math.max((viewportOrigin.getY() - GameSettings.tileSize * marginTiles), 0);

        ((WritableImage) frontBufferView.getImage()).getPixelWriter().setPixels(
                srcX == 0 ? marginTiles * GameSettings.tileSize : 0,
                srcY == 0 ? marginTiles * GameSettings.tileSize : 0,
                srcX == 0 ? width - marginTiles * GameSettings.tileSize : width,
                srcY == 0 ? height - marginTiles * GameSettings.tileSize : height,
                mapView.getImage().getPixelReader(),
                srcX, srcY
        );

        frontBufferView.setLayoutX(viewportOrigin.getX() - GameSettings.tileSize * marginTiles);
        frontBufferView.setLayoutY(viewportOrigin.getY() - GameSettings.tileSize * marginTiles);
        backBufferView.setLayoutX(viewportOrigin.getX() - GameSettings.tileSize * marginTiles);
        backBufferView.setLayoutY(viewportOrigin.getY() - GameSettings.tileSize * marginTiles);
    }

    @Override
    public void onUpdate(double timePerFrame) {
        Point2D viewportOrigin = getGameScene().getViewport().getOrigin();

        int minMargin = 2;
        int minMarginPixels = minMargin * GameSettings.tileSize;
        if (frontBufferView.getLayoutX() + width - (viewportOrigin.getX() + getAppWidth() / getGameScene().getViewport().getZoom()) < minMarginPixels) {
            flipBuffers();
        } else if (viewportOrigin.getX() - frontBufferView.getLayoutX() < minMarginPixels) {
            flipBuffers();
        } else if (viewportOrigin.getY() - frontBufferView.getLayoutY() < minMarginPixels) {
            flipBuffers();
        } else if (frontBufferView.getLayoutY() + height - (viewportOrigin.getY() + getAppHeight() / getGameScene().getViewport().getZoom()) < minMarginPixels) {
            flipBuffers();
        }
    }

    private void flipBuffers() {
        Point2D viewportOrigin = getGameScene().getViewport().getOrigin();

        int srcX = (int) (viewportOrigin.getX() - GameSettings.tileSize * marginTiles);
        int srcY = (int) (viewportOrigin.getY() - GameSettings.tileSize * marginTiles);

        int dstX = srcX < 0 ? -srcX : 0;
        int dstY = srcY < 0 ? -srcY : 0;

        int w = (int) Math.min(width - dstX, mapView.getImage().getWidth() - srcX);
        int h = (int) Math.min(height - dstY, mapView.getImage().getHeight() - srcY);

        ((WritableImage) backBufferView.getImage()).getPixelWriter().setPixels(
                dstX,
                dstY,
                (int) Math.min(w, mapView.getImage().getWidth()),
                (int) Math.min(h, mapView.getImage().getHeight()),
                mapView.getImage().getPixelReader(),
                Math.max(srcX, 0),
                Math.max(srcY, 0)
        );

        entity.getViewComponent().addChild(backBufferView);
        entity.getViewComponent().removeChild(frontBufferView);

        ImageView tmp = frontBufferView;
        frontBufferView = backBufferView;
        backBufferView = tmp;

        frontBufferView.setLayoutX(viewportOrigin.getX() - GameSettings.tileSize * marginTiles);
        frontBufferView.setLayoutY(viewportOrigin.getY() - GameSettings.tileSize * marginTiles);
    }
}
