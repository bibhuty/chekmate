package _01_BasicsAndMath.BitManipulation;

public class SingleNumberIII {

    //[1(1),2(10),3(11),3(11),4(100),4(100)]
    // aXORb=11
    // firstSetBit=1
    // setBucket=01, unsetBucket=10
    public int[] findNumbers(int[] nums){
        int aXORb=0;
        for(int num:nums)aXORb^=num;
        // int setBitPos=0;
        // while(setBitPos<32){
        //    if((aXORb&(1<<setBitPos))!=0) break;
        //    ++setBitPos;
        // }
        // int  firstSetBit=1<<setBitPos;


        // 10[1010]
        // -10 = (~10 + 1) -> [0101+1] = [0110]
        // 10&-10 = [1010 & 0110] = [0010]

        int setBucket=0, unsetBucket=0,firstSetBit=aXORb&(-aXORb);
        for(int num:nums){
            if((num&firstSetBit)!=0) setBucket^=num;
            else unsetBucket^=num;
        }
        return new int[]{setBucket, unsetBucket};
    }
}
