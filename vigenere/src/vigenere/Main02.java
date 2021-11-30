package vigenere;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main02 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path="";
//		输入需要加密的txt的路径
		Scanner input =new Scanner(System.in);
		System.out.println("请输入需要加密的文件的路径：");
		path=input.next();
		
//		对文件进行操作，存储到String中
		String plaintxt="";
		File file=new File(path);
		FileInputStream in=new FileInputStream(file);
		int size;
		byte[] buffer=new byte[1024];
		while((size=in.read(buffer))!=-1) {
			plaintxt=new String(buffer,0,size); 
//			System.out.println(text);
		}

		in.close();
		
		System.out.println("得到的明文为：");
		plaintxt=new String(buffer);
		System.out.println(plaintxt);
		
		String key;
		System.out.println("请输入您加密的密钥：");
		key=input.next();
		
		plaintxt=Vigenere02.encrypt(plaintxt, key);
		System.out.println(plaintxt);
		
		System.out.println("解密后结果为：");
		plaintxt=Vigenere02.decrypt(plaintxt, key);
		System.out.println(plaintxt);
		
	}

}
