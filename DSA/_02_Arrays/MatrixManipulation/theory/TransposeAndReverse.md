# 📐 Matrix Transformations (Transpose & Reverse)

Matrix rotation problems (and many grid manipulation tasks) can be simplified using basic linear algebra. By breaking complex rotations into two simple 1D reflections, we completely eliminate the risk of off-by-one errors associated with 4-way coordinate swapping.

### 1. The Two Primitives

#### Primitive A: Transpose (Reflection across Main Diagonal)
Transposing a matrix swaps its rows and columns (`matrix[i][j]` becomes `matrix[j][i]`).
* **Optimization:** Iterate only the upper triangle.
* Start the inner loop at `pivot + 1` to avoid uselessly swapping the diagonal elements with themselves.
* Stop the outer loop at `n - 1` because the bottom-right corner has no upper-triangle elements left to swap.

```java
public void transpose(int[][] matrix) {
    int n = matrix.length;
    for (int pivot = 0; pivot < n - 1; ++pivot) {
        for (int index = pivot + 1; index < n; ++index) {
            int temp = matrix[pivot][index];
            matrix[pivot][index] = matrix[index][pivot];
            matrix[index][pivot] = temp;
        }
    }
}
```

#### Primitive B: Reverse (Reflection across Vertical Center)
Reversing flips the matrix horizontally by swapping columns from the outside in.
* **Optimization:** Use a standard two-pointer approach (`left` and `right`) to squeeze toward the center, swapping entire columns row by row.

```java
public void reverse(int[][] matrix) {
    int n = matrix.length;
    int left = 0, right = n - 1;
    while (left < right) {
        for (int index = 0; index < n; ++index) {
            int temp = matrix[index][left];
            matrix[index][left] = matrix[index][right];
            matrix[index][right] = temp;
        }
        ++left;
        --right;
    }
}
```

---

### 2. Composing Transformations (Cheat Codes)

Once you have these two primitives in your toolkit, any orthogonal rotation is just a two-line function call:

* **Rotate 90° Clockwise:** `transpose(matrix) -> reverse(matrix)`
* **Rotate 90° Counter-Clockwise:** `reverse(matrix) -> transpose(matrix)`