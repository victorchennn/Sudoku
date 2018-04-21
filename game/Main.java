package game;

public class Main {

    public static void main(String... args) {
        int[][] ex = {
                {0,0,0,0,8,0,0,7,9},
                {0,0,0,4,1,9,0,0,5},
                {0,6,0,0,0,0,2,8,0},
                {7,0,0,0,2,0,0,0,6},
                {4,0,0,8,0,3,0,0,1},
                {8,0,0,0,6,0,0,0,3},
                {0,9,8,0,0,0,0,6,0},
                {6,0,0,1,9,5,0,0,0},
                {5,3,0,0,7,0,0,0,0},
        };
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r));
            }
        }
        System.out.println(m);
        GUI gui = new GUI("Sudoku", m);
        gui.display(true);
    }
}
