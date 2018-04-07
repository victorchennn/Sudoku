package game;

import java.util.*;

public class Model extends Observable{

    /**
     * A new sudoku game on a board of size SIZE with no pieces.
     * @param size Size of the board.
     */
    Model(int size) {
        _board = new Tile[size][size];
        clear();
        _gameover = false;
    }

    /** Clear the board to empty. */
    void clear() {
        _gameover = false;
        for (int r = 0; r < size(); r++) {
            for (int c = 0; c < size(); c++) {
                _board[r][c] = Tile.create(0, c, r);
            }
        }
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    void addTile(Tile tile) {
        assert _board[tile.col()][tile.row()].value() == 0;
        _board[tile.col()][tile.row()].changeValue(tile.value());
        setChanged();
    }

    /** Delete TILE at (COL, ROW) from the board. There must be a Tile
     * currently at that position. */
    void deleteTile(int col, int row) {
        assert _board[col][row].value() != 0;
        _board[col][row].changeValue(0);
        setChanged();
    }

    /** Delete TILE tile. */
    void deleteTile(Tile tile) {
        deleteTile(tile.col(), tile.row());
    }

    /** Generate a new random sudoku board with full values. */
    void generateFull() {
        Random ran = new Random();
        for (int c = 0; c < size(); c++) {
            List posb = _board[c][0].posbnum();
            int value = (Integer) posb.get(ran.nextInt(posb.size()));
            addTile(Tile.create(value, c, 0));
            deletepossible(c, 0, value);
        }
//        for (int r = 0; r < size(); r++) {
//            for (int c = 0; c < size(); c++) {
//                List posb = _board[c][r].posbnum();
//                int value = (Integer) posb.get(ran.nextInt(posb.size()));
//                addTile(Tile.create(value, c, r));
//                deletepossible(c, r, value);
//            }
//        }
    }

    /** Randomly delete some tiles with value and generate a complete sudoku
     * board with a unique solution. */
    void generateComplete() {

    }

    /** Return the current whole column values at that COL,
     * from bottom to top. */
    Tile[] col(int col) {
        return _board[col];
    }

    /** Return the current whole row values at that ROW,
     * from left to right. */
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
     * that (COL, ROW), from left to right, bottom to top. */
    Tile[] section(int col, int row) {
        Tile[] section = new Tile[size()];
        for (int i = 0, r = (row / 3 ) * 3; r < (row / 3 ) * 3 + 3; r++) {
            for (int c = (col / 3 ) * 3; c < (col / 3 ) * 3 + 3; c++) {
                section[i] = _board[c][r];
                i++;
            }
        }
        return section;
    }

    /** Convert a list of tiles to their values. */
    int[] covertTile(Tile[] tiles) {
        int[] result = new int[size()];
        int i = 0;
        for (Tile t : tiles) {
            if (t == null) {
                result[i] = 0;
            } else {
                result[i] = t.value();
            }
            i++;
        }
        return result;
    }

    void deletepossible(int c, int r, int value) {
        for (Tile t : col(c)) {
            t.posbnum().remove(new Integer(value));
        }
        for (Tile t : row(r)) {
            t.posbnum().remove(new Integer(value));
        }
        for (Tile t : section(c, r)) {
            t.posbnum().remove(new Integer(value));
        }
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
                if (tile(c, r).value() == 0) {
                    out.format("   ");
                } else {
                    out.format(" %2d", _board[c][r].value());
                }
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
            if (row == 5 || row == 2) {
                out.format("    -  -  -   -  -  -   -  -  -%n");
            }
            out.format("%d", row);
            for (int col = 0; col < size(); col += 1) {
                if (col % 3 == 0) {
                    if (tile(col, row).value() == 0) {
                        out.format(" |  ");
                    } else {
                        out.format(" |%2d", tile(col, row).value());
                    }
                } else {
                    if (tile(col, row).value() == 0) {
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
        out.format("%nGame is Over?  %s", gameOver());
        return out.toString();
    }

    /** Current contents of the board. */
    private Tile[][] _board;

    /** True iff game is ended. */
    private boolean _gameover;
}
