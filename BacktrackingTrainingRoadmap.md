# Backtracking — From Zero to Expert
### Your Personal Training Roadmap (Java)

---

## Realistic Time Estimates

| Starting Level | Time to Solve Medium Confidently | Time to Expert (Hard problems) |
|---|---|---|
| Complete beginner | 6–8 weeks | 4–6 months |
| Know recursion basics | 3–4 weeks | 2–3 months |
| Comfortable with DFS/recursion | 2–3 weeks | 6–8 weeks |

**Expert = you can solve any LeetCode Hard backtracking problem within 30–45 min, recognize the pattern within 2 minutes, and explain trade-offs.**

---

## The 3 Things That Will Make or Break You

1. **You must code every problem from memory** — reading solutions doesn't build muscle memory
2. **Always draw the recursion tree first** before coding (even just mentally)
3. **Pruning is the skill** — any brute-force can do backtracking; experts prune aggressively

---

## Phase 1 — Build the Foundation (Week 1–2)

**Goal:** Internalize the universal template. No stress about Hard problems yet.

### Week 1 — The Big 4 Patterns
Solve these in this exact order. Re-do until you can solve each from memory.

| Day | Problem | LC # | Key Learning |
|---|---|---|---|
| Day 1 | Subsets | 78 | Capture every node, start=i+1 |
| Day 2 | Permutations | 46 | used[] array, start=0 always |
| Day 3 | Combination Sum | 39 | Reuse = pass same i |
| Day 4 | Generate Parentheses | 22 | Count-based constraint |
| Day 5 | Subsets II | 90 | Sort + skip duplicate siblings |
| Day 6 | Permutations II | 47 | Sort + skip !used[i-1] |
| Day 7 | **Review Day** | — | Re-code all 6 without looking |

### Week 2 — String and Grid Patterns
| Day | Problem | LC # | Key Learning |
|---|---|---|---|
| Day 8 | Letter Combinations | 17 | Map digit→letters, index-based |
| Day 9 | Palindrome Partitioning | 131 | String segmentation pattern |
| Day 10 | Word Search | 79 | Grid DFS + mark/restore |
| Day 11 | Combination Sum II | 40 | No reuse + dup pruning |
| Day 12 | Combinations | 77 | k-size pruning |
| Day 13 | Phone Letter Combinations | 17 | Second pass — time yourself |
| Day 14 | **Review Day** | — | Re-code all patterns from memory |

---

## Phase 2 — Intermediate (Week 3–5)

**Goal:** Handle medium variants and tricky pruning. Start timing yourself (target: < 20 min/Medium).

| Problem | LC # | New Concept |
|---|---|---|
| Combination Sum III | 216 | k-size + range constraint |
| Restore IP Addresses | 93 | String segmentation + validity check |
| Beautiful Arrangement | 526 | Permutation + constraint check |
| Partition to K Equal Subsets | 698 | Bucket backtracking |
| Word Break II | 140 | Segmentation + memoization |
| N-Queens | 51 | Conflict set tracking (col/diag/antiDiag) |
| N-Queens II | 52 | Count solutions only |
| Sudoku Solver | 37 | Grid filling + return bool |
| Factor Combinations | 254 | Combination with math constraint |
| Additive Number | 306 | Number-sequence segmentation |

### Key Technique: Memoized Backtracking
When backtracking explores overlapping subproblems, add a `memo` map:
```java
Map<Integer, List<...>> memo = new HashMap<>();
// At start of function:
if (memo.containsKey(start)) return memo.get(start);
// At end of function:
memo.put(start, result);
return result;
```
Used in: Word Break II, Target Sum, Expression Add Operators.

---

## Phase 3 — Advanced (Week 6–10)

**Goal:** Solve Hard problems within 30–45 min.

| Problem | LC # | Why It's Hard |
|---|---|---|
| Expression Add Operators | 282 | Track lastOperand for `*` |
| Remove Invalid Parentheses | 301 | Min removals + BFS/backtrack hybrid |
| Zuma Game | 488 | State explosion, aggressive pruning |
| Word Squares | 425 | Prefix constraint + Trie optimization |
| Palindrome Partitioning II | 132 | DP for palindrome + backtrack |
| Verbal Arithmetic Puzzle | 1307 | Constraint propagation |
| Maximum Number of Achievable Transfer Requests | 1601 | Bitmask + backtrack |
| Minimum Unique Word Abbreviation | 411 | Backtrack + bitmask |

