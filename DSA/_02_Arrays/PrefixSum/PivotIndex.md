# [Find Pivot Index](https://leetcode.com/problems/find-pivot-index/)
**Category:** Arrays / Prefix Sum (Space Optimized)

## My Thought Process
- **The Core Math:** A pivot index strictly requires `leftSum == rightSum`. The element at the pivot index itself is excluded from both sides.
- **The Space Optimization:** I don't need a full $O(N)$ Prefix array and an $O(N)$ Suffix array. I can just calculate the total sum upfront and treat it as my `rightSum`.
- **The Execution:** As I walk through the array, the current element is "removed" from the `rightSum`. I check if `leftSum == rightSum`. If not, the current element is "added" to the `leftSum` as I move to the next index.

## Code (Optimal)
**Time:** $O(N)$ | **Space:** $O(1)$

```java
class Solution {
    public int pivotIndex(int[] nums) {
        int rightSum = 0, leftSum = 0;
        
        // Build the total initial state
        for (int num : nums) rightSum += num;
        
        // Dynamically shift the boundary
        for (int i = 0; i < nums.length; ++i) {
            rightSum -= nums[i]; // Remove current element from the right side
            
            if (rightSum == leftSum) return i;
            
            leftSum += nums[i]; // Add current element to the left side for the next iteration
        }
        
        return -1;
    }
}
```