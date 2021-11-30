package RSA;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
 
/**
 *
 * @author steven
 */
public class Utils {
 
    private static Random ran = null;
 
    static {
        ran = new SecureRandom();
    }
 
    /**
     * ���� base^exp % n
     *
     * @param base
     * @param exp
     * @param n
     * @return
     */
    public static BigInteger expmod(int base, BigInteger exp, BigInteger n) {
        if (exp.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
 
        if (!exp.testBit(0)) {//���Ϊż��
            return expmod(base, exp.divide(BigInteger.valueOf(2)), n).pow(2).remainder(n);
        } else {
            return (expmod(base, exp.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), n).pow(2).multiply(BigInteger.valueOf(base))).remainder(n);
        }
    }
 
    /**
     * �������, �������false, ��n�϶�Ϊ����, ���Ϊtrue, ��n��һ�����ϵĸ���Ϊ����
     *
     * @param n
     * @return
     */
    public static boolean fermatTest(BigInteger n) {
        int base = 0;
        if (n.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0) {
            base = ran.nextInt(n.intValue() - 1) + 1;
        } else {
            base = ran.nextInt(Integer.MAX_VALUE - 1) + 1;
        }
        if (expmod(base, n, n).equals(BigInteger.valueOf(base))) {
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * Miller-Rabin����
     *
     * @param n
     * @return
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
        while (!m.testBit(0)) {
            m = m.shiftRight(1);
            BigInteger z = expmod(base, m, n);
            if (z.equals(thisMinusOne)) {
                break;
            } else if (z.equals(BigInteger.ONE)) {
 
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
        if (sizeInBits < 100) {
            tryTime = 50;
        }
 
        if (sizeInBits < 256) {
            tryTime = 27;
        } else if (sizeInBits < 512) {
            tryTime = 15;
        } else if (sizeInBits < 768) {
            tryTime = 8;
        } else if (sizeInBits < 1024) {
            tryTime = 4;
        } else {
            tryTime = 2;
        }
        return isPrime(n, tryTime);
    }
 
    /**
     * ��ε�����������, �ж������n�Ƿ�Ϊ����
     *
     * @param n
     * @param tryTime
     * @return
     */
    public static boolean isPrime(BigInteger n, int tryTime) {
        for (int i = 0; i < tryTime; i++) {
            if (!passesMillerRabin(n)) {
                return false;
            }
        }
        return true;
    }
 
    /**
     * ����һ��n bit������
     *
     * @param bitCount
     * @return
     */
    public static BigInteger getPrime(int bitCount) {
        //�������һ��n bit�Ĵ�����
        BigInteger init = new BigInteger(bitCount, ran);
        //���nΪż��, ���һ��Ϊ����
        if (!init.testBit(0)) {
            init = init.setBit(0);
        }
        int i = 0;
        //������������, ƽ��ֻ��Ҫ����n������, �����ҵ�һ������
        while (!isPrime(init)) {
            i++;
            init = init.add(BigInteger.valueOf(2));
        }
        //System.out.println(String.format("try %d\ttimes", i));
        return init;
    }
}