package game;

import java.util.*;

public class Tile {

    /** A new tile with VALUE as its value at (ROW, COL). */
    private Tile(int value, int col, int row) {
        _value = value;
        _row = row;
        _col = col;
    }

    /** Return a new tile at (COL, ROW) with value VALUE. */
    static Tile create(int value, int col, int row) {
        return new Tile(value, col, row);
    }

    /** Change the value. **/
    void changeValue(int value) {
        _value = value;
    }

    /** Change the exist. **/
    void changeExist(boolean exist) {
        _exist = exist;
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

    /** Iff the tile is originally exist. */
    boolean exist() {
        return _exist;
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        if (value() == 0) {
            out.format("There is no number in current grid.");
        } else {
            out.format("value = %2d, column = %2d, row = %2d %n", value(), col(), row());
        }
        return out.toString();
    }

    /** Value. */
    private int _value;

    /** Position. */
    private final int _row, _col;

    private boolean _exist = true;
}
