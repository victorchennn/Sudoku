package game;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
        assertEquals(Arrays.equals(c0, m.covertTile(m.col(0))), true);
        assertEquals(Arrays.equals(c6, m.covertTile(m.col(6))), true);
        int[] r0 = {3,4,5,2,8,6,1,7,9};
        int[] r8 = {5,3,4,6,7,8,9,1,2};
        assertEquals(Arrays.equals(r0, m.covertTile(m.row(0))), true);
        assertEquals(Arrays.equals(r8, m.covertTile(m.row(8))), true);
        int[] s0_0 = {3,4,5,2,8,7,9,6,1};
        int[] s3_3 = {9,2,4,8,5,3,7,6,1};
        assertEquals(Arrays.equals(s0_0, m.covertTile(m.section(0, 0))), true);
        assertEquals(Arrays.equals(s0_0, m.covertTile(m.section(2, 2))), true);
        assertEquals(Arrays.equals(s0_0, m.covertTile(m.section(2, 1))), true);
        assertEquals(Arrays.equals(s3_3, m.covertTile(m.section(3, 3))), true);
        assertEquals(Arrays.equals(s3_3, m.covertTile(m.section(5, 5))), true);
        assertEquals(Arrays.equals(s3_3, m.covertTile(m.section(4, 5))), true);
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
        assertEquals(Arrays.equals(c0, m.covertTile(m.col(0))), false);
        assertEquals(Arrays.equals(c0_cg1, m.covertTile(m.col(0))), true);
        assertEquals(Arrays.equals(r0, m.covertTile(m.row(0))), false);
        assertEquals(Arrays.equals(r0_cg1, m.covertTile(m.row(0))), true);

        m.deleteTile(0,6);
        m.deleteTile(8,0);
        int[] c0_cg2 = {0,2,9,7,4,8,0,6,5};
        int[] r0_cg2 = {0,4,5,2,8,6,1,7,0};
        assertEquals(Arrays.equals(c0_cg2, m.covertTile(m.col(0))), true);
        assertEquals(Arrays.equals(r0_cg2, m.covertTile(m.row(0))), true);

        m.addTile(Tile.create(1,0, 6));
        m.addTile(Tile.create(9,8, 0));
        assertEquals(Arrays.equals(c0_cg1, m.covertTile(m.col(0))), true);
        assertEquals(Arrays.equals(r0_cg1, m.covertTile(m.row(0))), true);

        m.addTile(Tile.create(3,0, 0));
        assertEquals(m.tile(0,0).value(), 3);
        assertEquals(Arrays.equals(c0, m.covertTile(m.col(0))), true);
        assertEquals(Arrays.equals(r0, m.covertTile(m.row(0))), true);
    }

    @Test
    public void test_1() {
//        Model m = get_exmp1();
//        List posb = m.tile(0,0).posbnum();
//        Random r = new Random();
//        int a = (Integer) posb.get(r.nextInt(posb.size()));
//        System.out.println(a);
        Model m = new Model(9);
        m.generateFull();
        System.out.println(m);
//        m.deleteTile(3,3);
//        System.out.println(m.printsection(3,3));
    }

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

}
