package _02_Arrays.SweepLineAndScheduling;


import java.util.ArrayList;
import java.util.Collections;

public class MeetingRoomsII {
    // Assumption:
    // - end and start of meeting are non-overlapping
    // - min-1, max+1 doesn't overflow

    //[[0,30],[5,10],[15,20]]
    // TC: O(n log n) for sorting + O(n) traversal
    // SC: O(n) auxiliary space for sorting + O(1) for result calculation
    public int minMeetingRooms(int[][] intervals) {
        if(intervals.length<=1) return intervals.length;
        var timeline=new ArrayList<int[]>();//[[0,1],[30,-1],[5,1],[10,-1],[15,1],[20,-1]]
        for(int[] interval:intervals){
            timeline.add(new int[]{interval[0],1});
            timeline.add(new int[]{interval[1],-1});
        }
        Collections.sort(timeline, (a,b)->a[0]!=b[0]?Integer.compare(a[0],b[0]):Integer.compare(a[1],b[1]));
        // [[0,1]x,[5,1]x,[10,-1],[15,1],[20,-1],[30,-1]]
        int active=0, maxActive=0;//[1,1],[2,2],[1,2],[2,2],[1,2],[0,2]
        for(int[] delta: timeline){
            active+=delta[1];
            maxActive=Math.max(active,maxActive);
        }
        return maxActive;
    }
}
