package game;

public class Tile {

    /**
     * A new tile with VALUE as its value at (ROW, COL).
     * @param value Value in my position.
     * @param row My current row.
     * @param col My current column.
     */
    private Tile(int value, int col, int row) {
        _value = value;
        _row = row;
        _col = col;
    }

    /** Return a new tile at (ROW, COL) with value VALUE. */
    static Tile create(int value, int col, int row) {
        return new Tile(value, col, row);
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

    @Override
    public String toString() {
        return String.format("%d @ (%d, %d)", value(), col(), row());
    }

    /** Value. */
    private final int _value;

    /** Position. */
    private final int _row, _col;
}
