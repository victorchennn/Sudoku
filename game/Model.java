package game;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Observable;
import java.util.Random;

public class Model extends Observable{

    /**
     * A new sudoku game on a board of size SIZE with no pieces.
     * @param size Size of the board.
     */
    Model(int size) {
        _board = new Tile[size][size];
        _gameover = false;
    }

    /** Clear the board to empty. */
    void clear() {
        _gameover = false;
        for (Tile[] col : _board) {
            Arrays.fill(col, null);
        }
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    void addTile(Tile tile) {
        assert _board[tile.col()][tile.row()] == null;
        _board[tile.col()][tile.row()] = tile;
        setChanged();
    }

    /** Delete TILE at (COL, ROW) from the board. There must be a Tile
     * currently at that position. */
    void deleteTile(int col, int row) {
        assert _board[col][row] != null;
        _board[col][row] = null;
        setChanged();
    }

    /** Delete TILE tile. */
    void deleteTile(Tile tile) {
        deleteTile(tile.col(), tile.row());
    }

    void changeTile(int col, int row, int value) {
        assert _board[col][row] != null;
        _board[col][row].changeValue(value);
        setChanged();
    }

    /** Generate a new random sudoku board with full values. */
    void generateFull() {

    }

    /** Randomly delete some tiles with value and generate a complete sudoku
     * board with a unique solution. */
    void generateComplete() {

    }

    /** Return the current whole column values at that COL,
     * from bottom to top. */
    int[] col(int col) {
        int[] result = new int[size()];
        int i = 0;
        for (Tile t : _board[col]) {
            if (t == null) {
                result[i] = 0;
            } else {
                result[i] = t.value();
            }
            i++;
        }
        return result;
    }

    /** Return the current whole row values at that ROW,
     * from left to right. */
    int[] row(int row) {
        int[] result = new int[size()];
        int i = 0;
        while(i < size()) {
            if (_board[i][row] == null) {
                result[i] = 0;
            } else {
                result[i] = _board[i][row].value();
            }
            i++;
        }
        return result;
    }

    /** Return the current whole values in its 3*3 section at
     * that (COL, ROW), from left to right, bottom to top. */
    int[] section(int col, int row) {
        int[] result = new int[size()];
        for (int i = 0, r = (row / 3 ) * 3; r < (row / 3 ) * 3 + 3; r++) {
            for (int c = (col / 3 ) * 3; c < (col / 3 ) * 3 + 3; c++) {
                if (_board[c][r] == null) {
                    result[i] = 0;
                } else {
                    result[i] = _board[c][r].value();
                }
                i++;
            }
        }
        return result;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there. */
    Tile tile(int col, int row) {
        return _board[col][row];
    }

    /** Return the number of squares on one side of the board. */
    int size() {
        return _board.length;
    }

    /** Return true iff the game is over. */
    boolean gameOver() {
        return _gameover;
    }

    /** Return out format for the section at (COL, ROW). */
    public String printsection(int col, int row) {
        Formatter out = new Formatter();
        for (int r = (row / 3 ) * 3 + 2; r >= (row / 3 ) * 3; r--) {
            out.format("%d|", r);
            for (int c = (col / 3 ) * 3; c < (col / 3 ) * 3 + 3; c++) {
                out.format(" %2d", _board[c][r].value());
            }
            out.format(" %n");
        }
        out.format("  ");
        for (int c = (col / 3 ) * 3; c < (col / 3 ) * 3 + 3; c++) {
            out.format(" %2d", c);
        }
        return out.toString();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        for (int row = size() - 1; row >= 0; row -= 1) {
            out.format("%d", row);
            for (int col = 0; col < size(); col += 1) {
                if (col % 3 == 0) {
                    if (tile(col, row) == null) {
                        out.format(" |  ");
                    } else {
                        out.format(" |%2d", tile(col, row).value());
                    }
                } else {
                    if (tile(col, row) == null) {
                        out.format("   ");
                    } else {
                        out.format(" %2d", tile(col, row).value());
                    }
                }
            }
            out.format(" |%n");
        }
        for (int col = 0; col < 9; col++) {
            if (col == 0) {
                out.format(" %4d", col);
            } else if (col % 3 == 0) {
                out.format(" %3d", col);
            } else {
                out.format(" %2d", col);
            }
        }
        out.format("%n  Game is Over? %s", gameOver());
        return out.toString();
    }

    /** Current contents of the board. */
    private Tile[][] _board;

    /** True iff game is ended. */
    private boolean _gameover;
}
