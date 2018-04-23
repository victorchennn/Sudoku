package game;

import ucb.gui2.LayoutSpec;
import ucb.gui2.TopLevel;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;

public class GUI extends TopLevel implements Observer {

    GUI(String title, Model model) {
        super(title, true);
        _model = model;
        _model.addObserver(this);

        _widget = new Widget(model);
        add(_widget, new LayoutSpec("y", 0,
                "height", "REMAINDER",
                "width", "REMAINDER"));


        addMenuButton("Game->New", this::newGame);
        addMenuButton("Game->Quit", this::quit);

        _widget.requestFocusInWindow();
        _widget.setKeyHandler("keypress", this::keyPressed);


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


    /** The board widget. */
    private Widget _widget;
    /** The game model being viewed. */
    private Model _model;

    /** Queue of pending key presses. */
    private ArrayBlockingQueue<String> _pendingKeys =
            new ArrayBlockingQueue<>(5);
}
