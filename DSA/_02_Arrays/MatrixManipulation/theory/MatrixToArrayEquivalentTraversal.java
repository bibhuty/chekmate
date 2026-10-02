package _02_Arrays.MatrixManipulation.theory;

public class MatrixToArrayEquivalentTraversal {
    public void traverseIn1D(int[][] matrix){
        if(matrix.length==0) return;
        if(matrix[0].length==0) return;
        int rows=matrix.length, cols=matrix[0].length;
        // 1DIndex = row*cols + col
        int left=0, right=rows*cols-1;
        while(left<=right){
            System.out.println(matrix[left/cols][left%cols]+" - ");
            ++left;
        }
    }
}
