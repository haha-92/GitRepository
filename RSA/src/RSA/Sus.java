package rsa;
 
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Sus {
 
    private static Random ran = null;
 
    static {
        ran = new SecureRandom();
    }
 
    /**
     * ���� base^exp % n
     */
    public static BigInteger expmod1(int base, BigInteger exp, BigInteger n) {
        //base^0=1
    	if(exp.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        if(!exp.testBit(0)) {//���Ϊż��
            return expmod(base, exp.divide(BigInteger.valueOf(2)), n).pow(2).remainder(n);
        }else {
            return (expmod(base, exp.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), n).pow(2).multiply(BigInteger.valueOf(base))).remainder(n);
        }
    }
	public static  BigInteger expmod(BigInteger a,BigInteger b,BigInteger n){
		BigInteger t=a,d=BigInteger.ONE;
		while(b.compareTo(BigInteger.ZERO)>0) {
			if((b.and(BigInteger.ONE)).equals(BigInteger.ONE))
				d=fastAdd(d,t,n);
				//d=d.multiply(t).mod(n);
			t=fastAdd(t,t,n);
			//t=t.multiply(t).mod(n);
			b = b.shiftRight(1); //b=b>>1;
		}
		return d;
	}
	//���ٻ�����a*b mod n��
	BigInteger fastAdd(BigInteger a,BigInteger b,BigInteger n) {
		BigInteger t=a,d=BigInteger.ZERO;
		while(b.compareTo(BigInteger.ZERO)>0) {  //b>0
			if((b.and(BigInteger.ONE)).equals(BigInteger.ONE)) //(b&1)==1
				d = (d.add(t)).remainder(n); //(d+t)%n
			t = (t.add(t).remainder(n)); //(t+t)%n;
			b = b.shiftRight(1); //b>>1;
		}
		return d;
	}

    /**
     * �������, �������false, ��n�϶�Ϊ����, ���Ϊtrue, ��n��һ�����ϵĸ���Ϊ����
     */
    public static boolean fermatTest(BigInteger n) {
        int base = 0;
        if(n.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0) {
            base = ran.nextInt(n.intValue() - 1) + 1;
        } else {
            base = ran.nextInt(Integer.MAX_VALUE - 1) + 1;
        }
        //base^n=base mod n
        if(expmod(base, n, n).equals(BigInteger.valueOf(base))) {
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * Miller-Rabin����
     */
    public static boolean passesMillerRabin(BigInteger n) {
        int base = 0;
        if (n.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0) {
            base = ran.nextInt(n.intValue() - 1) + 1;
        } else {
            base = ran.nextInt(Integer.MAX_VALUE - 1) + 1;
        }
 
        BigInteger thisMinusOne = n.subtract(BigInteger.ONE);
        BigInteger m = thisMinusOne;
        while(!m.testBit(0)) {
            m = m.shiftRight(1);
            //z=base^(n-1) mod n
            BigInteger z = expmod(base, m, n);
            if (z.equals(thisMinusOne)) {//z=n-1
                break;
            } else if (z.equals(BigInteger.ONE)) {//z=1
 
            } else {
                return false;
            }
        }
        return true;
    }
 
    public static boolean isPrime(BigInteger n) {
        //copy��jdkԴ��, n��bit��Խ��, ��Ҫ�ļ�������Խ��
        //ע��˵�Ǹ��ݱ�׼ ANSI X9.80, "PRIME NUMBER GENERATION, PRIMALITY TESTING, AND PRIMALITY CERTIFICATES".
        //�Ҳ�֪��Ϊʲô
        int sizeInBits = n.bitLength();
        int tryTime = 0;
        if(sizeInBits < 100) {
            tryTime = 50;
        }
        if(sizeInBits < 256) {
            tryTime = 27;
        } else if(sizeInBits < 512) {
            tryTime = 15;
        } else if(sizeInBits < 768) {
            tryTime = 8;
        } else if(sizeInBits < 1024) {
            tryTime = 4;
        } else {
            tryTime = 2;
        }
        return isPrime(n, tryTime);
    }
 
    /**
     * ��ε�����������, �ж������n�Ƿ�Ϊ����
     */
    public static boolean isPrime(BigInteger n, int tryTime) {
        for(int i=0;i<tryTime;i++) {
            if(!passesMillerRabin(n)) {
                return false;
            }
        }
        return true;
    }
 
    /**
     * ����һ��n bit������
     */
    public static BigInteger getPrime(int bitCount) {
        //�������һ��n bit�Ĵ�����
        BigInteger init = new BigInteger(bitCount, ran);
        //���nΪż��, ���һ��Ϊ����
        if (!init.testBit(0)) {
            init = init.setBit(0);
        }
        //������������, ƽ��ֻ��Ҫ����n������, �����ҵ�һ������
        while (!isPrime(init)) {
            init = init.add(BigInteger.valueOf(2));
        }
        return init;
    }
}