package game;

public class Tile {

    /**
     * A new tile with VALUE as its value at (ROW, COL).
     * @param value Value in my position.
     * @param row My current row.
     * @param col My current column.
     */
    private Tile(int value, int row, int col) {
        _value = value;
        _row = row;
        _col = col;
    }

    /** Return my current row. */
    int row() {
        return _row;
    }

    /** Return my current column. */
    int col() {
        return _col;
    }

    /** Return the value supplied to my constructor. */
    int value() {
        return _value;
    }

    /** Value. */
    private final int _value;

    /** Position. */
    private final int _row, _col;
}
