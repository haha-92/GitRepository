package caser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class caser {

	public static void main(String[] args) throws Exception {
		byte[] buffer=new byte[1024];
		FileInputStream  in=new FileInputStream ("D:\\现代密码学\\1.txt");
		int size;
		String text = "";
		while((size=in.read(buffer))!=-1) {
			text=new String(buffer,0,size); 
//			System.out.println(text);
		}
		System.out.println(text);
		// TODO Auto-generated method stub
		System.out.println("请输入待加密或者待解密的文本");
		Scanner mw=new Scanner(System.in);
		String m=mw.nextLine();
		System.out.println("输入密钥");
		Scanner mw1=new Scanner(System.in);
		int key=mw1.nextInt();
		EP(m,key);
		DY(m,key);

	}
	public static void EP(String str,int k) {
		String string="";
		for(int i=0;i<str.length();i++) {
			char s=str.charAt(i);
			if(s>='a'&&s<='z') {
				s+=k%26;
				if(s>'z')
					s-=26;
			}
			if(s>='A'&&s<='Z') {
				s+=k%26;
				if(s>'Z')
					s-=26;
			}
			string+=s;
			
		}
		System.out.println("密文："+string);
	}
	public static void DY(String str,int k) {
		String string="";
		for(int i=0;i<str.length();i++) {
			char s=str.charAt(i);
			if(s>='a'&&s<='z') {
				s-=k%26;
				if(s<'a')
					s+=26;
			}
			if(s>='A'&&s<='Z') {
				s-=k%26;
				if(s<'A')
					s+=26;
			}
			string+=s;
		}
		System.out.println("明文："+string);
	}
	
	
}

