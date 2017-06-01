import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static java.lang.Math.sqrt;

class Tile extends StackPane {
    double y;
    double x;
    boolean hasBomb;
    boolean isOpen = false;
    boolean flagged = false;
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
            if (e.isPrimaryButtonDown() && !flagged) {
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


    private void open() {
        if (isOpen)
            return;

        if (hasBomb) {
            Main.callLose();
            return;
        }

        isOpen = true;
        text.setVisible(true);
        border.setFill(Color.ORANGE);

        if (text.getText().isEmpty()) {
            Game.getNeighbors(this).forEach(Tile::open);
        }

        Game.check();
    }
}