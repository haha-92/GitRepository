package des;

public class Des {
	 //��ʼ�û�
    private int[] IP={58,50,42,34,26,18,10,2,
                     60,52,44,36,28,20,12,4,
                     62,54,46,38,30,22,14,6,
                     64,56,48,40,32,24,16,8,
                     57,49,41,33,25,17,9,1,
                     59,51,43,35,27,19,11,3,
                     61,53,45,37,29,21,13,5,
                     63,55,47,39,31,23,15,7};
    //���ʼ�û�
    private int[] IP_1={40,8,48,16,56,24,64,32,
                       39,7,47,15,55,23,63,31,
                       38,6,46,14,54,22,62,30,
                       37,5,45,13,53,21,61,29,
                       36,4,44,12,52,20,60,28,
                       35,3,43,11,51,19,59,27,
                       34,2,42,10,50,18,58,26,
                       33,1,41,9,49,17,57,25};
    //E��չ
    private int[] E={32,1,2,3,4,5,
                      4,5,6,7,8,9,
                     8,9,10,11,12,13,
                     12,13,14,15,16,17,
                     16,17,18,19,20,21,
                     20,21,22,23,24,25,
                     24,25,26,27,28,29,
                     28,29,30,31,32,1};
    //P�û�
    private int[] P={16,7,20,21,29,12,28,17,
                      1,15,23,26,5,18,31,10,
                      2,8,24,14,32,27,3,9,
                      19,13,30,6,22,11,4,25};
    private static final int[][][] S_Box = {
            {
                    { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
            { 
                    { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
            { 
                    { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
            { 
                    { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
            { 
                    { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
            { 
                    { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
            { 
                    { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
            { 
                    { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } }
    };
    //PC-1
    private int[] PC1={57,49,41,33,25,17,9,
                       1,58,50,42,34,26,18,
                       10,2,59,51,43,35,27,
                        19,11,3,60,52,44,36,
                       63,55,47,39,31,23,15,
                       7,62,54,46,38,30,22,
                       14,6,61,53,45,37,29,
                       21,13,5,28,20,12,4};
    //PC-2
    private int[] PC2={14,17,11,24,1,5,3,28,
                       15,6,21,10,23,19,12,4,
                       26,8,16,7,27,20,13,2,
                       41,52,31,37,47,55,30,40,
                       51,45,33,48,44,49,39,56,
                       34,53,46,42,50,36,29,32};
    //���ƵĹ���
    private int[] LFT={1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
    
    private static int NUM=16;
//    ��������Ϊ16
    private String[] keys=new String[NUM];
	int[][] key_Round=new int[16][48];
//	�������ÿһ�ֵ�48λ����Կ��һ��16��
    
	public Des() {
		// TODO Auto-generated constructor stub
	}

	//	������������������Կ�Ĳ�����
//	����64λ��ʼ��Կ
    public void generateKeys(String key) {
    	while(key.length()<8) {
    		key=key+key;
//    		��Կ�������򲹳�
    	}
    	key=key.substring(0, 8);
    	//substring�����ǵ�endlength������ǰһ���ļ������ȡ����0~7
//    	��ȡ�˸��ֽ���Ϊ��Կ��ÿ���ֽڰ˸�bit�պ�64��bit
    	int[] key_bits=new int[64];
    	//������һ��ʼ��64��bitλ����Կ��
    	byte[] key_byte=key.getBytes();
//    	for(int i=0;i<key_byte.length;i++) {
//    		System.out.println(key_byte[i]);
//    	}
//    	System.out.println(key_byte.length);
    	int numFor64 =0;
//    	��String����Կת����byte�ֽڵ���Կ
    	for(int i=0;i<8;i++) {
    		//ȡ�˸��ֽڣ���ò�Ҫ�ó��ȣ��������Կ�����ĵĻ����Ⱦͻᳬ��8���ֽ�
//    		System.out.println("ԭ��byte�������ֵ");
//    		System.out.println(key_byte[i]);
    		String key_T=Integer.toBinaryString(key_byte[i]&0xff);
//    		�õ�8λ���߰�λ���µ�key_byte������ֵ
//    		�����&0xff�Ļ���int��ռ4���ֽڵģ���õ�32λ�Ķ���������toBinaryString�Ĳ�����int���ͣ��õ����ǲ���

//    		System.out.println("byteת�ɶ����Ƶ�ֵ");
//    		System.out.println(key_T+" ");
//    		��������������ǲ�����λզ���أ�����Ǹ����Ļ����ǹ���λ�ģ������Ͳ�һ����
    		if(key_T.length()<8) {
    			for(int k=key_T.length();k<8;k++) {
    				key_T="0"+key_T;
    				//�����λ�����ƵĽ���ǰ�油0����
    			}

    		}
//    		System.out.println(key_T);
    		for(int x=0;x<8;x++) {
    			int asc=Integer.valueOf(key_T.charAt(x));
//    			System.out.print(asc);
//    			�����asc�Ƿŵ�0/1��ascaii���ֵ��1��49,0��28
//    			�������ǰ�1/0�Ž�64λ��Կ��ŵ�λ������
    			if(asc==49) {
    				asc=1;
    			}else if(asc==48) {
    				asc=0;
    			}else {
    				System.out.println("������~");
    			}
    			key_bits[numFor64++]=asc;
    			//��1/0ÿ��bitֵ�����ʼ64λ��Կ����
//    			System.out.println(asc);
    		}
    	}
//    	���õ��ĳ�ʼ��64��bits�ĳ�ʼ��Կ����key_bits����
//    	������Խ���PC1ѹ����
    	dealPC1(key_bits);
    }
    
//    ����PC1ѹ������56λ�Ľ�������Կ��������Կ
    public void dealPC1(int[] key_bits) {
    	int[] key_bits_pc1=new int[56];
//    	��Ž���PC1�û�֮���56��bit������Կ
    	for(int i=0;i<PC1.length;i++) {
    		key_bits_pc1[i]=key_bits[PC1[i]-1];
//    		����PC[i]-1������ǵ�54���Ļ��±����53������Ҫ��һ
    	}
//    	���õ�56λ�Ľ��н�������Կ
    	dealROL(key_bits_pc1);
    }
//    �������ƺ�����Կ�Ĳ���
    public void dealROL(int[] key_bits_pc1) {
    	int[] c0=new int[28];
    	int[] d0=new int[28];
    	System.arraycopy(key_bits_pc1, 0, c0, 0,28);
    	System.arraycopy(key_bits_pc1, 28, d0, 0, 28);
//    	��0~27�����c0����28~56���ұ�d0
//    	�������16�ֱ任������������48
//    	ÿ�ζ�Ҫ���ƺ�PC2ѹ���û�����16������Կ
    	for(int i=0;i<16;i++) {
    		int[] cNew=new int[28];
    		int[] dNew=new int[28];
    		if(LFT[i]==1) {
    			//����������һλ
    			System.arraycopy(c0, 1, cNew, 0, 27);
    			cNew[27]=c0[0];
    			System.arraycopy(d0, 1, dNew, 0, 27);
    			dNew[27]=d0[0];
    		}else if(LFT[i]==2) {
    			//������������λ�����
    			System.arraycopy(c0, 2, cNew, 0, 26);
    			cNew[26]=c0[0];
    			cNew[27]=c0[1];
    			System.arraycopy(d0, 2, dNew, 0, 26);
    			dNew[26]=d0[0];
    			dNew[27]=d0[1];
    		}else {
    			System.out.println("������~��û����ô��λ����λ�����~");
    		}
    		
    		int[] tem=new int[56];
    		System.arraycopy(cNew, 0, tem, 0, 28);
    		System.arraycopy(dNew, 0, tem, 28, 28);
    		for(int j=0;j<PC2.length;j++) {
    			key_Round[i][j]=tem[PC2[j]-1];
//    			����PC2ѹ�����õ���i�ֵ�48λ������Կ
    		}
    		c0=cNew;
    		d0=dNew;
    	}
    }
    
    
    
//    ����ʵ�ֵ���F�����Ĺ���
//    ���ص���R��i-1���任֮��Ľ��
    public int[] fFuction(int[] R,int[] key) {
//    	R���ұߵĲ��֣�key�Ǳ��ֵ���Կ
    	int[] result=new int[32];
    	int[] eR=new int[48];
//    	����Eλ��չ
//    	for(int i=0;i<eKey.length;i++) {
//    		R[i]=R[E[i]-1];
////    		��չ��48λ
//    	}
    	
//    	��չ��֮����48keyλ����Կ�������
    	for(int i=0;i<eR.length;i++) {
    		eR[i]=R[E[i]-1]^key[i];
    	}
//    	��������Ժ󣬽���S�д���
    	//����ǰҪ��eR����ѽ���ֳ�8��ÿ��6λ����48λ
    	int[][] eKey_div=new int[8][6];
    	int[] s_result=new int[32];
//    	��������s���滻֮��Ľ��
    	int x=0;//�����±�����
    	int srcPos=0;
    	for(int i=0;i<8;i++) {
    		System.arraycopy(eR, srcPos, eKey_div[i], 0, 6);
    		srcPos=srcPos+6;
//    		�����48�����з��飬ÿ6λһ��
    		int row=(eKey_div[i][0]<<1)+ eKey_div[i][5];
//    		�õ���Ӧ��S�е�������
    		int col=(int) ((eKey_div[i][1]*Math.pow(2,3))+(eKey_div[i][2]*Math.pow(2,2))+(eKey_div[i][3]*Math.pow(2,1))+eKey_div[i][4]);//������
//    		�õ���Ӧ��S�е�������
//    		��S���ж�Ӧ����ת��4λ�����ƣ���4��bit��4*8=32
    		String tem=Integer.toBinaryString(S_Box[i][row][col]);
    		if(tem.length()<4) {
    			for(int j=tem.length();j<4;j++) {
    				tem="0"+tem;
    			}
    		}
    		for(int k=0;k<4;k++) {
    			int asc=Integer.valueOf(tem.charAt(k));
    			if(asc==49) {
    				asc=1;
    			}else if(asc==48) {
    				asc=0;
    			}else {
    				System.out.println("����~");
    			}
    			s_result[x++]=asc;
    		}
    		
    	}
    	 //    	S�д��������Ժ���ǽ���P�û���
    	
    	for(int i=0;i<P.length;i++) {
    		result[i]=s_result[P[i]-1];
    	}
    	return result;

    }
   
    
    
//    ������м���
    
    public byte[] Edescry(byte[] p,int[][] key_Round,int flag) {
//    	flag����ָʾ�ӽ���
    	int[] p_Bit=new int[64];
    	String p_s="";
    	for(int i=0;i<8;i++) {
    		String p_bit_S =Integer.toBinaryString(p[i]&0xff);
    		for(int j=p_bit_S.length();j<8;j++) {
    			p_bit_S="0"+p_bit_S;
    		}
    		p_s+=p_bit_S;
    		//p_s����64λ������������Ķ�����01��
    	}
    	for(int i=0;i<64;i++) {
    		int temp=Integer.valueOf(p_s.charAt(i));
    		if(temp==49) {
    			//1��ASCII
    			temp=1;
    		}else if(temp==48) {
    			temp=0;
    		}
    		p_Bit[i]=temp;
    	}
//    	����õ�64λ����
//    	�������IP�û�
    	int[] p_IP=new int[64];
    	for(int i=0;i<64;i++) {
    		p_IP[i]=p_Bit[IP[i]-1];
    	}
    	
//    	IP�û���֮����м��ܺͽ��ܲ���
//    	if(flag==1) {
//    		//����
//    		for(int i=0;i<16;i++) {
//    			LR(p_IP,i,flag,key_Round[i]);
//    		}
//    		
//    	}else if(flag==0) {
//    		//����
//    		for(int i=15;i>=0;i--) {
//    			LR(p_IP,i,flag,key_Round[i]);
//    		}
//    	}
    	
    	for(int i=0;i<16;i++) {
    		int index;
    		index=(flag==1)?i:(15-i);
    		//����ǽ��ܵĻ����ǽ���Կ��������
//			LR(p_IP,index,flag,key_Round[index]);
    		LR(p_IP,i,key_Round[index]);
		}
    	
    	int[] IP_new=new int[64];
    	//����IP_1
    	for(int i=0;i<IP_1.length;i++) {
    		IP_new[i]=p_IP[IP_1[i]-1];
    	}
    	
    	byte[] result_byte=new byte[8];
    	//������Ű˸��ֽڵļ��ܺ�����Ļ��߽��ܳ���������
    	for(int i=0;i<8;i++) {
    		result_byte[i]=(byte)((IP_new[8*i]*Math.pow(2, 7))
    				+(IP_new[8*i+1]*Math.pow(2, 6))
    				+(IP_new[8*i+2]*Math.pow(2, 5))
    				+(IP_new[8*i+3]*Math.pow(2, 4))
    				+(IP_new[8*i+4]*Math.pow(2, 3))
    				+(IP_new[8*i+5]*Math.pow(2, 2))
    				+(IP_new[8*i+6]*Math.pow(2, 1))
    				+(IP_new[8*i+7]));
    		//���л���byte�ֽ�����,64��bits����8���ֽ�
    	}
    	
    	return result_byte;
    }
    
    //�����Ǽ��ܽ���ʱ��16�ֱ任
    public void LR(int[] p_IP,int round,int[] key) {
    	int[] L0=new int[32];
    	int[] R0=new int[32];
    	int[] L1=new int[32];
    	int[] R1=new int[32];
    	int[] f_result=new int[32];
    	System.arraycopy(p_IP, 0, L0, 0, 32);
    	System.arraycopy(p_IP, 32, R0, 0, 32);
    	
    	L1=R0;
    	f_result=fFuction(R0, key);
    	for(int i=0;i<32;i++) {
    		R1[i]=L0[i]^f_result[i];
    		if(round==15) {
    			p_IP[i]=R1[i];
    			p_IP[i+32]=L1[i];
//    			�����Ǽ��ܽ��ܵ����һ��Ҫ����������
    		}else {
    			p_IP[i]=L1[i];
    			p_IP[i+32]=R1[i];
    		}
    	}
    }
    
    
    
}
