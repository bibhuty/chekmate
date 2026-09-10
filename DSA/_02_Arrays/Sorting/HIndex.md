# [H-Index](https://leetcode.com/problems/h-index/)
**Category:** Arrays / Bucket Sort (Not DNF!)

## My Thought Process & Assumptions
- **The Core Constraint:** A researcher's H-Index can never be larger than their total number of papers ($N$).
- **The Bucket Approach:** Instead of an $O(N \log N)$ sort, we can use an array to count the frequencies of citations. Any paper with citations $\ge N$ just gets dumped into the final bucket $N$.
- **Iterating Backwards:** By scanning the buckets from $N$ down to $0$ and keeping a running total of papers, the first index $i$ where `total >= i` is guaranteed to be the maximum H-Index.

## Key Insights & Traps (My Mistakes)
1. **The Size Trap (`n + 1`):** I initially missed that the bucket array needs to be size `n + 1`. If $N = 5$, the maximum possible H-index is 5. To have an index `5`, the array must be size 6.
2. **Over-complicating the return:** I initially used `Math.max` and `Math.min` to track the result. But since I am walking backwards from the best possible scenario ($N$), the very first time the condition is met, that index *is* the optimal answer. I can just return it directly.

## Code (Optimal Bucket Sort)
**Time:** $O(N)$ | **Space:** $O(N)$

```java
package _02_Arrays.BucketSort;

public class HIndex {
    public int hIndex(int[] citations) {
        int n = citations.length;
        
        // Trap avoided: n + 1 so we can store up to index 'n'
        int[] papers = new int[n + 1]; 
        
        for (int citation : citations) {
            papers[Math.min(n, citation)]++;
        }
        
        int total = 0;
        // Walk backwards from the highest possible H-Index
        for (int i = n; i >= 0; --i) {
            total += papers[i]; 
            
            // The first time we have at least 'i' papers with 'i' citations
            if (total >= i) {
                return i;
            }
        }
        
        return 0;
    }
}
```