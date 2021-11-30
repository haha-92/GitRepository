package rsa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Random;

public class Test {
	/*
	 * 随机生成一个TXT文件
	 */
	public static void test() throws IOException {
		
		File file = new File("D:\\workset\\rsa\\test.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		BufferedWriter out = new BufferedWriter(osw);
		Random r = new Random(); 
		
		long i = 0L;
		while(i<300000L){
			i++;
			out.write(Integer.toString(r.nextInt(1000)));
			out.write(Integer.toString(r.nextInt(1000)));
			
			if(i%100 ==0){
				out.newLine();
				out.flush();
			}			
		}		
		out.close();
		System.out.println("数据生成到"+file);
	}
	public static String textTostring(File file) throws Exception {
		StringBuffer buffer = new StringBuffer();
		FileInputStream fi = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fi,"GBK");
		Reader in = new BufferedReader(isr);
		int i;
		while((i=in.read())>-1) {
			buffer.append((char)i);
		}
		in.close();
		return buffer.toString();
	}
}
