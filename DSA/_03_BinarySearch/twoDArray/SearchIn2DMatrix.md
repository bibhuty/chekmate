# [Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)
**Category:** Arrays / Binary Search / Matrix

## Approach 1: Two-Pass Binary Search (Decomposition)
- **The Core Property:** The matrix has two key guarantees: each row is sorted, and the first integer of each row is strictly greater than the last integer of the previous row.
- **The Strategy:** We can solve this by breaking it into two distinct binary searches.
    - **Vertical Search:** Find the target row in $O(\log m)$ time by checking if the target falls between `matrix[mid][0]` and `matrix[mid][cols - 1]`.
    - **Horizontal Search:** Once the row is isolated, run a standard $O(\log n)$ binary search inside that specific row to find the exact column.
- **Mathematical Complexity:** $O(\log m) + O(\log n) = O(\log(mn))$.

### Code (Two-Pass)
**Time:** $O(\log(mn))$ | **Space:** $O(1)$

```java
public class SearchIn2DMatrix {
    
    // Approach 1: Two-Pass (Row then Column)
    public boolean searchMatrix(int[][] matrix, int target){
        int row = searchRow(matrix, target); 
        if(row == -1) return false;
        int col = binarySearch(matrix[row], target);
        return col != -1 ? true : false;
    }

    // Vertical Search to find the probable row
    public int searchRow(int[][] matrix, int target){
        int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;
        while(top < bottom){
            int mid = top + (bottom - top) / 2;
            if(matrix[mid][left] <= target && target <= matrix[mid][right]){ 
                return mid;
            } else if(matrix[mid][left] > target){
                bottom = mid - 1;
            } else if(target > matrix[mid][right]){
                top = mid + 1;
            }
        }
        return (matrix[top][left] <= target && target <= matrix[top][right]) ? top : -1;
    }

    // Horizontal Search within the isolated row
    public int binarySearch(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left < right){
            int mid = left + (right - left) / 2; 
            if(nums[mid] >= target){ 
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left] == target ? left : -1;
    }
}
```

---

## Approach 2: 1D Logical Mapping (Memory Abstraction)
- **The Core Insight:** In hardware (RAM), 2D matrices do not actually exist. Memory is a single, flat, continuous 1D tape. Languages like Java use "Row-Major Order" to store 2D arrays row by row. Because the matrix is strictly sorted across row boundaries, we can pretend the matrix is just one giant 1D array.
- **The Math (Flattening):** The universal formula to convert a 2D coordinate to a 1D index is:
  `index1D = (row * cols) + col`
- **The Math (Unflattening):** Because the column index is strictly bounded (`col < cols`), we can use standard division and modulo to reverse the formula and find our 2D coordinates from a 1D `mid` point:
    - `row = mid / cols` (How many full rows fit into this index?)
    - `col = mid % cols` (How many leftover steps in the current row?)
- **Why 0-Based Indexing Matters:** This modulo math completely breaks down if we use 1-based indexing (e.g., `1` to `m * n`). If we did, the last element of a row would yield a remainder of `0`, instantly snapping the calculated column back to the start of the row. 0-based indexing ensures the division/modulo boundaries perfectly align with the physical row lengths.

### Code (One-Pass)
**Time:** $O(\log(mn))$ | **Space:** $O(1)$

```java
public class SearchIn2DMatrixOnePass {

    public boolean searchMatrixOnePass(int[][] matrix, int target){
        int rows = matrix.length, cols = matrix[0].length;
        int left = 0, right = rows * cols - 1;

        // index1D = rNo * cols + cNo (<cols)
        while(left < right){
            int mid = left + (right - left) / 2;

            // Map the 1D logical mid to 2D physical coordinates
            int midRow = mid / cols;
            int midCol = mid % cols;

            // Standard FFFFTTTT Boolean Boundary Logic
            if(matrix[midRow][midCol] >= target){
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        // Final evaluation on converged pointer
        return matrix[left / cols][left % cols] == target ? true : false;
    }
}
```