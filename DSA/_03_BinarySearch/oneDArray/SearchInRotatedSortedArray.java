package _03_BinarySearch.oneDArray;

public class SearchInRotatedSortedArray {
    // Questions in my mind:
    //  - Array size is always >=1
    //  - Unique elements in an array
    //  - If answer not available -1

    // [4,5,6,7,0,1,2] -> length=7
    // [0,1,2,3,4,5,6] <= indices
    // TC:O(log n)
    // SC:O(1)
    // FFFFFTTTTT
    public int findPivotIndex(int[] nums){
        int left=0, right=nums.length-1;
        while(left<right){ // [0,6]->[4,6]-> [4,5] -> [4,4]
            int mid=left+(right-left)/2; // 3 -> 5 -> 4 // T preserve it because it can be the first solution
            if(nums[right]>nums[mid]){// false -> true -> true
                // if the right most value is greater than the middle
                // then there may be more values less than or equal to
                // middle which can satisfy this condition. So we shrink
                // right to incorporate the middle since it can be one of
                // the solution
                right=mid;
            }else{ // F so definitely solution is after it
                // if rightmost value is less than middle then middle is
                // definitely not the index for whom all the elements to the
                // right of it are greater than the same, so we shrink left
                // to reach to the pivot element
                left=mid+1;
            }
        }
        return left; // 4
    }

    // [4,5,6,7,0,1,2] -> length=7
    // [0,1,2,3,4,5,6] <= indices
    // start=4
    // target=0

    int jump,n;
    int idx(int idx){
        return (idx-jump+n)%n;
    }

    // TC:O(log n)
    // SC:O(1)
    public int binarySearchOnRotatedArray(int[] nums, int start, int target){
        n=nums.length;// 7
        jump=(n-start)%n;//3
        int left=0, right=n-1;
        while(left<right){//[0,6]->[0,3]->[0,1]->[0,0]
            int mid=left+(right-left)/2; // 3->1->0
            // int eqMid = idx(mid); // 0->5->4
            int eqMid = (mid+start)%n; // Bangle analogy
            if(nums[eqMid]>=target){ // true->true->true
                right=mid;
            }else{
                left=mid+1;
            }
        }
        // return nums[idx(left)]==target?idx(left):-1; //4
        return nums[(left+start)%n]==target?(left+start)%n:-1; //4
    }

    // TC:O(log n)
    // SC:O(1)
    public int search(int[] nums, int target){
        int start=findPivotIndex(nums);
        return binarySearchOnRotatedArray(nums,start,target);
    }

    // Our current graph in the worst case will consist of 2 increasing straight
    // lines where if there are 2 straight lines and you plot it along x-axis as
    // index and y-axis as values the lowest value of the first straight line will
    // be more than the highest value of the second straight line.

    // So here we're trying to find to which slope the target belongs to and
    // progressively narrowing there.

    // Missed Case: Left and Right slopes can be single points too

    // array [4,5,6,7,0,1,2]
    // index [0,1,2,3,4,5,6]
    public int searchInSinglePass(int[] nums, int target){ // 0 is the target
        int left=0, right=nums.length-1;
        while(left<right){
            int mid=left+(right-left)/2;
            int leftNum = nums[left];
            int midNum = nums[mid];
            int rightNum = nums[right];
            if(midNum==target) return mid;
            if(leftNum<=midNum){
                if(leftNum<=target && target<midNum){
                    right=mid-1;
                }else{
                    left=mid+1;
                }
            }else if(midNum<=rightNum){
                if(midNum<target && target<=rightNum){
                    left=mid+1;
                }else{
                    right=mid-1;
                }
            }
        }
        return nums[left]==target ? left : -1;
    }
}
