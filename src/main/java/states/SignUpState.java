package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Creates sign up screen.
 */
public class SignUpState extends State {
    private Stage stage;
    private Skin skin;
    private Texture background;
    private TextField username;
    private TextField password;
    private Skin cloudSkin;

    /**
     * Constructor which creates a new state within the game.
     * E.g. Play/Pause/Menu.
     *
     * @param gameManager which keeps track of the state of the game.
     */
    public SignUpState(GameStateManager gameManager) {
        super(gameManager);
        stage = new Stage(new ScreenViewport());
        background = new Texture("assets/bg.png");
        Gdx.input.setInputProcessor(stage);
        skin =  new Skin(Gdx.files.internal("assets/quantum-horizon/skin/quantum-horizon-ui.json"));
        cloudSkin = new Skin(Gdx.files.internal("assets/cloud-form/skin/cloud-form-ui.json"));
        initTitle();
        initReturn();
        initSignUp();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Texture getBackground() {
        return background;
    }

    public void setBackground(Texture background) {
        this.background = background;
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public Skin getCloudSkin() {
        return cloudSkin;
    }

    public void setCloudSkin(Skin cloudSkin) {
        this.cloudSkin = cloudSkin;
    }

    /**
     * Sets title of login screen.
     */
    private void initTitle() {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("assets/font.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont,
                new Color(0, 255, 0, 1));
        Label title = new Label("Lil' Snake", labelStyle);
        title.setSize(600, 120);
        title.setPosition(100,550);
        title.setFontScale(3);
        title.setAlignment(Align.center);
        stage.addActor(title);
    }

    /**
     * Sets username and password textfield,
     * Login and Sign Up buttons.
     */
    private void initSignUp() {
        TextButton signUpButton = new TextButton("Sign up", skin);
        //        loginButton.setPosition(300, 200);
        signUpButton.setPosition(320, 125);
        signUpButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.set(new LoginState(gameManager));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("pressed sign up");
                return true;
            }
        });
        BitmapFont bitmapFont = new BitmapFont();
        // Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, new Color(1, 0, 1, 1));
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont,
                new Color(255,  0, 255, 1));
        Label usernameLabel = new Label("Enter a username", labelStyle);
        usernameLabel.setPosition(340, 279);

        TextField usernameField = new TextField("", cloudSkin);
        usernameField.setSize(180, 30);
        usernameField.setPosition(300, 247);

        Label passwordLabel = new Label("Enter a password", labelStyle);
        passwordLabel.setPosition(340, 229);

        TextField passWordField = new TextField("", cloudSkin);
        passWordField.setSize(180, 30);
        passWordField.setPosition(300, 197);
        passWordField.setPasswordMode(true);
        passWordField.setPasswordCharacter('*');

        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);
        stage.addActor(usernameField);
        stage.addActor(passWordField);
        stage.addActor(signUpButton);
    }

    /**
     * Creates a return button for the player to return to login screen.
     */
    private void initReturn() {
        TextButton returnButton = new TextButton("return", skin);
        //        signUpButton.setPosition(300, 150);
        returnButton.setPosition(320, 65);
        returnButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.set(new LoginState(gameManager));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TODO
                System.out.println("pressed return");
                return true;
            }
        });
        stage.addActor(returnButton);
    }

    /**
     * This dialog box is shown when the password is not safe enough.
     */
    public void incorrectPassworldDialog() {
        Dialog dialog = new Dialog("Password not valid", cloudSkin, "dialog") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.text("Please make sure your password is at least xxx long and contains a number.");
        dialog.button("OK", true); //sends "true" as the result
        dialog.show(stage);
    }

    /**
     * This dialog box is shown when the username is has already been taken.
     */
    public void usernameTakenDialog() {
        Dialog dialog = new Dialog("Username taken", cloudSkin, "dialog") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.text("This username has already been taken, try a new one.");
        dialog.button("OK", true); //sends "true" as the result
        dialog.show(stage);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 800, 800);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        skin.dispose();
        stage.dispose();
    }
}