# Kadane's Algorithm (Theory)
**Purpose:** Finds the maximum contiguous subarray sum in an array of numbers in $O(N)$ time and $O(1)$ space.

## The Mathematical Foundation
Kadane's Algorithm is fundamentally a dynamic programming approach optimized for space. At every index `i`, you must answer one question:
**"Does the element at `i` benefit from joining the previous sequence, or is it better off starting a new sequence on its own?"**

The algorithm relies on this recurrence relation:
`LocalMax[i] = max(nums[i], nums[i] + LocalMax[i-1])`

## The Two Core Concepts
1. **The "Toxic Baggage" Rule:** If the running sum of a sequence becomes negative, it becomes a liability. Mathematically, adding a negative running sum to the next element `nums[i]` will yield a result strictly smaller than `nums[i]` by itself. When this happens, the algorithm naturally "cuts the cord," discards the negative prefix, and starts a fresh sum at `nums[i]`.
2. **Local vs. Global Record:** Because we are constantly discarding sequences and resetting our `LocalMax` when they go negative, we need a separate `GlobalMax` variable. At every step, we check if our current `LocalMax` beats the all-time `GlobalMax` high score.

## The "All-Negative" Edge Case
If an array is entirely negative (e.g., `[-3, -5, -2]`), resetting a negative sum to `0` would yield a final answer of `0`—which is incorrect because `0` isn't in the array.
**The theoretical fix:** Initialize both `LocalMax` and `GlobalMax` using the first element (`nums[0]`) rather than `0`. This ensures that in an all-negative array, the algorithm correctly returns the least-negative number.