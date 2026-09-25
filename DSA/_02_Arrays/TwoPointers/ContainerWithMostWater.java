package _02_Arrays.TwoPointers;

public class ContainerWithMostWater {
    // visualization in the problem statement is the biggest culprit
    // Assumptions: length of the array is > 1 always
    //              elements are integers
    //              area calculated will be integer

    // TC: O(n)
    // SC: O(1)

    // [1,2,3,1]
    // --
    // ----
    // ------
    // --
    public int maxArea(int[] nums){
        // Its important to get this logic without visualisation
        // At a given point the max water it can store is if we
        // take a line to the farthest point and calculate the area
        // If we are to maximise the water inside then we need to move
        // that point inwards that has the smallest height
        // We need to maximise min(leftheight,rightheight)*(rightIndex-leftIndex)
        int left=0, right=nums.length-1,maxArea=0;//[0,3,0]->[0,2,3]->[1,2,3]->[2,2,3]->exit
        while(left<right){
            maxArea=Math.max(maxArea,Math.min(nums[left],nums[right])*(right-left));
            if(nums[left]<nums[right]) ++left;
            else --right;
        }
        return maxArea;
    }
}
