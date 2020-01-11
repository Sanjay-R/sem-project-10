package objects.base;

import com.badlogic.gdx.graphics.Texture;
import gamelogic.Coordinate;
import states.PlayState;


/**
 * Interactive food object of Apple.
 */
public class Apple implements Food {

    protected final static int DEFAULT_SCORE = 10;
    private static final String texturePath = "assets/greenapplesmall.png";
    public Coordinate coordinate;
    private Texture texture;

    /**
     * Creates an apple with a predefined texture at Random coordinate in the
     * texture space (Coordinate is multiplied with cell size!).
     */
    public Apple(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.texture = new Texture(texturePath);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void start(PlayState play) {
        play.getScore().add(Apple.DEFAULT_SCORE);
    }
}
