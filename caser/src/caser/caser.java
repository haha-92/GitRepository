package caser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class caser {

	public static void main(String[] args) throws Exception {
		byte[] buffer=new byte[1024];
		FileInputStream  in=new FileInputStream ("D:\\�ִ�����ѧ\\1.txt");
		int size;
		String text = "";
		while((size=in.read(buffer))!=-1) {
			text=new String(buffer,0,size); 
//			System.out.println(text);
		}
		System.out.println(text);
		// TODO Auto-generated method stub
		System.out.println("����������ܻ��ߴ����ܵ��ı�");
		Scanner mw=new Scanner(System.in);
		String m=mw.nextLine();
		System.out.println("������Կ");
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
		System.out.println("���ģ�"+string);
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
		System.out.println("���ģ�"+string);
	}
	
	
}

