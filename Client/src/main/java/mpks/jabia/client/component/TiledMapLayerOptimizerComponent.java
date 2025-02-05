package mpks.jabia.client.component;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class TiledMapLayerOptimizerComponent extends Component {
    private final int marginTiles = 5;
    private final int width;
    private final int height;
    private ImageView frontBufferView;
    private ImageView backBufferView;
    private ImageView mapView;

    public TiledMapLayerOptimizerComponent() {
        width = (int) (getAppWidth() / getGameScene().getViewport().getZoom() + 32 * marginTiles * 2);
        height = (int) (getAppHeight() / getGameScene().getViewport().getZoom() + 32 * marginTiles * 2);

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

        int srcX = (int) Math.max((viewportOrigin.getX() - 32 * marginTiles), 0);
        int srcY = (int) Math.max((viewportOrigin.getY() - 32 * marginTiles), 0);

        ((WritableImage) frontBufferView.getImage()).getPixelWriter().setPixels(
                srcX == 0 ? marginTiles * 32 : 0,
                srcY == 0 ? marginTiles * 32 : 0,
                srcX == 0 ? width - marginTiles * 32 : width,
                srcY == 0 ? height - marginTiles * 32 : height,
                mapView.getImage().getPixelReader(),
                srcX, srcY
        );

        frontBufferView.setLayoutX(viewportOrigin.getX() - 32 * marginTiles);
        frontBufferView.setLayoutY(viewportOrigin.getY() - 32 * marginTiles);
        backBufferView.setLayoutX(viewportOrigin.getX() - 32 * marginTiles);
        backBufferView.setLayoutY(viewportOrigin.getY() - 32 * marginTiles);
    }

    @Override
    public void onUpdate(double timePerFrame) {
        Point2D viewportOrigin = getGameScene().getViewport().getOrigin();

        int MIN_MARGIN = 2;
        int MIN_MARGIN_PIXELS = MIN_MARGIN * 32;
        if (frontBufferView.getLayoutX() + width - (viewportOrigin.getX() + getAppWidth() / getGameScene().getViewport().getZoom()) < MIN_MARGIN_PIXELS) {
            flipBuffers();
        } else if (viewportOrigin.getX() - frontBufferView.getLayoutX() < MIN_MARGIN_PIXELS) {
            flipBuffers();
        } else if (viewportOrigin.getY() - frontBufferView.getLayoutY() < MIN_MARGIN_PIXELS) {
            flipBuffers();
        } else if (frontBufferView.getLayoutY() + height - (viewportOrigin.getY() + getAppHeight() / getGameScene().getViewport().getZoom()) < MIN_MARGIN_PIXELS) {
            flipBuffers();
        }
    }

    private void flipBuffers() {
        Point2D viewportOrigin = getGameScene().getViewport().getOrigin();

        int srcX = (int) (viewportOrigin.getX() - 32 * marginTiles);
        int srcY = (int) (viewportOrigin.getY() - 32 * marginTiles);

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

        frontBufferView.setLayoutX(viewportOrigin.getX() - 32 * marginTiles);
        frontBufferView.setLayoutY(viewportOrigin.getY() - 32 * marginTiles);
    }
}
