package _02_Arrays.SweepLineAndScheduling.theory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class LineSweep {
    // new int[][]{{2, 6}, {1, 4}, {4, 7}, {8, 9=10}}
    // [[1, +1], [2, +1], [4, -1], [4, +1], [6, -1], [7, -1], [8, +1], [10, -1]]
    // 1--4   |
    // -2---6 |
    // ---4--7|
    // -------|8-10

    // Assumption: [a,b)
    // TC: O(n log n) time for sorting + O(n) for traversal in 2 loops
    // SC: O(n) auxiliary space for sorting + O(n) auxiliary space for event storage
    int sortedLineSweep(int[][] logs){
        int n=logs.length;
        var events=new ArrayList<int[]>(2*n);
        for(int i=0;i<n;++i){
            events.add(new int[]{logs[i][0],1});
            events.add(new int[]{logs[i][1],-1});
        }
        Collections.sort(events,(a, b) -> a[0] != b[0]
                ? Integer.compare(a[0], b[0])   // Rule 1: Sort by timestamp (a[0] vs b[0])
                : Integer.compare(a[1], b[1])); // Rule 2: Tie-breaker on delta (a[1] vs b[1])
        int active=0,maxActive=0;
        for(int[] event:events){
            // System.out.println(Arrays.toString(event));
            active+=event[1];
            maxActive=Math.max(maxActive,active);
        }
        return maxActive;
    }

    // TC: O(n) traversal + O(max value - min value) for timeline traversal
    // SC: O(max value - min value) for timeline storage
    int maxConcurrentBounded(int[][] logs){

        if(logs.length==0) return 0;

        int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
        for(int[] log:logs){
            max=Math.max(max,log[1]);
            min=Math.min(min,log[0]);
        }
        // int[] timeline=new int[max-min+1]; // <- [a,b) <- exclusive
        // int[] timeline=new int[max-min+2]; // <- [a,b] <- inclusive
        int[] timeline=new int[max-min+2]; // <- safe side(will overflow if the length of array is 0)

        for(int[] log: logs){
            timeline[log[0]-min]+=1;
            timeline[log[1]-min]-=1;
        }

        int events=0, maxEvents=0;
        for(int delta:timeline){
            events+=delta;
            maxEvents=Math.max(events,maxEvents);
        }
        return maxEvents;
    }


    // TC: O(n log n) insertion into red-black tree + O(n) traversal
    // SC: O(n) red black tree
    int maxConcurrentWithTreeMap(int[][] logs){
        var timeline=new TreeMap<Integer,Integer>();
        for(int[] log:logs){
            int start=log[0],end=log[1];
            timeline.merge(start,1,Integer::sum);
            timeline.merge(end,-1,Integer::sum);
        }
        int active=0,maxActive=0;
        for(int delta:timeline.values()){
            active+=delta;
            maxActive=Math.max(maxActive,active);
        }
        return maxActive;
    }
}
