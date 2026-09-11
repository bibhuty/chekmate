package _02_Arrays.PrefixSum;

public class PivotIndex {

    // arr of length n
    // for any index i
    // S[0...i-1]==S[i+i...n]

    // S[-1]=S[n]=0


    // Important questions to ask:
    // 1. Is multiple pivot index possible?
    // 2. What to do when there's no pivot index available?
    // 3. What to do when multiple pivot index available?


    // Assumptions:
    // Summation of all the numbers of the array is bounded to integer
    // Array always have elements

    // [1,-2, 2]

    // TC: O(n), SC: O(1)
    public int findPivotIndex(int[] nums){ // 1 -2 2
        int rightSum=0, leftSum=0; // 1,0
        for(int num: nums) rightSum+=num;
        for(int i=0;i<nums.length;++i){ // i -> 0
            rightSum-=nums[i]; // 0
            if(rightSum==leftSum) return i;
            leftSum+=nums[i];
        }
        return -1;
    }
}
