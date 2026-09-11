package _02_Arrays.PrefixSum;

import java.util.HashMap;

public class SubarraySumEqualsK {
    // Assumptions:
    // - Addition of all the elements of array is an integer

    // [1], 1
    // [1,1,1], 2
    int subarraySum(int[] nums, int k){
        var subarrayIndices=new HashMap<Integer, Integer>(){{
            put(0,1); // Edge case: sum till that point
        }};
        int sum=0, total=0;
        for(int num:nums){ // 1 -> 1 -> 1
            sum+=num; // 1 -> 2 -> 3
            // I assume that till this point the total sum is more than k
            // Let's say I am x more than k then mathematically
            // sum=x+k
            // => x=sum-k
            // => we need to find the count of occurrence of x
            total+=(subarrayIndices.getOrDefault(sum-k, 0)); // (-1)0 -> (0)1-> (1)->2
            subarrayIndices.put(sum, subarrayIndices.getOrDefault(sum,0)+1); // [[0,1],[1,1],[2,1]]
        }
        return total;
    }
}
