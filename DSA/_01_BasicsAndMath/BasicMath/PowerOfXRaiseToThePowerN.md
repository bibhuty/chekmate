# [Pow(x, n)](https://leetcode.com/problems/powx-n/)
**Category:** Math / Basics

## 1. Interview Checklist & Edge Cases
**Questions to ask the interviewer:**
- What is the range of the base (`x`)?
- What is the range of the exponent (`n`)?
- If `x == 0`, is `n` guaranteed to be valid? (Assume yes)

**The "Gotchas":**
- `n = 1` and `n = 0` are critical edge cases.
- `n` can be negative.
- **Overflow Trap:** The `int` range is `[-2^31, 2^31-1]`. Taking the absolute value of the most negative `int` causes an overflow. Always cast `n` to `long` before `Math.abs()`.

---

## 2. Iterative Solution (Optimal)
**Time:** O(log n) | **Space:** O(1)

```java
public double power(double x, long n){ // e.g., 3, -5 (binary: 101)
    boolean isNegative = n < 0; 
    n = Math.abs(n);
    
    double result = 1.0;
    double bitVal = x; // Probable value at the binary level if the bit is set
    
    while(n != 0){
        if((n & 1) == 1) {
            result *= bitVal;
        }
        bitVal *= bitVal; // Double it because moving to each bit we double the value
        n = n >> 1;
    }
    
    return isNegative ? 1.0 / result : result;
}
```

---

## 3. Recursive Solution
**Time:** O(log n) | **Space:** O(log n) due to call stack.

```java
// Holds edge cases for pow values (0,1)
public double calculate(double exp, long pow){ // e.g., 3, -3
    if(pow == 0) return 1;
    if(pow == 1) return exp;
    
    boolean isNegative = pow < 0;
    double result = pow(exp, Math.abs(pow)); // 27
    
    return isNegative ? 1.0 / result : result; // 1/27
}

/* 
* DRY RUN TRACE: calculate(3, 3)
* Stack 1 (3, 3):
*      pow == 1 -> false
*      residual = 3
*      result = 3
*      return 3 * 3 * 3 -> 27 (ANS)
*
*      Stack 2 (3, 1):
*           pow == 1 -> true => return 3
*/
public double pow(double x, long pow){
    if(pow==1) return x;
    double residual = pow%2==0 ? 1: x;
    double halfPower = pow(x,pow/2);
    return halfPower*halfPower*residual;
}
```