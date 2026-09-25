# [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
**Category:** Arrays / Two Pointers

## My Thought Process
- **The Visual Trap:** The diagram in the problem statement makes this feel like a geometry puzzle, but it is strictly a mathematical bottleneck problem.
- **The Equation:** $Area = \min(nums[left], nums[right]) \times (right - left)$
- **The Derivation:**
    1. Start with pointers at the absolute edges. Width is at its maximum.
    2. Every time a pointer moves inward, the width strictly decreases.
    3. Because width is guaranteed to shrink, the *only* way the Area can increase is if the height bottleneck gets larger.
    4. If we move the taller pointer, the height remains bottlenecked by the shorter pointer (or gets worse), guaranteeing a smaller area.
    5. Therefore, the greedy choice is to always abandon the shorter pointer to seek a taller boundary.

## Code (Optimal Two-Pointer)
**Time:** $O(N)$ | **Space:** $O(1)$

```java
class Solution {
    public int maxArea(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            int width = right - left;
            int height = Math.min(nums[left], nums[right]);
            maxArea = Math.max(maxArea, height * width);
            
            // Abandon the shorter line in hopes of finding a taller one
            if (nums[left] < nums[right]) {
                ++left;
            } else {
                --right;
            }
        }
        
        return maxArea;
    }
}
```