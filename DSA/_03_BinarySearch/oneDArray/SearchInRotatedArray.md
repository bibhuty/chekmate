# [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
**Category:** Arrays / Binary Search

## My Thought Process
- **The Distraction:** The problem explicitly mentions the array is "rotated left." This is a deliberate trap meant to confuse candidates into writing nightmare spaghetti code with multiple `if/else` direction checks.
- **The "Bangle" Epiphany:** A rotated array is just a sorted array bent into a circle (like a bangle). Once it's a circle, whether it was rotated left or right doesn't matter at all. All that matters is finding the "start" of the bangle (the pivot/minimum element).
- **The Core Strategy:** Instead of a messy 1-pass solution, do it in 2 clean passes:
  1. **Find the Pivot:** Use binary search to find the index of the smallest element. This is our `start` offset.
  2. **Logical to Physical Mapping:** Run a *standard* binary search from logical index `0` to `n-1`. Whenever we need to check an element, map the logical index to the physical array using modulo math: `(logical_index + start) % n`.

## Code (Optimal 2-Pass: The Bangle Method)
**Time:** $O(\log N)$ (Two separate $O(\log N)$ passes) | **Space:** $O(1)$

```java
class Solution {
    
    // Pass 1: Find the physical index of the smallest element
    public int findPivotIndex(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[right] > nums[mid]) {
                // Right side is perfectly sorted, the pivot is to the left (or is mid)
                right = mid;
            } else {
                // Right side is broken, the pivot must be strictly to the right
                left = mid + 1;
            }
        }
        return left;
    }

    // Pass 2: Standard binary search using Logical -> Physical mapping
    public int search(int[] nums, int target) {
        int n = nums.length;
        int start = findPivotIndex(nums);
        
        int left = 0, right = n - 1;
        while (left < right) {
            int logicalMid = left + (right - left) / 2;
            
            // The Bangle Math: Map logical mid to physical mid
            int physicalMid = (logicalMid + start) % n;
            
            if (nums[physicalMid] >= target) {
                right = logicalMid;
            } else {
                left = logicalMid + 1;
            }
        }
        
        // Final check on the converged pointer
        int physicalLeft = (left + start) % n;
        return nums[physicalLeft] == target ? physicalLeft : -1;
    }
}
```

---

## Alternative: The 1-Pass Solution (Interval Containment)
- **The Core Insight:** This completely abandons our clean `FFFFTTTT` boolean boundary. Instead, it relies on the fact that no matter where you cut a rotated array, **at least one half is always perfectly sorted**.
- **The Execution:** We identify the sorted half first, then check if our `target` fits within its minimum and maximum bounds. If it does, we search that half. If it doesn't, we discard that half entirely.
- **The Unique Elements Advantage:** Because we explicitly check `if (midNum == target)` up front and exit early, we can aggressively discard `mid` on both sides (`left = mid + 1` and `right = mid - 1`). If the array had duplicates, this logic would break down entirely.

```java
class Solution {
    public int search(int[] nums, int target) { 
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            int leftNum = nums[left];
            int midNum = nums[mid];
            int rightNum = nums[right];
            
            // Early exit allows aggressive shrinking
            if (midNum == target) return mid;
            
            // Is the left half strictly sorted?
            if (leftNum <= midNum) {
                // Is the target bounded within this sorted left half?
                if (leftNum <= target && target < midNum) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } 
            // The left half is broken, meaning the right half MUST be sorted
            else if (midNum <= rightNum) {
                // Is the target bounded within this sorted right half?
                if (midNum < target && target <= rightNum) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        // Final check when pointers converge
        return nums[left] == target ? left : -1;
    }
}
```

## Java Utilities & Syntax Traps
- **State Leakage:** Never declare helper variables (like `int n;` or `int jump;`) at the class level outside your methods in competitive programming or LeetCode. The same `Solution` object instance is often reused across multiple test cases. If state leaks between them, tests will fail unpredictably. Always declare them locally inside the method.