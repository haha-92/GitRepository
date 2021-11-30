package dsa;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
   public static void main(String[] agrs) throws IOException {
       DSA dsa=new DSA();
       dsa.initKey();
       System.out.println("请输入需要签名的文件的路径：");
       Scanner input=new Scanner(System.in);
       String path="";
       path=input.next();
       byte[] in=Tool.getbytes(path);
       BigInteger[] sig= dsa.signature(in);
       System.out.println("签名的r为："+sig[0]);
       System.out.println("签名的s为："+sig[1]);



       System.out.println("请输入需要验证签名的文件的路径：");
       String path2=input.next();
       BigInteger[] verifySig=new BigInteger[2];
       System.out.println("请输入需要验证的签名的r：");
       verifySig[0]=input.nextBigInteger();
       System.out.println("请输入需要验证的签名的s：");
       verifySig[1]=input.nextBigInteger();
       System.out.println("DSASignture verifies result:" + dsa.verify(in,verifySig) );
   }
}

