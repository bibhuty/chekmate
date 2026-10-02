package _03_BinarySearch.twoDArray;

public class SearchIn2DMatrix {
    // 1 2 3
    // 4 5 6
    // 7 8 9

    // Probable Questions:
    //  - First integer of a row vs last integer of prev row
    //  - Elements unique or non-unique

    // 8
    // TC: O(log mn)
    // SC: O(1)
    public boolean searchMatrix(int[][] matrix, int target){
        int row=searchRow(matrix,target); // 2
        if(row==-1) return false;
        int col = binarySearch(matrix[row], target);
        return col!=-1?true:false;
    }


    // 1 2 3
    // 4 5 6
    // 7 8 9

    // 8(true)->1, 10(false)->-1
    public int searchRow(int[][] matrix, int target){
        int top=0, bottom=matrix.length-1, left=0, right=matrix[0].length-1;//0,2,0,2
        while(top<bottom){// 0,2-> 2,2
            int mid=top+(bottom-top)/2;//1
            if(matrix[mid][left]<=target && target<=matrix[mid][right]){ // false
                return mid;
            }else if(matrix[mid][left]>target){// false
                bottom=mid-1;
            }else if(target>matrix[mid][right]){// true
                top=mid+1;
            }
        }
        return (matrix[top][left]<=target && target<=matrix[top][right])?top:-1;
    }

    // 7 8 9 -> 8
    public int binarySearch(int[] nums, int target){
        int left=0, right=nums.length-1;
        while(left<right){//0,2, 0,1, 1,1
            int mid=left+(right-left)/2; // 1
            if(nums[mid]>=target){ // true, false
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return nums[left]==target?left:-1;
    }

    // TC: O(log mn)
    // SC: O(1)

    // 1 2 3
    // 4 5 6
    public boolean searchMatrixOnePass(int[][] matrix, int target){
        int rows=matrix.length, cols=matrix[0].length;
        int left=0, right=rows*cols-1;

        // index1D=rNo*cols+cNo(<cols)
        while(left<right){
            int mid=left+(right-left)/2;

            int midRow=mid/cols;
            int midCol=mid%cols;

            if(matrix[midRow][midCol]>=target){
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return matrix[left/cols][left%cols]==target?true:false;
    }
}
