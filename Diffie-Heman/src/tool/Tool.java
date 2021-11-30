package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Tool {
    public static String  path="";
    public static byte[] getbytes() throws Exception {
        Scanner input=new Scanner(System.in);
        System.out.println("请输入需要加密/解密的文件的路径：");
        path=input.next();
        File file=new File(path);
        FileInputStream in=new FileInputStream(file);
        byte[] buffer=new byte[in.available()];
        in.read(buffer);
        return buffer;
    }
    //得到文件对应的字节数组


//	位数够不够，不够就进行扩展，够了就不用管

    public static  byte[] divide(byte[] p){
        int group=p.length/8;
        int diff=8-(p.length-group*8);
        byte[] p_change;
        if(diff<8) {
            p_change=new byte[p.length+diff];
            System.arraycopy(p_change,0,p,0,p.length);
            for(int i=0;i<diff;i++) {
                p_change[p.length+i]=(byte)0;
            }
        }else {
            p_change=p;
        }
        return p_change;
    }

    //将加密或者解密的结果写回文件
    public static void writeback(byte[] p) throws Exception {
        String pathB=path.substring(0, path.lastIndexOf("\\"))+"\\outB.txt";
        File outByte=new File(pathB);
        if(!outByte.exists()) {
            outByte.createNewFile();
        }
        OutputStream fos=new FileOutputStream(outByte);
        fos.write(p);
        fos.flush();
        fos.close();
        System.out.println("加密解密后的文件放在："+pathB);
    }
}
