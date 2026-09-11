# Bit Manipulation: Core Concepts & Cheat Sheet

## 1. The Core Operators
- `&` **(AND):** `1` if both bits are `1`. (Used for masking/clearing).
- `|` **(OR):** `1` if either bit is `1`. (Used for setting bits).
- `^` **(XOR):** `1` if bits are different. (The MVP of bit problems).
- `~` **(NOT):** Flips all bits (`0` $\rightarrow$ `1`, `1` $\rightarrow$ `0`).
- `<<` **(Left Shift):** `x << 1` multiplies by 2.
- `>>` **(Signed Right Shift):** `x >> 1` divides by 2 (preserves sign bit).
- `>>>` **(Unsigned Right Shift):** Java-specific. Shifts right and forces the new left bit to be `0` (crucial for negative numbers).

## 2. The Golden Rules of XOR (`^`)
1. **Identity:** `x ^ 0 = x`
2. **Self-Inverse (Cancellation):** `x ^ x = 0` (Any number XORed with itself is `0`).
3. **Commutative/Associative:** Order doesn't matter. `a ^ b ^ a = b`.

## 3. The 6 Essential Bit Hacks
These cover 100% of the operational requirements for standard bit manipulation problems.

### Bitwise CRUD (Targeting the $i$-th bit)
1. **Check if $i$-th bit is set:** `(x & (1 << i)) != 0`
2. **Set the $i$-th bit to 1:** `x | (1 << i)`
3. **Clear the $i$-th bit to 0:** `x & ~(1 << i)`
4. **Toggle the $i$-th bit:** `x ^ (1 << i)`

### Algorithmic Shortcuts
5. **Clear the lowest set bit:** `x & (x - 1)`
   - *Origin:* Brian Kernighan's Algorithm.
   - *Use Case:* Counting set bits in $O(\text{set bits})$ time, or checking if a number is a power of 2 (`n > 0 && (n & (n - 1)) == 0`).
6. **Isolate the lowest set bit:** `x & (-x)`
   - *Origin:* Two's Complement math (`-x` is `~x + 1`).
   - *Use Case:* Finding the distinguishing bit between two numbers (crucial for *Single Number III*) and building Fenwick Trees.