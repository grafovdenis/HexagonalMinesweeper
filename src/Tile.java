import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

class Tile extends StackPane {
    private double y;
    private double x;
    boolean hasBomb;
    private boolean isOpen = false;
    private boolean flagged = false;
    private static final int TILE_SIZE = Main.TILE_SIZE;
    private static double SIDE = TILE_SIZE / sqrt(3);
    private Polygon border = new Polygon(
            0, (TILE_SIZE - SIDE) / 2,
            TILE_SIZE / 2, 0,
            TILE_SIZE, (TILE_SIZE - SIDE) / 2,
            TILE_SIZE, (TILE_SIZE - SIDE) / 2 + SIDE,
            TILE_SIZE / 2, TILE_SIZE,
            0, (TILE_SIZE - SIDE) / 2 + SIDE);
    Text text = new Text();


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

        setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) {
                open();
            } else if (e.isSecondaryButtonDown() && !isOpen) {
                if (flagged) {
                    deFlag();
                } else {
                    flag();
                }
            }
        });
    }

    private void deFlag() {
        flagged = false;
        border.setFill(Color.YELLOWGREEN);
    }

    private void flag() {
        flagged = true;
        if (!isOpen)
            border.setFill(Color.RED);
    }

    static List<Tile> getNeighbors(Tile tile) {
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

            if (newX >= 0 && newX < Main.X_TILES
                    && newY >= 0 && newY < Main.Y_TILES) {
                neighbors.add(Main.grid[(int) newX][(int) newY]);
            }
        }

        return neighbors;
    }

    private void open() {
        if (isOpen)
            return;

        if (hasBomb) {
            //open all
            for (Tile[] tiles : Main.grid) {
                for (Tile tile : tiles) {
                    tile.text.setVisible(true);
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Lose");
            alert.setHeaderText(null);
            alert.setContentText("Try Again!");
            alert.showAndWait();

            Main.scene.setRoot(CreateContent.createContent());
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