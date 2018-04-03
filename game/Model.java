package game;

import java.util.Arrays;
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

    /** Generate a new random sudoku board. */
    void generateFull() {

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

    /** Current contents of the board. */
    private Tile[][] _board;

    /** True iff game is ended. */
    private boolean _gameover;
}
