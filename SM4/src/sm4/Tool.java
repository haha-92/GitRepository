package sm4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tool {
	
//	������Կ
	public static byte[] getKeyByte() {
		Scanner input=new Scanner(System.in);
		String key;
		System.out.println("�����������ܵ���Կ��");
		key=input.next();
		while(key.length()<16) {
			key+=key;
		}
		key=key.substring(0,16);
		byte[] key_byte=key.getBytes();
		return key_byte;
	}
	
//	�õ���Ҫ������Ƶ��byte����
	public static byte[] getByte(String path) throws Exception {
		File file=new File(path);
		FileInputStream in=new FileInputStream(file);
		byte[] buffer=new byte[in.available()];
		byte[] buf=new byte[1024];
		int numBytesRead=0;
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		while((numBytesRead=in.read(buf))!=-1) {

			// ��ÿ�ζ����ֽ�����(buff����)����д���ڴ滺�����У��𵽱���ÿ�����ݵ�����
			output.write(buf,0,numBytesRead);
		}
		// ȡ�ڴ��б��������
		buffer=output.toByteArray();
		return buffer;
	}
	
//	���Ʋ���
	public static int shiftLeft(int input,int n) {
		return (input>>>(32-n))|(input<<(n));
//		����nλ������ǰ�漸λ��������ȥ
	}

	public static int combine_4x8(int s1,int s2,int s3,int s4) {
		return (((s1<<24)&0xff000000)
				|((s2<<16)&0x00ff0000)
				|((s3<<8)&0x0000ff00)
				|(s4&0x000000ff)		
				);
	}
	
}
