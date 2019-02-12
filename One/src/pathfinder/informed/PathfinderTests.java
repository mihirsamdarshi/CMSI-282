package pathfinder.informed;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Unit tests for Maze Pathfinder. Tests include completeness and
 * optimality.
 */
public class PathfinderTests {

    @Test
    public void testPathfinder_t0() {
        String[] maze = {
                "XXXXXXX",
                "XI...KX",
                "X.....X",
                "X.X.XGX",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        // result will be a 2-tuple (isSolution, cost) where
        // - isSolution = 0 if it is not, 1 if it is
        // - cost = numerical cost of proposed solution
        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]); // Test that result is a solution
        assertEquals(6, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t1() {
        String[] maze = {
                "XXXXXXX",
                "XI....X",
                "X.MMM.X",
                "X.XKXGX",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(14, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t2() {
        String[] maze = {
                "XXXXXXX",
                "XI.G..X",
                "X.MMMGX",
                "X.XKX.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(10, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t3() {
        String[] maze = {
                "XXXXXXX",
                "XI.G..X",
                "X.MXMGX",
                "X.XKX.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        assertNull(solution); // Ensure that Pathfinder knows when there's no solution
    }

    @Test
    public void testPathfinder_t4() {
        String[] maze = {
                "XXXXXXX",
                "X...MIX",
                "X...XXX",
                "XGX.X.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        assertNull(solution); // Ensure that Pathfinder knows when there's no solution
    }

    @Test
    public void testPathfinder_t5() {
        String[] maze = {
                "XXXXXXX",
                "X...MIX",
                "XK..XXX",
                "XGX.X.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(8, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t6() {
        String[] maze = {
                "XXXXXXX",
                "X..MMIX",
                "XK..XXX",
                "XGX.X.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(10, result[1]); // Ensure that the solution is optimal
    }

    public void testPathfinder_t7() {
        String[] maze = {
                "XXXX",
                "X.IX",
                "XG.X",
                "XXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        // result will be a 2-tuple (isSolution, cost) where
        // - isSolution = 0 if it is not, 1 if it is
        // - cost = numerical cost of proposed solution
        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]); // Test that result is a solution
        assertEquals(2, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t8() {
        String[] maze = {
                "XXXXXXX",
                "X..X..X",
                "XI...KX",
                "XXXXX.X",
                "XG....X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(10, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t9() {
        String[] maze = {
                "XXXXXXXXXX",
                "XXXXXXXXXX",
                "XXXXXXIXXX",
                "XXXXXX..XX",
                "XXXXXX..XX",
                "XX..K...XX",
                "XX.XXXX.XX",
                "XX..G...XX",
                "XXXXXXXXXX",
                "XXXXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(11,  result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t10() {
        String[] maze = {
                "XXXXXXXXXX",
                "XX.......X",
                "XXXX..XX.X",
                "XXXXIXXX.X",
                "XXXXXXXX.X",
                "X....K.M.X",
                "X.XX.XX.XX",
                "XGXXXXXX.XX",
                "XXG.....XX",
                "XXXXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(21, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t11() {
        String[] maze = {
                "XXXXXXX",
                "X....IX",
                "X..KXXX",
                "XGM.X.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(6,  result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t12() {
        String[] maze = {
                "XXXXXXXXXXXX",
                "X..XXXX...IX",
                "X..XXX..K.XX",
                "XGM....X...X",
                "XXXXXXXX..XX",
                "X.........XX",
                "XGMXXXXX.X.X",
                "XXXXXXXXXXXX",
                "X..XXXXXXXXX",
                "XXXXXXX.X.X.",
                "XXXXXXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(13,  result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t13() {
        String[] maze = {
                "XXXXXXXXXXXIXX",
                "X...........XX",
                "X..KXXXXXXXXXX",
                "XGM.X.XXXXXXXX",
                "XXXXXXXXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(13,  result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t15() {
        String[] maze = {
                "XXXXXXX",
                "X....IX",
                "X..XXXX",
                "XGM.X.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        assertNull(solution); // Test that result has no solution
    }

    @Test
    public void testPathfinder_t16() {
        String[] maze = {
                "XXXXXXXXXXXX",
                "X..XXXXX..IX",
                "X..XXXXXXXXX",
                "XGMXXXXX.X.X",
                "XXXXXXXXXXXX",
                "X..XXXXXXXXX",
                "XXXMXXXXX.X.X",
                "XXXXXXXXXXXX",
                "XXXXXXXKXXXX",
                "XGMXXXXX.X.X",
                "XXXXXXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        assertNull(solution); // Test that result has no solution
    }

    @Test
    public void testPathfinder_t17() {
        String[] maze = {
                "XXXXXXX",
                "X...MIX",
                "X...XXX",
                "XGKKXXX",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1,  result[0]); // Test that result is a solution
        assertEquals(8,  result[1]); // Ensure that the solution is optimal
        System.out.println(8 == result[1]);
    }

    @Test
    public void testPathfinder_t18() {
        String[] maze = {
                "XXXXXXX",
                "X....XX",
                "XIX.X.X",
                "XX.X..X",
                "XG....X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        assertNull(solution); // Test that result is a solution
    }
}
