package game;

import org.apache.commons.lang3.ArrayUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Model extends Observable{

    /** A new sudoku game on the board. */
    Model() {
        clear();
    }

    /** A copy of Model m. */
    Model(Model m) {
        copy(m);
    }

    /** Clear the board to empty. */
    void clear() {
        _board = new Tile[9][9];
        _gameover = false;
        for (int r = 0; r < size(); r++) {
            for (int c = 0; c < size(); c++) {
                Tile t = Tile.create(0, c, r);
                _board[c][r] = t;
            }
        }
        _startTime = LocalDateTime.now();
        setChanged();
    }

    void copy(Model m) {
        _board = new Tile[9][9];
        _gameover = m._gameover;
        for (int r = 0; r < size(); r++) {
            for (int c = 0; c < size(); c++) {
                Tile t = Tile.create(m.tile(c,  r).value(), c, r);
                _board[c][r] = t;
            }
        }
        setChanged();
    }

    LocalDateTime startime() {
        return _startTime;
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    void addTile(Tile tile, boolean playing) {
//        assert _board[tile.col()][tile.row()].value() == 0;
        _board[tile.col()][tile.row()].changeValue(tile.value());
        if (!playing) {
            _board[tile.col()][tile.row()].changeExist(true);
        }
        setChanged();
    }

    /** Delete TILE at (COL, ROW) from the board. There must be a Tile
     * currently at that position. */
    void deleteTile(int col, int row) {
        assert _board[col][row].value() != 0;
        _board[col][row].changeValue(0);
        _board[col][row].changeExist(false);
        setChanged();
    }

    /** Generate a new random sudoku board with full values. */
    Model generate() {
        setChanged();
        return generateComplete();
    }

    /** Randomly delete some tiles with value and generate a completed sudoku
     * board with a unique solution. */
    private Model generateComplete() {
        sudoku_solver();
        Random ran = new Random();
        List<Tile> assigned = tiles(true);
        for (int t = 81; t > 27; t--) {
            int i = ran.nextInt(assigned.size());
            int col = assigned.get(i).col();
            int row = assigned.get(i).row();
            deleteTile(col, row);
            assigned.remove(i);
        }
        while (true) {
            Object[] o = check_unique();
            if (!(Boolean)o[0]) {
                int index = (Integer)o[1];
                addTile(Tile.create((Integer)o[2], index%9, index/9), false);
            } else {
                break;
            }
        }
        return this;
    }

    /** A Sudoku solver. */
    Model sudoku_solver() {
        if (complete()) {
            return this;
        }
        List<Tile> unassigned = tiles(false);
        Random ran = new Random();
        Tile t = unassigned.get(0);
        assert t.value() == 0;
        ArrayList<Integer> temp = new ArrayList<>(possnumber(t.col(), t.row()));
        while (temp.size() > 0) {
            int i = temp.get((ran.nextInt(temp.size())));
            if (!ArrayUtils.contains(convertToArray(row(t.row())), i) &&
                    !ArrayUtils.contains(convertToArray(col(t.col())), i) &&
                    !ArrayUtils.contains(convertToArray(sec(t.col(), t.row())), i)) {
                addTile(Tile.create(i, t.col(), t.row()), false);
                unassigned.remove(t);
                Model model = sudoku_solver();
                if (model.complete()) {
                    return model;
                }
            }
            temp.remove(new Integer(i));
            deleteTile(t.col(), t.row());
            unassigned.add(0, t);
        }
        return this;
    }

    /** Check the board has a unique solution or not, record and return
     * the index and value of the first different tile if not, from left
     * to right, bottom to top. */
    Object[] check_unique() {
        Object[] o = new Object[3];
        o[0] = true;
        for (int t = 0; t < 10; t++) {
            Model temp_1 = new Model(this);
            Model temp_2 = new Model(this);
            Model t1 = temp_1.sudoku_solver();
            Model t2 = temp_2.sudoku_solver();
            for (int i = 0; i < size() * size(); i++) {
                if (t1.tile(i).value() != t2.tile(i).value()) {
                    o[0] = false;
                    o[1] = i;
                    o[2] = t1.tile(i).value();
                    return o;
                }
            }
        }
        return o;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns zero if there is no tile there. */
    Tile tile(int col, int row) {
        assert 0 <= col && col < size();
        assert 0 <= row && row < size();
        return _board[col][row];
    }

    /** Return the tile in its INDEX, numbered from left to right,
     * bottom to top. */
    Tile tile(int index) {
        assert 0 <= index && index < size() * size();
        return _board[index % size()][index / size()];
    }

    /** Return the current whole column values at that COL,
     * from bottom to top. */
    Tile[] col(int col) {
        assert 0 <= col && col < size();
        return _board[col];
    }

    /** Return the current whole row values at that ROW,
     * from left to right. */
    Tile[] row(int row) {
        assert 0 <= row && row < size();
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
    Tile[] sec(int col, int row) {
        assert 0 <= col && col <= 8;
        assert 0 <= row && row <= 8;
        Tile[] section = new Tile[size()];
        for (int i = 0, r = (row / 3 ) * 3; r < (row / 3 ) * 3 + 3; r++) {
            for (int c = (col / 3 ) * 3; c < (col / 3 ) * 3 + 3; c++) {
                section[i] = _board[c][r];
                i++;
            }
        }
        return section;
    }

    /** Use index to return the section, numbered from left to right,
     * bottom to top. */
    Tile[] sec(int index) {
        assert 0 <= index && index < 9;
        switch (index) {
            case 0: case 4: case 8:
                return sec(index, index);
            case 1:
                return sec(4,1);
            case 2:
                return sec(7,1);
            case 3:
                return sec(1,4);
            case 5:
                return sec(7,4);
            case 6:
                return sec(1,7);
            case 7:
                return sec(4,7);
        }
        return null;
    }

    /** Convert a list of tiles to an array with their values. */
    int[] convertToArray(Tile[] tiles) {
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

    /** Convert a list of tiles to a set with their values. */
    HashSet convertToSet(Tile[] tiles) {
        HashSet<Integer> result = new HashSet<>();
        for (Tile t : tiles) {
            if (t.value() != 0) {
                result.add(t.value());
            }
        }
        return result;
    }

    /** Check board is complete or not. */
    boolean complete() {
        boolean result = true;
        for (int i = 0; i < 9; i++) {
            int row_size = convertToSet(row(i)).size();
            int col_size = convertToSet(col(i)).size();
            int sec_size = convertToSet(sec(i)).size();
            if (row_size != 9 || col_size != 9 || sec_size != 9) {
                result = false;
                break;
            }
        }
        _gameover = true;
        return result;
    }

    ArrayList<Integer> possnumber(int col, int row) {
        ArrayList<Integer> p = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            p.add(i);
        }
        for (Tile t : col(col)) {
            if (t.value() != 0) {
                p.remove(new Integer(t.value()));
            }
        }
        for (Tile t : row(row)) {
            if (t.value() != 0) {
                p.remove(new Integer(t.value()));
            }
        }
        for (Tile t : sec(col, row)) {
            if (t.value() != 0) {
                p.remove(new Integer(t.value()));
            }
        }
        return p;
    }

    /** Return the number of squares on one side of the board. */
    int size() {
        return _board.length;
    }

    /** Return true iff the game is over. */
    boolean gameOver() {
        return _gameover;
    }

    /** Return a list of assigned or unassigned tiles in this board. */
    List<Tile> tiles(boolean assign) {
        List<Tile> assigned = new ArrayList<>();
        List<Tile> unassigned = new ArrayList<>();
        for (int i = 0; i < size() * size(); i++) {
            if (tile(i).value() == 0) {
                unassigned.add(tile(i));
            } else {
                assigned.add(tile(i));
            }
        }
        return assign? assigned:unassigned;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Model m = (Model) o;
        for (int i = 0; i < size() * size(); i++) {
            if (tile(i).value() != m.tile(i).value()) {
                return false;
            }
        }
        return true;
    }

    /** Current contents of the board. */
    private Tile[][] _board;

    /** True iff game is ended. */
    private boolean _gameover;

    private LocalDateTime _startTime;
}
