# [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
**Category:** Arrays / Binary Search

## My Thought Process
- **The Core Strategy:** Instead of writing one messy loop with complex post-processing to expand outwards, write two hyper-focused, mathematically sound binary searches. One finds the exact left boundary, the other finds the exact right boundary.
- **The First Occurrence (Left Boundary):**
    - Applying the condition `nums[mid] >= target` creates a clean `FFFFTTTT` boundary.
    - When we hit a `True`, we want to keep searching left, so `right = mid`.
    - Because `right` moves down, the standard left-biased `mid = left + (right - left) / 2` works perfectly without causing infinite loops.
- **The Last Occurrence (Right Boundary):**
    - Applying the condition `nums[mid] <= target` creates a `TTTTFFFF` boundary.
    - When we hit a `True`, we want to keep searching right, so `left = mid`.
    - **The Trap:** Because standard integer division truncates down, `left = mid` will cause an infinite loop when `left` and `right` are adjacent (e.g., `(4+5)/2 = 4`).
    - **The Fix:** We *must* right-bias the calculation by adding 1: `mid = left + (right - left + 1) / 2`.

## Code (Optimal)
**Time:** $O(\log N)$ (Two separate $O(\log N)$ passes) | **Space:** $O(1)$

```java
import java.util.List;

public class FirstAndLastPositionInSortedArray {

    // One important question forgot to ask/clarify:
    //  - The array can be empty/not

    // 1 2 3 4 4 4 5 --- 4
    // TC: O(log n) for searching
    // SC: O(1)
    public int[] searchRange(int[] nums, int target){
        return new int[]{binarySearchFirst(nums,target), binarySearchLast(nums,target)};
    }

    // 1 2 3 4 4 4 5 --- 4
    // TC: O(log n) for searching
    // SC: O(1)
    public int binarySearchLast(int[] nums,int target){
        if(nums.length==0) return -1;
        int left=0, right=nums.length-1;
        while(left<right){ // [0,7]->[4,7]->[6,7]->[6,6]
            int mid=left+(right-left+1)/2; // 4 -> 6 -> 7
            if(nums[mid]<=target){ // true->true->false
                left=mid;
            }else{
                right=mid-1;
            }
        }
        return nums[left]==target?left:-1; // 6
    }

    // 1 2 3 4 4 4 5 --- 4
    // TC: O(log n) for searching
    // SC: O(1)
    public int binarySearchFirst(int[] nums, int target){
        if(nums.length==0) return -1;
        int left=0,right=nums.length-1;
        while(left<right){ // [0,7]->[0,3]->[2,3]->[3,3]
            int mid=left+(right-left)/2; // 3 -> 1 -> 2
            if(nums[mid]>=target){ // true->false->false
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return nums[left]==target?left:-1; // 3
    }
}

```

## Java Utilities & Syntax Traps
- **The Empty Array Trap:** If you don't explicitly check `if(nums.length == 0)`, initializing `right = nums.length - 1` results in `right = -1`. When the `while(left < right)` loop is skipped, `nums[left] == target` attempts to access `nums[0]` on an empty array, throwing an `ArrayIndexOutOfBoundsException`.
- **Inline Array Initialization:** `return new int[]{val1, val2};` is the cleanest way to instantiate and return a fixed-size array in one line without declaring a local variable.