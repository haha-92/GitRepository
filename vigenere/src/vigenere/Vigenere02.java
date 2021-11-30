package vigenere;

public class Vigenere02 {
//	传入明文字符串以及密钥字符串进行加密
	public static String encrypt(String plaintxt,String key) {
		plaintxt=plaintxt.toLowerCase();
		plaintxt=plaintxt.replaceAll("[^a-zA-Z]", "");
		key=key.toLowerCase();
		String temp="";
		for(int i=0;i<plaintxt.length();i++) {
			if(plaintxt.charAt(i)>='a'&&plaintxt.charAt(i)<='z') {
				temp+=(char)(((plaintxt.charAt(i)-'a')+(key.charAt(i%key.length())-'a'))%26+'a');
			}
			else {
				temp+=plaintxt.charAt(i);
			}
		}
		
		return temp;
	}
	public static String decrypt(String cipher,String key) {
		cipher=cipher.replaceAll("[^a-zA-Z]", "");
		cipher=cipher.toLowerCase();
		key=key.toLowerCase();
		String temp="";
		for(int i=0;i<cipher.length();i++) {
			if(cipher.charAt(i)>='a'&&cipher.charAt(i)<='z') {
				temp+=(char)(((cipher.charAt(i)-'a')-(key.charAt(i%key.length())-'a')+26)%26+'a');
			}
			else {
				temp+=cipher.charAt(i);
			}
		}
		
		return temp;
	}
}
