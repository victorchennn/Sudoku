package game;

public class Main {

    public static void main(String... args) {
        Model model = new Model();
        GUI gui = new GUI("Sudoku", model);
        gui.display(true);
    }

}
