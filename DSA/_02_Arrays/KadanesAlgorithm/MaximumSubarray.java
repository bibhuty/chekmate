package _02_Arrays.KadanesAlgorithm;

public class MaximumSubarray {
    /*
        Assumptions:
            - All the elements are integer(positive and negative)
            - The array contains at least one element
        Considerations:
            - Adding all the integers may overflow so let's go for long,
              unless there's an explicit limit that says that the addition
              will always be bounded to an integer value
        Mistake:
            - When the entire sum goes negative, you need to reset the sum
     */

    // [-3] -> -3
    // [-3,-2,1,-2,2]
    // TC:O(n), SC:O(1)
    public long calculateMaxSum(int[] nums){
        long maxSum=nums[0]; // -3
        long currentSum=0; //
        // int left=0; -> redundant variable declaration
        int right=0, n=nums.length; // n=3
        while(right<n){//[left,right]->[0,0]->[0,1]->[1,2]->[2.3]
            currentSum+=nums[right];// -3->-5->-1->3
            // while (right>left && nums[left]<0){ // left -> 0->1, 1->2
            //    currentSum-=nums[left++]; // -1 -> 1
            // }
            maxSum=Math.max(currentSum, maxSum);// -3->[-2]->1->3
            ++right; // 1 -> 2 -> 3 -> 4
            // mistake: if the entire currentSum goes to negative post optimization
            //          then, it's better to restart the calculations from the next
            //          possible index
            if(currentSum<0){
                // left=right;
                currentSum=0;
            }
        }
        return maxSum; // 3
    }


    // TC=O(Nlog N), SC=O(log N)
    public int calculateMaxSumDC(int[] nums, int start, int end){

        if(start>end) return Integer.MIN_VALUE;

        int mid=start+(end-start)/2;
        int midNum=nums[mid];
        int leftMax=0, current=0;
        for(int i=mid-1;i>=start && i>=0;--i){
            current+=nums[i];
            leftMax=Math.max(leftMax,current);
        }
        int rightMax=0;
        current=0;
        for(int i=mid+1;i<nums.length && i<=end;++i){
            current+=nums[i];
            rightMax=Math.max(rightMax, current);
        }
        int leftSubarrayMaxSum=calculateMaxSumDC(nums, start, mid-1);
        int rightSubarrayMaxSum=calculateMaxSumDC(nums, mid+1, end);

        return Math.max(leftMax+rightMax+midNum,Math.max(leftSubarrayMaxSum, rightSubarrayMaxSum));
    }

}
