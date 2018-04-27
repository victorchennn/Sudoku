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

        m.addTile(Tile.create(1,0, 6), false);
        m.addTile(Tile.create(9,8, 0), false);
        assertEquals(Arrays.equals(c0_cg1, m.convertToArray(m.col(0))), true);
        assertEquals(Arrays.equals(r0_cg1, m.convertToArray(m.row(0))), true);

        m.addTile(Tile.create(3,0, 0), false);
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
    public void test_generate_Full() {
        Model m = new Model();
        System.out.println(m.generate());
        for (int i = 0; i < 10; i++) {
            Model temp = m.generate();
            assertEquals(temp.complete(), true);
            assertEquals(temp.tile(16), temp.tile(7,1));
            assertEquals(temp.tile(30), temp.tile(3,3));
            assertEquals(temp.tile(42), temp.tile(6,4));
        }
    }

    @Test
    public void test_solver(){
        Model m1 = get_exmp1_init();
        Model m1_completed = get_exmp1();
        Model m4 = get_exmp4_init();
        Model m4_completed = get_exmp4();
        assertEquals(false, m1.equals(m1_completed));
        assertEquals(false, m4.equals(m4_completed));
        m1.sudoku_solver();
        m4.sudoku_solver();
        assertEquals(true, m1.equals(m1_completed));
        assertEquals(true, m1.equals(m1_completed));
        for (int i = 0; i < 81; i++) {
            assertEquals(true, m1.tile(i).exist());
            assertEquals(true, m4.tile(i).exist());
        }
    }


    @Test
    public void test_multiple_solutions(){
        Model m5_1 = get_exmp5_multiple();
        Model m5_2 = get_exmp5_multiple();
//        System.out.println(m5_1);
//        System.out.println(m5_1.sudoku_solver());
//        System.out.println(m5_2.sudoku_solver());
        for (int i = 0; i < 10; i++) {
            Model m = get_exmp5_multiple();
            assertEquals(false, m.check_unique());
        }
    }

    @Test
    public void test_generate_complete() {
        Model m = new Model();
        m.sudoku_solver();
        Random ran = new Random();
        List<Tile> assigned = m.tiles(true);
        for (int t = 81; t > 27; t--) {
            int i = ran.nextInt(assigned.size());
            int col = assigned.get(i).col();
            int row = assigned.get(i).row();
            m.deleteTile(col, row);
            assigned.remove(i);
        }
        System.out.println(m);
        while (true) {
            Object[] o = m.check_unique();
            if (!(Boolean)o[0]) {
                int index = (Integer)o[1];
                m.addTile(Tile.create((Integer)o[2], index % 9, index / 9), false);
                System.out.println("add");
            } else {
                break;
            }
        }
        System.out.println(m);
//        System.out.println(m.check_unique());
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
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r), false);
            }
        }
        return m;
    }

    /** Example1, initialized. */
    private Model get_exmp1_init() {
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
                m.addTile(Tile.create(ex[r][c], c, r), false);
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
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r), false);
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
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r), false);
            }
        }
        return m;
    }

    /** Example4, initialized. */
    private Model get_exmp4_init() {
        int[][] ex = {
                {0,1,0,9,0,7,0,6,0},
                {2,0,0,8,0,4,0,0,7},
                {0,9,0,0,3,0,0,8,0},
                {0,0,2,0,0,0,7,0,0},
                {5,4,0,0,0,0,0,1,9},
                {0,0,1,0,0,0,6,0,0},
                {0,3,0,0,6,0,0,7,0},
                {8,0,0,2,0,3,0,0,6},
                {0,2,0,5,0,1,0,9,0},
        };
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r), false);
            }
        }
        return m;
    }

    /** Example4, completed. */
    private Model get_exmp4() {
        int[][] ex = {
                {3,1,8,9,5,7,4,6,2},
                {2,6,5,8,1,4,9,3,7},
                {7,9,4,6,3,2,5,8,1},
                {6,8,2,1,4,9,7,5,3},
                {5,4,3,7,2,6,8,1,9},
                {9,7,1,3,8,5,6,2,4},
                {1,3,9,4,6,8,2,7,5},
                {8,5,7,2,9,3,1,4,6},
                {4,2,6,5,7,1,3,9,8},
        };
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r), false);
            }
        }
        return m;
    }

    /** Example5, multiple solutions. */
    private Model get_exmp5_multiple() {
        int[][] ex = {
                {0,0,3,0,0,0,0,0,0},
                {5,0,0,0,8,0,0,3,6},
                {0,0,2,7,9,0,1,0,0},
                {0,0,0,0,0,0,0,0,0},
                {2,4,0,0,0,0,0,7,3},
                {0,0,7,3,1,8,0,6,0},
                {0,0,0,0,2,0,3,1,0},
                {0,0,0,0,0,0,0,0,0},
                {8,0,0,6,0,0,9,0,5},
        };
        Model m = new Model();
        for (int r = 0; r < m.size(); r++) {
            for (int c = 0; c < m.size(); c++) {
                m.addTile(Tile.create(ex[r][c], c, r), false);
            }
        }
        return m;
    }
}
