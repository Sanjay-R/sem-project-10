package entities;

import com.badlogic.gdx.graphics.Texture;
import models.Coordinate;
import states.PlayState;
import world.GameMap;


/**
 * Interactive food object of Apple.
 */
public class Apple implements Food {

    public static final int DEFAULT_SCORE = 10;
    private static final String texturePath = "assets/redapple16px.png";
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

    public Apple() {

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void action(PlayState play) {
        play.getScore().add(Apple.DEFAULT_SCORE);
        play.getSnake().growSnake();
    }

    /**
     * This method takes a map's score and increases it.
     * Afterwards the snake is grown;
     * @param map
     */
    @Override
    public void actionTwo(GameMap map) {
        map.getScore().add(Apple.DEFAULT_SCORE);
        map.getSnake().growSnake();
    }
}
