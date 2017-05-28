import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Math.sqrt;


public class Main extends Application {

    private static final int TILE_SIZE = 80;
    private static final int W = 800 + TILE_SIZE / 2;
    private static final int H = 800;
    private static double SIDE = TILE_SIZE / sqrt(3);

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private Scene scene;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W, H);

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.2);
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }

        return root;
    }

    private List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        //  t t
        // t X t
        //  t t

        int[] points = (tile.y % 2 != 0) ? new int[]{
                0, -1,
                1, -1,
                -1, 0,
                1, 0,
                0, 1,
                1, 1
        } : new int[]{
                -1, -1,
                0, -1,
                -1, 0,
                1, 0,
                -1, 1,
                0, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            double newX = tile.x + dx;
            double newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[(int) newX][(int) newY]);
            }
        }

        return neighbors;
    }

    private class Tile extends StackPane {
        private double y;
        private double x;
        private boolean hasBomb;
        private boolean isOpen = false;
        private Polygon border = new Polygon(
                0, (TILE_SIZE - SIDE) / 2,
                TILE_SIZE / 2, 0,
                TILE_SIZE, (TILE_SIZE - SIDE) / 2,
                TILE_SIZE, (TILE_SIZE - SIDE) / 2 + SIDE,
                TILE_SIZE / 2, TILE_SIZE,
                0, (TILE_SIZE - SIDE) / 2 + SIDE);
        private Text text = new Text();


        Tile(double x, double y, boolean hasBomb) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);
            border.setFill(Color.YELLOWGREEN);

            text.setFont(Font.font(18));
            text.setFill(Color.BLACK);
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            getChildren().addAll(border, text);


            double curX = (y % 2 != 0) ? x + 0.5 : x;

            setTranslateX(curX * TILE_SIZE);
            setTranslateY(y * 0.8 * TILE_SIZE);
            setOnMouseReleased(e -> flagged());
            setOnMouseClicked(e -> open());
        }

        void flagged() {
            if (!isOpen)
                border.setFill(Color.RED);
        }

        void open() {
            if (isOpen)
                return;

            if (hasBomb) {
                System.out.println("Game Over");
                scene.setRoot(createContent());
                return;
            }

            isOpen = true;
            text.setVisible(true);
            border.setFill(Color.ORANGE);

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile::open);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Minesweeper");
        stage.getIcons().add(new Image("icon.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}