package game;

public class Game {

    /** Game controller based on the MODEL, using Input as source of key
     * inputs and random Tiles. */
    public Game(Model model, Input input) {
        _model = model;
        _input = input;
        _playing = true;
    }

    void playGame() {
        _model.clear();
        _model.generateFull();
        while (_playing) {
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
    private Model _model;

    /** Input source from standard input. */
    private Input _input;

    /** True while user is still willing to play. */
    private boolean _playing;
}
