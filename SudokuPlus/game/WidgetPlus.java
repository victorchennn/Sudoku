package game;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;

/** A widget that displays a Sudoku board.
 *  @author Victor Chen
 */
public class WidgetPlus extends Pad{

    /** A graphical representation of game Sudoku with model MODEL. */
    public WidgetPlus(int size) {
        _size = size;
        _boardSide = size * TILE_SIDE_SEP + TILE_SEP_1;
        _tiles = new ArrayList<>();
        setPreferredSize(_boardSide + TILE_SIDE + TILE_SIDE_SEP, _boardSide);
    }

    /** Add the value of tile TILE to the board. */
    private void draw_tile(Graphics2D g, Tile tile) {
        int col = tile.col();
        int row = tile.row();
        if (tile.exist()) {
            g.setColor(FONT_COLOR_1);
        } else {
            g.setColor(FONT_COLOR_2);
        }
        String number = Integer.toString(tile.value());

        int x_position = ADJUST + col * TILE_SIDE_SEP;
        int y_position = _boardSide - row * TILE_SIDE_SEP - ADJUST;
        g.drawString(number, x_position, y_position);
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(EMPTY_SQUARE_COLOR);
        g.fillRect(0,0, _boardSide, _boardSide);
        g.setColor(BAR_COLOR);
        for (int k = 0, t = 0; k <= _boardSide; k += TILE_SIDE_SEP, t++) {
            g.fillRect(0, k, _boardSide, TILE_SEP_1);
            g.fillRect(k, 0, TILE_SEP_1, _boardSide);
            if (t == 3 || t == 6) {
                g.fillRect(0, k, _boardSide, TILE_SEP_2);
                g.fillRect(k, 0, TILE_SEP_2, _boardSide);
            }
        }
        g.setFont(TILE_FONT);
        for (Tile t : _tiles) {
            if (t.value() != 0) {
                draw_tile(g, t);
            }
        }
        if (no_value) {
            g.setColor(EMPTY_SQUARE_COLOR);
            g.fillRect(_boardSide + TILE_SIDE, 0, TILE_SIDE, _boardSide);
            g.setColor(BAR_COLOR);
            g.fillRect(_boardSide + TILE_SIDE, 0, TILE_SEP_1, _boardSide);
            g.fillRect(_boardSide + TILE_SIDE * 2, 0, TILE_SEP_1, _boardSide);
            for (int k = 0, t = 0; k <= _boardSide; k += TILE_SIDE_SEP, t++) {
                g.fillRect(_boardSide + TILE_SIDE, k, TILE_SIDE, TILE_SEP_1);
                if (t == 3 || t == 6) {
                    g.fillRect(_boardSide + TILE_SIDE, k, TILE_SIDE, TILE_SEP_2);
                }
            }
            for (int i = 0; i < _size; i++) {
                g.setColor(FONT_COLOR_2);
                String number = Integer.toString(i + 1);
                int x_position = ADJUST + _boardSide + TILE_SIDE;
                int y_position = i * TILE_SIDE_SEP + TILE_SIDE - ADJUST + TILE_SEP_2;
                g.drawString(number, x_position, y_position);
            }

        }
        if (_end) {
            g.setFont(GAME_OVER);
            FontMetrics metrics = g.getFontMetrics();
            g.setColor(GAME_OVER_COLOR);
            g.drawString("GAME OVER",
                    (_boardSide
                            - metrics.stringWidth("GAME OVER")) / 2,
                    (2 * _boardSide + metrics.getMaxAscent()) / 4);

        }
    }

    /** Update the model after each step. */
    synchronized void update(Model model) {
        _tiles = new ArrayList<>();
        for (int i = 0; i < _size * _size; i++) {
            _tiles.add(0, model.tile(i));
        }
        _end = model.complete();
        repaint();
        tick();
    }

    /** Wait for one tick (TICK milliseconds). */
    private void tick() {
        try {
            wait(TICK);
        } catch (InterruptedException excp) {
            assert false : "Internal error: unexpected interrupt";
        }
    }

    /** Width of board and grid lines. */
    static final int
        ADJUST = 16,
        TILE_SEP_1 = 2,
        TILE_SEP_2 = 5,
        TILE_SIDE = 50,
        DIFF = TILE_SEP_2 - TILE_SEP_1,
        TILE_SIDE_SEP = TILE_SEP_1 + TILE_SIDE;

    /** Color of board, grid lines and values. */
    static final Color
        EMPTY_SQUARE_COLOR = new Color(255, 255, 255),
        BAR_COLOR = new Color(140, 140, 140),
        FONT_COLOR_1 = new Color(0,0,0),
        FONT_COLOR_2 = new Color(0, 204, 255),
        GAME_OVER_COLOR = new Color(200, 0, 0, 64);

    /** Wait between animation steps (in milliseconds). */
    static final int TICK = 40;

    /** Font of number. */
    static final Font TILE_FONT = new Font("Number", Font.ITALIC, 30);

    /** Font for overlay text on board. */
    static final Font GAME_OVER = new Font("Game Over", 1, 64);

    /** The size of board row or column. */
    private final int _size;

    /** Length (in pixels) of the side of the board. */
    private int _boardSide;

    /** A list of Tiles currently being displayed. */
    private ArrayList<Tile> _tiles;

    /** True iff "GAME OVER" message is being displayed. */
    private boolean _end;

    /** True iff there is no value in this tile. */
    public boolean no_value;
}
