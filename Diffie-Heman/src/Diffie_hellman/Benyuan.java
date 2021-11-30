package Diffie_hellman;

import java.math.BigInteger;
import java.util.Random;

public class Benyuan {

    public static BigInteger[] get_p_a() {
        Random random=new Random();
        BigInteger p,q,a,x,y;
        while(true) {
//			q=BigInteger.probablePrime(63, random);
//			p=q.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(1));
//			if(p.isProbablePrime(20)) break;
            //p是安全素数了，p=q*2+1(p、q都是素数）

            p=BigInteger.probablePrime(64, random);
            BigInteger r=p.subtract(BigInteger.ONE).mod(BigInteger.valueOf(2));
            if(r.equals(BigInteger.ZERO)) {
                q=p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
                break;
            }
        }
//		while(true) {
//			//随机选取a，如果a^2 mod p!=1 且 a ^ q mod p!=1，那么a就是本原元
//			a=BigInteger.probablePrime(32, random);
//			x=expMode(q, BigInteger.valueOf(2), p);
//			y=expMode(a, q, p);
//			if((x.equals(BigInteger.valueOf(1))==false)&&(y.equals(BigInteger.ONE)==false)){
//				break;
//			}
//		}
        //求本原a
        while(true) {
            a=BigInteger.probablePrime(32, random);
            BigInteger temp=p.subtract(BigInteger.ONE).divide(q);
            if(!expMode(a, temp, p).equals(BigInteger.valueOf(0))) {
                break;
            }


        }
        return new BigInteger[] {
                p,a
        };
    }
    //计算base的exponentmod N
    public static BigInteger expMode(BigInteger base, BigInteger exponent, BigInteger n) {
        BigInteger result=BigInteger.ONE;
        BigInteger t=base;
        char[] binaryArray=new StringBuilder(exponent.toString(2)).reverse().toString().toCharArray();
        int r=binaryArray.length;
        //得到指数的二进制串以及串的长度
        for(int i=0;i<r;i++) {
            if(binaryArray[i]=='1') {
                result=result.multiply(t).mod(n);
            }
            t=t.multiply(t).mod(n);
        }
//		指数m写成二进制串bk、bk-1、bk-2、、、b0即有
//		m=bk*2^k+(bk-1)*2^(k-1)+(bk-2)*2^(k-2)+...+b0
//		a的m次方等于
//		(...((（a^bk）^2)a^bk-1)^2)...a^b1)^2)a^b0
        return result;
    }
}
