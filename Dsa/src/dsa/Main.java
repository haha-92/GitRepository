package dsa;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
   public static void main(String[] agrs) throws IOException {
       DSA dsa=new DSA();
       dsa.initKey();
       System.out.println("��������Ҫǩ�����ļ���·����");
       Scanner input=new Scanner(System.in);
       String path="";
       path=input.next();
       byte[] in=Tool.getbytes(path);
       BigInteger[] sig= dsa.signature(in);
       System.out.println("ǩ����rΪ��"+sig[0]);
       System.out.println("ǩ����sΪ��"+sig[1]);



       System.out.println("��������Ҫ��֤ǩ�����ļ���·����");
       String path2=input.next();
       BigInteger[] verifySig=new BigInteger[2];
       System.out.println("��������Ҫ��֤��ǩ����r��");
       verifySig[0]=input.nextBigInteger();
       System.out.println("��������Ҫ��֤��ǩ����s��");
       verifySig[1]=input.nextBigInteger();
       System.out.println("DSASignture verifies result:" + dsa.verify(in,verifySig) );
   }
}
