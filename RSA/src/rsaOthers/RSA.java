package rsaOthers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

/**
 * RSA加密、解密、测试正确性
 * @author 北门大官人
 *
 */
public class RSA {
	/**
	 * <pre>
	 def gen_key(p, q):
	    n = p * q
	    fy = (p - 1) * (q - 1)
	    e = 3889
	    # generate d
	    a = e
	    b = fy
	    r, x, y = ext_gcd(a, b)
	    print x
	    d = x
	    # 公钥    私钥
	    return (n, e), (n, d)
	    </pre>
	 * @param p
	 * @param q
	 * @return
	 */
	public BigInteger[][] genKey(BigInteger p, BigInteger q){
		BigInteger n = p.multiply(q) ;
		BigInteger fy = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)) ;
		BigInteger e = new BigInteger("3889") ;
		// generate d
		BigInteger a = e ;
		BigInteger b = fy ;
		BigInteger[] rxy = new GCD().extGcd(a, b) ;
		BigInteger r = rxy[0] ;
		BigInteger x = rxy[1] ;
		BigInteger y = rxy[2] ;
		
		BigInteger d = x ;
		// 公钥  私钥
		return new BigInteger[][]{{n , e}, {n , d}} ;
	}
	
	/**
	 * 加密
	 * @param m 被加密的信息转化成为大整数m
	 * @param pubkey 公钥
	 * @return
	 */
	public BigInteger encrypt(BigInteger m, BigInteger[] pubkey){
		BigInteger n = pubkey[0] ;
		BigInteger e = pubkey[1] ;
	    
		BigInteger c = new Exponentiation().expMode(m, e, n) ;
	    return c ;
	}
	
	/**
	 * 解密
	 * @param c 
	 * @param selfkey 私钥
	 * @return
	 */
	public BigInteger decrypt(BigInteger c, BigInteger[] selfkey){
		BigInteger n = selfkey[0] ;
		BigInteger d = selfkey[1] ;
		
		BigInteger m = new Exponentiation().expMode(c, d, n) ;
		return m ;
	}

	public byte[] bigToByte(BigInteger b) {
		byte[] array=b.toByteArray();
		System.out.println(b.toByteArray().length);
		if(array[0]==0) {
			byte[] tmp=new byte[array.length-1];
			System.arraycopy(array, 1, tmp, 0, tmp.length);
			array=tmp;
		}
		return array;
	}
	
	public static void main(String[] args) throws Exception {
		// 公钥私钥中用到的两个大质数p,q'''
		BigInteger p = new BigInteger("106697219132480173106064317148705638676529121742557567770857687729397446898790451577487723991083173010242416863238099716044775658681981821407922722052778958942891831033512463262741053961681512908218003840408526915629689432111480588966800949428079015682624591636010678691927285321708935076221951173426894836169") ;
		BigInteger q = new BigInteger("144819424465842307806353672547344125290716753535239658417883828941232509622838692761917211806963011168822281666033695157426515864265527046213326145174398018859056439431422867957079149967592078894410082695714160599647180947207504108618794637872261572262805565517756922288320779308895819726074229154002310375209") ;
		
		RSA rsa = new RSA() ;
	    // 生成公钥私钥'''
	    // pubkey, selfkey = gen_key(p, q)
	    BigInteger[][] keys = rsa.genKey(p, q) ;
	    BigInteger[] pubkey  = keys[0] ;
	    BigInteger[] selfkey = keys[1] ;
	    
	    String path;
		Scanner input=new Scanner(System.in);
		System.out.println("请输入您需要加密的文件的路径：");
		path=input.nextLine();
		String text;
//		创建文件
		File file= new File(path);
//		创建缓冲区
		byte[] buffer=new byte[1024];
//		创建输入流
		FileInputStream  in=new FileInputStream (file);
		
		byte[] buf=new byte[in.available()];
		int numBytesRead=0;
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		while((numBytesRead=in.read(buffer))!=-1) {

			// 将每次读到字节数组(buff变量)内容写到内存缓冲区中，起到保存每次内容的作用
			output.write(buffer,0,numBytesRead);
		}
		// 取内存中保存的数据
	    buf=output.toByteArray();
	    
//	    System.out.println(buf.length);
//	    分组进行加密
	    int group;
	    group=buf.length/128;//看一下有多少组
	    int diff=(buf.length-group*128);
	    BigInteger[] cipherBigIntagerList;
	    if(diff>0) {
	    	 cipherBigIntagerList=new BigInteger[group+1];
	    }else {
	    	 cipherBigIntagerList=new BigInteger[group];
	    }
	    int len = 0;
	    for(int i=0;i<group;i++) {
	    	byte[] group_byte=new byte[128];
	    	System.arraycopy(buf, i*128, group_byte, 0, 128);
	    	BigInteger m=new BigInteger(group_byte);
	    	System.out.println("m:" + Arrays.toString(m.toByteArray()));
	    	BigInteger c = rsa.encrypt(m, pubkey) ;
	    	cipherBigIntagerList[i]=c;
	    	len+=c.toByteArray().length;
	    	
	    }
		if(diff>0) {
			
			byte[] group_byte=new byte[128];
			System.arraycopy(buf, group*128, group_byte, 0, diff);
	    	BigInteger m=new BigInteger(group_byte);
//	    	System.out.println("m:" + Arrays.toString(m.toByteArray()));
	    	BigInteger c = rsa.encrypt(m, pubkey) ;
	    	cipherBigIntagerList[group]=c;	
	    	len+=c.toByteArray().length;
		}
		byte[] result=new byte[len];
		byte[] temp;
		int destPos=0;
		for(int i=0;i<cipherBigIntagerList.length;i++) {
			temp=cipherBigIntagerList[i].toByteArray();
//			temp=rsa.bigToByte(cipherBigIntagerList[i]);
			System.arraycopy(temp, 0, result, destPos, temp.length);
			destPos+=temp.length;
		}
		System.out.println(result.length);
		System.out.println("byte:"+Arrays.toString(result));
	    String path1=path.substring(0, path.lastIndexOf("\\"))+"\\outbyte.txt";
		File outByte=new File(path1);
		if(!outByte.exists()) {
			outByte.createNewFile();
		}
		OutputStream fos=new FileOutputStream(outByte);
		fos.write(result);
		fos.flush();
		fos.close();
		
		
		int len2 = 0;
	   
		BigInteger[] mBigIntager=new BigInteger[cipherBigIntagerList.length];
		for(int i=0;i<cipherBigIntagerList.length;i++) {
	    	BigInteger cm=cipherBigIntagerList[i];
	    	BigInteger mm=rsa.decrypt(cm, selfkey);
	    	mBigIntager[i]=mm;
			len2+=mm.toByteArray().length;

	    }
		byte[] out=new byte[len2]; 
		byte[] temp2;
		int dest=0;
		for(int i=0;i<mBigIntager.length;i++) {
			temp2=mBigIntager[i].toByteArray();
//			temp=rsa.bigToByte(cipherBigIntagerList[i]);
			System.arraycopy(temp2, 0, out, dest, temp2.length);
			dest+=temp2.length;
		}
		System.out.println(out.length);
		System.out.println("byte:"+Arrays.toString(out));
	    String path2=path.substring(0, path.lastIndexOf("\\"))+"\\outbytem.txt";
		File outByte2=new File(path2);
		if(!outByte2.exists()) {
			outByte2.createNewFile();
		}
		OutputStream fos2=new FileOutputStream(outByte2);
		fos2.write(out);
		fos2.flush();
		fos2.close();
		
	}
}

