import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnakeGame {
    private Camera camera;
    private SpriteBatch batch;
    private Sprite sprite;
    private SnakeBody snake;
    private STATE currState;
    private SnakeBody.Direction currDir;

    private enum STATE {
        PLAYING, GAME_OVER;
    }

    public void render(float delta) {
        switch(currState) {
            case PLAYING: {
                //checks for updated direction after a keypress
                queryInput();
                //
                snake.moveSnake(currDir);
                checkOutOfMap(); // maybe move this to snakebody???
            }
            break;
            case GAME_OVER: {
                //present gameover screen
            }
            break;
        }
//        clearScreen();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        sprite.draw(batch);
        snake.renderSnake(batch);
        batch.end();
    }

//    private void moveSnake(float delta) {
//
//    }

    private void queryInput() {
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.D);

        if (upPressed) updateDirection(SnakeBody.Direction.UP);
        if (downPressed) updateDirection(SnakeBody.Direction.DOWN);
        if (leftPressed) updateDirection(SnakeBody.Direction.LEFT);
        if (rightPressed) updateDirection(SnakeBody.Direction.RIGHT);
    }

    public void updateDirection(SnakeBody.Direction direction) {
        if (!direction.equals(currDir)) {
            switch (direction) {
                case UP: {
                    updateIfNotOpposite(SnakeBody.Direction.UP, SnakeBody.Direction.DOWN);
                }
                case DOWN: {
                    updateIfNotOpposite(SnakeBody.Direction.DOWN, SnakeBody.Direction.UP);
                }
                case LEFT: {
                    updateIfNotOpposite(SnakeBody.Direction.LEFT, SnakeBody.Direction.RIGHT);
                }
                case RIGHT: {
                    updateIfNotOpposite(SnakeBody.Direction.RIGHT, SnakeBody.Direction.LEFT);
                }
            }
        }
    }

    private void updateIfNotOpposite(SnakeBody.Direction newDir, SnakeBody.Direction oppositeDirection) {
        if (!newDir.equals(oppositeDirection)) {
            currDir = newDir;
        } else {
            currState = STATE.GAME_OVER;
        }
    }



}
