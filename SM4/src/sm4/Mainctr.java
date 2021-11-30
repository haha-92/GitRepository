package sm4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;





//int cm= 0x2c333a41;
//for(int i=0;i<turnNum;i++) {
 // System.arraycopy(p_change, i*16, oneGroup, 0, 16);
  //for(int h=15;h>=0;h--)
  //oneGroup[h]^=cm>>(8*(15-h))&0xff;
 // cm+=1;
  //System.arraycopy(sm4.e_decrypt(oneGroup, rk, 0), 0, cipher, i*16, 16);
//}

public class Mainctr {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			String path="";
			Scanner input =new Scanner(System.in);
			System.out.println("����������Ҫ���ܵ���Ƶ��·����");
			path=input.next();
			byte[] p;
			p=Tool.getByte(path);
//			������Կ
			byte[] key_byte;
			key_byte=Tool.getKeyByte();
//			System.out.println(key_byte.length);
			Sm4 sm4=new Sm4();
			int[] rk=new int[32];
			sm4.keyExpand(key_byte, rk);
//			�õ�������Կrk��
//			for(int i=0;i<32;i++) {
//				System.out.printf("%08x\n",rk[i]);
//			}
//			���Խ��мӽ���������
			
//			�������128bit����������ô���أ���չ��
			int numOfbyte=p.length/16;
			//16��byte����128��bit
			int diff=16-(p.length-numOfbyte*16);
			//����ٸ�byte������16��������
			byte[] p_change;
			if(diff<16) {
				p_change=new byte[p.length+diff];
				System.arraycopy(p, 0, p_change, 0, p.length);
				for(int i=0;i<diff;i++) {
					p_change[p.length+i]=(byte)diff;
				}
			}else {
				p_change=p;
			}
			int turnNum=p_change.length/16;
			byte[] oneGroup=new byte[16];
			byte[] cipher=new byte[p_change.length];
			
			String counter;
			String base="0000001123456789";
			
			counter=base;
			byte[] temp=new byte[16];
//			temp=counter.getBytes();
//			���ܺ������
			for(int i=0;i<turnNum;i++) {
				temp=counter.getBytes();
				for(int j=0;j<16;j++) {
					p_change[i*16+j]=(byte) (temp[j]^p_change[i*16+j]);//�������
				}
				System.arraycopy(p_change, i*16, oneGroup, 0, 16);
				temp=sm4.e_decrypt(oneGroup, rk, 0);//������SM4���ܵĽ��
				System.arraycopy(temp, 0, cipher, i*16, 16);
				//������֮���������Ϊ��һ������
				counter=String.valueOf(Integer.parseInt(counter)+1);
				while(counter.length()<16) {
					counter='0'+counter;
				}
				
			}
			System.out.println("�������!�����ܺ����Ƶ�ļ�����"+path.substring(0, path.lastIndexOf("\\"))+"\\outbyte.mp4"+"��");
			String mp4path=path.substring(0, path.lastIndexOf("\\"))+"\\outbyteCTR.mp4";
			File outByte=new File(mp4path);
			if(!outByte.exists()) {
				outByte.createNewFile();
			}
			OutputStream fos=new FileOutputStream(outByte);
			fos.write(cipher);
			fos.flush();
			fos.close();
			
			//����
			byte[] result=new byte[p.length];
			byte[] result_change=new byte[p_change.length];
			
			byte[] temp2=new byte[16];
			String counter2;
			counter2=base;
			for(int i=0;i<turnNum;i++) {
				temp2=counter2.getBytes();
				System.arraycopy(cipher, i*16, oneGroup, 0, 16);
//				sm4.e_decrypt(oneGroup, rk, 0);//������SM4���ܵĽ��
//				System.arraycopy(cipher, i*16, temp2, 0, 16);
				//��temp2���ó��Ǹ�ǰһ������
				byte[] t=sm4.e_decrypt(oneGroup, rk, 1);
				for(int j=0;j<16;j++) {
					result_change[i*16+j]=(byte) (temp2[j]^t[j]);
				}
				counter2=String.valueOf(Integer.parseInt(counter2)+1);
				while(counter2.length()<16) {
					counter2='0'+counter2;
				}
			}
			System.arraycopy(result_change, 0, result, 0, result.length);
			System.out.println("�������!�����ܺ����Ƶ�ļ�����"+path.substring(0, path.lastIndexOf("\\"))+"\\outbyte2.mp4"+"��");
			String mp4path2=path.substring(0, path.lastIndexOf("\\"))+"\\outbyteCTR2.mp4";
			File outByte2=new File(mp4path2);
			if(!outByte2.exists()) {
				outByte2.createNewFile();
			}
			OutputStream fos2=new FileOutputStream(outByte2);
			fos2.write(result);
			fos2.flush();
			fos2.close();
		
	}

}
