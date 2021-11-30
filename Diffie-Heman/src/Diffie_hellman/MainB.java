package Diffie_hellman;

import java.math.BigInteger;
import java.util.Scanner;

import Des.Des;
import tool.RandomBigInteger;
import tool.Tool;


public class MainB {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        BigInteger p,a;
        Scanner input=new Scanner(System.in);
        System.out.println("输入p：");
        p=input.nextBigInteger();
        System.out.println("输入a：");
        a=input.nextBigInteger();
        BigInteger Xb=RandomBigInteger.getRandomBigInteger();
//		选择一个用户保密的Xa；
        BigInteger Yb;
//		发送到用户B的公开密钥
        Yb=Benyuan.expMode(a, Xb, p);//计算Yb
        System.out.println("Yb:"+Yb);
        BigInteger Ya;
        System.out.println("请输入用户A发送的Ya：");
        Ya=input.nextBigInteger();//接收用户A发送的Ya
        BigInteger key;
        //计算加解密的密钥
        key=Benyuan.expMode(Ya, Xb, p);
        byte[] m;
        m=Tool.getbytes();//得到密文数组
        m= Tool.divide(m);
        byte[] c = new byte[m.length];
        Des des=new Des();
        des.generateKeys(key.toString(2));
        //产生了密钥
         for(int i=0;i<m.length/8;i++) {
        			byte[] oneGroup=new byte[8];
        			//这里进行解密
        			System.arraycopy(m, i*8, oneGroup, 0, 8);
        			System.arraycopy(des.Edescry(oneGroup, des.key_Round, 0), 0, c, i*8, 8);
         }
         Tool.writeback(c);
    }

}
