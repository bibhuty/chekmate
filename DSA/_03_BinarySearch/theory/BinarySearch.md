# 📐 Binary Search Theory

### The Core Mental Model: The Boolean Boundary
Binary Search does not require a sorted array of numbers. It requires **Monotonicity**—a sequence of states that transition exactly once (e.g., `FFFFTTTT`).

If your condition transitions twice (like `FFTFF`), binary search is impossible because if your pointer lands on a `F`, it doesn't know if the `T` is to the left or the right.

**The "Sorted Array" Epiphany:**
How do we search for the number `7` in `[2, 4, 6, 7, 9, 10]`?
* **The Trap:** If we ask the naive question, *"Is `x == 7`?"*, we get `[False, False, False, True, False, False]`. This breaks the rule! It transitions twice.
* **The Fix:** We change the question to leverage the sorted order. We ask: *"Is `x >= 7`?"*
* **The Result:** `[False, False, False, True, True, True]`.

Because the array is sorted, we have a mathematical guarantee: once a number is `>= 7`, every subsequent number is also `>= 7`. This creates a perfect, single-transition boundary.

**The Goal:** Binary search is simply an algorithm to find that **Boundary** (the *First True*). Once we find the first number that is `>= 7`, we just do a quick post-check: *"Is it exactly 7?"*

---

### The "Boring Elegance" Universal Template
To avoid infinite loops and off-by-one bugs, pick **one** template and use it for everything. The `left < right` template is the most mathematically sound because it shrinks the search space until exactly one element remains (`left == right`), eliminating the need to guess which pointer holds the answer.

```java
public int binarySearch(int[] arr) {
    int left = 0;
    int right = arr.length - 1; // Or the maximum possible answer space
    
    while (left < right) {
        // 1. Calculate mid (safely avoiding integer overflow)
        int mid = left + (right - left) / 2; 
        
        // 2. Evaluate the Predicate Condition
        if (condition(mid)) {
            // We found a True. The boundary could be here, or to the left.
            // We must KEEP mid in the search space.
            right = mid; 
        } else {
            // We found a False. The boundary is strictly to the right.
            // mid is useless, so we aggressively discard it.
            left = mid + 1; 
        }
    }
    
    // Post-processing: left and right converged to the exact same spot.
    // Just verify if the converged spot actually satisfies the condition.
    return condition(left) ? left : -1; 
}
```

---

### The Physics of the Infinite Loop (And How to Avoid It)
Why do candidates get stuck in infinite loops? Because of how integer division works in Java/C++.

Whether you use `mid = (left + right) / 2` or the overflow-safe `mid = left + (right - left) / 2`, division always **truncates down**.
* If `left = 4` and `right = 5`, let's look at the math:
* `(right - left) / 2` becomes `1 / 2`.
* Because it's integer division, the fraction is dropped: `1 / 2 = 0`.
* `mid = 4 + 0`, so `mid` becomes `4`.
* Therefore, `mid` is permanently biased towards `left`.

**The Ultimate Sanity Check:**
To guarantee your loop terminates, the search space must shrink on every iteration.
* If your logic requires `left = mid`, you must ensure **`mid != left`** (which requires adding `+1` to right-bias the calculation).
* If your logic requires `right = mid`, you must ensure **`mid != right`** (which standard left-bias naturally guarantees).

If you assign a pointer to the exact same index it is currently sitting on (e.g., `left = 4` when `left` is already `4`), you are trapped forever.

* **Safe (Flavor 1):** `left = mid + 1` and `right = mid`
* **Safe (Flavor 2):** `left = mid` and `right = mid - 1`
