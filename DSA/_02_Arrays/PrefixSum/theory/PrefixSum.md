### The Prefix "Dummy Zero" Rule
Whenever you use Prefix Sums, you **must** explicitly represent the state *before* the array started (the sum of zero elements).
- **In an Array:** Make the array size `N + 1` and let `prefix[0] = 0`.
- **In a HashMap:** Always initialize the map with `{0: 1}` (meaning: "a sum of 0 has occurred 1 time before we even started iterating").
- **Why:** This prevents OutOfBounds/Null errors when a valid subarray starts exactly at index `0`. If your formula evaluates to `sum - k == 0`, the Dummy Zero catches it. It is the Prefix equivalent of a Linked List Dummy Head.