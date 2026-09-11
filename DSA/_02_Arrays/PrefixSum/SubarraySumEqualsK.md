# [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)
**Category:** Arrays / Prefix Sum + HashMap

## My Thought Process
- **The Core Math:** Instead of searching forward for a subarray that equals `k`, I track the running `sum`. If a valid subarray exists ending at my current index, then the prefix sum just *before* that subarray must equal `sum - k`.
- **The Equation:** `sum = x + k` => `x = sum - k`. We just need to check if `x` exists in our history.
- **The HashMap:** I use a map to store `(PrefixSum, Frequency)` as I iterate. When I calculate `sum - k`, I look up its frequency and add it to my total.

## Key Traps
1. **The Exact Match Trap (`put(0, 1)`):** If the running sum from the very beginning of the array exactly equals `k`, then `sum - k = 0`. If `0` isn't in the map, we miss counting this valid subarray.
    - *Fix:* Always initialize the map with `{0: 1}` to represent the implicit prefix sum of `0` before the array begins.
2. **Negative Numbers:** Sliding windows fail here because negative numbers mean the sum doesn't monotonically increase. Prefix Sums handle negative numbers flawlessly.

## Code (My Approach)
**Time:** $O(N)$ | **Space:** $O(N)$

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        // Map stores (PrefixSum, Frequency)
        Map<Integer, Integer> subarrayIndices = new HashMap<>();
        subarrayIndices.put(0, 1); // Edge case: a subarray starting from index 0 equals k
        
        int sum = 0, total = 0;
        
        for (int num : nums) { 
            sum += num; 
            
            // I assume that till this point the total sum is more than k
            // Let's say I am x more than k. Mathematically: sum = x + k => x = sum - k
            // We need to find the frequency of x in our past prefix sums
            total += subarrayIndices.getOrDefault(sum - k, 0);   
            
            // Log the current prefix sum into the map for future lookups
            subarrayIndices.put(sum, subarrayIndices.getOrDefault(sum, 0) + 1); 
        }
        
        return total;
    }
}
```