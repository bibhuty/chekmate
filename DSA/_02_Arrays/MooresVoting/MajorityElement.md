# [Majority Element](https://leetcode.com/problems/majority-element/)
**Category:** Arrays / Moore's Voting Algorithm

## My Thought Process
- **Core Pattern:** Boyer-Moore Voting Algorithm (Battle Royale / King of the Hill).
- **The Guarantee:** LeetCode explicitly guarantees that the majority element always exists, allowing me to skip Phase 2 (the verification pass).
- **The Ternary Optimization:** Instead of writing out a bulky `if/else` chain, I can streamline the logic. If the hill is empty, I assign the new candidate. Then, using a ternary operator, I evaluate whether to add `1` (reinforcements) or `-1` (mutual destruction). This elegantly handles putting the very first soldier on the hill without needing extra lines of code.

## Code (My Approach)
**Time:** $O(N)$ | **Space:** $O(1)$

```java
package _02_Arrays.MooresVoting;

public class MajorityElement {
    public int majorityElement(int[] nums) {
        int candidate = 0, count = 0;
        
        for (int num : nums) {
            if (count == 0) candidate = num; // The new king claims the hill
            count += (candidate == num ? 1 : -1); // Reinforcements (+1) or Destruction (-1)
        }
        
        return candidate;
    }
}
```