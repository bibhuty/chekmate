package _02_Arrays.MooresVoting.theory;

public class BoyerMooresVotingAlgorithm {
    public Integer findMajorityElement(int[] nums){
        int candidate=0,count=0,n=nums.length;
        for(int num:nums){
            if(count==0) candidate=num;
            count+=(candidate==num?1:-1);
        }
        count=0;
        for(int num:nums)
            if(candidate==num)
                ++count;
        return count>n/2? candidate : null;
    }
}
