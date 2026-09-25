package _02_Arrays.IntervalOverlaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {

    // Assumptions intervals always have positive length
    // Question: [1,2],[2,4] -> is overlapping

    // [[1,4],[2,5],[6,7]]
    // TC: O(nlog n) merge sort
    // SC: O(n)
    // AS: O(n)
    public int[][] merge(int[][] intervals){
        // Comparator.comparing vs Integer.compare
        Arrays.sort(intervals,(o1,o2)->{
            var firstCompare=Integer.compare(o1[0],o2[0]);
            var secondCompare=Integer.compare(o1[1],o2[1]);
            return firstCompare!=0?firstCompare:secondCompare;
        });
        var result=new ArrayList<int[]>();// [i, left, right, result]->[0,1,4,{}]->[1,1,5,{}]->[2,1,5,{[1,5]}]---[3,6,7,{[1,5],[6,7]}]
        int left=intervals[0][0], right=intervals[0][1],i=0;
        int n = intervals.length;
        while(i< n){
            // if(intervals[i][0]<=right && right<=intervals[i][1]){ <- wrong merge [1,4],[2,3] is a merge also


            // Below is the mistake I did, I avoided visualisation even if all the tools were available
            // -----
            // -------
            // -------

            // ------
            //     ------
            // ----------

            // --------
            //     ---
            // --------
            if(left<=intervals[i][0] && intervals[i][0]<=right){
                right=Math.max(right,intervals[i][1]);
            }else{
                int[] merged=new int[]{left,right};
                result.add(merged);
                left = intervals[i][0];
                right = intervals[i][1];
            }
            ++i;
            if(i==n){
                int[] merged=new int[]{left,right};
                result.add(merged);
            }
        }
        int[][] resultvals=new int[result.size()][2];
        for(i=0;i<result.size();++i)resultvals[i]=result.get(i);
        return resultvals;
    }

    // TC: O(n log n) for sorting + O(n) for traversal
    // SC: O(n) for sorting and storing
    public int[][] mergeCleanly(int[][] intervals){
        if(intervals.length<=1) return intervals;

        Arrays.sort(intervals,(o1,o2)->Integer.compare(o1[0],o2[0]));

        List<int[]> results=new ArrayList<>(){{
            add(intervals[0]);
        }};
        for(int i=1;i<intervals.length;++i){
            // Get the last valid interval
            // int[] lastMerged=results.get(results.size()-1);
            int[] lastMerged=results.getLast(); // <-better alternative

            // if the beginning of the next object which is always greater than
            // or equals the beginning of prev object courtesy to sorting happens
            // to lie in between the last valid interval, its time to increase
            // the span of the interval wherever possible
            if(intervals[i][0]<=lastMerged[1]){
                lastMerged[1]=Math.max(lastMerged[1],intervals[i][1]);
            }else{
                // get it ready for the next interval since they are not
                // part of the current interval
                results.add(intervals[i]);
            }
        }
        return results.toArray(new int[results.size()][2]); // <- key takeaway for the syntax
    }
}
