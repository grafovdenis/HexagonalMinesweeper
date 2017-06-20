public class Cell {
    double y;
    double x;
    boolean hasBomb;
    State state = State.CLOSED;

    Cell(double x, double y, boolean hasBomb) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;
    }

    void flag() {
        if (state == State.OPENED)
            return;
        state = (state == State.FLAGGED) ? State.CLOSED : State.FLAGGED;
    }


    boolean open() {
        if (state == State.OPENED || state == State.FLAGGED)
            return false;
        state = State.OPENED;
        return true;
    }
}