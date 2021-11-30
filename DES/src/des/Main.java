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
		System.out.println("����������Ҫ���ܵ���Ƶ��·����");
		path=input.next();
		
		String key="";
		System.out.println("���������Կ��");
		key=input.next();
		File file=new File(path);
		FileInputStream in=new FileInputStream(file);
		
		
		byte[] buffer=new byte[in.available()];
//		byte[] buf=new byte[1024];
//		int numBytesRead=0;
//		ByteArrayOutputStream output=new ByteArrayOutputStream();
//		while((numBytesRead=in.read(buf))!=-1) {
//
//			// ��ÿ�ζ����ֽ�����(buff����)����д���ڴ滺�����У��𵽱���ÿ�����ݵ�����
//			output.write(buf,0,numBytesRead);
//		}
//		// ȡ�ڴ��б��������
		in.read(buffer);
		
		//���м��ܺͽ��ܣ�����flag��1������flag��0
		int numOfbyte=buffer.length/8;
		int diff=8-(buffer.length-numOfbyte*8);
		//8���ֽ�Ϊһ�飬���������һ�����ٸ��ֽڲ���8��
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
		byte[] oneGroup=new byte[8];//����ÿ����������/���ܵ���һ��8�ֽ�����/����
		byte[] result=new byte[byte_buffer.length];
		
		for(int i=0;i<numOfbyte;i++) {
			//ÿ�μ���8���ֽڣ�һ�����ܶ��ٴ�
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
		
		System.out.println("���ܳɹ���");
		
		
		byte[] cipher=new byte[result.length];
		byte[] result_decry=new byte[buffer.length];
		for(int i=0;i<numOfbyte;i++) {
			//ÿ�μ���8���ֽڣ�һ�����ܶ��ٴ�
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
		System.out.println("���ܳɹ���");
	}

}
