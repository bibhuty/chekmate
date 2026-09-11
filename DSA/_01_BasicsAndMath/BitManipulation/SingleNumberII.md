# [Single Number II](https://leetcode.com/problems/single-number-ii/)
**Category:** Bit Manipulation (M1)

## The Core Concept: Modulo 3
Since every number except one appears exactly 3 times, if we count the total number of `1`s at each bit position across the entire array, the sum at each position *must* be a multiple of 3. If a sum is not a multiple of 3, it means our "single number" has a `1` at that exact bit position.

---

## 1. The "Manual Sign Management" Approach (Mathematically Sound, but Overcomplicated)
**Time:** $O(N)$ | **Space:** $O(1)$ (Technically constant, but uses a 64-length array)

**The Logic:** If you manually separate positive and negative numbers, you must use `long` variables to prevent `Integer.MIN_VALUE` overflow when calling `Math.abs()`, and you must use `long` when doing manual power-of-2 math (`current *= 2`) to avoid silent overflow at the 31st bit.

While logically sound, this leads to overcomplicated code, unnecessary array allocations, and constant type-casting.

```java
public int singleNumber(int[] nums) {
    int[] bitVal = new int[64]; // 0-31 for positive, 32-63 for negative

    for (int num : nums) {
        int offset = num < 0 ? 32 : 0;
        long currentAbs = Math.abs((long) num);

        for (int i = 0; i < 32 && currentAbs != 0; ++i) {
            if ((currentAbs & 1) != 0) ++bitVal[offset + i];
            currentAbs >>= 1;
        }
    }

    // CRITICAL: Must use long to prevent current *= 2 from overflowing at 2^31
    long current = 1, positive = 0, negative = 0;
    for (int i = 0; i < 32; ++i) {
        if (bitVal[i] % 3 != 0) positive += current;
        if (bitVal[32 + i] % 3 != 0) negative -= current;
        current *= 2;
    }

    return (int) (positive + negative);
}
```

---

## 2. The Clean Bitwise Approach (Trusting Two's Complement)
**Time:** $O(32N) \rightarrow O(N)$ | **Space:** $O(1)$ (Pure constant space)

**The Fix:** Java uses Two's Complement, meaning the 31st bit is the sign bit. We do not need to separate positive/negative numbers or use `long`. If we loop exactly 32 times and apply bitwise operators across all numbers, the sign bit is counted just like any other bit. When we reconstruct the result using `|`, setting the 31st bit automatically makes the resulting `int` negative natively. `1 << i` safely handles bit placement without arithmetic overflow.

```java
public int singleNumber(int[] nums) {
    int result = 0;

    // Iterate through all 32 bit positions (including the sign bit)
    for (int i = 0; i < 32; ++i) {
        int count = 0;

        // Count how many numbers have the i-th bit set
        for (int num : nums) {
            if ((num & (1 << i)) != 0) {
                ++count;
            }
        }

        // If count is not a multiple of 3, the single number has this bit set
        if (count % 3 != 0) {
            // Reconstruct the number bit by bit using bitwise OR
            result |= (1 << i);
        }
    }

    return result;
}
```

---

## 3. The Ultimate Optimization: Logic Gates ("Ones and Twos")
**Time:** $O(N)$ (True single pass) | **Space:** $O(1)$

**The Concept:** Instead of looking at bits position-by-position, we process the entire 32-bit integer simultaneously using a state machine. We track bits that have appeared exactly once in a variable `ones`, and bits that have appeared exactly twice in a variable `twos`. When a bit appears a third time, we clear it out entirely.

```java
public int singleNumber(int[] nums) {
    int ones = 0;
    int twos = 0;
    
    for (int num : nums) {
        // Step 1: Add to 'ones' ONLY if it's not already in 'twos'.
        // (ones ^ num) adds the bit. (& ~twos) blocks it if we've seen it twice.
        ones = (ones ^ num) & ~twos;
        
        // Step 2: Add to 'twos' ONLY if it's not already in 'ones'.
        // (twos ^ num) adds the bit. (& ~ones) blocks it if we just saw it for the first time.
        twos = (twos ^ num) & ~ones;
    }
    
    // The bits that appeared exactly once will be left perfectly inside 'ones'.
    return ones;
}
```