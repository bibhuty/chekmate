package _02_Arrays.Sorting;

public class HIndex {
    // TC = SC = O(n)
    public int hIndex(int[] citations) {
        // 3 0 6 1 5
        int n=citations.length;
        // You missed this and only have to look at the solution to figure out that n+1 should be the case
        int[] papers=new int[n+1]; //[1, 1, 0, 1, 0, 2]
        for(int citation: citations) ++papers[Math.min(n,citation)];
        int result=0, total=0;
        for(int i=n;i>=0;--i){ // 2 -> 0 -> 1
            total+=papers[i]; // 2 -> 2 -> 3
            int currentH=Math.min(i, total);
            result=Math.max(result,currentH); // 2 -> 2 -> 3
            if(currentH==i) break;
        }
        return result;
    }
}
