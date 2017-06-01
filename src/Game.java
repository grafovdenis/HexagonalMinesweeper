import java.util.ArrayList;
import java.util.List;

class Game {
    private static int X_TILES;
    private static int Y_TILES;
    static Tile[][] cells;

    static void loadCells() {
        X_TILES = Main.X_TILES;
        Y_TILES = Main.Y_TILES;
        cells = Main.grid;
    }

    static void createField() {
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = cells[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }
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

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
                neighbors.add(cells[(int) newX][(int) newY]);
            }
        }

        return neighbors;
    }

    static void check() {
        int c = 0;
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = cells[x][y];
                if (tile.isOpen || tile.flagged || tile.hasBomb) c++;
            }
        }
        if (c == X_TILES * Y_TILES) Main.callWin();
    }
}
