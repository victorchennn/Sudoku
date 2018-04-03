package game;

import java.util.Observable;

public class Model extends Observable{

    /**
     * A new sudoku game on a board of size SIZE with no pieces.
     * @param size Size of the board.
     */
    Model(int size) {
        _board = new Tile[size][size];
        _gameover = false;
    }

    /** Current contents of the board. */
    private Tile[][] _board;

    /** True iff game is ended. */
    private boolean _gameover;
}
