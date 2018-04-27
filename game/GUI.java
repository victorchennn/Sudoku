package game;

import ucb.gui2.LayoutSpec;
import ucb.gui2.TopLevel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//        addMenuButton("Game->Resume", this::ResumeGame);
        addMenuButton("Game->Quit", this::quit);

        addLabel("", "Time", new LayoutSpec("y", 1));
        
        add(_widget, new LayoutSpec("y", 0,
                "height", "REMAINDER",
                "width", "REMAINDER"));

        _widget.requestFocusInWindow();
        _widget.setKeyHandler("keypress", this::keyPressed);
        _widget.setMouseHandler("click", this::mouseClicked);
        setPreferredFocus(_widget);
        setTime(_model.startime());
    }

    /** Respond to the mouse-clicking event. */
    private void mouseClicked(String s, MouseEvent e) {
        _widget.no_value = false;
        int x = e.getX(), y = e.getY();
        if (x <= SIZE) {
            int col = x / SEP;
            int row = _model.size() - y / SEP - 1;
            col = (col == 9 ? col - 1 : col);
            row = (row == 9 ? row - 1 : row);
            Tile t = _model.tile(col, row);
            if (!t.exist()) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    _col = col;
                    _row = row;
                    _widget.no_value = true;
                    _widget.repaint();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    _model.deleteTile(col, row);
                    _widget.update(_model);
                }
            }
        }
        if (x >= SIZE + TILE) {
            int value = y / SEP + 1;
            value = (value == 10 ? value - 1 : value);
            _model.addTile(Tile.create(value, _col, _row), true);
            _widget.update(_model);
        }
    }

    /** Respond to the key press by queuing it to the queue. */
    private void keyPressed(String s, KeyEvent e) {
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
    private void newGame(String s) {
        _pendingKeys.offer("New");
        _widget.requestFocusInWindow();
    }

    /** Response to "New Game" button click. */
    private void ResumeGame(String s) {
        _pendingKeys.offer("Resume");
        _widget.requestFocusInWindow();
    }

    /** Response to "Quit" button click. */
    private void quit(String s) {
        _pendingKeys.offer("Quit");
        _widget.requestFocusInWindow();
    }

    private void setTime(LocalDateTime time) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        setLabel("Time", "Time: " + f.format(time));
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
