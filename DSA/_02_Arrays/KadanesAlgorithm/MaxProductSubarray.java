package _02_Arrays.KadanesAlgorithm;

public class MaxProductSubarray {
    /*
    Assumptions:
        - Product will fit int values(or else I'd take long)
        - nums have always at least one element
        - if nums has one element then that's the answer
    Subarray with the largest product:
        - Use Kadane's algorithm but instead of summing up
          use the product operation
    Mistake[Important Case]:
        - Product of 2 negative number is a positive number
        - Not mutating interdependent variables via temp or equivalent methods
    Good catch:
        - Handling zeroes
     */

    public int maxProduct(int[] nums){
        int maxPositive=1, maxNegative=1, max=nums[0];
        for(int num:nums){
            max=Math.max(max, Math.max(maxPositive*num, maxNegative*num));
            int tempMaxNegative=maxNegative; // not adding this was the mistake
            maxNegative=Math.min(num,Math.min(maxNegative*num, maxPositive*num));
            maxPositive=Math.max(num,Math.max(tempMaxNegative*num, maxPositive*num));
            if(maxNegative>=0)maxNegative=1;
            if(maxPositive<=0)maxPositive=1;
        }
        return max;
    }
}
