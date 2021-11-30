package sm4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path="";
		Scanner input =new Scanner(System.in);
		System.out.println("请您输入需要加密的视频的路径：");
		path=input.next();
		byte[] p;
		p=Tool.getByte(path);
//		输入密钥
		byte[] key_byte;
		key_byte=Tool.getKeyByte();
//		System.out.println(key_byte.length);
		Sm4 sm4=new Sm4();
		int[] rk=new int[32];
		sm4.keyExpand(key_byte, rk);
//		得到了轮密钥rk了
//		for(int i=0;i<32;i++) {
//			System.out.printf("%08x\n",rk[i]);
//		}
//		可以进行加解密运算了
		
//		如果不是128bit的整数倍怎么办呢，扩展叭
		int numOfbyte=p.length/16;
		//16个byte构成128个bit
		int diff=16-(p.length-numOfbyte*16);
		//差多少个byte可以是16的整数倍
		byte[] p_change;
		if(diff<16) {
			p_change=new byte[p.length+diff];
			System.arraycopy(p, 0, p_change, 0, p.length);
			for(int i=0;i<diff;i++) {
				p_change[p.length+i]=(byte)diff;
			}
		}else {
			p_change=p;
		}
		int turnNum=p_change.length/16;
		byte[] oneGroup=new byte[16];
		byte[] cipher=new byte[p_change.length];
		
		
		byte[] temp=new byte[16];
		byte[] base= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		temp=base;
//		加密后的数组 
		for(int i=0;i<turnNum;i++) {
			for(int j=0;j<16;j++) {
				p_change[i*16+j]=(byte) (temp[j]^p_change[i*16+j]);//进行异或
			}
			System.arraycopy(p_change, i*16, oneGroup, 0, 16);
			temp=sm4.e_decrypt(oneGroup, rk, 0);//这里是SM4加密的结果
			//加密完之后做异或做为下一轮输入
			System.arraycopy(temp, 0, cipher, i*16, 16);
		}
		System.out.println("加密完成!，加密后的视频文件放在"+path.substring(0, path.lastIndexOf("\\"))+"\\outbyte.mp4"+"下");
		String mp4path=path.substring(0, path.lastIndexOf("\\"))+"\\outbyteCBC.mp4";
		File outByte=new File(mp4path);
		if(!outByte.exists()) {
			outByte.createNewFile();
		}
		OutputStream fos=new FileOutputStream(outByte);
		fos.write(cipher);
		fos.flush();
		fos.close();
		
		//解密
		byte[] result=new byte[p.length];
		byte[] result_change=new byte[p_change.length];
		
		byte[] temp2=new byte[16];
		temp2=base;
		for(int i=0;i<turnNum;i++) {
			
			System.arraycopy(cipher, i*16, oneGroup, 0, 16);
//			sm4.e_decrypt(oneGroup, rk, 0);//这里是SM4加密的结果
//			System.arraycopy(cipher, i*16, temp2, 0, 16);
			//把temp2设置成那个前一个密文
			byte[] t=sm4.e_decrypt(oneGroup, rk, 1);
			for(int j=0;j<16;j++) {
				result_change[i*16+j]=(byte) (temp2[j]^t[j]);
			}
			
			System.arraycopy(cipher, i*16, temp2, 0, 16);
		}
		System.arraycopy(result_change, 0, result, 0, result.length);
		System.out.println("解密完成!，加密后的视频文件放在"+path.substring(0, path.lastIndexOf("\\"))+"\\outbyte2.mp4"+"下");
		String mp4path2=path.substring(0, path.lastIndexOf("\\"))+"\\outbyteCBC2.mp4";
		File outByte2=new File(mp4path2);
		if(!outByte2.exists()) {
			outByte2.createNewFile();
		}
		OutputStream fos2=new FileOutputStream(outByte2);
		fos2.write(result);
		fos2.flush();
		fos2.close();
	}

}
