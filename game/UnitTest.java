package game;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class UnitTest {
    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] ignored) {
        /* textui.runClasses(); */
        Random ran = new Random();
        int a = ran.nextInt(9) + 1;
        System.out.println(a);
    }

    @Test
    public void test_iflegal1() {
        int[][] ex = {
                {3,4,5,2,8,6,1,7,9},
                {2,8,7,4,1,9,6,3,5},
                {9,6,1,5,3,7,2,8,4},
                {7,1,3,9,2,4,8,5,6},
                {4,2,6,8,5,3,7,9,1},
                {8,5,9,7,6,1,4,2,3},
                {1,9,8,3,4,2,5,6,7},
                {6,7,2,1,9,5,3,4,8},
                {5,3,4,6,7,8,9,1,2},
        };
        Model m = new Model(9);
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r));
            }
        }
        System.out.println(m);
    }

}
