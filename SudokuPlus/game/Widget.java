package game;

import java.util.Observable;
import javax.swing.JComponent;

public abstract class Widget extends Observable {
    protected JComponent me;

    public Widget() {
    }

    public boolean requestFocusInWindow() {
        return this.me.requestFocusInWindow();
    }
}

