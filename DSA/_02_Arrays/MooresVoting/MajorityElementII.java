package _02_Arrays.MooresVoting;

import java.util.*;

public class MajorityElementII {

    // For [2,2] instead of 2 the answer is [2,2]
    public List<Integer> majorityElementWrongAnswer1(int[] nums) {
        int candidate1=0, count1=0, candidate2=0, count2=0, n=nums.length;
        for(int num:nums){
            if(count1==0) candidate1=num;
            else if(count2==0) candidate2=num;

            if(candidate1==num) ++count1;
            else if(candidate2==num) ++count2;
            else {
                --count1;
                --count2;
            }
        }
        count1=0;
        count2=0;
        for(int num:nums){
            if(num==candidate1) ++count1;
            if(num==candidate2) ++count2;
        }

        // mistake: knowingly I didn't calculate the occurence
        // was blindly returing candidates and when figured out
        // I need that forgot to initialise n.
        List<Integer> result=new ArrayList<>();
        if(count1>n/3) result.add(candidate1);
        if(count2>n/3) result.add(candidate2);

        return result;
    }


    // misses the edge case [0,0,0]
    public List<Integer> majorityElementWrongAnswer2(int[] nums) {
        int candidate1=0, count1=0, candidate2=0, count2=0, n=nums.length;
        for(int num:nums){
            if(candidate1==num && count1>0) ++count1;
            else if(candidate2==num && count2>0) ++count2;
            else if(count1==0){
                candidate1=num;
                ++count1;
            }else if(count2==0){
                candidate2=num;
                ++count2;
            }else{
                --count1;
                --count2;
            }
        }
        count1=0;
        count2=0;
        for(int num:nums){
            if(num==candidate1) ++count1;
            if(num==candidate2) ++count2;
        }

        // mistake: knowingly I didn't calculate the occurence
        // was blindly returing candidates and when figured out
        // I need that forgot to initialise n.
        List<Integer> result=new ArrayList<>();
        if(count1>n/3) result.add(candidate1);
        if(count2>n/3) result.add(candidate2);

        return result;
    }

    // TC: O(n), SC: O(1)
    public List<Integer> majorityElement(int[] nums) {
        int count1=0,count2=0,candidate1=0,candidate2=0;
        for(int num:nums){

            if(count1==0 && candidate2!=num) candidate1=num;
            else if(count2==0 && candidate1!=num) candidate2=num;

            if(candidate1==num) ++count1;
            else if(candidate2==num) ++count2;
            else{
                --count1;
                --count2;
            }
        }

        int n=nums.length, occurence1=0, occurence2=0;
        for(int num:nums){
            if(count1>0) occurence1+=(candidate1==num?1:0);
            if(count2>0) occurence2+=(candidate2==num?1:0);
        }
        List<Integer> result=new ArrayList<>();
        if(occurence1>n/3) result.add(candidate1);
        if(occurence2>n/3) result.add(candidate2);
        return result;
    }
}
