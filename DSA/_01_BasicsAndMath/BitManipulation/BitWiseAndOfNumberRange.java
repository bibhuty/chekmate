package _01_BasicsAndMath.BitManipulation;

public class BitWiseAndOfNumberRange {

    // SC=TC=O(1)
    // Mistake: I thought finding out the sequence of matching 1s is the answer
    //          instead of finding out the first contiguous matching sequence
    public int bitWiseAndOfRange(int left, int right){
        int shifts=0;
        // while(shifts<32){
        //    if((left>>shifts)==(right>>shifts)) break;
        //    ++shifts;
        // }
        while ((left>>shifts)!=(right>>shifts)) ++shifts;
        return (left>>shifts)<<shifts;
    }

    public int bitWiseAndOfRangeI(int left, int right){
        int currentLeft=left, currentRight=right, shifts=0;
        while(currentLeft!=currentRight){
            currentLeft>>=shifts;
            currentRight>>=shifts;
            ++shifts;
        }
        return currentLeft<<shifts;
    }

    // When we unset the rightmost bit of the rightmost value incrementally we shrink the value
    // 2 cases:
    //   case 1: if the right's farthest bit is at the higher level than that of left we eventually
    //           come to the position where it shrinks to zero since there's no way it can match
    //   case 2: if the right's farthest bit and left's farthest bit belong to the same placeholder
    //           we reduce the right's farthest bit till the point it goes below left's entire value
    //           so that we know what's the common sequence between them
    //           [<common sequence><any bit low>,<common sequence><any bit high>] -> aim is to eliminate any bit high
    public int bitWiseAndOfRangeBrianKernighanAlgorithm(int left, int right){
        while(right>left) right&=(right-1);
        return right;
    }
}
