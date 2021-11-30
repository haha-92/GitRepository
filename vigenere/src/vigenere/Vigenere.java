package vigenere;

public class Vigenere {

    public static String encrypt(String plain,String key) {
    	String cipher="";
    	char[] keyR=new char[plain.length()];
    	int len=key.length();
    	for(int i=0;i<plain.length();i++) {
    		keyR[i]=key.toLowerCase().charAt(i%len);
//    		keyR[i]=key.charAt(i%len);
//    		把密钥重复多次，直到与明文长度相同为止
    	}
    	
    	char[] plainArray=plain.toCharArray();
//    	进行加密
    	for(int i=0;i<plain.length();i++) {
    		if(plainArray[i]>='a'&&plainArray[i]<='z') {
    			 char tem=(char)(((plainArray[i]-97)+(keyR[i]-97))%26+97);
    			 cipher=cipher+tem;
    		}
    		else if(plainArray[i]>='A'&&plainArray[i]<='Z') {
    			 char tem=(char)(((plainArray[i]-65)+(keyR[i]-97))%26+65);
    			 cipher=cipher+tem;
    		}else {
    			cipher=cipher+plainArray[i];
    		}
    	}
    	
    	return cipher;
    }
    public static String decrypt(String cipher,String key) {
    	String plaintext="";
    	char[] keyR=new char[cipher.length()];
    	int len=key.length();
    	for(int i=0;i<cipher.length();i++) {
    		keyR[i]=key.toLowerCase().charAt(i%len);
//    		把密钥重复多次，直到与明文长度相同为止
    	}
    	char[] cipherArray=cipher.toCharArray();
//    	进行加密
    	for(int i=0;i<cipher.length();i++) {
    		if(cipherArray[i]>='a'&&cipherArray[i]<='z') {
    			 char tem=(char)(((cipherArray[i]-97)-(keyR[i]-97)+26)%26+97);
    			 plaintext=plaintext+tem;
    		}
    		else if(cipherArray[i]>='A'&&cipherArray[i]<='Z') {
    			 char tem=(char)(((cipherArray[i]-65)-(keyR[i]-97)+26)%26+65);
    			 plaintext=plaintext+tem;
    		}else {
    			plaintext=plaintext+cipherArray[i];
    		}
    	}
    	
    	return plaintext;
    }

}
