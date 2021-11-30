package rsa;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Rsa {
	private BigInteger p,q,n,u,e,d;
	Random ran = new SecureRandom();
//	BigInteger sjs() {
//		
//	    BigInteger n = new BigInteger(10, ran);
//	    System.out.print("n="+n);
//	    if(!(n.testBit(0))) 
//	    	n = n.setBit(0);
//	    while(!isPrime(n))
//	    	n = n.add(BigInteger.valueOf(2));
//	    return n;
//	}
	public BigInteger encrypt(BigInteger M,BigInteger e,BigInteger n) {
		/*
		//ѡȡ����������p,q
		BigInteger p=sjs();
		System.out.print("p="+p);
		BigInteger q=sjs();
		System.out.println(",q="+q);
		//n=p*q
		BigInteger n=fastAdd(p,q);
		System.out.print("n="+n);
		//u=(p-1)(q-1)
		BigInteger u=fastAdd(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE));
		System.out.println(",u="+u);
		//ѡһ����e,����1<e<u,��gcd(u,e)=1
		e=sjs();
		while(e.compareTo(u)>0 && !isPrime(e)) {
			e.subtract(BigInteger.valueOf(2));
		}
		while(e.compareTo(BigInteger.ONE)<=0 && !isPrime(e))
			e.add(BigInteger.valueOf(2));
		System.out.println("e="+e);
		//��{e,n}Ϊ����Կ
		*/
		BigInteger c=fastExponential(M,e,n);
		return c;
	}
//	public BigInteger decrypt(BigInteger c) {
//		//����d
//		d=inverse(u,e);
//		d=(d.remainder(u).remainder(u)).remainder(u);
//		System.out.println("d="+d);
//		//��{d,n}Ϊ����Կ
//		return fastExponential(c,d,n);
//	}
	//����ָ���㷨����a^b mod n��
	BigInteger fastExponential(BigInteger a,BigInteger b,BigInteger n){
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
	//���ٻ�����a*b��
	BigInteger fastAdd(BigInteger a,BigInteger b) {
		BigInteger t=a,d=BigInteger.ZERO;
		while(b.compareTo(BigInteger.ZERO)>0) {  //b>0
			if((b.and(BigInteger.ONE)).equals(BigInteger.ONE)) //(b&1)==1
				d = d.add(t); //(d+t)%n
			t = t.add(t); //(t+t)%n;
			b = b.shiftRight(1); //b>>1;
		}
		return d;
	}
//	//Miller-Rabin���ʼ�ⷨ
//	boolean Miller_Rabin(BigInteger a,BigInteger n) {
//		BigInteger k;
//		BigInteger s = BigInteger.ZERO;
//		BigInteger temp = n.subtract(BigInteger.ONE);      
//	    //��n-1��ʾΪ(2^s)*t
//		while((temp.and(BigInteger.ONE)).equals(BigInteger.ZERO))  //(temp&1)==0
//	    {
//	        temp = temp.shiftRight(1);
//	        s.add(BigInteger.ONE);
//	    }   
//		BigInteger t = temp;
//		BigInteger b=fastExponential(a,t,n); //�����a^t
//        for(BigInteger i=BigInteger.ONE;i.compareTo(s)<=0;i=i.add(BigInteger.ONE))  //Ȼ�����s��ƽ�� 
//        {
//            k = fastAdd(b,b,n);  //��b��ƽ�� 
//            if(k.compareTo(BigInteger.ONE)==0 && b.compareTo(BigInteger.ONE)!=0 && b.compareTo(n.subtract(BigInteger.ONE))!=0)   //�ö���̽���ж� 
//              return false;
//            b=k;
//        }
//        if(b.compareTo(BigInteger.ONE)!=0) //b!=1
//        	return false;
//		return true;
//	}
//	boolean isPrime(BigInteger n) {
//        int sizeInBits = n.bitLength();
//        int tryTime = 0;
//        if(sizeInBits < 100)
//            tryTime = 50;
//        if(sizeInBits < 256)
//            tryTime = 27;
//        else if(sizeInBits < 512)
//            tryTime = 15;
//        else if(sizeInBits < 768)
//            tryTime = 8;
//        else if(sizeInBits < 1024)
//            tryTime = 4;
//        else
//            tryTime = 2;
//        System.out.print("tryTime="+tryTime);
//        return isPrime(n, tryTime);
//    }
	//��ε�����������, �ж������n�Ƿ�Ϊ����
//    boolean isPrime(BigInteger n, int tryTime) {
//    	Random r = new Random();
//		BigInteger a;
//		StringBuilder rs;
//        for(int i=0;i<tryTime;i++) {
//        	rs = new StringBuilder();
//        	for(int j=0;j<10;j++) {
//        		rs.append(r.nextInt(10));
//        	}
//        	System.out.print("rs="+rs);
//        	a = new BigInteger(rs.toString());
//        	
//            if(!Miller_Rabin(a,n)) {
//                return false;
//            }
//        }
//        return true;
//    }
    //��˷���Ԫ
    BigInteger inverse(BigInteger a,BigInteger b) {
    	BigInteger x1=BigInteger.ONE,x2=BigInteger.ZERO,x3=a;
    	BigInteger y1=BigInteger.ZERO,y2=BigInteger.ONE,y3=b;
    	BigInteger t1,t2,t3;
    	BigInteger q;
    	while(true) {
    		if(y3.compareTo(BigInteger.ZERO)==0) {
    			System.out.println("û����Ԫ");
    			return BigInteger.ZERO;
    		}
    		if(y3.compareTo(BigInteger.ONE)==0) {
    			
    			return y2;
    		}
    			
    		q=x3.divide(y3);
    		t1=x1.subtract(q.multiply(y1));
    		t2=x2.subtract(q.multiply(y2));
    		t3=x3.subtract(q.multiply(y3));
    		x1=y1;
    		x2=y2;
    		x3=y3;
    		y1=t1;
    		y2=t2;
    		y3=t3;
    	}
    }
}
