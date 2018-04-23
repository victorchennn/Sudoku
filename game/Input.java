package game;

public class Input {

    Input(GUI gui) {
        _gui = gui;
    }

    public String getKey() {
        return _gui.readKey();
    }

    /** Input source. */
    private GUI _gui;
}
