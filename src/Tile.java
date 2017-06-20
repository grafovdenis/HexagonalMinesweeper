import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static java.lang.Math.sqrt;

class Tile extends StackPane {
    Cell cell;
    private static final int TILE_SIZE = Main.TILE_SIZE;
    private static final double SIDE = TILE_SIZE / sqrt(3);
    private static Main main;
    private Polygon border = new Polygon(
            0, (TILE_SIZE - SIDE) / 2,
            TILE_SIZE / 2, 0,
            TILE_SIZE, (TILE_SIZE - SIDE) / 2,
            TILE_SIZE, (TILE_SIZE - SIDE) / 2 + SIDE,
            TILE_SIZE / 2, TILE_SIZE,
            0, (TILE_SIZE - SIDE) / 2 + SIDE);
    Text text = new Text();


    Tile(Cell cell) {
        this.cell = cell;

        border.setStroke(Color.LIGHTGRAY);
        border.setFill(Color.YELLOWGREEN);

        text.setFont(Font.font(18));
        text.setFill(Color.BLACK);
        text.setText(cell.hasBomb ? "X" : "");
        text.setVisible(false);

        getChildren().addAll(border, text);


        double curX = (cell.y % 2 != 0) ? cell.x + 0.5 : cell.x;

        setTranslateX(curX * TILE_SIZE);
        setTranslateY(cell.y * 0.8 * TILE_SIZE);

        setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) {
                this.open();
            } else if (e.isSecondaryButtonDown()) {
                cell.flag();
                System.out.println(cell.state);
                border.setFill((cell.state == State.FLAGGED) ? Color.RED :
                        (cell.state == State.CLOSED) ? Color.YELLOWGREEN : Color.ORANGE);
            }

        });
    }

    private void open() {
        if (cell.open()) {
            if (cell.hasBomb)
                Tile.main.callLose();
            text.setVisible(true);
            border.setFill(Color.ORANGE);

            if (text.getText().isEmpty()) {
                Tile.main.game.getNeighbors(this).forEach(Tile::open);
            }
            Tile.main.game.check();
        }
    }

    public static void link(Main main) {
        Tile.main = main;
    }
}