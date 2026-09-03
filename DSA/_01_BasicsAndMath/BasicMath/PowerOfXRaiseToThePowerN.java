package _01_BasicsAndMath.BasicMath;

public class PowerOfXRaiseToThePowerN  {
    /*
    * Most important questions:
    * - Range of exp
    * - Range of pow
    * - if exp==0 then pow is valid or not(assume valid)
    *
    * NOTE:
    * - It's important to catch that pow=1 is an edge case and exp=1 is also an edge case
    * - It's important to remember that int range is [-2^31, 2^31-1], so if you take absolute value of the negative most
    * value there will be overflow.
    * */
    // Holds edge cases for pow values (0,1)
    public double calculate(double exp, long pow){ // 3,-3
        if(pow==0) return 1;
        if(pow==1) return exp;
        boolean isNegative = pow<0;
        double result=pow(exp, Math.abs(pow)); // 27
        return isNegative ? 1/result : result; // 1/27
    }
    // (3,3)
    // Stack 1(3,3):
    //      pow==1 -> false
    //      res=3
    //      result=3
    //      return 3*3*3 -> 27 (ANS)
    //
    //     Stack 2(3,1):
    //          pow==1 -> true => return 3
    //
    // Space Complexity and Time Complexity is O(log n)
    public double pow(double x, long pow){
        if(pow==1) return x;
        double residual = pow%2==0 ? 1: x;
        double halfPower = pow(x,pow/2);
        return halfPower*halfPower*residual;
    }


    public double power(double x, long n){// 3,-5(101)
        boolean isNegative = n<0; // true
        n=Math.abs(n);
        double result=1;
        double bitVal=x; // probable value at the binary level if the bit is set
        while(n!=0){
            if((n&1)==1) result*=bitVal;
            bitVal*=bitVal; // we double it because moving from each bit we double the value of power
            n=n>>1;
        }
        return isNegative?1/result:result;
    }
    // TC: O(log n) SC: O(1)

}
