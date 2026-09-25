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

### Why 3Sum Output Space is $O(N^2)$ (Mathematical Intuition)

To understand why the maximum number of valid, unique triplets in an array of size $N$ is bounded at $O(N^2)$, we can model the worst-case scenario using an Arithmetic Progression (AP).

**1. The Worst-Case Array Setup**
Imagine a perfectly balanced, sequential array centered around zero:
`[-K, -(K-1), ..., -2, -1, 0, 1, 2, ..., K-1, K]`
The total length of this array is $N = 2K + 1$.
Therefore, the maximum value $K \approx \frac{N}{2}$.

**2. Counting Valid Pairs for a Single Anchor**
If we pin a single negative anchor, say $-X$, we need to find two distinct positive numbers, $Y$ and $Z$, that sum to $X$ (so $-X + Y + Z = 0$).
*   If $X = 10$, the pairs are $(1, 9), (2, 8), (3, 7), (4, 6)$.
*   For any arbitrary anchor $X$, there are roughly $\frac{X}{2}$ valid pairs.

**3. Summing Across All Anchors (The AP)**
To find the total possible triplets, we sum the valid pairs for every negative anchor from $X = 1$ up to $K$:
$$\text{Total Triplets} \approx \sum_{X=1}^{K} \frac{X}{2}$$

We can pull out the constant $\frac{1}{2}$ to reveal the standard Arithmetic Progression sum formula:
$$\frac{1}{2} \sum_{X=1}^{K} X = \frac{1}{2} \left( \frac{K(K+1)}{2} \right) \approx \frac{K^2}{4}$$

**4. Translating Back to Big-O ($N$)**
Since $K \approx \frac{N}{2}$, we substitute $K$ back into the formula:
$$\text{Total Triplets} \approx \frac{(\frac{N}{2})^2}{4} = \frac{\frac{N^2}{4}}{4} = \frac{N^2}{16}$$

**The Verdict:**
In Big-O notation, we drop the constant divisor ($16$). The mathematical ceiling for the number of unique triplets that can be generated from an array of size $N$ is exactly **$O(N^2)$**.

### Visualizing the $O(N^2)$ Growth

Let's build a small, perfectly balanced array without zero to strictly visualize the mathematical proof (where we pin a negative anchor $-X$ and find two positive numbers $Y$ and $Z$).

**Array:** `[-6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6]`  
Here, $N = 12$, and our maximum value $K = 6$.

Let's count how many valid pairs we can find for each negative anchor <br/>
**NOTE**: is the anchor is positive then all the numbers after the same is positive

*   **Anchor `-6` (Needs sum of +6):**
    Pairs: `(1, 5)`, `(2, 4)` $\rightarrow$ **2 triplets**
*   **Anchor `-5` (Needs sum of +5):**
    Pairs: `(1, 4)`, `(2, 3)` $\rightarrow$ **2 triplets**
*   **Anchor `-4` (Needs sum of +4):**
    Pairs: `(1, 3)` $\rightarrow$ **1 triplet**
*   **Anchor `-3` (Needs sum of +3):**
    Pairs: `(1, 2)` $\rightarrow$ **1 triplet**
*   **Anchor `-2` (Needs sum of +2):**
    No distinct positive integers sum to 2 $\rightarrow$ **0 triplets**
*   **Anchor `-1` (Needs sum of +1):**
    No distinct positive integers sum to 1 $\rightarrow$ **0 triplets**

**The Progression:**
Look at the number of valid triplets generated as the anchor grows: `0, 0, 1, 1, 2, 2`.

If we expanded $K$ to 10 (array size 20), the sequence of triplets generated per anchor would be:
`0, 0, 1, 1, 2, 2, 3, 3, 4, 4`

This creates a distinct "staircase" pattern. Every time you increase the size of the array, you are adding consecutive integers to this sequence (an Arithmetic Progression).

Because the sum of an arithmetic sequence $1 + 2 + 3 + ... + M$ evaluates to $\frac{M(M+1)}{2}$ (which is $O(M^2)$), the total number of triplets inherently grows at a quadratic $O(N^2)$ rate relative to the size of the array.