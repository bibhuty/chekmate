package _02_Arrays.IntervalOverlaps;

import java.util.Arrays;

public class NonOverlappingIntervals {
    // Important is defining what is the meaning of overlapping is
    // In this case inclusion of boundary doesn't mean overlapping

    // Intuition
    //   ---
    //    ------
    //      ---
    //        ------
    // Among overlaps you have to chose min overlap

    // [[1,2],[2,3],[3,4],[1,3]]
    // [[1,2]x,[1,3]x,[2,3]x,[3,4]x]

    // TC: O(n log n) for sorting + O(n) for the interval traversal
    // SC: O(n) auxiliary space for sorting + O(1) solution
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length < 2) return 0;
        // Arrays.sort(intervals,(o1,o2)->o1[0]-o2[0]); // bad approach because if the things are not properly bounded we'll get integer overflow
        Arrays.sort(intervals, (o1,o2)->Integer.compare(o1[0],o2[0]));
        int left=intervals[0][0], right=intervals[0][1], removed=0; // [left,right,removed]=>[1,2,0]->[1,2,1]->[2,3,1]->[3,4,1]
        for(int i=1;i<intervals.length;++i){
            if(intervals[i][0] < right){
                // --removed; <- silly mistake
                ++removed;
                if(intervals[i][1]<right){
                    left=intervals[i][0];
                    right=intervals[i][1];
                }
            }else{
                left=intervals[i][0];
                right=intervals[i][1];
            }
        }
        return removed;
    }

    // --------
    //         ----
    //      ------
    //   ------
    public int eraseOverlappingIntervalsIdeal(int[][] intervals){
        if(intervals.length<2) return 0;

        Arrays.sort(intervals,(o1,o2)->Integer.compare(o1[1],o2[1]));

        int erase=0,right=intervals[0][1],left=1;
        while(left<intervals.length){
            int nextLeft=intervals[left][0];
            if(nextLeft<right) ++erase;
            else right=intervals[left][1];
            ++left;
        }
        return erase;
    }
}
