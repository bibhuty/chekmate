# [3Sum](https://leetcode.com/problems/3sum/)
**Category:** Arrays / Sorting + Two Pointers

## My Thought Process
- **The Space Trap:** A standard 2Sum HashMap approach requires $O(N^2)$ space to store pairs, and deduplicating triplets using String keys in a Set destroys performance.
- **The Pivot:** Because the problem asks for *values* (not indices), sorting the array is completely free.
- **The Architecture:**
    1. Sort the array ($O(N \log N)$).
    2. Pin an anchor `i`.
    3. Use Two Pointers (`left = i + 1`, `right = n - 1`) to find pairs that sum to `-nums[i]`.
    4. Natively skip adjacent duplicate numbers to guarantee unique triplets without needing a `HashSet`.

## Code (Optimal Two-Pointer)
**Time:** $O(N^2)$ | **Auxiliary Space:** $O(1)$ | **Total Space:** $O(N^2)$

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; ++i) {
            // Early exit: a solution is never possible if the first number is positive
            if (nums[i] > 0) break; 

            // Skip duplicate anchors to prevent duplicate triplets
            if (i > 0 && nums[i] == nums[i - 1]) continue; 

            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    triplets.add(Arrays.asList(nums[i], nums[left], nums[right])); 
                    
                    // Skip duplicates for the inner pointers
                    while (left + 1 <= right && nums[left + 1] == nums[left]) ++left;
                    while (right - 1 >= left && nums[right - 1] == nums[right]) --right;
                    
                    // Move towards next valid triplet
                    ++left;
                    --right;
                } else if (sum > 0) {
                    --right; // reduce the sum
                } else {
                    ++left; // increase the sum
                }
            }
        }
        return triplets;
    }
}
```

## Code (Follow-Up: Without Sorting)
**Time:** $O(N^2)$ | **Auxiliary Space:** $O(N)$
*Constraint:* If the array is read-only and cannot be sorted, we must use HashSets/HashMaps, eating an $O(N)$ space penalty per loop.

```java
class Solution {
    public List<List<Integer>> threeSumWithoutSorting(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> dups = new HashSet<>();
        Map<Integer, Integer> seen = new HashMap<>();
        
        for (int i = 0; i < nums.length; ++i) {
            // dups.add() is the equivalent operation from sorted approach: if(i>0 && nums[i]==nums[i-1]) continue;
            if (dups.add(nums[i])) { 
                for (int j = i + 1; j < nums.length; ++j) {
                    // x + y + z = 0  =>  x = -y - z (below stores the RHS)
                    int complement = -nums[i] - nums[j];
                    
                    // lookup of the LHS in case it belongs to same loop
                    // similar to restricting calculation inside the 2 pointer bounds
                    if (seen.getOrDefault(complement, -1) == i) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                        
                        // takeaway: how list hashes its value inside set (native deduplication)
                        Collections.sort(triplet); 
                        res.add(triplet);
                    }
                    // marker of value as a part of 2 pointer
                    seen.put(nums[j], i);
                }
            }
        }
        return new ArrayList<>(res);
    }
}
```