class Game {
    static void createField() {
        final int X_TILES = Main.X_TILES;
        final int Y_TILES = Main.Y_TILES;

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
    }
}
