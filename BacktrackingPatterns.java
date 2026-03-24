import java.util.*;

/**
 * ============================================================
 *  BACKTRACKING PATTERNS IN JAVA — LEETCODE MASTER REFERENCE
 * ============================================================
 *
 * UNIVERSAL TEMPLATE (memorize this skeleton):
 *
 *   void backtrack(state, choices) {
 *       if (baseCase) { capture result; return; }
 *       for (choice : choices) {
 *           if (!isValid(choice)) continue;   // prune
 *           makeChoice(choice);               // choose
 *           backtrack(nextState, nextChoices);// explore
 *           undoChoice(choice);               // un-choose
 *       }
 *   }
 *
 * The 3 key questions for every problem:
 *   1. What is the "choice" at each step?
 *   2. What is the base case / termination?
 *   3. How do we prune invalid branches early?
 * ============================================================
 */
public class BacktrackingPatterns {

    // =========================================================
    // PATTERN 1 — SUBSETS (Power Set)
    // LeetCode 78. Subsets
    // Input: [1,2,3]  Output: [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]
    //
    // KEY IDEA: At each index, decide include or skip.
    //           Start next recursion from i+1 to avoid re-use.
    // =========================================================
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrackSubsets(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));          // every node is a valid subset
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);                       // choose
            backtrackSubsets(nums, i + 1, current, result); // explore
            current.remove(current.size() - 1);        // un-choose
        }
    }

    // =========================================================
    // PATTERN 2 — SUBSETS WITH DUPLICATES
    // LeetCode 90. Subsets II
    // Input: [1,2,2]  Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
    //
    // KEY IDEA: Sort first, then skip duplicate siblings at
    //           the same recursion level (i > start && nums[i] == nums[i-1]).
    // =========================================================
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsetsDup(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrackSubsetsDup(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue; // PRUNE: skip duplicate siblings
            current.add(nums[i]);
            backtrackSubsetsDup(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // =========================================================
    // PATTERN 3 — PERMUTATIONS
    // LeetCode 46. Permutations
    // Input: [1,2,3]  Output: all 6 orderings
    //
    // KEY IDEA: Use a boolean[] used array. Unlike subsets,
    //           start from index 0 every time but skip used elements.
    // =========================================================
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermute(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private void backtrackPermute(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;                     // PRUNE: already placed
            used[i] = true;
            current.add(nums[i]);
            backtrackPermute(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    // =========================================================
    // PATTERN 4 — PERMUTATIONS WITH DUPLICATES
    // LeetCode 47. Permutations II
    // Input: [1,1,2]  Output: [[1,1,2],[1,2,1],[2,1,1]]
    //
    // KEY IDEA: Sort + skip if same value AND previous sibling
    //           was not used (!used[i-1]). This ensures only one
    //           ordering of duplicate values is explored.
    // =========================================================
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermuteUnique(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private void backtrackPermuteUnique(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            // PRUNE: skip duplicate that would create a repeated permutation
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            used[i] = true;
            current.add(nums[i]);
            backtrackPermuteUnique(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    // =========================================================
    // PATTERN 5 — COMBINATION SUM (unlimited reuse)
    // LeetCode 39. Combination Sum
    // Input: candidates=[2,3,6,7], target=7
    // Output: [[2,2,3],[7]]
    //
    // KEY IDEA: Pass same index i (not i+1) to allow reuse.
    //           Prune when remaining < 0.
    // =========================================================
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtrackCombSum(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrackCombSum(int[] candidates, int start, int remaining, List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) { result.add(new ArrayList<>(current)); return; }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) break;      // PRUNE: sorted, no point going further
            current.add(candidates[i]);
            backtrackCombSum(candidates, i, remaining - candidates[i], current, result); // i not i+1 = reuse allowed
            current.remove(current.size() - 1);
        }
    }

    // =========================================================
    // PATTERN 6 — COMBINATION SUM II (no reuse, has duplicates)
    // LeetCode 40. Combination Sum II
    // Input: candidates=[10,1,2,7,6,1,5], target=8
    // Output: [[1,1,6],[1,2,5],[1,7],[2,6]]
    //
    // KEY IDEA: i+1 (no reuse) + skip duplicate siblings (i > start && nums[i]==nums[i-1]).
    // =========================================================
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombSum2(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrackCombSum2(int[] candidates, int start, int remaining, List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) { result.add(new ArrayList<>(current)); return; }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) break;
            if (i > start && candidates[i] == candidates[i - 1]) continue; // PRUNE: skip duplicate sibling
            current.add(candidates[i]);
            backtrackCombSum2(candidates, i + 1, remaining - candidates[i], current, result);
            current.remove(current.size() - 1);
        }
    }

    // =========================================================
    // PATTERN 7 — COMBINATIONS (k elements from 1..n)
    // LeetCode 77. Combinations
    // Input: n=4, k=2  Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
    //
    // KEY IDEA: Same as subsets but stop when size == k.
    //           Prune: if remaining slots > remaining numbers, stop early.
    // =========================================================
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombine(n, k, 1, new ArrayList<>(), result);
        return result;
    }

    private void backtrackCombine(int n, int k, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) { result.add(new ArrayList<>(current)); return; }
        int needed = k - current.size();
        for (int i = start; i <= n - needed + 1; i++) { // PRUNE: not enough numbers left
            current.add(i);
            backtrackCombine(n, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // =========================================================
    // PATTERN 8 — GENERATE PARENTHESES
    // LeetCode 22. Generate Parentheses
    // Input: n=3  Output: ["((()))","(()())","(())()","()(())","()()()"]
    //
    // KEY IDEA: Track open and close counts.
    //           Add '(' if open < n.
    //           Add ')' if close < open (never more closing than opening).
    // =========================================================
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrackParens(n, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void backtrackParens(int n, int open, int close, StringBuilder sb, List<String> result) {
        if (sb.length() == 2 * n) { result.add(sb.toString()); return; }
        if (open < n) {
            sb.append('(');
            backtrackParens(n, open + 1, close, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (close < open) {
            sb.append(')');
            backtrackParens(n, open, close + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // =========================================================
    // PATTERN 9 — LETTER COMBINATIONS OF PHONE NUMBER
    // LeetCode 17. Letter Combinations of a Phone Number
    // Input: "23"  Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
    //
    // KEY IDEA: Map digit -> letters. At each recursion level,
    //           iterate over letters for current digit.
    // =========================================================
    private static final String[] PHONE = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return result;
        backtrackPhone(digits, 0, new StringBuilder(), result);
        return result;
    }

    private void backtrackPhone(String digits, int index, StringBuilder sb, List<String> result) {
        if (index == digits.length()) { result.add(sb.toString()); return; }
        String letters = PHONE[digits.charAt(index) - '0'];
        for (char c : letters.toCharArray()) {
            sb.append(c);
            backtrackPhone(digits, index + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // =========================================================
    // PATTERN 10 — PALINDROME PARTITIONING
    // LeetCode 131. Palindrome Partitioning
    // Input: "aab"  Output: [["a","a","b"],["aa","b"]]
    //
    // KEY IDEA: Try every prefix. If it's a palindrome, recurse
    //           on the suffix. This is "string segmentation" backtracking.
    // =========================================================
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrackPalindrome(s, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrackPalindrome(String s, int start, List<String> current, List<List<String>> result) {
        if (start == s.length()) { result.add(new ArrayList<>(current)); return; }
        for (int end = start + 1; end <= s.length(); end++) {
            String sub = s.substring(start, end);
            if (!isPalindrome(sub)) continue;          // PRUNE: only explore palindrome prefixes
            current.add(sub);
            backtrackPalindrome(s, end, current, result);
            current.remove(current.size() - 1);
        }
    }

    private boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) { if (s.charAt(l++) != s.charAt(r--)) return false; }
        return true;
    }

    // =========================================================
    // PATTERN 11 — WORD SEARCH (Grid DFS Backtracking)
    // LeetCode 79. Word Search
    // Input: board with letters, word="ABCCED" → true
    //
    // KEY IDEA: For each cell, try DFS in 4 directions.
    //           Mark cell visited by mutating board (board[r][c] ^= 256),
    //           then restore after recursion.
    // =========================================================
    private static final int[][] DIRS = {{0,1},{0,-1},{1,0},{-1,0}};

    public boolean exist(char[][] board, String word) {
        int rows = board.length, cols = board[0].length;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (dfsWordSearch(board, word, r, c, 0)) return true;
        return false;
    }

    private boolean dfsWordSearch(char[][] board, String word, int r, int c, int idx) {
        if (idx == word.length()) return true;
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
        if (board[r][c] != word.charAt(idx)) return false;
        board[r][c] ^= 256;                            // mark visited (bit trick to avoid extra array)
        for (int[] d : DIRS)
            if (dfsWordSearch(board, word, r + d[0], c + d[1], idx + 1)) {
                board[r][c] ^= 256;                    // restore
                return true;
            }
        board[r][c] ^= 256;                            // restore
        return false;
    }

    // =========================================================
    // PATTERN 12 — N-QUEENS (Classic Constraint Backtracking)
    // LeetCode 51. N-Queens
    // Place N queens on NxN board so none attack each other.
    //
    // KEY IDEA: Place one queen per row. Use 3 boolean arrays
    //           to track column, diagonal, and anti-diagonal conflicts.
    //           cols[], diag[] (r-c+n-1), antiDiag[] (r+c).
    // =========================================================
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        boolean[] cols = new boolean[n];
        boolean[] diag = new boolean[2 * n - 1];
        boolean[] antiDiag = new boolean[2 * n - 1];
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');
        backtrackNQueens(board, 0, cols, diag, antiDiag, result);
        return result;
    }

    private void backtrackNQueens(char[][] board, int row, boolean[] cols, boolean[] diag, boolean[] antiDiag, List<List<String>> result) {
        int n = board.length;
        if (row == n) {
            List<String> solution = new ArrayList<>();
            for (char[] r : board) solution.add(new String(r));
            result.add(solution);
            return;
        }
        for (int col = 0; col < n; col++) {
            int d = row - col + n - 1;
            int ad = row + col;
            if (cols[col] || diag[d] || antiDiag[ad]) continue; // PRUNE: conflicts
            board[row][col] = 'Q';
            cols[col] = diag[d] = antiDiag[ad] = true;
            backtrackNQueens(board, row + 1, cols, diag, antiDiag, result);
            board[row][col] = '.';
            cols[col] = diag[d] = antiDiag[ad] = false;
        }
    }

    // =========================================================
    // PATTERN 13 — SUDOKU SOLVER (Grid Constraint Backtracking)
    // LeetCode 37. Sudoku Solver
    //
    // KEY IDEA: Find the next empty cell. Try digits 1-9.
    //           Check row, col, and 3x3 box validity.
    //           Backtrack if no digit works.
    // =========================================================
    public void solveSudoku(char[][] board) {
        backtrackSudoku(board);
    }

    private boolean backtrackSudoku(char[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') continue;
                for (char digit = '1'; digit <= '9'; digit++) {
                    if (!isValidSudoku(board, r, c, digit)) continue;
                    board[r][c] = digit;
                    if (backtrackSudoku(board)) return true;
                    board[r][c] = '.';                 // backtrack
                }
                return false;                          // no digit worked — backtrack to caller
            }
        }
        return true;                                   // all cells filled
    }

    private boolean isValidSudoku(char[][] board, int row, int col, char digit) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == digit) return false;  // same row
            if (board[i][col] == digit) return false;  // same col
            // same 3x3 box
            int r = 3 * (row / 3) + i / 3;
            int c = 3 * (col / 3) + i % 3;
            if (board[r][c] == digit) return false;
        }
        return true;
    }

    // =========================================================
    // PATTERN 14 — WORD BREAK II (String Segmentation)
    // LeetCode 140. Word Break II
    // Input: s="catsanddog", wordDict=["cat","cats","and","sand","dog"]
    // Output: ["cats and dog","cat sand dog"]
    //
    // KEY IDEA: Try every prefix that is in the dictionary.
    //           Use memoization on start index to avoid recomputation.
    // =========================================================
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        return backtrackWordBreak(s, 0, dict, memo);
    }

    private List<String> backtrackWordBreak(String s, int start, Set<String> dict, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) return memo.get(start);
        List<String> result = new ArrayList<>();
        if (start == s.length()) { result.add(""); return result; }
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (!dict.contains(word)) continue;
            List<String> rest = backtrackWordBreak(s, end, dict, memo);
            for (String sentence : rest)
                result.add(word + (sentence.isEmpty() ? "" : " " + sentence));
        }
        memo.put(start, result);
        return result;
    }

    // =========================================================
    // PATTERN 15 — EXPRESSION ADD OPERATORS
    // LeetCode 282. Expression Add Operators
    // Input: num="123", target=6  Output: ["1+2+3","1*2*3"]
    //
    // KEY IDEA: At each position, try +, -, *. Track lastOperand
    //           separately to handle multiplication (undo last add/sub
    //           and apply multiplication: eval - last + last*curr).
    // =========================================================
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        backtrackOperators(num, target, 0, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void backtrackOperators(String num, int target, int idx, long eval, long last, StringBuilder sb, List<String> result) {
        if (idx == num.length()) {
            if (eval == target) result.add(sb.toString());
            return;
        }
        for (int end = idx + 1; end <= num.length(); end++) {
            String part = num.substring(idx, end);
            if (part.length() > 1 && part.charAt(0) == '0') break; // no leading zeros
            long curr = Long.parseLong(part);
            int len = sb.length();
            if (idx == 0) {
                sb.append(part);
                backtrackOperators(num, target, end, curr, curr, sb, result);
                sb.setLength(len);
            } else {
                // Addition
                sb.append('+').append(part);
                backtrackOperators(num, target, end, eval + curr, curr, sb, result);
                sb.setLength(len);
                // Subtraction
                sb.append('-').append(part);
                backtrackOperators(num, target, end, eval - curr, -curr, sb, result);
                sb.setLength(len);
                // Multiplication (undo last, apply mult)
                sb.append('*').append(part);
                backtrackOperators(num, target, end, eval - last + last * curr, last * curr, sb, result);
                sb.setLength(len);
            }
        }
    }

    // =========================================================
    // CHEAT SHEET — When to use which variation
    // =========================================================
    /*
     *  PROBLEM TYPE                     PATTERN
     *  ─────────────────────────────────────────────────────
     *  All subsets                      start=i+1, capture every node
     *  All subsets (with dups)          sort + skip i>start && arr[i]==arr[i-1]
     *  All permutations                 used[], start=0 always
     *  All permutations (with dups)     sort + skip !used[i-1] && arr[i]==arr[i-1]
     *  Combinations, no reuse           start=i+1
     *  Combinations, reuse allowed      start=i (same index)
     *  Combinations, k elements         stop when size==k
     *  String segmentation              try all prefixes, recurse on suffix
     *  Grid path / Word search          DFS + mark visited + restore
     *  Constraint placement (N-Queens)  conflict sets (col, diag, antiDiag)
     *  Constraint filling (Sudoku)      find empty cell, try values, return bool
     *  Expression building              track eval + lastOperand for *
     *
     *  DUPLICATE PRUNING RULE (critical!):
     *    Sort the input, then inside the loop:
     *    if (i > start && nums[i] == nums[i-1]) continue;
     *    This skips duplicate elements at the SAME recursion level,
     *    but still allows them at different levels (different depths).
     */

    public static void main(String[] args) {
        BacktrackingPatterns bp = new BacktrackingPatterns();

        System.out.println("=== Pattern 1: Subsets ===");
        System.out.println(bp.subsets(new int[]{1, 2, 3}));

        System.out.println("\n=== Pattern 2: Subsets With Duplicates ===");
        System.out.println(bp.subsetsWithDup(new int[]{1, 2, 2}));

        System.out.println("\n=== Pattern 3: Permutations ===");
        System.out.println(bp.permute(new int[]{1, 2, 3}));

        System.out.println("\n=== Pattern 4: Permutations With Duplicates ===");
        System.out.println(bp.permuteUnique(new int[]{1, 1, 2}));

        System.out.println("\n=== Pattern 5: Combination Sum (reuse) ===");
        System.out.println(bp.combinationSum(new int[]{2, 3, 6, 7}, 7));

        System.out.println("\n=== Pattern 6: Combination Sum II (no reuse) ===");
        System.out.println(bp.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));

        System.out.println("\n=== Pattern 7: Combinations k of n ===");
        System.out.println(bp.combine(4, 2));

        System.out.println("\n=== Pattern 8: Generate Parentheses ===");
        System.out.println(bp.generateParenthesis(3));

        System.out.println("\n=== Pattern 9: Letter Combinations ===");
        System.out.println(bp.letterCombinations("23"));

        System.out.println("\n=== Pattern 10: Palindrome Partitioning ===");
        System.out.println(bp.partition("aab"));

        System.out.println("\n=== Pattern 11: Word Search ===");
        char[][] board = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        System.out.println(bp.exist(board, "ABCCED")); // true

        System.out.println("\n=== Pattern 12: N-Queens ===");
        System.out.println(bp.solveNQueens(4).size() + " solutions for 4-Queens");

        System.out.println("\n=== Pattern 14: Word Break II ===");
        System.out.println(bp.wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));

        System.out.println("\n=== Pattern 15: Add Operators ===");
        System.out.println(bp.addOperators("123", 6));
    }
}
