# 📐 M2: Set Matrix Zeroes

* **LeetCode 73:** [Set Matrix Zeroes](https://leetcode.com/problems/set-matrix-zeroes/)

---

### 1. The $O(M + N)$ Space Approach (Baseline)
The naive trap is modifying the matrix immediately, which causes a cascading effect (a zero at `[0][0]` turns the whole row to zero, which then turns all columns to zero).
* **Fix:** Do a read-only pass to collect all rows and columns that need zeroing into two `HashSet`s.
* **Pass 2:** Iterate the matrix again. If `row` is in `zeroRows` OR `col` is in `zeroCols`, set `matrix[row][col] = 0`.

---

### 2. The $O(1)$ Space Approach (The In-Place Ledger)
To achieve constant space, we use the matrix's own **first row** and **first column** as our `HashSet`s (the ledger). Because the first row and column overlap at `matrix[0][0]`, we don't have two separate placeholders for the first row/col details.

**The "Boring Elegance" 4-Phase Architecture:**
Squeezing everything inside a single loop with multiple `if (col == 0)` conditions makes the code brittle and hard to read. Boring elegance with multiple, isolated `for` loops makes much more sense:
1. **Save outer state:** Check if the first row or first column natively contain any `0`s.
2. **Mark the ledger:** Iterate the inner matrix. If `matrix[r][c] == 0`, mark `matrix[r][0] = 0` and `matrix[0][c] = 0`.
3. **Apply the ledger:** Iterate the inner matrix again. If the row's ledger or col's ledger is `0`, set `matrix[r][c] = 0`.
4. **Apply outer state:** Zero out the first row/column if their original boolean flags demand it.

---

### 3. Implementation

```java
import java.util.HashSet;

public class SetMatrixZeroes {

    // Assumption: At least one row and one column
    // TC:O(m*n) matrix traversal
    // SC:O(m+n) storage of zero pointers
    public void setZeroes(int[][] matrix){
        int rows=matrix.length, cols=matrix[0].length;
        var zeroRows=new HashSet<Integer>(); 
        var zeroCols=new HashSet<Integer>(); 
        for(int row=0;row<rows;++row){
            for(int col=0;col<cols;++col){
                if(matrix[row][col]==0){
                    zeroRows.add(row);
                    zeroCols.add(col);
                }
            }
        }
        for(int row=0;row<rows;++row){
            for(int col=0;col<cols;++col){
                if(zeroRows.contains(row) || zeroCols.contains(col)){
                    matrix[row][col]=0;
                }
            }
        }
    }

    // Here the important trick is realising unlike other columns we don't have
    // 2 placeholders for storing rows/col details for the first col/first row
    // separately.
    //
    // Also realising how to set the matrix element zero is also tricky
    // Boring elegance with multiple occurrence of for loop makes more sense than
    // squeezing everything inside a loop

    // TC: O(m*n)+O(m+n) full matrix+partial row traversal
    // SC: O(1)
    public void setZeroesWithConstantSpace(int[][] matrix){
        int rows=matrix.length, cols=matrix[0].length;
        boolean firstColZero=matrix[0][0]==0, firstRowZero=firstColZero;
        
        // 1. Save outer state
        for(int row=0;row<rows;++row) firstColZero|=(matrix[row][0]==0);
        for(int col=0;col<cols;++col) firstRowZero|=(matrix[0][col]==0);
        
        // 2. Mark the ledger
        for(int row=1;row<rows;++row){
            for(int col=1;col<cols;++col){
                if(matrix[row][col]==0){
                    matrix[row][0]=0;
                    matrix[0][col]=0;
                }
            }
        }
        
        // 3. Apply the ledger
        for(int row=1;row<rows;++row)
            for(int col=1;col<cols;++col)
                if(matrix[row][0]==0 || matrix[0][col]==0)
                    matrix[row][col]=0;

        // 4. Apply outer state
        if(firstRowZero)
            for(int i=0;i<cols;++i)
                matrix[0][i]=0;

        if(firstColZero)
            for(int i=0;i<rows;++i)
                matrix[i][0]=0;
    }
}
```