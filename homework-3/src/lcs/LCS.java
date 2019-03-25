package lcs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LCS {
    
    /**
     * memoCheck is used to verify the state of your tabulation after
     * performing bottom-up and top-down DP. Make sure to set it after
     * calling either one of topDownLCS or bottomUpLCS to pass the tests!
     */
    public static int[][] memoCheck;
    
    // -----------------------------------------------
    // Shared Helper Methods
    // -----------------------------------------------
    
    // [!] TODO: Add your shared helper methods here!
    
    public static Set<String> collectSolution (String rStr, int r, String cStr, int c, int[][] memo) {
        
        Set<String> result = new HashSet<String>();
        if (r == 0 || c == 0) {
            result.add("");
            return result;
        }       
        
        if (rStr.charAt(r) == cStr.charAt(c)) {
            
            for (String substring : collectSolution(rStr, r - 1, cStr, c - 1, memo)) {
                result.add(substring + rStr.charAt(r));
            }
            return result;
        }
        
        if (memo[r][c - 1] >= memo[r - 1][c]) { 
            result.addAll(collectSolution(rStr, r, cStr, c - 1, memo));
        }
        
        if (memo[r - 1][c] >= memo[r][c - 1]) {
            result.addAll(collectSolution(rStr, r - 1, cStr, c, memo));
        }

        return result;

    }

    // -----------------------------------------------
    // Bottom-Up LCS
    // -----------------------------------------------
    
    /**
     * Bottom-up dynamic programming approach to the LCS problem, which
     * solves larger and larger subproblems iterative using a tabular
     * memoization structure.
     * @param rStr The String found along the table's rows
     * @param cStr The String found along the table's cols
     * @return The longest common subsequence between rStr and cStr +
     *         [Side Effect] sets memoCheck to refer to table
     */
    public static Set<String> bottomUpLCS (String rStr, String cStr) {
        memoCheck = new int[rStr.length() + 1][cStr.length() + 1];
        for (int r = 1; r <= rStr.length(); r++) {
            for (int c = 1; c <= cStr.length(); c++) {
                memoCheck[r][c] = rStr.charAt(r - 1) != cStr.charAt(c - 1) 
                        ? Math.max(memoCheck[r - 1][c], memoCheck[r][c - 1]) : memoCheck[r - 1][c - 1] + 1;
            }
        }   
//      System.out.println(Arrays.deepToString(memoCheck));
        return collectSolution("0" + rStr, rStr.length(), "0" + cStr, cStr.length(), memoCheck); 
    }
    
    // [!] TODO: Add any bottom-up specific helpers here!
    
    
    // -----------------------------------------------
    // Top-Down LCS
    // -----------------------------------------------
    
    /**
     * Top-down dynamic programming approach to the LCS problem, which
     * solves smaller and smaller subproblems recursively using a tabular
     * memoization structure.
     * @param rStr The String found along the table's rows
     * @param cStr The String found along the table's cols
     * @return The longest common subsequence between rStr and cStr +
     *         [Side Effect] sets memoCheck to refer to table  
     */
    public static Set<String> topDownLCS (String rStr, String cStr) {
        
        int[][] memo = new int[rStr.length() + 1][cStr.length() + 1];
        
        memoCheck = tableFill(rStr, rStr.length(), cStr, cStr.length(), memo);
        
        throw new UnsupportedOperationException();
    }
    
    // [!] TODO: Add any top-down specific helpers here!
    
    /**
     */
    public static int[][] tableFill (String rStr, int r, String cStr, int c, int[][] memo) {
        
        if (r == 0 || c == 0) {
            return table;
        }
        
        if (rStr.charAt(r) == cStr.charAt(c)) {
            int[][] temp = tableFill(rStr, r - 1, cStr, c - 1, table);
            
        }

    } 
    
    
}
