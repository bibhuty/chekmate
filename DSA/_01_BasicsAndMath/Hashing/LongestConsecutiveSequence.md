# [Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)
**Category:** Basic Hashing

## 1. Interview Checklist & Edge Cases
**Questions to ask the interviewer:**
- Do you consider duplicates (e.g., `1, 1`) as consecutive? *(Usually no, a sequence of `1, 1, 2` has a consecutive length of 2: `[1, 2]`)*.
- Can the length of the array be `0`?

**The Constraints Trap:**
- Sorting takes `O(N log N)`. If the interviewer demands strictly `O(N)` time, a Hash Table (specifically a `HashSet`) is mandatory.

## 2. Optimal Solution (HashSet)
**Time:** `O(N)` | **Space:** `O(N)`

**The Core Logic:** We only care about a number if it is the *absolute start* of a sequence. A number `num` is a start if `num - 1` does not exist in our set.

**🚨 The Duplicate Loop Trap (TLE):** If you deduplicate an array into a `HashSet`, you **must iterate over the Set**, NOT the original array (`for (int num : set)`). Iterating over the array causes redundant processing of duplicates. On an input like `[1, 1, 1... (50,000 times), 2, 3...]`, iterating over `nums` triggers the `while` loop 50,000 times for the same sequence, degrading an `O(N)` algorithm to `O(N^2)` and causing a TLE.

```java
public int lcsWithHashSet(int[] nums) {
    var set = new HashSet<Integer>();
    for (int num : nums) set.add(num);
    
    int max = 0;
    
    // CRITICAL: Iterate over 'set', not 'nums'
    // for(int num:nums){ -> Recipe for TLE [1,1... half array, 2,3... second half] -> O(N^2)
    for (int num : set) {
        // Only start counting if this is the BEGINNING of a sequence
        if (!set.contains(num - 1)) {
            int key = num;
            while (set.contains(key)) {
                ++key;
            }
            // Smart length calculation: ending number minus starting number
            max = Math.max(max, key - num);
        }
    }
    return max;
}
```
*Note on Time Complexity:* The nested `while` loop only executes when we find the start of a sequence. Because we strictly count forward, the inner loop processes each number in a sequence exactly once globally. `O(N)` to build + `O(N)` to traverse = `O(N)`.

## 3. The State Machine Approach (Alternative)
Using two HashMaps to track the "next" and "prev" boundaries of sequences. Conceptually `O(N)`. It runs slightly slower than the HashSet approach in milliseconds due to Java Object boxing (`Integer` and `Boolean`), but passes perfectly once the duplicate loop trap is fixed. *Great mental model for state-tracking.*

```java
public int lcs(int[] nums) {
    var next = new HashMap<Integer, Boolean>();
    var prev = new HashMap<Integer, Boolean>();

    for (int num : nums) {
        if (prev.containsKey(num)) continue;

        prev.putIfAbsent(num, false);
        next.putIfAbsent(num, false);

        // Link neighbors
        if (prev.containsKey(num + 1)) {
            prev.put(num + 1, true);
            next.put(num, true);
        }
        if (next.containsKey(num - 1)) {
            next.put(num - 1, true);
            prev.put(num, true);
        }
    }

    int maxLength = 0;

    // CRITICAL FIX: Iterate over prev.keySet(), NOT nums.
    // Iterating over nums triggers O(N^2) TLE on duplicates.
    for (int key : prev.keySet()) {
        // If it's a valid start (no previous elements connected)
        if (!prev.get(key)) {
            int counter = 0;
            int current = key;
            while (next.containsKey(current)) {
                ++current;
                ++counter;
            }
            maxLength = Math.max(maxLength, counter);
        }
    }
    return maxLength;
}
```

## 4. Interview Follow-Up: Return the Sequence
**The Question:** "Instead of returning the length (e.g., `4`), return the actual sequence itself (e.g., `[1, 2, 3, 4]`)."

**The Optimization:**
Do not create a new `ArrayList` every time you find a longer sequence inside the loop—that triggers constant Garbage Collection. Instead, use `O(1)` extra space during your loop to simply track `bestStart`, and build the list exactly *once* at the very end.

```java
// Inside the loop:
int currentLength = key - num;
if (currentLength > max) {
    max = currentLength;
    bestStart = num; // Lock in the starting number of the winning sequence
}

// After the loop, build the result exactly once:
List<Integer> result = new ArrayList<>();
for (int i = 0; i < max; i++) {
    result.add(bestStart + i);
}
return result;
```
*Note:* The overall SC remains `O(N)` because of the `HashSet` and the result array, but this avoids allocating redundant, intermediate lists during traversal.

## 5. Bonus Optimal: Destructive Traversal
You can still iterate over the original `nums` array and avoid the `O(N^2)` TLE by removing elements from the set as you process them. This guarantees that every number is visited and processed exactly once.

**🚨 The Java Modification Trap:** If you try to remove elements while iterating over the `set` itself (`for (int num : set)`), Java will throw a `ConcurrentModificationException`. Iterating over the array decouples the loop from the set being modified.

```java
public int longestConsecutiveDestructive(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);
    
    int max = 0;
    
    // Iterate over the array, NOT the set, so we can safely modify the set
    for (int num : nums) {
        // Only start if it's the beginning of a sequence
        if (!set.contains(num - 1)) {
            int key = num;
            
            while (set.contains(key)) {
                // Remove it so duplicates in 'nums' instantly fail the while loop later
                set.remove(key); 
                ++key;
            }
            max = Math.max(max, key - num);
        }
    }
    return max;
}
```