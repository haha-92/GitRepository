package dsa;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class DSA {

    public BigInteger p,q,g,x,y,k;
//    全局公开钥的获取
    public void  initKey(){
////        1、获取p，p是长度在512~1024比特之间能被64整除的长度的大素数
//        int len=0;
        Random random=new Random();
//        do{
//            len=random.nextInt(1024-512+1)+512;
//        }while (len%64!=0);
//        System.out.println("len:"+len);
////        产生512~1024能被64整除的长度
//        BigInteger p=BigInteger.probablePrime(len,new SecureRandom());
////        产生p
////        q是p-1的素因子，且q的长度为160比特

        q=BigInteger.probablePrime(160,new SecureRandom());
//        先产生q，再通过p=（q*k）+1;判断q是否为素数，如果是就可以了；因为q是p-1的素因子，且是160bit
//
        int len1;
        do {
            do{
                len1=random.nextInt(1024-512+1)+512;
                BigInteger t=new BigInteger(len1,new SecureRandom());
                p=t.multiply(q).add(BigInteger.ONE);
            }while ( p.bitLength()%64!=0&&p.bitLength()<1024);
        }while (!p.isProbablePrime(100));
//        System.out.println("len1:"+p.bitLength());
//        System.out.println("len1:"+len1);
        System.out.println("p:"+p);
        System.out.println("q:"+q);
//        产生h，h是1~p-1之间的能使h^((p-1)/q)mod p>1的任意一整数个
        BigInteger h=getH();
//        System.out.println("h:"+h);
//        g=h.modPow((p.subtract(BigInteger.ONE).divide(q)),p);
        g=expMode(h,(p.subtract(BigInteger.ONE).divide(q)),p);
        //g=h^((p-1)/q) mod p
        System.out.println("g:"+g);
        //产生用户密钥x,x满足的要求是0<x<q
        do{
           int len=random.nextInt(160);
           x=new BigInteger(len,new SecureRandom());
           //随机产生一个x
        }while((x.compareTo(q))>=0);//x大于等于q就继续循环
//        System.out.println("x:"+x);

//        选择用户的公开密钥y
        y=expMode(g,x,p);
        //y=(g^x) mod p
        System.out.println("公开密钥y:"+y);
//        BigInteger Test = expMode(new BigInteger("2"), new BigInteger("4"), new BigInteger("13"));
//        System.out.println("Test:"+Test);
//        为待签消息选取秘密数k
        do{
            int len=random.nextInt(160);
            k=new BigInteger(len,new SecureRandom());
            //用户为待签消息选取秘密数
        }while((k.compareTo(q))>=0);//x大于等于q就继续循环
//        System.out.println("k:"+k);
    }

//    求h的函数;h满足的条件是1<h<p-1且满足h^((p-1)/q) mod p>1的任何一个整数
        public BigInteger  getH(){
        BigInteger temp=null;
        Random random=new Random();
        int len;
        do{
            len=random.nextInt(160)+1;
//            h的长度随机
//            System.out.println("hlen:" +len);
            temp=new BigInteger(len,new SecureRandom());
            //产生h
        }while(temp.compareTo(p)>=0//h和p相比(h>p时不满足条件，继续循环
                ||(((temp.modPow(p.subtract(BigInteger.ONE).divide(q),p)).compareTo(BigInteger.ONE))<1));//h^((p-1)/q) mod p<=1,不满足条件
//       System.out.println("temp即H:"+temp);
        return temp;
    }
//    大整数快速幂模法
    public BigInteger expMode(BigInteger base,BigInteger exponent,BigInteger n){
        BigInteger result=BigInteger.ONE;
        char[] binaryAarry=new StringBuilder(exponent.toString(2)).reverse().toString().toCharArray();
        int k=binaryAarry.length;
        for(int i=k-1;i>=0;i--){
            result=result.multiply(result).mod(n);
            if(binaryAarry[i]=='1'){
                result=result.multiply(base).mod(n);
            }
        }
        return result;
    }


//    扩展的欧几里得算法求取逆元
    public BigInteger inverse(BigInteger k,BigInteger q){
        BigInteger X1=BigInteger.ONE;
        BigInteger X2=BigInteger.ZERO;
        BigInteger X3=q;
        BigInteger Y1=BigInteger.valueOf(0);
        BigInteger Y2=BigInteger.valueOf(1);
        BigInteger Y3=k;

        while(true){
            if(Y3.equals(BigInteger.ZERO)){
                System.out.println("没有逆元~");
            }
            if(Y3.equals(BigInteger.valueOf(1))){
                return Y2;//返回逆元
            }
            BigInteger Q=X3.divide(Y3);
            BigInteger T1=X1.subtract(Q.multiply(Y1));
            BigInteger T2=X2.subtract(Q.multiply(Y2));
            BigInteger T3=X3.subtract(Q.multiply(Y3));
            X1=Y1;
            X2=Y2;
            X3=Y3;
            Y1=T1;
            Y2=T2;
            Y3=T3;
        }


    }


    public BigInteger[] signature(byte[] m){
        BigInteger[] sign=new BigInteger[2];
        //返回签名的（r，s）
        BigInteger r=(expMode(g,k,p)).mod(q);
//                g.modPow(k, p).mod(q);
//
//        System.out.println("r:"+r);
        //r=((g^k)mod p)mod q
//        System.out.println((k.modInverse(q)));

        //用扩展的欧几里得算法求（k mod p)逆元
        BigInteger s=((inverse(k,q)).multiply(SHA.Transform(SHA.messageAppend(m)).add(x.multiply(r))).mod(q)).mod(q);
//                hash(m).add(x.multiply(r)).mod(q).multiply(k.modInverse(q)).mod(q);
//
//        System.out.println("s:"+s);
        sign[0]=r;
        sign[1]=s;

        return sign;
    }

    public boolean verify(byte[] m,BigInteger[] sign){
        BigInteger w=inverse(sign[1],q);
//        计算w=1/（s')mod q
        BigInteger u1=SHA.Transform(SHA.messageAppend(m)).multiply(w).mod(q);
        BigInteger u2=sign[0].multiply(w).mod(q);
        BigInteger v=expMode(g,u1,p).multiply(expMode(y,u2,p)).mod(p).mod(q);
//        expMode(g,u1,p).multiply(expMode(y,u2,p)).mod(p).mod(q);
        System.out.println("v:"+v);
        System.out.println("r:"+sign[0]);
        return v.compareTo(sign[0])==0;//比较v和r是不是相等
    }
//    //通过SHA求出哈希值
//    public BigInteger hash(byte[] m){
////        BigInteger result = null;
////        return result;
//        MessageDigest md;
//        try {
//            md = MessageDigest.getInstance("SHA-1");
//            md.update(m);
//            byte b[] = new byte[17];
//            System.arraycopy(md.digest(), 0, b, 1, 16);
//            return new BigInteger(b);
//        }catch (NoSuchAlgorithmException e){
//            System.out.print("This cannot happen!");
//        }
//        return null;
//    }
}
