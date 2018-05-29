package game;

/** Describes a source of input commands.
 *  @author Victor Chen
 */
public class Input {

    Input(GUI gui) {
        _gui = gui;
    }

    /** Returns one command string. */
    public String getKey() {
        return _gui.readKey();
    }

    /** Input source. */
    private GUI _gui;
}
