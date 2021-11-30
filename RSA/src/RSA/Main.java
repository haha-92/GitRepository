package rsa;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws Exception{
		long startTime=System.nanoTime();   //��ȡ��ʼʱ��  
		
		Rsa volu1 = new Rsa();
		//��Կ����
		BigInteger p = Sus.getPrime(512);
		BigInteger q = Sus.getPrime(512);
		//System.out.println("p="+p+",q="+q);
		BigInteger n = volu1.fastAdd(p,q);
		//System.out.println("n="+n);
		BigInteger u=volu1.fastAdd(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE));
		//System.out.println("u="+u);
		BigInteger e;
		while(true) {
			e = Sus.getPrime(512);
			if(e.compareTo(u)<0)
				break;
		}
		//System.out.println("e="+e);
		BigInteger d = volu1.inverse(u, e);
		d=(d.mod(u).add(u)).mod(u);
		//System.out.println("d="+d);
		
		Test.test();
		//���ļ�
		File file = new File("D:\\workset\\rsa\\test.txt");
		String text = Test.textTostring(file);
		String text1 = text.replaceAll("[^0-9a-zA-Z]","");
		//System.out.println(text1);
		int len = text1.length();
		//System.out.println("len="+len+",len/500="+len/500);
		String c="";
		int i;
		/*����
		for(i=0;i<len/500;i++) {
			BigInteger c1=volu1.encrypt(new BigInteger(text1.substring(500*i,500*(i+1))),e,n);
			//System.out.println("c"+i+"="+c1);
			c+=c1.toString();
		}
		if(len%500!=0) {
			BigInteger c1=volu1.encrypt(new BigInteger(text1.substring(500*i)),e,n);
			c+=c1.toString();
		}
		*/
		String t=text1.substring(0,100);
		System.out.println("text="+t);
		//����
		BigInteger c1=volu1.encrypt(new BigInteger(t),e,n);
		c+=c1.toString();
		System.out.println("c="+c);
		/*
		String m="";
		for(i=0;i<c.length()/500;i++) {
			BigInteger m1=volu1.encrypt(new BigInteger(c.substring(500*i,500*(i+1))),d,n);
			System.out.println("m"+i+"="+m1);
			m+=m1.toString();
		}
		if(len%500!=0) {
			BigInteger m1=volu1.encrypt(new BigInteger(c.substring(500*i)),d,n);
			m+=m1.toString();
		}
		System.out.println("m="+m);
		*/
		
		//����
		BigInteger m1=volu1.encrypt(new BigInteger(c),d,n);
		String m=m1.toString();
		System.out.println("m="+m);
		if(t.equals(m))
			System.out.println("���ܳɹ�");
		else
			System.out.println("����ʧ��");
		
		long endTime=System.nanoTime(); //��ȡ����ʱ��  
		System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ns");
	}
}
