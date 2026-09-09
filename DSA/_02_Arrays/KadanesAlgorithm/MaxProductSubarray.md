# [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)
**Category:** Arrays / Kadane's Algorithm (M2)

## My Thought Process & Assumptions
- **Core Idea:** Use Kadane's algorithm, but swap the addition operation for multiplication.
- **Constraints Check:** Assume the product will fit in `int` values (otherwise, I would immediately use `long`). The array always has at least one element.
- **Handling Zeroes (Good Catch):** If we hit a zero or the streaks break their intended polarity, we manually reset the trackers (`maxNegative = 1`, `maxPositive = 1`) to start fresh.

## Key Mistakes & Traps
- **The State Mutation Trap (`temp` variable):** This was my biggest mistake. When calculating `maxNegative` and `maxPositive`, they both rely on each other's previous values. If I update `maxNegative` first, the original value is lost, permanently corrupting the `maxPositive` calculation on the very next line. **Fix:** Always store the necessary previous state in a `temp` variable (`tempMaxNegative`) before mutating.
- **The "Two Negatives" Case:** Unlike Kadane's for sum, the product of two negative numbers becomes a massive positive number. I have to track both the positive streak and the negative streak simultaneously.

## Code (My Approach)
**Time:** $O(N)$ | **Space:** $O(1)$

```java
package _02_Arrays.KadanesAlgorithm;

public class MaxProductSubarray {
    
    public int maxProduct(int[] nums) {
        int maxPositive = 1, maxNegative = 1, max = nums[0];
        for(int num : nums) {
            // Update global max first before mutating the running trackers
            max = Math.max(max, Math.max(maxPositive * num, maxNegative * num));
            
            // TRAP: Must save maxNegative before updating it, so maxPositive can use it
            int tempMaxNegative = maxNegative; 
            
            maxNegative = Math.min(num, Math.min(maxNegative * num, maxPositive * num));
            maxPositive = Math.max(num, Math.max(tempMaxNegative * num, maxPositive * num));
            
            // Manual Kadane's Reset
            if (maxNegative >= 0) maxNegative = 1;
            if (maxPositive <= 0) maxPositive = 1;
        }
        return max;
    }
}
```