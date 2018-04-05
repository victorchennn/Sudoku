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

    /** Add TILE to the board.  There must be no Tile currently at the
     *  same position. */
    void addTile(Tile tile) {
        assert _board[tile.col()][tile.row()] == null;
        _board[tile.col()][tile.row()] = tile;
        setChanged();
    }

    /** Generate a new random sudoku board with full values. */
    void generateFull() {

    }

    /** Randomly delete some tiles with value and generate a complete sudoku
     * board with a unique solution. */
    void generateComplete() {

    }

    /** Return the current whole column values at that COL. */
    Tile[] col(int col) {
        return _board[col];
    }

    /** Return the current whole row values at that ROW. */
    Tile[] row(int row) {
        Tile[] column = new Tile[size()];
        int i = 0;
        while(i < size()) {
            column[i] = _board[i][row];
            i++;
        }
        return column;
    }

    /** Return the current whole values in its 3*3 section at
     * that (COL, ROW). */
    Tile[] section(int col, int row) {
        Tile[] part = new Tile[size()];
        return part;
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

    @Override
    public String toString() {
        Formatter out = new Formatter();
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (col % 3 == 0) {
                    if (tile(col, row) == null) {
                        out.format(" | ");
                    } else {
                        out.format(" |%2d", tile(col, row).value());
                    }
                } else {
                    if (tile(col, row) == null) {
                        out.format("  ");
                    } else {
                        out.format(" %2d", tile(col, row).value());
                    }
                }
            }
            out.format(" |%n");
        }
        out.format("%s", gameOver());
        return out.toString();
    }

    /** Current contents of the board. */
    private Tile[][] _board;

    /** True iff game is ended. */
    private boolean _gameover;
}
