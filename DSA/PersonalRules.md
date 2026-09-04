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

## Numeric Limits & Traps (Mental Model)

### 1. Integers (Exact Math)
- **The 10-3 Rule:** `2^10 ≈ 10^3`, `2^20 ≈ 10^6`, `2^30 ≈ 10^9`
- **`int` (32-bit):** Max ≈ `2.1 × 10^9` (`2^31 - 1`).
- **`long` (64-bit):** Max ≈ `9.2 × 10^18` (`2^63 - 1`).

**🚨 The Integer Traps:**
- **The Upgrade Rule:** If a sum, product, or factorial could exceed `10^9`, cast to `long` *before* the math happens (e.g., `long j = (long) i * i;`).
- **The `MIN_VALUE` Trap:** `Integer.MIN_VALUE` is `-2147483648`, but `MAX_VALUE` is `2147483647`. `Math.abs(Integer.MIN_VALUE)` overflows and remains negative. Always cast to `long` first!
- **The Modulo Hint:** If a problem says "Return the answer modulo 10^9 + 7", it's a massive hint that the intermediate steps will overflow even a `long`.

### 2. Floating Point (Approximations)
- **`float` (32-bit):** ~7 significant decimal digits. Max ≈ `3.4 × 10^38`.
- **`double` (64-bit):** ~15 significant decimal digits. Max ≈ `1.8 × 10^308`.

**💡 When to use what:**
- **Default:** Always use `double` for general math (Java's `Math` library expects/returns `double`).
- **Memory Override:** Use `float` ONLY when storing massive matrices (e.g., millions of coordinates, ML weights) to cut RAM usage in half.

**🚨 The Floating Point Traps:**
- **The Equality Trap:** NEVER compare floats using `==` (e.g., `0.1 * 3 == 0.3` evaluates to `false`). Always use an epsilon: `Math.abs(a - b) < 1e-9`.
- **The Money Trap:** NEVER use `float` or `double` for currency or exact decimal arithmetic (`0.1 + 0.2 = 0.30000000000000004`). Store money as `long` (in cents) or use `BigDecimal`.

### 3. Arrays & Limits (CP & Interviews)
- **True Max Array Size (Memory):** A standard 256MB limit holds about `6.5 × 10^7` primitive `int`s. A safe upper bound for a 1D array before `OutOfMemoryError` is `~5 × 10^7`.
- **The Object Overhead Trap:** An `Integer[]` takes 4-5x more memory than an `int[]`. Always use primitive arrays (`int[]`, `long[]`, `boolean[]`) for massive datasets.
- **The 10^5 Rule (Time):** If a problem states `N ≤ 10^5` (or `2 × 10^5`), it is a hint about *Time*, not Memory. Modern judges process `~10^8` ops/sec. `N = 10^5` strictly requires an `O(N)` or `O(N log N)` solution. `O(N^2)` will TLE.

## Java Utilities

- `Arrays.toString(arr)` — pretty print 1D array
- `Arrays.deepToString(arr)` — pretty print 2D+ array
- `System.arraycopy(src, srcPos, dest, destPos, len)` — fastest array copy
- `ArrayDeque` not `ArrayDequeue` — no such class, silent typo until compile.
- **`StringBuilder` over `StringBuffer`**: Always use `StringBuilder` in interviews. `StringBuffer` is synchronized (thread-safe) which adds unnecessary execution overhead.
- **`map.computeIfAbsent(key, k -> new ArrayList<>()).add(val)`**: The absolute cleanest way to handle grouping/adjacency lists. Replaces the multi-line `putIfAbsent` + `get` + `add` pattern with a single, highly optimized line.

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

