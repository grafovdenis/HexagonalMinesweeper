import javafx.scene.Parent;
import javafx.scene.layout.Pane;

class CreateContent {
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

        Game.createField();
        return root;
    }
}
