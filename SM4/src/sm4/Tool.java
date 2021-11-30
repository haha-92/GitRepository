package sm4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tool {
	
//	输入密钥
	public static byte[] getKeyByte() {
		Scanner input=new Scanner(System.in);
		String key;
		System.out.println("请输入您加密的密钥：");
		key=input.next();
		while(key.length()<16) {
			key+=key;
		}
		key=key.substring(0,16);
		byte[] key_byte=key.getBytes();
		return key_byte;
	}
	
//	得到需要加密视频的byte数组
	public static byte[] getByte(String path) throws Exception {
		File file=new File(path);
		FileInputStream in=new FileInputStream(file);
		byte[] buffer=new byte[in.available()];
		byte[] buf=new byte[1024];
		int numBytesRead=0;
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		while((numBytesRead=in.read(buf))!=-1) {

			// 将每次读到字节数组(buff变量)内容写到内存缓冲区中，起到保存每次内容的作用
			output.write(buf,0,numBytesRead);
		}
		// 取内存中保存的数据
		buffer=output.toByteArray();
		return buffer;
	}
	
//	左移操作
	public static int shiftLeft(int input,int n) {
		return (input>>>(32-n))|(input<<(n));
//		左移n位，最后把前面几位补到后面去
	}

	public static int combine_4x8(int s1,int s2,int s3,int s4) {
		return (((s1<<24)&0xff000000)
				|((s2<<16)&0x00ff0000)
				|((s3<<8)&0x0000ff00)
				|(s4&0x000000ff)		
				);
	}
	
}
