package _02_Arrays.MooresVoting;

public class MajorityElement {
    // TC: O(N), SC: O(1)
    int majorityElement(int[] nums){
        int candidate=0, count=0;
        for(int num:nums){
            if(count==0) candidate=count;
            count+=(candidate==num?1:-1);
        }
        return candidate;
    }
}
