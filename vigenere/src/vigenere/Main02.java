package vigenere;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main02 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path="";
//		������Ҫ���ܵ�txt��·��
		Scanner input =new Scanner(System.in);
		System.out.println("��������Ҫ���ܵ��ļ���·����");
		path=input.next();
		
//		���ļ����в������洢��String��
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
		
		System.out.println("�õ�������Ϊ��");
		plaintxt=new String(buffer);
		System.out.println(plaintxt);
		
		String key;
		System.out.println("�����������ܵ���Կ��");
		key=input.next();
		
		plaintxt=Vigenere02.encrypt(plaintxt, key);
		System.out.println(plaintxt);
		
		System.out.println("���ܺ���Ϊ��");
		plaintxt=Vigenere02.decrypt(plaintxt, key);
		System.out.println(plaintxt);
		
	}

}
