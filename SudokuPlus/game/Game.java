package game;

/** The input/output and GUI controller for play of game Sudoku.
 *  @author Victor Chen
 */
public class Game {

    /** Game controller based on the MODEL, using Input as source of key
     * inputs and random Tiles. */
    public Game(Model model, Input input) {
        _model = model;
        _input = input;
        _playing = true;
    }

    /** Clear the board and play one game, until receiving a quit or
     *  new-game request.  Update the viewer with each added tile or
     *  change in the board from mouse clicking. */
    void playGame() {
        _model.clear();
        _model.generate();
        while (_playing) {
            _model.notifyObservers();
            String command = _input.getKey();
            switch (command) {
                case "Quit":
                    _playing = false;
                    return;
                case "New":
                    return;
            }
        }
    }

    /** Return true iff we have not received a Quit command. */
    boolean playing() {
        return _playing;
    }

    /** The playing board. */
    public Model _model;

    /** Input source from standard input. */
    private Input _input;

    /** True while user is still willing to play. */
    private boolean _playing;
}
