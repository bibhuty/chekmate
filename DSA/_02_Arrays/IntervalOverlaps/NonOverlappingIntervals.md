# [Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/)
**Category:** Arrays / Intervals (Greedy)

## My Thought Process
- **Boundary Definition:** Crucial to define what "overlapping" means first. Here, touching boundaries like `[1,2]` and `[2,3]` are *not* overlapping (`intervals[i][0] < right` is strictly `<`, not `<=`).
- **The Intuition:**
  ```text
    ---
     ------
       ---
         ------
  ```
  Among overlapping intervals, we must always choose to keep the one that finishes earliest (minimum overlap/smallest right boundary) to leave maximum room on the timeline for future intervals.
- **Two Valid Approaches:**
    1. **Sort by Start Time:** Requires dynamically resolving conflicts inside the loop. If an overlap occurs (`intervals[i][0] < right`), we increment `removed` and shrink our `right` boundary to `min(right, intervals[i][1])` (simulating deleting the longer interval).
    2. **Sort by End Time (Ideal Greedy):** By sorting by end time (`o1[1]`), the intervals are already ordered by who finishes earliest. The first interval we keep is guaranteed to have the smallest `right` boundary, so any subsequent overlapping interval can be blindly erased without comparing end times again.

## Code (Both Approaches)
**Time:** $O(N \log N)$ (Sorting) + $O(N)$ (Traversal) | **Space:** $O(N)$ (Timsort auxiliary space)

```java
package _02_Arrays.IntervalOverlaps;

import java.util.Arrays;

public class NonOverlappingIntervals {

    // Approach 1: Sort by Start Time (Dynamic boundary resolution)
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length < 2) return 0;
        
        // Avoid (o1, o2) -> o1[0] - o2[0] to prevent integer overflow on unbounded inputs
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        
        int left = intervals[0][0], right = intervals[0][1], removed = 0; 
        for (int i = 1; i < intervals.length; ++i) {
            if (intervals[i][0] < right) {
                ++removed;
                // Greedy choice: retain the interval that ends earlier
                if (intervals[i][1] < right) {
                    left = intervals[i][0];
                    right = intervals[i][1];
                }
            } else {
                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        return removed;
    }

    // Approach 2: Sort by End Time (Ideal Greedy)
    // --------
    //         ----
    //      ------
    //   ------
    public int eraseOverlappingIntervalsIdeal(int[][] intervals) {
        if (intervals.length < 2) return 0;

        // Sort strictly by end time
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[1], o2[1]));

        int erase = 0, right = intervals[0][1], left = 1;
        while (left < intervals.length) {
            int nextLeft = intervals[left][0];
            if (nextLeft < right) {
                ++erase;
            } else {
                right = intervals[left][1];
            }
            ++left;
        }
        return erase;
    }
}
```

## Key Takeaways & Traps
- **Start vs. End Sorting Rule:**
    - Merging / Inserting intervals $\rightarrow$ Sort by **Start Time** (`o[0]`).
    - Maximizing non-overlapping intervals / Minimizing removals $\rightarrow$ Sort by **End Time** (`o[1]`).