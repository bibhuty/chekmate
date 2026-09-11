# Boyer-Moore Voting Algorithm (Theory)
**Purpose:** Find the majority element (an element that appears strictly $> N / 2$ times) in $O(N)$ time with $O(1)$ space.

## The Core Concept: Battle Royale (King of the Hill)
Think of every number as a soldier from a specific faction (e.g., all `7`s belong to faction 7).
1. **Empty Hill:** If the hill is empty (`count == 0`), the current soldier claims it and becomes the `candidate`.
2. **Reinforcements:** If a soldier from the *same* faction arrives, they join the hill (`+1`).
3. **Mutual Destruction:** If a soldier from a *different* faction arrives, they fight to the death and both die (`-1`).

**Why it works:** Because the majority faction has more soldiers than *all other factions combined*, they will mathematically always have at least one survivor left standing after all mutual destruction is complete.

## The Two Phases
1. **Phase 1 (The War):** Run the algorithm to find the surviving `candidate`.
2. **Phase 2 (The Verification):** The battle royale *only* works if a majority element actually exists. If a problem doesn't guarantee one (e.g., `[1, 2, 3]`), the algorithm will still spit out a fake winner (in this case, `3`). You must do a second pass to count the survivor's true occurrences.

## Key Traps & Optimizations
1. **The Missing Soldier Trap (`count == 0`):** When a new candidate takes an empty hill, you **must** increment the count. If you just assign `candidate = num`, the hill remains empty and is instantly lost.
    - *Optimization:* By using the ternary operator `count += (candidate == num ? 1 : -1);` *after* assigning the candidate, the very first soldier automatically triggers the `+1` condition, completely bypassing this trap in a single line of code.
2. **The Even-Number Math Trap:** Checking `count >= (n + 1) / 2` fails on even-length arrays with no majority (e.g., `[1, 1, 2, 2]`). For `n = 4`, the math yields `2`, and the max count is `2`, making it falsely look like a majority.
    - *Fix:* A majority must be strictly greater than half. Use clean integer division: `count > n / 2`.

## The Core Algorithm (Ternary Optimized)
```java
public class BoyerMooresVotingAlgorithm {
    public Integer findMajorityElement(int[] nums) {
        int candidate = 0, count = 0, n = nums.length;
        
        // Phase 1: The Battle Royale
        for (int num : nums) {
            if (count == 0) candidate = num;
            count += (candidate == num ? 1 : -1);
        }
        
        // Phase 2: Verification
        count = 0;
        for (int num : nums) {
            if (candidate == num) ++count;
        }
        
        return count > n / 2 ? candidate : null;
    }
}
```

## Extending the Algorithm: Occurrences > N / k
The standard algorithm finds $> N / 2$, but the math scales cleanly to find elements occurring strictly $> N / k$ times (e.g., $N / 3$, $N / 4$).

**The Core Rule:** If an element must appear $> N / k$ times, there can be at most **$k - 1$** valid majority elements.
* $> N / 2$ means at most **1** winner.
* $> N / 3$ means at most **2** winners.
* $> N / 4$ means at most **3** winners.

**The Scaled Battle Royale:**
Instead of tracking 1 King of the Hill, you track $k - 1$ hills (e.g., `candidate1`, `candidate2` and `count1`, `count2`).
1. **Reinforce:** If a soldier matches an existing candidate, they join that hill (`++count`).
2. **Claim:** If they don't match, they claim the first empty hill they find (`count == 0`). *(Trap: Ensure they don't claim an empty hill if they already rule another!)*
3. **k-Way Mutual Destruction:** If a soldier arrives, doesn't match any existing faction, and *all* $k - 1$ hills are occupied, they throw a grenade. The current soldier dies, and **one soldier from every single hill dies** (decrement all $k - 1$ counts). This perfectly eliminates distinct groups of $k$ elements.

**Phase 2 remains mandatory:** You must manually count the occurrences of all $k - 1$ surviving candidates to verify which ones actually cross the $> N / k$ threshold.