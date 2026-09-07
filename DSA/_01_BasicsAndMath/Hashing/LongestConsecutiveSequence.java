package _01_BasicsAndMath.Hashing;

import java.util.HashMap;
import java.util.HashSet;

public class LongestConsecutiveSequence {

    /*
     * Questions: Do you consider 1,1 as consecutive or not?
     * Can the length of the array be 0?
     */
    // [1,2,2,3,5,6]
    // TC: O(n)
    // SC: O(n)
    public int lcs(int[] nums) {
        var next = new HashMap<Integer, Boolean>();
        var prev = new HashMap<Integer, Boolean>();
        for (int num : nums) {
            if (prev.containsKey(num)) continue;
            prev.putIfAbsent(num, false);//[[1,false],[2,true],[3,true],[5,false],[6,true]]
            next.putIfAbsent(num, false);//[[1,true],[2,true],[3,false].[5,true],[6,false]]
            if (prev.containsKey(num + 1)) {
                prev.put(num + 1, true);
                next.put(num, true);
            }
            if (next.containsKey(num - 1)) {
                next.put(num - 1, true);
                prev.put(num, true);
            }
        }
        int maxLength = 0;//0->3
        // for(int key: nums){ -> recipe for O(N*N)
        for (int key : prev.keySet()) {
            if (!prev.get(key)) {
                int counter = 0;//1,
                int current = key;
                while (next.containsKey(current)) {
                    ++current;//2->3->4
                    ++counter;//1->2->3
                }
                maxLength = Math.max(maxLength, counter);
            }
        }
        return maxLength; // 3
    }

    // TC = SC = O(N)
    public int lcsWithHashSet(int[] nums) {
        var set = new HashSet<Integer>();
        for (int num : nums) set.add(num);
        int max = 0;
        // for(int num:nums){ -> Recipe for TLE[1,1,1,1,1,1,....half of the array,2,3,....,n/2 consecutively for second half] -> O(N^2)
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int key = num;
                while (set.contains(key))
                    ++key;
                max = Math.max(max, key - num);
            }
        }

        // for (int num : set) { -> Recipe for ConcurrentModificationException
        for (int num : nums) {
            if (!set.contains(num - 1)) {
                int key = num;
                while (set.contains(key)) {
                    set.remove(key);
                    ++key;
                }
                max = Math.max(max, key - num);
            }
        }
        return max;
    }
}
