# 📐 M1: Minimum Platforms / Divide Intervals Into Minimum Number of Groups

* **LeetCode 2406:** [Divide Intervals Into Minimum Number of Groups](https://leetcode.com/problems/divide-intervals-into-minimum-number-of-groups/)
* **GeeksforGeeks Equivalent:** [Minimum Platforms](https://www.geeksforgeeks.org/problems/minimum-platforms-1587115620/1)

---

### 1. Core Intuition & Dilworth's Theorem
The problem asks for the **minimum number of non-overlapping groups** (or train platforms) required to partition all intervals.

By **Dilworth's Theorem on Interval Graphs**, the minimum number of non-overlapping groups required is strictly equal to the **maximum number of intervals overlapping at any single point in time** (`maxActive`):
* If $K$ intervals all intersect at a single timestamp $t$, no two of them can share a group—requiring at least $K$ groups.
* Because intervals are continuous on a 1D line, a greedy left-to-right assignment guarantees we never need *more* than $K$ groups.

### 2. Boundary Rule & Assumptions
* **Closed / Inclusive `[left, right]`:** If one interval ends at `t` and another starts at `t` (e.g., `[1, 5]` and `[5, 10]`), they **overlap** and cannot share a group/platform.
* **The `right + 1` Shift:** Because the interval occupies the resource *through* timestamp `right`, the resource only frees up at **`right + 1`**. We record `+1` at `left` and `-1` at `right + 1`.
* **Overflow Assumption:** Shifting `right + 1` assumes `right < Integer.MAX_VALUE` (in LC 2406, `right <= 10^6`). If `right` could reach `Integer.MAX_VALUE`, use `TreeMap<Long, Integer>` or **Approach 1 (Event List Sorting)** with the inclusive tie-breaker `Integer.compare(b[1], a[1])`.

---

### 3. Implementation (`MinimumPlatforms.java`)

```java
public class MinimumPlatforms {

    // -----
    //   -------
    //       ---
    // -----
    //       ---
    //    ------

    // Note: inclusive
    // Figuring out the meaning of the value of 1 and -1 in the line sweep in very important
    // Max interval means the no of intervals that totally overlap among each other which can't
    // be divided into groups even if you remove one of the interval in between

    // Important assumptions:
    //     - range of intervals will confer to standard data structure
    //     - value is in the range so that we're not overflowing the integer with +1/-1

    // [[5,10],[6,8],[1,5],[2,3],[1,10]]
    // 1(1)---------11(-1)
    // 1(1)----6(-1)
    // ----5(1)-----11(-1)
    // -----6(1)--9(-1)
    // -2(1)-4(-1)

    // TC: O(n log n) for insertion + O(n) traversal
    // SC: O(2n) for interval storage
    public int minGroups(int[][] intervals){
        if(intervals.length<=1) return intervals.length;
        var timeline=new TreeMap<Integer,Integer>();//(1->2,2->1,4->-1,5->1,6->0,9->-1,11->-2)
        for(int[] interval:intervals){
            int left=interval[0], right=interval[1]+1;
            timeline.merge(left,1,Integer::sum);
            timeline.merge(right,-1,Integer::sum);
        }
        int active=0, maxActive=0;
        for(int delta: timeline.values()){//[values,active,maxActive]->[1,2,2]->[2,3,3]->[4,2,3]->[5,3,3]->[6,3,3]->[9,2,3]->[11,0,3]
            active+=delta;
            maxActive=Math.max(active, maxActive);
        }
        return maxActive;
    }
}
```