# [Single Number III](https://leetcode.com/problems/single-number-iii/)
**Category:** Bit Manipulation (M2)

## The Core Concept: The Difference Detector
When exactly two numbers (`a` and `b`) appear once, XORing the entire array leaves us with `a ^ b`. Because `a` and `b` are different, this resulting XOR will have at least one bit set to `1`.

That `1` is our **Difference Detector**—it represents a bit position where `a` and `b` differ. By isolating that specific bit, we can split the array into two buckets: numbers that have a `1` at that position, and numbers that have a `0`. The duplicates will cancel each other out in their respective buckets, leaving exactly `a` in one bucket and `b` in the other.

## Optimal Solution: Two-Pass Bit Masking
**Time:** $O(N)$ | **Space:** $O(1)$

**The Shortcut:** Instead of looping to find the first set bit, we use the Two's Complement hack `x & (-x)` to instantly isolate the rightmost set bit in $O(1)$ time.

```java
public class Solution {
    // Trace: [1(01), 2(10), 3(11), 3(11), 4(100), 4(100)]
    // aXORb = 1 ^ 2 = 3 (011)
    // firstSetBit = 1
    // setBucket = 1 (01), unsetBucket = 2 (10)
    public int[] singleNumber(int[] nums) {
        int aXORb = 0;
        for (int num : nums) {
            aXORb ^= num;
        }
        
        // Old way: Loop 32 times to find the first bit
        // int setBitPos = 0;
        // while(setBitPos < 32){
        //    if((aXORb & (1 << setBitPos)) != 0) break;
        //    ++setBitPos;
        // }
        // int firstSetBit = 1 << setBitPos;
        
        // The Hacker Way: Isolate the lowest set bit instantly
        // Example using 10 [1010]:
        // -10 = (~10 + 1) -> [0101 + 1] = [0110]
        // 10 & -10 = [1010 & 0110] = [0010]
        int firstSetBit = aXORb & (-aXORb);
        
        int setBucket = 0;
        int unsetBucket = 0;
        
        // Split the array into two buckets based on that single bit
        for (int num : nums) {
            if ((num & firstSetBit) != 0) {
                setBucket ^= num;
            } else {
                unsetBucket ^= num;
            }
        }
        
        return new int[]{setBucket, unsetBucket};
    }
}
```