package rsaOthers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 String path;
			Scanner input=new Scanner(System.in);
			path=input.nextLine();
			String text;
//			�����ļ�
			File file= new File(path);
//			����������
			byte[] buffer=new byte[1024];
//			����������
			FileInputStream  in=new FileInputStream (file);
			
			byte[] buf=new byte[in.available()];
			int numBytesRead=0;
			ByteArrayOutputStream output=new ByteArrayOutputStream();
			while((numBytesRead=in.read(buffer))!=-1) {

				// ��ÿ�ζ����ֽ�����(buff����)����д���ڴ滺�����У��𵽱���ÿ�����ݵ�����
				output.write(buffer,0,numBytesRead);
			}
			// ȡ�ڴ��б��������
		    buf=output.toByteArray();
		    
			System.out.println("old:"+Arrays.toString(buf));
//		    System.out.println(buf.length);
		    BigInteger m = new BigInteger(1, buf);
//		    System.out.println(m);
		    System.out.println("new:" + Arrays.toString(m.toByteArray()));
		    System.out.println(m.toByteArray().length);
		    byte[] temp= {1};
		    BigInteger m2 = new BigInteger(1, temp);
//		    System.out.println(m2);
		    System.out.println("new:" + Arrays.toString(m2.toByteArray()));
//		    System.out.println(m2.toByteArray().length);
	}

}
