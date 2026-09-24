package _02_Arrays.TwoPointers;

import java.util.*;

public class ThreeSum {

    // nums.length == 3000 tells you that its okay if the TC and SC is O(n^2)
    // nums[i] in the range of 10^5 tells you that the sums needed to be calculated
    // will be integer range


    // Implementation is tough not the problems' solution itself
    // [-1,0,1,->2,->-1,-4]
    // TC: O(nlog n+ n^2)
    // SC: O(n^2)
    // AS: O(N^2)
    public List<List<Integer>> calculate(int[] nums) {
        final int n = nums.length;
        // x+y+z=0;
        // Think of storing y+z
        var sumPair = new HashMap<Integer, Set<Integer>>(); // [-5->[-4],1->[-1,0],-2->[-4],3->[1],0->[-1],-3->[-4],2->[0],-1->[-1],-4->[-4]]
        var tripletSum = new HashMap<Integer, Set<Integer>>(); //[0->[-1]],[-1,[-1,0]]
        for (int i = n - 2; i >= 0; --i) {
            if (i <= n - 3) {
                if (sumPair.containsKey(-nums[i])) {
                    var sumSet = tripletSum.computeIfAbsent(nums[i], _ -> new HashSet<>());
                    sumSet.clear();
                    sumSet.addAll(sumPair.get(-nums[i]));
                }
            }
            for (int j = i + 1; j < n && i != 0; ++j) {
                sumPair.computeIfAbsent(nums[i] + nums[j], _ -> new HashSet<>()).add(Math.min(nums[i], nums[j]));
            }
        }
        List<List<Integer>> result = new ArrayList<>();//[0,-1,1],[-1,-1,2]
        var seen = new HashSet<String>();// "-1 1","-1 2"
        for (int first : tripletSum.keySet()) {
            for (int second : tripletSum.get(first)) {
                var triplet = new ArrayList<Integer>();
                var third = -(first + second);
                var key = Math.min(first, Math.min(second, third)) + " " + Math.max(first, Math.max(second, third));
                if (seen.contains(key)) continue;
                seen.add(key);
                triplet.add(first);
                triplet.add(second);
                triplet.add(third);
                result.add(triplet);
            }

        }
        return result;
    }

    // TC: O(nlog n+ n^2)
    // SC: O(n^2)
    // AS: O(1)
    public List<List<Integer>> calculateWith2Pointer(int[] nums) {
        Arrays.sort(nums); // -1 -1 0 1 2 4
        // initial thought was to move the 2 pointer before and after the pivot point
        // it means calculating the same answer again and again hence for a sorted array
        // if we calculate the answer after the pivot point then we can ensure that the
        // answer we're going to get are unique with 2 special cases
        // if some number !=0 have 2 or more occurrence then its better to calculate the
        // answer only once starting the left pointer from the last occurrence of the number
        // if we've zero occurring more than 2 times then we know what to do
        // same thing needs to be done for both left and right pointers where we need to keep the pointers around
        // the second last occurrence in each direction
        int n = nums.length;
        var triplets = new ArrayList<List<Integer>>(); // new ArrayList<ArrayList<Integer>>() is wrong(Why?)
        for (int i = 0; i < n - 2; ++i) {
            int left = i, right = n - 1;
            while (left + 1 <= right && nums[left + 1] == nums[i])
                ++left; // left+1<right -> edge case all zeroes will not be covered
            int indexOfLastOccurrence = left;
            if (left == i) ++left;
            while (left < right) {
                // Moving left/right will be premature here if we're willing to count duplicates
                int sum = nums[i] + nums[left] + nums[right];
                if (sum <= 0) {
                    if (sum == 0) {
                        var triplet = new ArrayList<Integer>();
                        triplet.add(nums[left]);
                        triplet.add(nums[right]);
                        triplet.add(nums[i]);
                        triplets.add(triplet);
                    }
                    int _left = left;
                    // while(nums[left]==nums[_left] && left<=right)++left; <- silly mistake
                    while (left <= right && nums[left] == nums[_left]) ++left;
                } else {
                    int right_ = right;
                    // while(nums[right]==nums[right_] && right>=left)--right; <- silly mistake
                    while (right >= left && nums[right] == nums[right_]) --right;
                }
            }

            i = indexOfLastOccurrence;
        }
        int zeroes = 0;
        for (int num : nums) {
            if (num == 0) ++zeroes;
            if (zeroes == 3) {
                triplets.add(new ArrayList<>() {{
                    add(0);
                    add(0);
                    add(0);
                }});
                break;
            }
        }
        return triplets;
    }

    // TC: O(nlog n+ n^2)
    // SC: O(n^2)
    // AS: O(1)
    public List<List<Integer>> calculateWith2PointerOptimised(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; ++i) {
            if (nums[i] > 0) break; // a solution is never possible if the first number is positive

            if (i > 0 && nums[i] == nums[i - 1]) continue; // solution for the same is already evaluated

            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    triplets.add(Arrays.asList(nums[i], nums[left], nums[right])); // trick to avoid multi line arraylist creation
                    while (left + 1 <= right && nums[left + 1] == nums[left]) ++left;
                    while (right - 1 >= left && nums[right - 1] == nums[right]) --right;

                    // moving towards next valid triplet
                    ++left;
                    --right;
                } else if (sum > 0) {
                    --right; // reduce the sum
                } else {
                    ++left; // increase the sum
                }
            }
        }
        return triplets;
    }

    // TC: O(n^2)
    // SC: O(n^2)
    // AS: O(n)
    public List<List<Integer>> threeSumWithoutSorting(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> dups = new HashSet<>();
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; ++i)
            if (dups.add(nums[i])) { // it is the equivalent operation from sorted approach if(i>0 && nums[i]==nums[i-1]) continue;
                for (int j = i + 1; j < nums.length; ++j) {
                    // x+y+z=0
                    // x=-y-z
                    // below stores the RHS
                    int complement = -nums[i] - nums[j];
                    // lookup of the LHS in case it belongs to same loop
                    // similar to restricting calculation inside the 2 pointer
                    if (seen.getOrDefault(complement,-1) == i) {
                        List<Integer> triplet = Arrays.asList(
                                nums[i],
                                nums[j],
                                complement);
                        Collections.sort(triplet); // takeaway of how list hashes its value inside set
                        res.add(triplet);
                    }
                    // marker of value as a part of 2 pointer
                    seen.put(nums[j], i);
                }
            }
        return new ArrayList(res);
    }
}
