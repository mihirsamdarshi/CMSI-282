package lcs;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import java.util.Arrays;

public class LCSTests {

    // Bottom-up LCS Tests
    // -----------------------------------------------
    @Test
    public void BULCSTest_t0() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        ""
                        )),
                LCS.bottomUpLCS("", "")
                );
        // LCS.memoCheck can either be the 1 element matrix
        // or null -- up to you, I won't check for cases with
        // empty-String arguments
    }

    @Test
    public void BULCSTest_t1() {
        // First assertion: correctness test for set with LCS
        assertEquals(
                new HashSet<>(Arrays.asList(
                        ""
                        )),
                LCS.bottomUpLCS("A", "B")
                );
        // Second assertion: proper format / solution of memo table
        assertArrayEquals(
                new int[][] {
                    {0, 0},
                    {0, 0}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void BULCSTest_t2() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "A"
                        )),
                LCS.bottomUpLCS("A", "A")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0},
                    {0, 1}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void BULCSTest_t3() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "ABC"
                        )),
                LCS.bottomUpLCS("ABC", "ABC")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0},
                    {0, 1, 1, 1},
                    {0, 1, 2, 2},
                    {0, 1, 2, 3}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void BULCSTest_t4() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "AA", "BA"
                        )),
                LCS.bottomUpLCS("ABA", "BAA")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0},
                    {0, 0, 1, 1},
                    {0, 1, 1, 1},
                    {0, 1, 2, 2}
                },
                LCS.memoCheck
                );
    }


    // Top-Down LCS Tests
    // -----------------------------------------------
    @Test
    public void TDLCSTest_t0() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        ""
                        )),
                LCS.topDownLCS("", "")
                );
        // LCS.memoCheck can either be the 1 element matrix
        // or null -- up to you, I won't check for cases with
        // empty-String arguments
    }

    @Test
    public void TDLCSTest_t1() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        ""
                        )),
                LCS.topDownLCS("A", "B")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0},
                    {0, 0}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_t2() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "A"
                        )),
                LCS.topDownLCS("A", "A")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0},
                    {0, 1}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_t3() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "ABC"
                        )),
                LCS.topDownLCS("ABC", "ABC")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 2, 0},
                    {0, 0, 0, 3}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_t4() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "AA", "BA"
                        )),
                LCS.topDownLCS("ABA", "BAA")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0},
                    {0, 0, 1, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 2}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_5() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "AAAAA"
                        )),
                LCS.topDownLCS("AAAAA", "AAAAA")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0},
                    {0, 0, 2, 0, 0, 0},
                    {0, 0, 0, 3, 0, 0},
                    {0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 5}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_6() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "AY", "AX"
                        )),
                LCS.topDownLCS("AXYT", "AYZX")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 1, 1, 2},
                    {0, 1, 2, 2, 2},
                    {0, 1, 2, 2, 2}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_7() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "AB", "AX"
                        )),
                LCS.topDownLCS("AXB", "ABX")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0},
                    {0, 1, 1, 0},
                    {0, 1, 0, 2},
                    {0, 0, 2, 2}
                },
                LCS.memoCheck
                );
    }

    @Test
    public void TDLCSTest_t100() {
        assertEquals(
                new HashSet<>(Arrays.asList(
                        "ATGATGATGATGAT", "ATGATCATCATCAT",
                        "ATCATGATCATGAT", "ATCATCATGATGAT",
                        "ATGATCATGATCAT", "ATCATGATCATCAT",
                        "ATGATCATGATGAT", "ATCATCATCATGAT",
                        "ATGATGATCATCAT", "ATCATCATGATCAT",
                        "ATCATCATCATCAT", "ATGATGATCATGAT",
                        "ATCATGATGATGAT", "ATGATGATGATCAT",
                        "ATGATCATCATGAT", "ATCATGATGATCAT"
                        )),
                LCS.topDownLCS("ATCGATCGATCGATCGATCG", "GCATGCATGCAATGCATGCAT")
                );
        assertArrayEquals(
                new int[][] {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 2, 0, 3, 3, 3, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 2, 3, 3, 3, 4, 5, 5, 5, 5, 5, 6, 6, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 2, 2, 3, 0, 4, 4, 5, 0, 6, 6, 6, 6, 0, 7, 0, 0, 0, 0, 0, 0},
                    {0, 1, 2, 2, 3, 4, 4, 4, 5, 6, 6, 6, 6, 6, 7, 7, 0, 0, 0, 0, 0, 0},
                    {0, 1, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7, 7, 8, 0, 0, 0, 0, 0},
                    {0, 1, 2, 3, 4, 4, 4, 5, 6, 6, 6, 7, 7, 8, 8, 8, 8, 9, 0, 0, 0, 0},
                    {0, 0, 2, 3, 4, 0, 5, 5, 6, 0, 7, 7, 7, 8, 0, 9, 9, 9, 0, 0, 0, 0},
                    {0, 1, 2, 3, 4, 5, 5, 5, 6, 7, 7, 7, 7, 8, 9, 9, 9, 9, 10, 0, 0, 0},
                    {0, 0, 0, 3, 4, 5, 5, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 0, 0, 0},
                    {0, 0, 0, 0, 4, 5, 5, 6, 7, 7, 7, 8, 8, 9, 9, 9, 10, 11, 11, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 6, 6, 7, 0, 8, 8, 8, 9, 0, 10, 10, 11, 0, 12, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 9, 10, 10, 10, 11, 12, 12, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 10, 10, 11, 11, 12, 12, 13, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 11, 12, 12, 12, 13, 14},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 11, 12, 0, 13, 13, 14},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 13, 13, 14}
                },
                LCS.memoCheck
                );
    }



}