# [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)
**Category:** Arrays / Kadane's Algorithm (M1)

## Key Insights & Traps (My Mistakes)
1. **The "Sliding Window" Trap:** My initial instinct was to treat this like a standard sliding window, using a `left` pointer and a `while (nums[left] < 0)` loop to manually shrink the window from behind.
    * *The Correction:* Kadane's is not a standard sliding window. We don't care about individual negative numbers at the back; we only care if the **aggregate sum** of the current sequence drops below zero. If `currentSum < 0`, you don't inch a `left` pointer forward—you just nuke the entire sequence and reset `currentSum = 0`.
2. **The All-Negative Edge Case:** If the array is `[-3, -2, -1]`, resetting to `0` and returning `0` is wrong (since `0` isn't in the array).
    * *The Fix:* Initialize `maxSum = nums[0]` (not `0`) so the algorithm can at least return the least-negative number.
3. **Data Type Consideration:** Adding up massive integers can overflow a 32-bit `int`. Using a `long` for `currentSum` and `maxSum` is a great defensive programming habit unless explicitly bounded.

---

## Solutions

```java
package _02_Arrays.KadanesAlgorithm;

public class MaximumSubarray {
    
    // 1. Optimal Solution: Kadane's Algorithm
    // TC: O(N) | SC: O(1)
    public long calculateMaxSum(int[] nums) {
        long maxSum = nums[0]; 
        long currentSum = 0; 
        int right = 0, n = nums.length; 
        
        while (right < n) {
            currentSum += nums[right];
            
            // Record keeping: update max before we potentially reset
            maxSum = Math.max(currentSum, maxSum);
            
            ++right; 
            
            // The Core Logic: If the entire prefix goes negative, it's toxic.
            // Drop it completely and restart from the next index.
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        return maxSum; 
    }

    // 2. Follow-Up Solution: Divide & Conquer
    // TC: O(N log N) | SC: O(log N)
    public int calculateMaxSumDC(int[] nums, int start, int end) {
        if (start > end) return Integer.MIN_VALUE;

        int mid = start + (end - start) / 2;
        int midNum = nums[mid];
        
        // Scan left from mid-1
        int leftMax = 0, current = 0;
        for (int i = mid - 1; i >= start && i >= 0; --i) {
            current += nums[i];
            leftMax = Math.max(leftMax, current);
        }
        
        // Scan right from mid+1
        int rightMax = 0;
        current = 0;
        for (int i = mid + 1; i < nums.length && i <= end; ++i) {
            current += nums[i];
            rightMax = Math.max(rightMax, current);
        }
        
        // Recursively find max strictly in left or strictly in right
        int leftSubarrayMaxSum = calculateMaxSumDC(nums, start, mid - 1);
        int rightSubarrayMaxSum = calculateMaxSumDC(nums, mid + 1, end);

        // Return the max of the 3 possibilities
        return Math.max(leftMax + rightMax + midNum, 
                        Math.max(leftSubarrayMaxSum, rightSubarrayMaxSum));
    }
}
```