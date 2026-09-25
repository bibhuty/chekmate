# [Merge Intervals](https://leetcode.com/problems/merge-intervals/)
**Category:** Arrays / Intervals

## My Thought Process
- **The Visual Bottleneck:** Trying to code overlaps without visualizing them leads to missed edge cases (like a smaller interval fully swallowed by a larger one).
- **Overlap Scenarios:**
  ```text
  1. Partial Overlap:    ------
                             ------
                         ---------- (Result)

  2. Full Encapsulation: --------
                             ---
                         --------   (Result)
  ```
- **The Core Pattern:** Sort the intervals strictly by their start times. Because of the sort, the next interval's start time is *guaranteed* to be $\ge$ the current one.
- **The Overlap Condition:** Interval A and B overlap if `B.start <= A.end`.
- **The Merge:** The merged interval becomes `[A.start, max(A.end, B.end)]`. Using `max()` handles the full encapsulation scenario.

## Code (Optimal)
**Time:** $O(N \log N)$ (Sorting) | **Space:** $O(N)$ (Timsort auxiliary space + output list)

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        // Sort strictly by the start time
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0]));

        List<int[]> results = new ArrayList<>() {{
            add(intervals[0]);
        }};
        
        for (int i = 1; i < intervals.length; ++i) {
            // Get the last valid interval (results.getLast() is cleaner than results.size() - 1)
            int[] lastMerged = results.getLast(); 

            // If the beginning of the next object happens to lie in between the last 
            // valid interval, increase the span of the interval wherever possible
            if (intervals[i][0] <= lastMerged[1]) {
                lastMerged[1] = Math.max(lastMerged[1], intervals[i][1]);
            } else {
                // Get it ready for the next interval since it's disjoint
                results.add(intervals[i]);
            }
        }
        
        // Key takeaway: 2D array conversion syntax
        return results.toArray(new int[results.size()][2]); 
    }
}
```

## Java Utilities & Syntax Traps
- **Memory Thrashing with `new int[size][2]`**: When pre-allocating an array just to hold references (e.g., during Merge Sort `left`/`right` array splits), use `new int[size][]`. Specifying the inner `[2]` forces Java to instantiate objects in the heap that you immediately overwrite, thrashing the Garbage Collector.