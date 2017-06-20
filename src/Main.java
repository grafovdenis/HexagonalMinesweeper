import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    static final int TILE_SIZE = 80;
    private static final int W = 800 + TILE_SIZE / 2;
    private static final int H = 600;
    Tile[][] grid = new Tile[Game.X_TILES][Game.Y_TILES];
    private Scene scene;

    Game game;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        for (int y = 0; y < Game.Y_TILES; y++) {
            for (int x = 0; x < Game.X_TILES; x++) {
                Tile tile = new Tile(new Cell(x, y, Math.random() < 0.2));
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }
        game = new Game(grid, this);
        Tile.link(this);
        return root;
    }

    void callLose() {
        for (Tile[] tiles : game.cells) {
            for (Tile tile : tiles) {
                tile.text.setVisible(true);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Lose");
        alert.setHeaderText(null);
        alert.setContentText("Try Again!");
        alert.showAndWait();

        scene.setRoot(createContent());
    }

    void callWin() {

        for (Tile[] tiles : game.cells) {
            for (Tile tile : tiles) {
                tile.text.setVisible(true);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Good news, everyone!");
        alert.setHeaderText(null);
        alert.setContentText("You WON!");
        alert.showAndWait();

        scene.setRoot(createContent());
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());

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