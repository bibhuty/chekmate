package _03_BinarySearch.oneDArray;

import java.util.List;

public class FirstAndLastPositionInSortedArray {

    // One important question forgot to ask/clarify:
    //  - The array can be empty/not

    // 1 2 3 4 4 4 5 --- 4
    // TC: O(log n) for searching
    // SC: O(1)
    public int[] searchRange(int[] nums, int target){
        return new int[]{binarySearchFirst(nums,target), binarySearchLast(nums,target)};
    }

    // 1 2 3 4 4 4 5 --- 4
    // TC: O(log n) for searching
    // SC: O(1)
    public int binarySearchLast(int[] nums,int target){
        if(nums.length==0) return -1;
        int left=0, right=nums.length-1;
        while(left<right){ // [0,7]->[4,7]->[6,7]->[6,6]
            int mid=left+(right-left+1)/2; // 4 -> 6 -> 7
            if(nums[mid]<=target){ // true->true->false
                left=mid;
            }else{
                right=mid-1;
            }
        }
        return nums[left]==target?left:-1; // 6
    }

    // 1 2 3 4 4 4 5 --- 4
    // TC: O(log n) for searching
    // SC: O(1)
    public int binarySearchFirst(int[] nums, int target){
        if(nums.length==0) return -1;
        int left=0,right=nums.length-1;
        while(left<right){ // [0,7]->[0,3]->[2,3]->[3,3]
            int mid=left+(right-left)/2; // 3 -> 1 -> 2
            if(nums[mid]>=target){ // true->false->false
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return nums[left]==target?left:-1; // 3
    }
}
