package objects.base;


import game.SnakeGame;
import gamelogic.Score;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import snake.SnakeBody;
import states.PlayState;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppleTest extends FoodTest {

    @Override
    protected Food getFood() {
        return new Apple();
    }

    @Test
    void appleActionTest() {
        Apple apple = new Apple();
        PlayState playState = Mockito.mock(PlayState.class);

        Score score = new Score();
        int initialScore = 20;
        score.setValue(initialScore);

        Mockito.when(playState.getScore()).thenReturn(score);
        SnakeBody snake = Mockito.mock(SnakeBody.class);
        Mockito.when(playState.getSnake()).thenReturn(snake);

        apple.action(playState);

        assertEquals(initialScore + Apple.DEFAULT_SCORE, score.getValue());
        Mockito.verify(snake).growSnake();
    }

}