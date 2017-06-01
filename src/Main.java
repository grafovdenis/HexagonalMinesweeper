import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Main extends Application {

    static final int TILE_SIZE = 80;
    static final int W = 800 + TILE_SIZE / 2;
    static final int H = 600;

    static final int X_TILES = 10;
    static final int Y_TILES = 9;

    static Tile[][] grid = new Tile[X_TILES][Y_TILES];
    static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(Game.createContent());

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Minesweeper");
        stage.getIcons().add(new Image("icon.png"));
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}