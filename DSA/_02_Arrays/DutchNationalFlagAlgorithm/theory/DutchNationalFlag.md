# Dutch National Flag Algorithm (Theory)
**Purpose:** Sort an array of 3 distinct elements (e.g., 0s, 1s, and 2s) in a single $O(N)$ pass with $O(1)$ space.

## The Core Concept: Three Pointers, Four Zones
The algorithm uses three pointers (`low`, `mid`, `high`) to divide the array into four conceptual zones as it processes:
1.  **`< low`:** Strictly `0`s.
2.  **`low` to `mid - 1`:** Strictly `1`s.
3.  **`mid` to `high`:** Unknown territory.
4.  **`> high`:** Strictly `2`s.

The algorithm terminates the exact moment the explorer pointer (`mid`) crosses the back boundary (`high`).

## The Three Rules of the Explorer (`mid`)
As `mid` walks through the array, it evaluates `nums[mid]`:
*   **Case 0 (Found a 0):** Belongs at the front. Swap `nums[mid]` with `nums[low]`. Move BOTH `low` and `mid` forward.
*   **Case 1 (Found a 1):** Belongs in the middle. Already in place. Just move `mid` forward.
*   **Case 2 (Found a 2):** Belongs at the back. Swap `nums[mid]` with `nums[high]`. Move `high` backward.

## Key Traps
1. **The `mid` Pointer Trap on 2s:** When swapping a `2` to the back (`nums[mid] = nums[high]`), you **cannot** increment `mid`. The number you just pulled from the `high` index is completely un-evaluated. If you increment `mid`, you skip checking it entirely.
2. **The Loop Condition (`mid <= high`):** It must be `<=` and not `<`. If `mid == high`, that specific element still needs to be evaluated and potentially swapped before guaranteeing the array is sorted.

## The Core Algorithm
```java
public void dutchNationalFlag(int[] nums) {
    int n = nums.length, low = 0, mid = 0, high = n - 1;
    while (mid <= high) { 
        int current = nums[mid]; 
        
        if (current == 0) {
            nums[mid++] = nums[low];
            nums[low++] = current;
        } else if (current == 2) {
            nums[mid] = nums[high];
            nums[high--] = current;
        } else {
            ++mid;
        }
    }
}