import java.util.ArrayList;
import java.util.List;

class Game {
    static final int X_TILES = 10;
    static final int Y_TILES = 9;
    Tile[][] cells;
    Main main;

    Game(Tile[][] cells, Main main) {
        this.cells = cells;
        this.main = main;
        createField();
    }

    void createField() {
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = cells[x][y];

                if (tile.cell.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.cell.hasBomb).count();

                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }
    }

    List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        //  t t
        // t X t
        //  t t

        int[] points = (tile.cell.y % 2 != 0) ? new int[]{
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

            double newX = tile.cell.x + dx;
            double newY = tile.cell.y + dy;

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
                neighbors.add(cells[(int) newX][(int) newY]);
            }
        }

        return neighbors;
    }

    void check() {
        int c = 0;
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = cells[x][y];
                if (tile.cell.state != State.CLOSED) c++;
            }
        }
        if (c == X_TILES * Y_TILES) main.callWin();
    }
}
