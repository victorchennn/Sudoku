package game;

import ucb.gui2.LayoutSpec;
import ucb.gui2.TopLevel;

import java.util.Observable;
import java.util.Observer;

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

    }

    public void newGame(String s) {

    }

    public void quit(String s) {

    }

    @Override
    public void update(Observable model, Object arg) {

    }


    /** The board widget. */
    private Widget _widget;
    /** The game model being viewed. */
    private Model _model;
}
