package _02_Arrays.SweepLineAndScheduling;

import java.util.TreeMap;

public class MinimumPlatforms {

    // -----
    //   -------
    //       ---
    // -----
    //       ---
    //    ------

    // Note: inclusive
    // Figuring out the meaning of the value of 1 and -1 in the line sweep in very important
    // Max interval means the no of intervals that totally overlap among each other which can't
    // be divided into groups even if you remove one of the interval in between

    // Important assumptions:
    //     - range of intervals will confer to standard data structure
    //     - value is in the range so that we're not overflowing the integer with +1/-1

    // [[5,10],[6,8],[1,5],[2,3],[1,10]]
    // 1(1)---------11(-1)
    // 1(1)----6(-1)
    // ----5(1)-----11(-1)
    // -----6(1)--9(-1)
    // -2(1)-4(-1)

    // TC: O(n log n) for insertion + O(n) traversal
    // SC: O(2n) for interval storage
    public int minGroups(int[][] intervals){
        if(intervals.length<=1) return intervals.length;
        var timeline=new TreeMap<Integer,Integer>();//(1->2,2->1,4->-1,5->1,6->0,9->-1,11->-2)
        for(int[] interval:intervals){
            int left=interval[0], right=interval[1]+1;
            timeline.merge(left,1,Integer::sum);
            timeline.merge(right,-1,Integer::sum);
        }
        int active=0, maxActive=0;
        for(int delta: timeline.values()){//[values,active,maxActive]->[1,2,2]->[2,3,3]->[4,2,3]->[5,3,3]->[6,3,3]->[9,2,3]->[11,0,3]
            active+=delta;
            maxActive=Math.max(active, maxActive);
        }
        return maxActive;
    }
}
