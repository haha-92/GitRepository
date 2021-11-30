package RSA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;
 
public class FileCreate {
         
        public static void fileCreate(String path) throws Exception {
        	File file = new File(path);
        	 String str = "012345678vasdjhklsadfqwiurewopt";
        	 int len = str.length();
        	String text="";
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for (int i = 0; i < 512; i++)
            {
	            StringBuilder s = new StringBuilder();
	            for (int j = 0; j < 256; j++)
	           	{
	            s.append(str.charAt((int)(Math.random()*len)));
	            }
	            pw.println(s.toString());
            }
            pw.close();
        }
}