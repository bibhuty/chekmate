# [Majority Element II](https://leetcode.com/problems/majority-element-ii/)
**Category:** Arrays / Moore's Voting Algorithm (Extension)

## My Thought Process
- **The Core Idea:** If an element must appear $> N/3$ times, there can be at most **TWO** such elements.
- **The Mental Model (Two Hills):** It's a 3-way Battle Royale. A soldier joins Hill 1, joins Hill 2, claims an empty hill, or throws a grenade that kills one soldier from Hill 1, one from Hill 2, and themselves.

## Key Insights & Traps (My Custom Fixes)
1. **The Order of Operations Trap:** If you check for an empty hill *before* checking if a soldier already belongs to a faction, a soldier might claim an empty hill instead of reinforcing their own (e.g., `[2, 2]` outputting `[2, 2]`).
    - **My Fix:** I added `&& candidate2 != num` when claiming Hill 1. This strictly forbids a soldier from taking an empty hill if they are already the King of the other hill.
2. **The "Phantom Zero" Trap:** Because Java variables default to `0`, an unclaimed candidate sits as a "phantom 0". If the array is `[0, 0, 0]`, both candidates might verify as `0` and double-count.
    - **My Fix:** An unclaimed candidate will end Phase 1 with a `count == 0`. By gating the verification logic behind `if (count1 > 0)`, I force the algorithm to completely ignore phantom candidates.

## Code (My Accepted Approach)
**Time:** $O(N)$ | **Space:** $O(1)$

```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        int count1 = 0, count2 = 0, candidate1 = 0, candidate2 = 0;
        
        // Phase 1: 3-Way Battle Royale
        for (int num : nums) {
            // My Custom Fix: Prevent claiming if already belonging to the other faction
            if (count1 == 0 && candidate2 != num) candidate1 = num;
            else if (count2 == 0 && candidate1 != num) candidate2 = num;

            if (candidate1 == num) ++count1;
            else if (candidate2 == num) ++count2;
            else {
                --count1;
                --count2;
            }
        }

        // Phase 2: Verification
        int n = nums.length, occurence1 = 0, occurence2 = 0;
        for (int num : nums) {
            // My Custom Fix: Ignore phantom candidates that never held a hill
            if (count1 > 0) occurence1 += (candidate1 == num ? 1 : 0);
            if (count2 > 0) occurence2 += (candidate2 == num ? 1 : 0);
        }
        
        List<Integer> result = new ArrayList<>();
        if (occurence1 > n / 3) result.add(candidate1);
        if (occurence2 > n / 3) result.add(candidate2);
        
        return result;
    }
}
```