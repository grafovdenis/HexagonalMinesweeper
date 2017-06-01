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

    static final int X_TILES = 10;
    static final int Y_TILES = 9;

    static Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private static Scene scene;

    private static Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.2);
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        Game.loadCells();
        Game.createField();
        return root;
    }

    static void callLose() {

        for (Tile[] tiles : Game.cells) {
            for (Tile tile : tiles) {
                tile.text.setVisible(true);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Lose");
        alert.setHeaderText(null);
        alert.setContentText("Try Again!");
        alert.showAndWait();

        Main.scene.setRoot(Main.createContent());
    }

    static void callWin() {

        for (Tile[] tiles : Game.cells) {
            for (Tile tile : tiles) {
                tile.text.setVisible(true);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Good news, everyone!");
        alert.setHeaderText(null);
        alert.setContentText("You WON!");
        alert.showAndWait();

        Main.scene.setRoot(Main.createContent());
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