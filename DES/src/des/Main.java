package des;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		
		String key="";
		System.out.println("请输入加密钥：");
		key=input.next();
		File file=new File(path);
		FileInputStream in=new FileInputStream(file);
		
		
		byte[] buffer=new byte[in.available()];
//		byte[] buf=new byte[1024];
//		int numBytesRead=0;
//		ByteArrayOutputStream output=new ByteArrayOutputStream();
//		while((numBytesRead=in.read(buf))!=-1) {
//
//			// 将每次读到字节数组(buff变量)内容写到内存缓冲区中，起到保存每次内容的作用
//			output.write(buf,0,numBytesRead);
//		}
//		// 取内存中保存的数据
		in.read(buffer);
		
		//进行加密和解密，加密flag是1，解密flag是0
		int numOfbyte=buffer.length/8;
		int diff=8-(buffer.length-numOfbyte*8);
		//8个字节为一组，这里是最后一组差多少个字节不满8个
		byte[] byte_buffer;
		if(diff<8) {
			byte_buffer=new byte[buffer.length+diff];
			System.arraycopy(buffer, 0, byte_buffer, 0, buffer.length);
			for(int i=0;i<diff;i++) {
				byte_buffer[buffer.length+i]=(byte)diff;
				
			}
		}else {
			byte_buffer=buffer;
		}
		numOfbyte=byte_buffer.length/8;
		Des des=new Des();
		des.generateKeys(key);
		byte[] oneGroup=new byte[8];//保存每次用来加密/解密的那一组8字节明文/密文
		byte[] result=new byte[byte_buffer.length];
		
		for(int i=0;i<numOfbyte;i++) {
			//每次加密8个字节，一共加密多少次
			System.arraycopy(byte_buffer, i*8, oneGroup, 0, 8);
			System.arraycopy(des.Edescry(oneGroup, des.key_Round, 1), 0, result, i*8, 8);
		}
		String path1=path.substring(0, path.lastIndexOf("\\"))+"\\outbyte.mp4";
		File outByte=new File(path1);
		if(!outByte.exists()) {
			outByte.createNewFile();
		}
		OutputStream fos=new FileOutputStream(outByte);
		fos.write(result);
		fos.flush();
		fos.close();
		
		System.out.println("加密成功！");
		
		
		byte[] cipher=new byte[result.length];
		byte[] result_decry=new byte[buffer.length];
		for(int i=0;i<numOfbyte;i++) {
			//每次加密8个字节，一共加密多少次
			System.arraycopy(result, i*8, oneGroup, 0, 8);
			System.arraycopy(des.Edescry(oneGroup, des.key_Round, 0), 0, cipher, i*8, 8);
		}
		System.arraycopy(cipher, 0, result_decry, 0, result_decry.length);
		String path2=path.substring(0, path.lastIndexOf("\\"))+"\\outbyte2.mp4";
		File outByte2=new File(path2);
		if(!outByte2.exists()) {
			outByte2.createNewFile();
		}
		OutputStream fos2=new FileOutputStream(outByte2);
		fos2.write(result_decry);
		fos2.flush();
		fos2.close();
		System.out.println("解密成功！");
	}

}
