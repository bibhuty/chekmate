# Binary Exponentiation (Exponentiation by Squaring)

Instead of multiplying a base `x` by itself `n` times (which takes $O(n)$ time), Binary Exponentiation calculates $x^n$ in $O(\log n)$ time by breaking the exponent $n$ down into its binary representation.

At each step, we continuously square the base ($x^1 \rightarrow x^2 \rightarrow x^4 \rightarrow x^8$). We only multiply the current base into our final result when the corresponding bit in the binary exponent is `1`.

**Example:** $x^{13}$
* The exponent `13` in binary is `1101` (which represents 8 + 4 + 0 + 1).
* Therefore, $x^{13} = x^8 \cdot x^4 \cdot x^1$.
* By continuously squaring the base, we skip calculating the intermediate powers, strictly arriving at the answer in logarithmic time.

## Iterative Implementation
**Time:** $O(\log n)$ | **Space:** $O(1)$

```java
public double binaryExpIterative(double x, int n) {
    long N = n; // Cast to long to prevent overflow if n is Integer.MIN_VALUE
    boolean isNegative = N < 0; 
    N = Math.abs(N);
    
    double result = 1.0;
    double currentBase = x; 
    
    while (N > 0) {
        // If the current rightmost bit is 1, multiply the base into the result
        if ((N & 1) == 1) {
            result *= currentBase;
        }
        // Square the base for the next bit (x^1 -> x^2 -> x^4 -> x^8)
        currentBase *= currentBase; 
        
        // Shift bits right by 1
        N >>= 1; 
    }
    
    return isNegative ? 1.0 / result : result;
}
```