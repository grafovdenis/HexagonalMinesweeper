import javafx.scene.Parent;
import javafx.scene.layout.Pane;

class Game {
    static Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(Main.W, Main.H);

        final int X_TILES = Main.X_TILES;
        final int Y_TILES = Main.Y_TILES;

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.2);
                Main.grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = Main.grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = Tile.getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }

        return root;
    }
}