---

## Core Mental Models (Memorize These)

### Decision Tree Visualization
```
                    []
            /       |       \
          [1]      [2]      [3]       ← choose 1st element
         / \        |
      [1,2] [1,3] [2,3]               ← choose 2nd element
       |
    [1,2,3]                           ← base case
```
Every backtracking problem is navigating this tree. Pruning = cutting branches early.

### The 5-Question Framework (use before every problem)
```
1. What am I choosing at each step?
2. What are the valid choices? (constraint)
3. When do I stop? (base case)
4. When can I prune? (optimization)
5. Do I need to undo my choice? (always yes in backtracking)
```

### Reuse vs No-Reuse
```java
// REUSE ALLOWED (Combination Sum)
backtrack(nums, i, ...);       // pass same i

// NO REUSE (Subsets, Combinations)
backtrack(nums, i + 1, ...);   // pass i+1

// PERMUTATIONS (order matters)
// use boolean[] used, always start from 0
```

### Duplicate Pruning (The Hardest Concept)
```java
Arrays.sort(nums);  // MUST sort first
for (int i = start; i < nums.length; i++) {
    // Skip if same value as previous sibling AT THIS LEVEL
    if (i > start && nums[i] == nums[i-1]) continue;
    ...
}
```
`i > start` (not `i > 0`) means: "only skip if we already tried this value
at the current recursion level". This allows duplicates at different depths.

---

## Weekly Practice Schedule

```
Monday    — New problems (2–3 new)
Tuesday   — New problems (2–3 new)
Wednesday — Re-code this week's problems from memory
Thursday  — New problems (2–3 new)
Friday    — Timed contest (solve 1 medium in < 20 min)
Saturday  — Hard problem attempt (don't worry about success yet)
Sunday    — Review week + write down patterns in your own words
```

**Consistency beats intensity.** 1 hour daily beats 7 hours on weekends.

---

## Progress Checkpoints

### You're a Beginner if:
- [ ] You need to look at the template for every problem
- [ ] You forget to un-choose (restore state)
- [ ] You can't handle duplicates

### You're Intermediate if:
- [ ] You can identify the pattern within 2 min of reading a problem
- [ ] You code subsets/permutations from memory in < 10 min
- [ ] You understand when to memoize

### You're Expert if:
- [ ] You solve Hard backtracking in < 45 min
- [ ] You think about pruning before writing code
- [ ] You can optimize backtracking with Trie, bitmask, or constraint propagation
- [ ] You can explain the time complexity of your solution

---

## Common Bugs to Watch For

| Bug | Symptom | Fix |
|---|---|---|
| Forgot to un-choose | Duplicates in result | Add `list.remove(list.size()-1)` |
| Using `i > 0` instead of `i > start` | Missing valid subsets | Change to `i > start` |
| Not copying result | All entries point to same list | `new ArrayList<>(current)` |
| Leading zeros in number problems | Wrong expressions | `if (part.length() > 1 && part.charAt(0) == '0') break` |
| Grid: forgot to restore cell | Grid is permanently modified | Restore `board[r][c]` after recursion |
| Integer overflow in large inputs | Wrong sums | Use `long` instead of `int` |

---

## Your First Week Assignment

**Today (Day 1):**
1. Read `BacktrackingPatterns.java` top to bottom — understand each pattern
2. Run `main()` and verify output
3. Close the file and code **Subsets (LC 78)** from scratch in a new file

**Day 2:** Code Permutations from scratch
**Day 3:** Code Combination Sum from scratch
**Day 4:** Code Generate Parentheses from scratch
**Day 5:** Re-code all 4 in one sitting without looking at solutions

If you can do Day 5 in under 40 minutes total, you're ahead of schedule.

---

*"The expert has failed more times than the beginner has tried." — Keep going.*
