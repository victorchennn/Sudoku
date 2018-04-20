package game;

public class Main {

    public static void main(String... args) {
        Model model = new Model();
//        model.generateFull();
        model.addTile(Tile.create(9,0,0));
        System.out.println(model);
        GUI gui = new GUI("Sudoku", model);
        gui.display(true);
    }

}
