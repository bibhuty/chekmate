## Naming & Readability

- **Multi-array problems**: name pointers by role (`read1`/`read2`/`write`) or symmetric pair (`p1`/`p2`). Never size-derived (`mi`, `ni`) — they echo the size variables and invite confusion.
- **Reserve `i`/`j`/`k`** for single-array loops with short bodies. Two arrays or nested traversal → use named pointers.
- **Name by role when one exists**: `read`/`write`, `slow`/`fast`, `left`/`right`. Self-documenting beats clever.
- **Don't lie with names**: `max` is wrong if the value isn't always the max. Pick `value`, `current`, or whatever's actually true.
- **Be consistent within a function**: don't mix `p1`/`p2` with a stray `k`.

## Code Review Habits

- **ALWAYS DRY RUN WITH SIMPLE USE CASES.**
- **Dead conditions**: after writing branching logic, check if outer loop/guard conditions make any branch redundant. Example: `while(write >= 0)` + `read1 < 0` already implies `read2 >= 0`.
- **Redundant guards**: `if (start >= end) return;` right before `while (start < end)` — the loop handles it.
- **Swap via temp**: never `a = f(b)` then `b = f(a)` — b is already overwritten. Always save both before assigning.

## Numeric Limits (mental model)

**Integers (Exact Math)**
- `2^10 ≈ 10^3`, so `2^20 ≈ 10^6`, `2^30 ≈ 10^9`
- `int` max ≈ `2.1 × 10^9` (`2^31 - 1`)
- `long` max ≈ `9.2 × 10^18` (`2^63 - 1`)
- Rule of thumb: if a sum/product could exceed ~`10^9`, use `long`.

**Floating Point (Approximations)**
- `float` (32-bit): ~7 significant decimal digits. Max ≈ `3.4 × 10^38`
- `double` (64-bit): ~15 significant decimal digits. Max ≈ `1.8 × 10^308`
- Rule of thumb: Default to `double` for all general math (Java's `Math` library expects/returns `double`).
- Memory override: Use `float` ONLY when storing massive arrays (e.g., millions of coordinates, ML weights) to cut RAM usage in half.
- 🚨 Danger zone: NEVER use `float` or `double` for money or exact decimal arithmetic (e.g., `0.1 + 0.2 = 0.30000000000000004`). Use `long` (store as cents) or `BigDecimal`.
## Java Utilities

- `Arrays.toString(arr)` — pretty print 1D array
- `Arrays.deepToString(arr)` — pretty print 2D+ array
- `System.arraycopy(src, srcPos, dest, destPos, len)` — fastest array copy
- - `ArrayDeque` not `ArrayDequeue` — no such class, silent typo until compile.

## Reading Constraints Carefully

- Constraint values reveal expected complexity:
    - n ≤ 10 → backtracking / brute force ok
    - n ≤ 20 → 2^n bitmask DP possible
    - n ≤ 500 → O(n³) ok
    - n ≤ 5000 → O(n²) ok
    - n ≤ 10^5 → need O(n log n) or O(n)
    - n ≤ 10^7 → O(n) only
- "Unique solution guaranteed" or similar guarantees are tools, not decoration — they often enable a tighter algorithm.

## Modulo:
- **Negative modulo:** `((i % n) + n) % n`
    - Example: `i = -1, n = 5` → `-1 % 5 = -1` → `-1 + 5 = 4` → `4 % 5 = 4` ✓
    - `(i + n) % n` breaks when `i < -n`: `i = -6, n = 5` → `-6 + 5 = -1` → `-1 % 5 = -1` ✗

