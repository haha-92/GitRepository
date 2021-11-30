package tool;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomBigInteger {
    public static BigInteger getRandomBigInteger() {
        String randStr ="01";
        StringBuilder generateStr=new StringBuilder();
        SecureRandom rand=new SecureRandom();
        int len=(rand.nextInt(50)+15);
//		len=64;
        for(int i=0;i<len;i++) {
            int randnum=rand.nextInt(randStr.length());
            generateStr.append(randStr.substring(randnum, randnum+1));
        }
//        System.out.println(generateStr);
        BigInteger temp=new BigInteger(generateStr.toString(),2);
//        System.out.println(generateStr.length());
//        System.out.println("generateStr:"+temp);
        return temp;
    }
}
