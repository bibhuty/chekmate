# Algorithmic Fundamentals: Sieve of Eratosthenes

## 1. Standard Sieve
The base implementation. We cross out multiples starting from $i \times i$ because any smaller multiple $i \times k$ (where $k < i$) was already crossed out when we evaluated $k$.

```java
// Time Complexity: O(n log log n)
// Space Complexity: O(n)
boolean[] sieveOfEratosthenes(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = false;
    isPrime[1] = false;
    
    for (int i = 2; i <= n; ++i) {
        if (isPrime[i]) {
            // Cast to long to prevent overflow if n is near Integer.MAX_VALUE
            for (long j = (long) i * i; j <= n; j += i) {
                isPrime[(int) j] = false;
            }
        }
    }
    return isPrime;
}
```

## 2. Sieve Till Root
Optimizes the outer loop. We only need to find prime factors up to $\sqrt{n}$.
*Note: Time complexity remains $O(n \log \log n)$ because the inner loop still traverses up to $n$, keeping the mathematical limit the same.*

```java
// Time Complexity: O(n log log n)
// Space Complexity: O(n)
boolean[] sieveOfEratosthenesTillRoot(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    // Outer loop stops at sqrt(n)
    for (int i = 2; (long) i * i <= (long) n; ++i) {
        if (isPrime[i]) {
            for (long j = (long) i * i; j <= (long) n; j += i) {
                isPrime[(int) j] = false;
            }
        }
    }
    return isPrime;
}
```

## 3. Sieve With Odd Numbers Only (Optimal Single-Array)
The gold standard for standard memory constraints.
1. Pre-marks all even numbers as `false`.
2. Steps the outer loop by `2` to skip evens.
3. Steps the inner loop by `2L * i` (Odd + Even = Odd) to completely skip evaluating even multiples.

```java
// Time Complexity: O(n log log n)
// Space Complexity: O(n)
boolean[] sieveOfEratosthenesTillRootWithOddNumbersOnly(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    // Mark even numbers immediately
    for (int i = 4; i <= n; i += 2) {
        isPrime[i] = false;
    }
    
    for (int i = 3; (long) i * i <= (long) n; i += 2) {
        if (isPrime[i]) {
            // j += 2L * i skips checking even multiples entirely
            for (long j = (long) i * i; j <= (long) n; j += 2L * i) {
                isPrime[(int) j] = false;
            }
        }
    }
    return isPrime;
}
```

## 4. Segmented Sieve (Memory Optimization)
Solves the `OutOfMemoryError` for massive limits (e.g., $N = 10^{10}$) by processing numbers in small, cache-friendly chunks.
* **Auxiliary Space:** $O(\sqrt{n})$ for the computational arrays.
* **Total Space:** $O(n / \log n)$ due to storing the final list of primes (Prime Number Theorem).

```java
// Time Complexity: O(n log log n)
// Auxiliary Space: O(sqrt(n)) 
// Output Space: O(n / log n) to store the primes
ArrayList<Integer> segmentedSieve(int n) {
    var primes = new ArrayList<Integer>();
    if (n < 2) return primes;
    
    int rootN = (int) Math.sqrt(n);
    boolean[] isPrime = new boolean[rootN + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    for (int i = 4; i <= rootN; i += 2) isPrime[i] = false;
    primes.add(2);
    
    for (int i = 3; i <= rootN; i += 2) {
        if (isPrime[i]) {
            primes.add(i);
            if (((long) i * i) <= rootN) {
                for (int j = i * i; j <= rootN; j += 2 * i) {
                    isPrime[j] = false;
                }
            }
        }
    }
    
    int segmentSize = 100000; // 10^5 is L1 cache friendly
    var newPrimes = new ArrayList<Integer>();
    
    for (int start = rootN; start <= n; start += segmentSize) {
        boolean[] primeRange = new boolean[segmentSize];
        Arrays.fill(primeRange, true);
        int right = Math.min(n, start + segmentSize);
        
        for (Integer prime : primes) {
            // CRITICAL: Only sieve using base primes <= sqrt(n)
            if (prime > rootN) break; 
            
            // start - start % prime + prime ensures we safely find 
            // the first multiple strictly greater than start
            for (int left = (start - start % prime) + prime; left <= right; left += prime) {
                primeRange[left - start - 1] = false;
            }
        }
        
        // Ceiling boundary condition protects against ghost primes on the last chunk
        for (int i = 0; i < primeRange.length && start + 1 + i <= n; ++i) {
            if (primeRange[i]) {
                newPrimes.add(start + 1 + i);
            }
        }
    }
    
    primes.addAll(newPrimes);
    return primes;
}
```