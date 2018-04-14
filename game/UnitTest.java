package game;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;

public class UnitTest {
    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] ignored) {
        /* textui.runClasses(); */

    }

    @Test
    public void test_getpart() {
        Model m = get_exmp1();
//        System.out.println(m);
        int[] c0 = {3,2,9,7,4,8,1,6,5};
        int[] c6 = {1,6,2,8,7,4,5,3,9};
        assertEquals(Arrays.equals(c0, m.convertToArray(m.col(0))), true);
        assertEquals(Arrays.equals(c6, m.convertToArray(m.col(6))), true);
        int[] r0 = {3,4,5,2,8,6,1,7,9};
        int[] r8 = {5,3,4,6,7,8,9,1,2};
        assertEquals(Arrays.equals(r0, m.convertToArray(m.row(0))), true);
        assertEquals(Arrays.equals(r8, m.convertToArray(m.row(8))), true);
        int[] s0_0 = {3,4,5,2,8,7,9,6,1};
        int[] s3_3 = {9,2,4,8,5,3,7,6,1};
        assertEquals(Arrays.equals(s0_0, m.convertToArray(m.sec(0, 0))), true);
        assertEquals(Arrays.equals(s0_0, m.convertToArray(m.sec(2, 2))), true);
        assertEquals(Arrays.equals(s0_0, m.convertToArray(m.sec(2, 1))), true);
        assertEquals(Arrays.equals(s3_3, m.convertToArray(m.sec(3, 3))), true);
        assertEquals(Arrays.equals(s3_3, m.convertToArray(m.sec(5, 5))), true);
        assertEquals(Arrays.equals(s3_3, m.convertToArray(m.sec(4, 5))), true);
    }

    @Test
    public void test_changepart() {
        Model m = get_exmp1();

        m.deleteTile(0,0);
        assertEquals(m.tile(0,0).value(), 0);
        int[] c0 = {3,2,9,7,4,8,1,6,5};
        int[] c0_cg1 = {0,2,9,7,4,8,1,6,5};
        int[] r0 = {3,4,5,2,8,6,1,7,9};
        int[] r0_cg1 = {0,4,5,2,8,6,1,7,9};
        assertEquals(Arrays.equals(c0, m.convertToArray(m.col(0))), false);
        assertEquals(Arrays.equals(c0_cg1, m.convertToArray(m.col(0))), true);
        assertEquals(Arrays.equals(r0, m.convertToArray(m.row(0))), false);
        assertEquals(Arrays.equals(r0_cg1, m.convertToArray(m.row(0))), true);

        m.deleteTile(0,6);
        m.deleteTile(8,0);
        int[] c0_cg2 = {0,2,9,7,4,8,0,6,5};
        int[] r0_cg2 = {0,4,5,2,8,6,1,7,0};
        assertEquals(Arrays.equals(c0_cg2, m.convertToArray(m.col(0))), true);
        assertEquals(Arrays.equals(r0_cg2, m.convertToArray(m.row(0))), true);

        m.addTile(Tile.create(1,0, 6));
        m.addTile(Tile.create(9,8, 0));
        assertEquals(Arrays.equals(c0_cg1, m.convertToArray(m.col(0))), true);
        assertEquals(Arrays.equals(r0_cg1, m.convertToArray(m.row(0))), true);

        m.addTile(Tile.create(3,0, 0));
        assertEquals(m.tile(0,0).value(), 3);
        assertEquals(Arrays.equals(c0, m.convertToArray(m.col(0))), true);
        assertEquals(Arrays.equals(r0, m.convertToArray(m.row(0))), true);
    }

    @Test
    public void test_legal1() {
        Model m1 = get_exmp1();
        Model m2 = get_exmp2();
        Model m3 = get_exmp3();
//        System.out.println(m3);
        int[] row_size = new int[9];
        int[] col_size = new int[9];
        int[] sec_size = new int[9];
        for (int i = 0; i < 9; i++) {
            row_size[i] = m2.convertToSet(m2.row(i)).size();
            col_size[i] = m2.convertToSet(m2.col(i)).size();
            sec_size[i] = m2.convertToSet(m2.sec(i)).size();
        }
        int[] row = new int[]{9,5,8,7,5,5,9,9,7};
        int[] col = new int[]{6,7,6,7,7,8,8,9,7};
        int[] sec = new int[]{7,7,8,3,7,8,9,8,8};
        assertEquals(Arrays.equals(row, row_size), true);
        assertEquals(Arrays.equals(col, col_size), true);
        assertEquals(Arrays.equals(sec, sec_size), true);

        assertEquals(m1.complete(), true);
        assertEquals(m2.complete(), false);
        assertEquals(m3.complete(), true);
    }

    @Test
    public void test_complete() {
        Model m = new Model(9);
        System.out.println(m.generateFull(m));

    }

    /** Example1, completed. */
    private Model get_exmp1() {
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
        return m;
    }

    /** Example2, uncompleted. */
    private Model get_exmp2() {
        int[][] ex = {
                {3,4,5,2,8,6,1,7,9},
                {0,8,7,4,0,0,0,3,5},
                {6,6,1,5,3,7,2,8,4},
                {1,1,1,9,2,4,8,5,6},
                {4,4,4,4,5,3,7,9,0},
                {8,8,8,8,8,1,4,2,3},
                {1,9,8,3,4,2,5,6,7},
                {6,7,2,1,9,5,3,4,8},
                {5,3,4,5,7,8,9,1,0},
        };
        Model m = new Model(9);
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r));
            }
        }
        return m;
    }

    /** Example3, completed. */
    private Model get_exmp3() {
        int[][] ex = {
                {7,1,2,4,3,6,9,5,8},
                {5,8,4,9,2,1,3,6,7},
                {9,6,3,8,5,7,1,4,2},
                {4,5,6,7,1,9,2,8,3},
                {3,9,1,2,8,5,6,7,4},
                {2,7,8,6,4,3,5,1,9},
                {1,3,7,5,9,4,8,2,6},
                {8,4,5,3,6,2,7,9,1},
                {6,2,9,1,7,8,4,3,5},
        };
        Model m = new Model(9);
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r));
            }
        }
        return m;
    }

}
