package game;

public class Main {


    public static void main(String... args) {
        Model m = new Model();
        GUI gui = new GUI("Sudoku", m);
        gui.display(true);
        Object inp = new Input(gui);
        Game game = new Game(m, (Input) inp);
        while (game.playing()) {
            game.playGame();
        }
        System.exit(0);
    }
}
