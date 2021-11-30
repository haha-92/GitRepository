package RSA;

import java.math.BigInteger;
import java.util.Random;

public class Record {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger n,e,M,d;
		Random random=new Random();
//		BigInteger r = new BigInteger(10, random);
		BigInteger p=Utils.getPrime(10);
		BigInteger q=Utils.getPrime(10);
		n=p.multiply(q);
		BigInteger fn;
		fn=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e=Utils.getPrime(10);
		System.out.println("e: "+e);
		while(!((e.gcd(fn)).equals(BigInteger.ONE))) {
			e=Utils.getPrime(10);
			System.out.println("e:"+e);
		}
		Random rnd=new Random();
		M=new BigInteger(10, rnd);
		RSA rsa=new RSA();
		BigInteger[] temp=rsa.extGcd(e,fn);
		d=temp[1];
//		while()
		System.out.println("n: "+n+" e: "+e+" M: "+M+" d: "+d);
		long startTime=System.nanoTime();
		BigInteger C=rsa.expMode(M,e,n) ;
		long endTime=System.nanoTime();
		System.out.println("startTime:"+startTime+" endTime:"+endTime+" endTime-startTime :"+(endTime-startTime)+"ms");
		long startTime2=System.nanoTime();
		M=rsa.expMode(C,d,n) ; 
		long endTime2=System.nanoTime();
		System.out.println("startTime2:"+startTime2+" endTime2:"+endTime2+" endTime2-startTime2 :"+(endTime2-startTime2)+"ms");
	}
	

}
