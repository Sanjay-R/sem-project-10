package snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import gamelogic.Coordinate;
import utils.Direction;
import utils.Sizes;
import world.GameMap;

import java.util.LinkedList;

/**
 * Class that defines the snake's body logic.
 */
public class SnakeBody {

    public static final int CELL_SIZE = Sizes.TILE_PIXELS;
    private static final int INITIAL_LENGTH = Sizes.INITIAL_LENGTH;

    private Coordinate headCoord;
    private LinkedList<BodyPart> bodyParts;
    private Direction currDir;

    //public enum Direction { LEFT, RIGHT, UP, DOWN }

    /**
     * Constructs a snake with INITIAL_LENGTH amount of bodyparts.
     *
     * @param headX - X coordinate of head
     * @param headY - Y coordinate of head
     */
    public SnakeBody(int headX, int headY) {
        this.headCoord = new Coordinate(headX / 2, headY / 2);
        this.currDir = Direction.RIGHT;
        this.bodyParts = new LinkedList<BodyPart>();
        growSnake(INITIAL_LENGTH+2);
    }

    public LinkedList<BodyPart> getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(LinkedList<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
    }

    public Direction getCurrDir() {
        return currDir;
    }

    public void setCurrDir(Direction currDir) {
        this.currDir = currDir;
    }

    public Coordinate getHeadCoord() {
        return headCoord;
    }

    public void setHeadCoord(Coordinate headCoord) {
        this.headCoord = headCoord;
    }

    /**
     * Grows the snake body by one body part.
     */
    public void growSnake() {
        if (bodyParts.size() == 0) {
            bodyParts.add(new BodyPart(headCoord.getCoordinateX(), headCoord.getCoordinateY()));
        } else if (bodyParts.size() > 0) {
            int tailID = bodyParts.size() - 1;
            BodyPart tail = bodyParts.get(tailID);
            Coordinate tailCoord = tail.getCoordinate();
            bodyParts.add(new BodyPart(tailCoord.getCoordinateX(), tailCoord.getCoordinateY()));
        }
    }

    /**
     * Grows the snake body by a specified number of body parts.
     *
     * @param length - by how many body parts the snake will be grown
     */
    public void growSnake(int length) {
        for (int i = 0; i < length; i++) {
            this.growSnake();
        }
    }

    /**
     * First renders the head of the snake as a rectangle.
     * Then loops through the bodyparts and renders those.
     *
     * @param shapeRenderer - ShapeRenderer object
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void renderSnake(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(Color.GREEN));
        int x = this.headCoord.getCoordinateX();
        int y = this.headCoord.getCoordinateY();
        shapeRenderer.rect(x, y, CELL_SIZE, CELL_SIZE);
        if (bodyParts.size() > 0) {
            for (BodyPart bp : bodyParts) {
                Coordinate bodyCoord = bp.getCoordinate();
                shapeRenderer.rect(bodyCoord.getCoordinateX(), bodyCoord.getCoordinateY(),
                        CELL_SIZE, CELL_SIZE);
            }
        }
        shapeRenderer.end();
    }

    /**
     * This method renders the snake on the map using textures.
     * It also rotates the head in the appropriate direction.
     * @param batch The sprite batch that got passed on.
     * @param textureRegions This contains the texture for the head and body.
     * @param map The game map.
     */
    public void renderSnake(SpriteBatch batch, TextureRegion[][] textureRegions, GameMap map) {

        float rot;
        switch (getCurrDir()) {
            case RIGHT:
                rot = -90f;
                break;
            case LEFT:
                rot = 90f;
                break;
            case DOWN:
                rot = 180f;
                break;
            default:
                rot = 0f;
                break;
        }
        int x = headCoord.getCoordinateX() * Sizes.TILE_PIXELS;
        int y = headCoord.getCoordinateY() * Sizes.TILE_PIXELS;
        batch.draw(textureRegions[0][0],
                x,
                y,
                Sizes.TILE_PIXELS / 2, Sizes.TILE_PIXELS / 2,
                Sizes.TILE_PIXELS, Sizes.TILE_PIXELS, 1, 1,
                rot, true);
        //originX is amount of pixels away from origin
        //originX takes from the MIDDLE OF the square tile away
        //so if the first x, y take from bottom (left,right) corner of the square tile,
        // originX will take from middle

        for (int i = 1; i < getBodyParts().size(); i++) {
            BodyPart part = getBodyParts().get(i);
            Coordinate curr = part.getCoordinate();
            int a = curr.getCoordinateX();
            int b = curr.getCoordinateY();
            batch.draw(textureRegions[0][1],
                    a * Sizes.TILE_PIXELS,
                    b * Sizes.TILE_PIXELS);
        }
//        map.update(Sizes.MOVE_TIME+1f); //remove TODO


    }

    /**
     * Updates currDir to the given direction.
     *
     * @param snakeDirection - Updates currDir to this direction
     */
    public void moveSnake(Direction snakeDirection) {
        switch (snakeDirection) {
            case RIGHT:
                headCoord.addToX(1);
                updateBodyPartsPosition(headCoord);
                break;
            case LEFT:
                headCoord.subtractFromX(1);
                updateBodyPartsPosition(headCoord);
                break;
            case UP:
                headCoord.addToY(1);
                updateBodyPartsPosition(headCoord);
                break;
            case DOWN:
                headCoord.subtractFromY(1);
                updateBodyPartsPosition(headCoord);
                break;
            default:
                // will not execute
        }
    }

    /**
     * Updates the position of each body part.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void updateBodyPartsPosition(Coordinate coordinate) {
        if (bodyParts.size() > 0) {
            for (BodyPart bp : bodyParts) {
                int currX = bp.getCoordinate().getCoordinateX();
                int currY = bp.getCoordinate().getCoordinateY();
                bp.updateBodyPartPos(coordinate);
                coordinate = new Coordinate(currX, currY);
            }
        }
    }

}