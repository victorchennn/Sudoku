package game;

import ucb.gui2.Pad;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;

public class Widget extends Pad{

    /** A graphical representation of game Sudoku with model MODEL. */
    public Widget(int size) {
        _size = size;
        _boardSide = size * TILE_SIDE_SEP + TILE_SEP_1;
        _tiles = new ArrayList<>();
        setPreferredSize(_boardSide + 100, _boardSide);
    }

    /** Add the value of tile TILE to the board. */
    private void draw_tile(Graphics2D g, Tile tile) {
        int col = tile.col();
        int row = tile.row();
        int adjust = 16;
        g.setFont(TILE_FONT);
        String number = Integer.toString(tile.value());

        int x_position = adjust + col * TILE_SIDE_SEP;
        int y_position = _boardSide - row * TILE_SIDE_SEP - adjust;
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
        g.setColor(FONT_COLOR);
        for (Tile t : _tiles) {
            if (t.value() != 0) {
                draw_tile(g, t);
            }
        }
    }

    synchronized void update(Model model) {
        _tiles = new ArrayList<>();
        for (int i = 0; i < _size * _size; i++) {
            _tiles.add(0, model.tile(i));
        }
        repaint();
    }


    /** Width of board and grid lines. */
    static final int
        TILE_SEP_1 = 2,
        TILE_SEP_2 = 5,
        TILE_SIDE = 50,
        TILE_SIDE_SEP = TILE_SEP_1 + TILE_SIDE;

    /** Color of board, grid lines and values. */
    static final Color
        EMPTY_SQUARE_COLOR = new Color(255, 255, 255),
        BAR_COLOR = new Color(140, 140, 140),
        FONT_COLOR = new Color(0,0,0);

    /** Font of number. */
    static final Font TILE_FONT = new Font("Number", Font.ITALIC, 30);

    /** The size of board row or column. */
    private final int _size;

    /** Length (in pixels) of the side of the board. */
    private int _boardSide;

    /** A list of Tiles currently being displayed. */
    private ArrayList<Tile> _tiles;
}
