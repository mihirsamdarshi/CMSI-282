package lcs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <DiBiagio, Will>
 * @author <Samdarshi, Mihir>
 */

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
    
    /**
     * Helper method to return a Set of solution strings from a DP table and its inputs.
     * @param rStr The String found along the table's rows
     * @param r The length of rStr
     * @param cStr The String found along the table's cols
     * @param C The length of cStr
     * @param table The DP table.
     * @return Set<String> The Set of solution strings from a DP table.
     *
     */ 
    private static Set<String> collectSolution (String rStr, int r, String cStr, int c, int[][] table) {
    	if (r == 0 || c == 0) {
    		return new HashSet<String>(Arrays.asList(""));
    	}    	
    	
    	Set<String> result = new HashSet<String>();
    	
    	if (rStr.charAt(r) == cStr.charAt(c)) {
    		for (String substring : collectSolution(rStr, r - 1, cStr, c - 1, table)) {
    			result.add(substring + rStr.charAt(r));
    		}
    		return result;
    	}
    	
    	if (table[r][c - 1] >= table[r - 1][c]) {	
    		result.addAll(collectSolution(rStr, r, cStr, c - 1, table));
    	}
    	
    	if (table[r - 1][c] >= table[r][c - 1]) {
    		result.addAll(collectSolution(rStr, r - 1, cStr, c, table));
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
    	memoCheck = bottomUpTableFill(rStr, cStr);
        return collectSolution("0" + rStr, rStr.length(), "0" + cStr, cStr.length(), memoCheck); 
    } 
    
    /**
     * Helper function for bottomUpLCS. Provides a solution DP table given two input strings.
     * @param rStr 
     * @param cStr
     * @return int[][] Solution DP table.
     *
     */
    private static int[][] bottomUpTableFill (String rStr, String cStr) {
    	int table[][] = new int[rStr.length() + 1][cStr.length() + 1];
    	for (int r = 1; r <= rStr.length(); r++) {
    		for (int c = 1; c <= cStr.length(); c++) {
    			table[r][c] = rStr.charAt(r - 1) != cStr.charAt(c - 1) 
    					? Math.max(table[r - 1][c], table[r][c - 1]) : table[r - 1][c - 1] + 1;
    		}
    	}
    	return table;
    }
    
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
    	memoCheck = topDownTableFill(rStr, rStr.length(), cStr, cStr.length(), 
    			new int[rStr.length() + 1][cStr.length() + 1]);
    	return collectSolution("0" + rStr, rStr.length(), "0" + cStr, cStr.length(), memoCheck);
    }
    
    /**
     * Helper function for topDownLCS. Provides a solution DP table.
     * @param rStr 
     * @param r
     * @param cStr
     * @param c
     * @param table
     * @return int[][]
     *
     */
    private static int[][] topDownTableFill (String rStr, int r, String cStr, int c, int[][] table) {
    	
    	if (r == 0 || c == 0) {
    		return table;
    	}
    	
    	if (table[r][c] != 0) {
    		return table;
    	}
    	
    	if (rStr.charAt(r - 1) == cStr.charAt(c - 1)) {
    		table[r][c] = topDownTableFill(rStr, r - 1, cStr, c - 1, table)[r - 1][c - 1] + 1;
    		return table;	
    	}
    	
    	table = topDownTableFill(rStr, r - 1, cStr, c, table);
    	table = topDownTableFill(rStr, r, cStr, c - 1, table);
		
    	table[r][c] = Math.max(table[r][c - 1], table[r - 1][c]);
		
		return table; 	
    }
}
