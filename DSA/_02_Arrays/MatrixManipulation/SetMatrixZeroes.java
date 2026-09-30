package _02_Arrays.MatrixManipulation;

import java.util.HashSet;

public class SetMatrixZeroes {

    // Assumption: At least one row and one column
    // TC:O(m*n) matrix traversal
    // SC:O(m+n) storage of zero pointers

    // 1x 0x 3x
    // 4x 5x 7x
    // 8x 9x 0x

    // 0x 0x 0x
    // 4x 5x 0x
    // 0x 0x 0x

    public void setZeroes(int[][] matrix){
        int rows=matrix.length, cols=matrix[0].length;
        var zeroRows=new HashSet<Integer>(); // 0,3
        var zeroCols=new HashSet<Integer>(); // 1,3
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
    // 2 placeholders for storing rows/col details for the firt col/first row
    // separately

    // Also realising how to set the matrix element zero is also tricky
    // Boring elegance with multiple occurrence of for loop makes more sense than
    // squeezing everything inside a loop

    // TC: O(m*n)+O(m+n) full matrix+partial row traversal
    // SC: O(1)
    public void setZeroesWithConstantSpace(int[][] matrix){
        int rows=matrix.length, cols=matrix[0].length;
        boolean firstColZero=matrix[0][0]==0, firstRowZero=firstColZero;
        for(int row=0;row<rows;++row) firstColZero|=(matrix[row][0]==0);
        for(int col=0;col<cols;++col) firstRowZero|=(matrix[0][col]==0);
        for(int row=1;row<rows;++row){
            for(int col=1;col<cols;++col){
                if(matrix[row][col]==0){
                    matrix[row][0]=0;
                    matrix[0][col]=0;
                }
            }
        }
        for(int row=1;row<rows;++row)
            for(int col=1;col<cols;++col)
                if(matrix[row][0]==0 || matrix[0][col]==0)
                    matrix[row][col]=0;


        if(firstRowZero)
            for(int i=0;i<cols;++i)
                matrix[0][i]=0;

        if(firstColZero)
            for(int i=0;i<rows;++i)
                matrix[i][0]=0;

    }
}
