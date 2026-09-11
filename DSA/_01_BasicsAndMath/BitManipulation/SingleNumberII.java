package _01_BasicsAndMath.BitManipulation;

// SC: O(1), TC: O(n)
public class SingleNumberII {
    public int singleNumber(int[] nums) {
        int[] bitVal=new int[64];
        for(int num:nums){
            int offset=num<0?32:0;
            long current=Math.abs((long)num);
            for(int i=0;i<32 && current!=0 ;++i){
                if((current&1)!=0) ++bitVal[offset+i];
                current>>=1;
            }
        }
        long current=1, positive=0, negative=0;
        for(int i=0;i<32;++i){
            if(bitVal[i]%3!=0) positive+=current;
            if(bitVal[(32+i)]%3!=0) negative-=current;
            current*=2;
        }
        return (int)(positive + negative);
    }

    public int singleNumberBitWise(int[] nums) {
        int result=0;
        for(int i=0;i<32;++i){
            int count=0;
            for(int num:nums)
                if((num & (1<<i))!=0)
                    ++count;
            if(count%3!=0)
                result|=(1<<i);
        }
        return result;
    }

    public int singleNumber2Ultimate(int[] nums){
        int singleOccurrence=0, doubleOccurrence=0;
        // 7[111] -> [7,7,7]
        // [s,d]->[0,0]->[111,000]->[000,111]->[000,000]
        for(int num:nums){
            // 1. When a digit first comes to single occurrence, there's
            //    no double occurrence, hence we keep it
            // 2. When a digit comes for the second time, that part is
            //    negated in single occurrence and put up in double occurrence
            // 3. When it comes for the third time, we negate the same using
            //    the rule x&~x in single occurrence and x^x in double occurrence
            singleOccurrence=(singleOccurrence^num)&~doubleOccurrence;
            doubleOccurrence=(doubleOccurrence^num)&~singleOccurrence;
        }
        return singleOccurrence;
    }


}
