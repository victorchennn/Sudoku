package game;

import ucb.gui2.LayoutSpec;
import ucb.gui2.TopLevel;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;

public class GUI extends TopLevel implements Observer {

    GUI(String title, Model model) {
        super(title, true);
        _model = model;
        _model.addObserver(this);
        _widget = new Widget(model.size());

        addMenuButton("Game->New", this::newGame);
        addMenuButton("Game->Quit", this::quit);

        add(_widget, new LayoutSpec("y", 0,
                "height", "REMAINDER",
                "width", "REMAINDER"));

        _widget.requestFocusInWindow();
        _widget.setKeyHandler("keypress", this::keyPressed);
        _widget.setMouseHandler("click", this::mouseClicked);
        setPreferredFocus(_widget);

    }

    /** Respond to the mouse-clicking event. */
    public void mouseClicked(String s, MouseEvent e) {
        _widget.no_value = false;
        int x = e.getX(), y = e.getY();
        if (x <= SIZE) {
            int col = x / SEP;
            int row = _model.size() - y / SEP - 1;
            col = (col == 9 ? col - 1 : col);
            row = (row == 9 ? row - 1 : row);
            int value = _model.tile(col, row).value();
            if (value == 0) {
                _col = col;
                _row = row;
                _widget.no_value = true;
            }
            _widget.repaint();
        }
        if (x >= SIZE + TILE) {
            int value = y / SEP + 1;
            value = (value == 10 ? value - 1 : value);
            _model.addTile(Tile.create(value, _col, _row));
            _widget.no_value = true;
            _widget.update(_model);
        }
    }

    /** Respond to the key press by queuing it to the queue. */
    public void keyPressed(String s, KeyEvent e) {
        _pendingKeys.offer(e.getKeyText(e.getKeyCode()));
    }

    /** Return the key, take it from the queue. */
    String readKey() {
        try {
            return _pendingKeys.take();
        } catch (InterruptedException e) {
            throw new Error("unexpected interrupt");
        }
    }

    /** Response to "New Game" button click. */
    public void newGame(String s) {
        _pendingKeys.offer("New");
        _widget.requestFocusInWindow();
    }

    /** Response to "Quit" button click. */
    public void quit(String s) {
        _pendingKeys.offer("Quit");
        _widget.requestFocusInWindow();
    }

    @Override
    public void update(Observable model, Object arg) {
        _widget.update(_model);
    }

    static final int
        TILE = 50,
        SEP = 52,
        SIZE = 470;

    /** The board widget. */
    private Widget _widget;

    /** The game model being viewed. */
    private Model _model;

    private int _col;

    private int _row;

    /** Queue of pending key presses. */
    private ArrayBlockingQueue<String> _pendingKeys =
            new ArrayBlockingQueue<>(5);
}
