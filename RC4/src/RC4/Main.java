package RC4;

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
		
		File file=new File(path);
		FileInputStream in=new FileInputStream(file);
		
		byte[] buffer=new byte[in.available()];
		in.read(buffer);
		
//		for(int i=0;i<80;i++) {
//			System.out.print(buffer[i]+"  ");
//			if(i%10==0) {
//				System.out.println();
//			}
//		}
//		

		
		
		Rc4 rc4=new Rc4();
		int[] S=new int[256];
		buffer=rc4.encrypt(S, buffer,"abcdefghigk");

		String textpath=path.substring(0, path.lastIndexOf("\\"))+"\\outbyte.mp4";
		File outByte=new File(textpath);
		if(!outByte.exists()) {
			outByte.createNewFile();
		}
		OutputStream fos=new FileOutputStream(outByte);
		fos.write(buffer);
		fos.flush();
		fos.close();
//		in.close();
//		input.close();
		
		
//		rc4	.changeS(S, "abcdefghigk");
		String textpath2=path.substring(0, path.lastIndexOf("\\"))+"\\outbyte2.mp4";
		File outByte2=new File(textpath2);
		if(!outByte2.exists()) {
			outByte2.createNewFile();
		}
		OutputStream fos2=new FileOutputStream(outByte2);
		buffer=rc4.encrypt(S, buffer,"abcdefghigk");
		fos2.write(buffer);
		fos2.flush();
		fos2.close();
		in.close();
		input.close();
	}

}
