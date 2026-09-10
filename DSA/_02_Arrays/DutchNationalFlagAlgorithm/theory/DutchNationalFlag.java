package _02_Arrays.DutchNationalFlagAlgorithm.theory;

public class DutchNationalFlag {

    // 1 1 2 2 2 0 0 0
    public void dutchNationalFlag(int[] nums){
        int n=nums.length,low=0,mid=0,high=n-1;
        // 0 0 0 |1 1 ||2 2 2
        while(mid<=high){ // [low, mid, high] -> [0, 0, 7] -> [0, 1, 7] -> [0, 2, 7] -> [0, 2, 6] -> [1, 3, 6] -> [1, 3, 5] -> [2, 4, 5] -> [2, 4, 4] -> [3, 5, 4]
            int current=nums[mid]; // 1 -> 1 -> 2 -> 0 -> 2 -> 0 -> 2 -> 0
            if(current==0){
                nums[mid++]=nums[low];
                nums[low++]=current;
            }else if(current==2){
                nums[mid]=nums[high];
                nums[high--]=current;
            } else ++mid;
        }
    }
}
