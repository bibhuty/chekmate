# [Search a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)
**Category:** Arrays / Matrix / Search Space Reduction

## Problem Assumptions & Constraints
- The matrix is sorted left-to-right and top-to-bottom.
- Unlike Matrix I, the last element of a row is **not** strictly smaller than the first element of the next row. We cannot flatten this into a 1D array.
- Any occurrence is fine (we don't need first/last bounds).

## The Journey & Mistakes

### 1. The $O(M \log N)$ Row/Col Binary Search
- **Initial Thought:** Binary search every row and every column.
- **Mistake / Correction:** If you search every row and don't find the target, searching the columns is completely redundant. The columns are made of the same elements! You only need to run binary search across the rows OR the columns, not both.

### 2. The 4-Way Bounding Box (Gridlock)
- **Initial Thought:** Try to crush the search space from all 4 sides (`top`, `bottom`, `left`, `right`) simultaneously.
- **The Flaw:** Managing 4 converging pointers creates extreme edge cases (pointer crossovers). More importantly, it causes **gridlock**: if the target is physically surrounded by the bounding box but the boundaries themselves don't overlap the target, the pointers stop moving and you get trapped in an infinite loop.

### 3. The $O(M + N)$ Saddle Point (Staircase)
- **The Epiphany:** To avoid gridlock and ambiguity, you must start at a corner where the two directions do *opposite* things.
- **The Bottom-Left Saddle Point:**
    - Moving **UP** strictly *decreases* the value.
    - Moving **RIGHT** strictly *increases* the value.
- By asking a simple question (`Is current < target?`), we deterministically eliminate an entire row or column at every single step. No branching, no gridlock.

## Code
**Time:** $O(M + N)$ | **Space:** $O(1)$

```java
package _03_BinarySearch.twoDArray;

public class SearchIn2DMatrixII {
    // 1 4 7
    // 2 5 8
    // 3 6 9

    // Assumptions:
    // - Any occurrence is fine not first/last occurrence
    // - Non-unique number can not be there in the matrix
    // - At least matrix length is 1x1

    // Thought process:
    // Search in each row in binary search
    // Search in each column in binary search

    // Mistake 1:
    // I went ahead to find rows and columns where I can find the target
    // But the example made me think twice where we're trying to find 3
    // with trying to pinpoint rows and columns
    // 1 2 100
    // 2 4 100
    // 3 7 100

    // Mistake 2:
    // I searched in rows first and if not found then in columns
    // Ideally if you don't find things in rows how you can find
    // in columns since the columns are already traversed as part
    // of rows

    // TC: O(n log m) col binary search
    // SC: O(1)
    public boolean searchMatrix(int[][] matrix, int target){
        int top=0, bottom=matrix.length-1, left=0, right=matrix[0].length-1;
        for(int col=left;col<=right;++col)
            if(searchInCol(matrix,target,col))
                return true;
        // Not required: Since if you can't find anything in all the rows
        //               there's no way you'd find them in the columns
        // for(int row=top;row<=bottom;++row)
        //    if(searchInRow(matrix,target,row))
        //        return true;
        return false;
    }

    public boolean searchInCol(int[][] matrix, int target, int colNo){
        if(colNo==-1) return false;
        int top=0, bottom=matrix.length-1, left=0, right=matrix[0].length-1;
        while(top<bottom){
            int mid=top+(bottom-top)/2;
            if(matrix[mid][colNo]>=target){
                bottom=mid;
            }else{
                top=mid+1;
            }
        }
        return matrix[top][colNo]==target;
    }

    // When you try to reduce the search space in 4 directions, you basically have to handle
    // crossovers very much seriously
    public boolean searchInSinglePass(int[][] matrix, int target){
        int top=0, bottom=matrix.length-1, left=0, right=matrix[0].length-1;// 0, 2, 0, 3 -> target=5
        while(left<=right && top<=bottom){//[0,2],[0,3] -> [1,2], [1,3] -> [1,1], [1,2]
            // Try finding the elements in four corners, if found then return true
            if(     matrix[top][left]==target ||
                    matrix[top][right]==target ||
                    matrix[bottom][left]==target ||
                    matrix[bottom][right]==target) return true; 

            // Since these variables are used after change
            // so need a placeholder store the change to be used later
            int _top=top, _bottom=bottom, _left=left, _right=right;

            // Search along first and last rows excluding corners
            // Whichever row doesn't contain the element moves
            if(top!=bottom){
                if(!(matrix[top][left]<target && target<matrix[top][right])) ++_top; 
                if(!(matrix[bottom][left]<target && target<matrix[bottom][right])) --_bottom; 
            }

            // Search along first and last cols excluding corners
            // Whichever col doesn't contain the element moves
            if(left!=right){
                if(!(matrix[top][left]<target && target<matrix[bottom][left])) ++_left; 
                if(!(matrix[top][right]<target && target<matrix[bottom][right])) --_right; 
            }

            // Breakout condition to avoid infinite loop
            if(left==right && top==bottom) break;

            // Apply the change
            top=_top;
            bottom=_bottom;
            left=_left;
            right=_right;
        }
        return false;
    }
    // TC: O(m+n)
    // SC: O(1)
    public boolean searchInSinglePassElegant(int[][] matrix, int target){
        int rows=matrix.length, cols=matrix[0].length, row=rows-1, col=0;
        while(row>=0 && col<cols){
            if(matrix[row][col]==target) return true;
            else if(matrix[row][col]<target) ++col;
            else --row;
        }
        return false;
    }
}
```