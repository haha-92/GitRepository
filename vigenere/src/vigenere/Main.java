package vigenere;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

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
		int size=in.available();
		byte[] buffer=new byte[size];
		in.read(buffer);
		in.close();
		
		plaintxt=new String(buffer);
		System.out.println(plaintxt);
		
		String key="";
		String cipher="";
		System.out.println("请输入密钥：");
		key=input.next();
		cipher=Vigenere.encrypt(plaintxt, key);
		System.out.println(cipher);
		plaintxt=Vigenere.decrypt(cipher, key);
		System.out.println(plaintxt);
	}

}
