# 📐 M1: Rotate Image

* **LeetCode 48:** [Rotate Image](https://leetcode.com/problems/rotate-image/)

---

### 1. Core Intuition & The Cartesian Trap
To rotate a matrix 90 degrees clockwise in-place, we process it layer by layer (like peeling an onion). In each layer, we perform a 4-way cyclic swap of the elements.
* **The Cartesian Trap:** Do not think of matrix indexing as `(x, y)`. A matrix is `[row][col]`, meaning the first index moves *down* (vertical) and the second moves *right* (horizontal).
* **The Pivot & Distance Model:**
    * `pivot` represents the top-left anchor `[pivot][pivot]` of the current shell.
    * `distance` represents the length of the current shell minus 1.
    * Top edge: `[pivot][pivot + i]`
    * Right edge: `[pivot + i][pivot + distance]`
    * Bottom edge: `[pivot + distance][pivot + distance - i]`
    * Left edge: `[pivot + distance - i][pivot]`

---

### 2. Implementation (4-Way Coordinate Swap)

```java
public class RotateImage {

    // TC: O(n*n) due to the loop traversal (visits each cell once)
    // SC: O(1) in-place swaps
    public void rotate(int[][] matrix){
        int n=matrix.length, pivot=0, distance=n-1;

        while(distance>0){
            for(int i=0;i<distance;++i){
                // 1. Save all 4 corners/edges relative to the pivot
                int topLeft=matrix[pivot][pivot+i];
                int topRight=matrix[pivot+i][pivot+distance];
                int bottomRight=matrix[pivot+distance][pivot+distance-i];
                int bottomLeft=matrix[pivot+distance-i][pivot];
                
                // 2. Perform the cyclic clockwise assignment
                matrix[pivot+i][pivot+distance]=topLeft;
                matrix[pivot+distance][pivot+distance-i]=topRight;
                matrix[pivot+distance-i][pivot]=bottomRight;
                matrix[pivot][pivot+i]=bottomLeft;
            }
            // Move into the next inner shell
            ++pivot;
            distance-=2; // Shell shrinks by 2 (1 from left, 1 from right)
        }
    }
}
```