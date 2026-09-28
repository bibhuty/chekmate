# 📐 Pattern Note: 1D Sweep Line Algorithm

### 1. Core Mental Model & Formal Definition
A **Sweep Line Algorithm** solves interval overlap and scheduling problems by passing an imaginary vertical line across a 1D timeline. Instead of checking every continuous point on the axis, the line **jumps strictly from discrete "event point" to "event point"**, updating a running state only where changes occur.

```text
Input:  {{2, 6}, {1, 4}, {4, 7}, {8, 10}}
Events: [[1, +1], [2, +1], [4, -1], [4, +1], [6, -1], [7, -1], [8, +1], [10, -1]]

Timeline (Half-Open [a, b)):
1--4   |
-2---6 |
---4--7|
-------|8-10
```

Every Sweep Line implementation has three formal components:
1. **The Event Queue (Where the line stops):** Each interval `[start, end]` is decomposed into two discrete `[coordinate, delta]` events:
    * `start` $\rightarrow$ `[start, +1]` (an interval begins intersecting the line).
    * `end` $\rightarrow$ `[end, -1]` (an interval stops intersecting the line).
2. **The Sweep Status (What the line remembers right now):** `int active` (or `events`) — the running count of intervals currently intersecting the sweep line.
3. **The Sweep (Moving the line forward):** Iterating chronologically through sorted events, updating `active += delta`, and querying `maxActive = Math.max(maxActive, active)`.

---

### 2. The Boundary Tie-Breaker Rule: Half-Open `[a, b)` vs. Closed `[a, b]`

When one interval ends at timestamp `t` (`[1, 4]`) and another starts at the exact same timestamp `t` (`[4, 7]`), how we
handle coordinate `4` determines whether touching boundaries count as overlapping:

| Interval Type                | Real-World Example                                            | Touching Bounds Overlap?                   | Event List Comparator Tie-Breaker                  | Diff Array / `TreeMap` End Index                                   |
|:-----------------------------|:--------------------------------------------------------------|:-------------------------------------------|:---------------------------------------------------|:-------------------------------------------------------------------|
| **Half-Open `[start, end)`** | *Meeting Rooms II* (`[1, 4)` frees room at `4`)               | **No** (End before Start: `-1` then `+1`)  | `Integer.compare(a[1], b[1])` *(Ascending delta)*  | Subtract at **`end`** (`-1` and `+1` cancel to `0` at `t`)         |
| **Closed `[start, end]`**    | *Minimum Platforms* (Train occupies track through minute `4`) | **Yes** (Start before End: `+1` then `-1`) | `Integer.compare(b[1], a[1])` *(Descending delta)* | Subtract at **`end + 1`** (Resource frees up one tick after `end`) |

---

### 3. The 3 Implementation Templates

#### Approach 1: Event List Sorting (`sortedLineSweep` — Universal Default)
* **When to use:** Default choice for static array inputs. Handles negative timestamps, `Integer.MIN_VALUE`, and $10^9$ ranges with zero index math.
* **Complexity:** **TC:** $O(N \log N)$ sorting + $O(N)$ traversal | **SC:** $O(N)$ auxiliary space for sorting and event storage.

```java
// Assumption: [a,b)
// TC: O(n log n) time for sorting + O(n) for traversal in 2 loops
// SC: O(n) auxiliary space for sorting + O(n) auxiliary space for event storage
int sortedLineSweep(int[][] logs){
    int n=logs.length;
    var events=new ArrayList<int[]>(2*n);
    for(int i=0;i<n;++i){
        events.add(new int[]{logs[i][0],1});
        events.add(new int[]{logs[i][1],-1});
    }
    Collections.sort(events,(a, b) -> a[0] != b[0]
            ? Integer.compare(a[0], b[0])   // Rule 1: Sort by timestamp (a[0] vs b[0])
            : Integer.compare(a[1], b[1])); // Rule 2: Tie-breaker on delta (a[1] vs b[1])
    int active=0,maxActive=0;
    for(int[] event:events){
        // System.out.println(Arrays.toString(event));
        active+=event[1];
        maxActive=Math.max(maxActive,active);
    }
    return maxActive;
}
```

#### Approach 2: Difference Array / Prefix Sum Sweep (`maxConcurrentBounded` — Bounded Ranges Only)
* **When to use:** **ONLY** when timestamps are small and strictly bounded by problem constraints (e.g., 24-hour clock `0000..2359` or $0 \le T \le 10^5$).
* **When to AVOID:** Avoid generalizing with `min`/`max` offset math (`log[0] - min`) in interviews—high risk of off-by-one errors, negative index bugs, and `OutOfMemoryError` if `max - min` exceeds $\sim 5 \times 10^7$.
* **Complexity:** **TC:** $O(N)$ traversal + $O(\max - \min)$ timeline sweep | **SC:** $O(\max - \min)$ timeline storage.

```java
// TC: O(n) traversal + O(max value - min value) for timeline traversal
// SC: O(max value - min value) for timeline storage
int maxConcurrentBounded(int[][] logs){

    if(logs.length==0) return 0;

    int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
    for(int[] log:logs){
        max=Math.max(max,log[1]);
        min=Math.min(min,log[0]);
    }
    // int[] timeline=new int[max-min+1]; // <- [a,b) <- exclusive
    // int[] timeline=new int[max-min+2]; // <- [a,b] <- inclusive
    int[] timeline=new int[max-min+2]; // <- safe side(will overflow if the length of array is 0)

    for(int[] log: logs){
        timeline[log[0]-min]+=1;
        timeline[log[1]-min]-=1; // Use timeline[log[1]-min+1]-=1 for inclusive [a,b]
    }

    int events=0, maxEvents=0;
    for(int delta:timeline){
        events+=delta;
        maxEvents=Math.max(events,maxEvents);
    }
    return maxEvents;
}
```

#### Approach 3: `TreeMap` Compressed Timeline (`maxConcurrentWithTreeMap` — Dynamic Streams & Huge Coordinates)
* **When to use:** When coordinates are huge ($10^9$) or negative, **or** when intervals arrive dynamically in a stream (e.g., *My Calendar I / II / III*). Replaces manual sorting with Red-Black Tree sorted insertion and collapses duplicate timestamps on the fly.
* **Complexity:** **TC:** $O(N \log N)$ insertion into Red-Black Tree + $O(N)$ traversal | **SC:** $O(N)$ Red-Black Tree storage.

```java
// TC: O(n log n) insertion into red-black tree + O(n) traversal
// SC: O(n) red black tree
int maxConcurrentWithTreeMap(int[][] logs){
    var timeline=new TreeMap<Integer,Integer>();
    for(int[] log:logs){
        int start=log[0],end=log[1];
        timeline.merge(start,1,Integer::sum);
        timeline.merge(end,-1,Integer::sum); // Use (end + 1) for inclusive [a,b]
    }
    int active=0,maxActive=0;
    for(int delta:timeline.values()){
        active+=delta;
        maxActive=Math.max(maxActive,active);
    }
    return maxActive;
}
```
