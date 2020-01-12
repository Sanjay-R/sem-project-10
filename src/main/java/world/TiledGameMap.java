package world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import snake.SnakeBody;
import states.GameStateManager;
import utils.Sizes;
import utils.TileType;

public class TiledGameMap extends GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    String fileName;

    SnakeBody snake;
    GameStateManager manager;

    /**
     * Default constructor that will use a pre-defined map.
     */
    public TiledGameMap(SnakeBody snake, GameStateManager manager) {
        this("assets/def3.tmx", snake, manager);
    }

    /**
     * Constructor that takes in a file name for the map.
     * @param fileName The file's name in string format.
     * @param snake The snake that gets passed through.
     * @param manager The game's state manager that's required to manage the game.
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public TiledGameMap(String fileName, SnakeBody snake, GameStateManager manager) {
        this.fileName = fileName;
        this.tiledMap = new TmxMapLoader().load(fileName);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.snake = snake;
        this.manager = manager;
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch, SnakeBody snakeBody) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        //after rendering map up here^ u wanna render entities on the map
        //which is what u do in the super class GameMap

        //set projection matrix to this so it renders according to how the camera wants to render it
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        super.render(camera, batch, this.snake);
        batch.end();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose(OrthographicCamera camera) {
        tiledMap.dispose();
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        //check if its out of bounds first
        if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
            return null;
        }
        //check if layer isn't out of bounds
        if (layer >= getLayers() || layer < 0) {
            layer = getLayers() - 1;
        }

        Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);

        if (cell != null) {
            TiledMapTile tile = cell.getTile();

            if (tile != null) {
                int id = tile.getId();
                return TileType.getTileTypeById(id);
            }
        }
        return null;
    }

    @Override
    public int getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    @Override
    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    @Override
    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public void setTiledMapRenderer(OrthogonalTiledMapRenderer tiledMapRenderer) {
        this.tiledMapRenderer = tiledMapRenderer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SnakeBody getSnake() {
        return snake;
    }

    @Override
    public GameStateManager getManager() {
        return manager;
    }

    public void setManager(GameStateManager manager) {
        this.manager = manager;
    }

    public void setSnake(SnakeBody snake) {
        this.snake = snake;
    }
}
