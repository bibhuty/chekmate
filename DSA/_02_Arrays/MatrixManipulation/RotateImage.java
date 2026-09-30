package _02_Arrays.MatrixManipulation;

public class RotateImage {

    // Questions:
    //      - At least length of matrix is 1
    //      - it is nxn
    //      - Constant auxiliary space is needed

    // Thought process:
    //      - We think matrix in terms of outer lines
    //      - At each interation we cyclically assign values and there will be 4
    //        data needs moving since only 4 lines make up a matrix

    // Mistake:
    //      - Looking a matrix in terms of y=0,x=0 instead of (i,j)
    //        because if we see in terms of cartesian axis then where i=0, y=0
    //        hence the entire calculation changes

    //[[1,2],[3,4]]
    // TC: O(n*n) due to the loop traversal
    // SC: O(1)
    public void rotate(int[][] matrix){
        int n=matrix.length, pivot=0, distance=n-1;//[2,0,0,1]

        while(distance>0){
            for(int i=0;i<distance;++i){
                int topLeft=matrix[pivot][pivot+i];
                int topRight=matrix[pivot+i][pivot+distance];
                int bottomRight=matrix[pivot+distance][pivot+distance-i];
                int bottomLeft=matrix[pivot+distance-i][pivot];
                matrix[pivot+i][pivot+distance]=topLeft;
                matrix[pivot+distance][pivot+distance-i]=topRight;
                matrix[pivot+distance-i][pivot]=bottomRight;
                matrix[pivot][pivot+i]=bottomLeft;
            }
            ++pivot;
            distance-=2;
        }
    }

    public void rotateUsingTransposeAndReverse(int[][] matrix){
        transpose(matrix);
        reverse(matrix);
    }

    // Transpose: reflection around the diagonal
    public void transpose(int[][] matrix){
        int n=matrix.length;
        for(int pivot=0;pivot<n-1;++pivot){
            for(int index=pivot+1;index<n;++index){
                int temp=matrix[pivot][index];
                matrix[pivot][index]=matrix[index][pivot];
                matrix[index][pivot]=temp;
            }
        }
    }

    // Reverse: swap along the middle
    public void reverse(int[][] matrix){
        int n=matrix.length;
        int left=0, right=n-1;
        while(left<right){
            for(int index=0; index<n;++index){
                int temp=matrix[index][left];
                matrix[index][left]=matrix[index][right];
                matrix[index][right]=temp;
            }
            ++left;
            --right;
        }
    }
}
