package dsa;

import java.math.BigInteger;

public class SHA {
    public  static int H0 = 0x67452301;

    public  static int H1 = 0xEFCDAB89;

    public  static int H2 = 0x98BADCFE;

    public  static int H3 = 0x10325476;

    public  static int H4 = 0xC3D2E1F0;

    private static int[] MD={0x67452301,0xEFCDAB89,0x98BADCFE,0x10325476,0xC3D2E1F0};
//    算法使用160比特长的缓冲区存储中间结果和最终的哈希值
    //缓冲区初始化表示成5个32比特长的寄存器

    //对消息进行填充
    public static int[] messageAppend(byte[] in){
        int inLen=in.length;//输入的消息的长度
        int less=inLen%64;
        int zeros=0;
        int ones=1;
        int size=0;
        if(less==56){
//            说明刚刚好可以%512等于448
            zeros=63;
            size=inLen+zeros+ones+8;
        }else if (less<56){
            //less是余数，说明离64还差（64-less）个，离比特数mod 512,剩余448，即字节数 mod 64 剩余56还差 (56-less)个，补一个1，就剩（55-less）个0
            zeros=55-less;
            size=inLen+zeros+ones+8;
        }else{
            //应该补（64-less+56）位，一位是1，其他是0
            zeros=63-less+56;
            size=inLen+zeros+ones+8;//计算的时候需要
        }
//        System.out.println("zeros:"+zeros+" ones:"+ones+" size"+size+" inLen:"+inLen+" less"+less);
        byte[] newByte=new byte[size];
        System.arraycopy(in,0,newByte,0,in.length);
        int xiaobiao=in.length;
        if(ones==1){
           newByte[xiaobiao++]=(byte)(0x80);
        }
        //第一个字节补1000 0000
        for(int i=0;i<zeros;i++){
            newByte[xiaobiao++]=(byte)(0);
        }
        //除最后64位，即八个字外，其他字节补0000 0000

        long Blen;
        Blen=(long)(inLen*8);
//        System.out.println("Blen:"+Blen);
        byte[] B=new byte[8];
        for(int i=0;i<8;i++){
            B[8-i-1]=(byte)(Blen>>((7-i)*8)&0xFF);
            newByte[xiaobiao++]=B[8-i-1];//补长度的64个字节中的一个字节，先补高位，再补低位
        }
//        //采取小端模式，高对高，低对低
//        for(int i=7;i>=0;i--){
//            bs.add(B[i]);
//        }
//        把补好的那个Arraylist转成字节数组

        int[] output=new int[size/4];
        for(int  i=0,j=0;i<size;j++,i+=4){
            int temp=0;
            temp=temp|((int)newByte[(int)i]<<24);
            temp=temp|((int)newByte[(int)i]<<16);
            temp=temp|((int)newByte[(int)i]<<8);
            temp=temp|((int)newByte[(int)i]);
            output[(int)j]=temp;
        }
        return  output;
    }

    public static BigInteger Transform(int[] input){
        //缓冲区初始化
        int[] buffer=new int[5];
        for(int i=0;i<5;i++){
            buffer[i]=MD[i];
        }

        int[] K = {
                0x5A827999,
                0x6ED9EBA1,
                0x8F1BBCDC,
                0xCA62C1D6
        };
        //常量K

        int temp;
        int[] W=new int[80];
        //保存每次分组16个字节组512比特导出的一个32比特的字，共导出（80-16）个
        int A,B,C,D,E;
        for(int k=0;k<input.length;k+=16){
            //分成多个512比特数据块来计算
            for(int t=0;t<16;t++){
                W[t]=input[k+t];
                //将 数据块 分成 16 个字 W0, W1, ... , W15,  W0 是最左边的字
            }
//            前16个值直接取为输入分组的16个相应的字
//            SHA算法将输入分组的16个字扩展成80个字来使用
//            对于 t = 16 到 79 令 Wt = CLS(1)(Wt-3 XOR Wt-8 XOR Wt- 14 XOR Wt-16);CLS(S)为左循环s位
            for(int t=16;t<80;t++){
                W[t]=CLS((W[t-16]^W[t-14]^W[t-8]^W[t-3]),1);
                //异或的结果左循环一位
            }
//            压缩函数处理，压缩函数由四轮处理过程组成，每一轮又由20步迭代组成
            //4轮的结构一样，但是使用的基本逻辑函数不同
            A=buffer[0];
            B=buffer[1];
            C=buffer[2];
            D=buffer[3];
            E=buffer[4];
            //缓冲区设置成5个32比特长的寄存器，给初始值为缓冲区里面的值
//            第一轮的20次迭代
            for(int t=0;t<20;t++){
                temp=CLS(A,5)+W[t]+K[0]+(((B&C)|((~B)&D)))+E;
                temp &= 0xFFFFFFFF;
//                根据公式计算新的A
                E=D;
                D=C;
                C=CLS(B,30);
//                C等于B左循环移位30次
                B=A;
//                B等于A
                A=temp;


            }
//            第二轮的20次迭代
            for(int t=20;t<40;t++){
                temp=CLS(A,5)+W[t]+K[1]+(((B^C)^D))+E;
                temp &= 0xFFFFFFFF;
//                根据公式计算新的A
                E=D;
                D=C;
                C=CLS(B,30);
//                C等于B左循环移位30次
                B=A;
//                B等于A
                A=temp;
            }

//            第三轮的20次迭代
            for(int t=40;t<60;t++){
                temp=CLS(A,5)+W[t]+K[2]+((B&C)|(B&D)|(C&D))+E;
                temp &= 0xFFFFFFFF;
//                根据公式计算新的A
                E=D;
                D=C;
                C=CLS(B,30);
//                C等于B左循环移位30次
                B=A;
//                B等于A
                A=temp;

            }
//            第四轮的20次迭代
            for(int t=60;t<80;t++){
                temp=CLS(A,5)+W[t]+K[3]+(B^C^D)+E;
                temp &= 0xFFFFFFFF;
//                根据公式计算新的A
                E=D;
                D=C;
                C=CLS(B,30);
//                C等于B左循环移位30次
                B=A;
//                B等于A
                A=temp;
            }
//            第四轮的输出（即寄存器的值，ABCDE）再与第一轮的输入（即当前缓冲区的值）进行mod 32相加。产生新的输入缓冲区

            buffer[0]=(buffer[0]+A)&0XFFFFFFFF;
            buffer[1]=(buffer[1]+B)&0XFFFFFFFF;
            buffer[2]=(buffer[2]+A)&0XFFFFFFFF;
            buffer[3]=(buffer[3]+A)&0XFFFFFFFF;
            buffer[4]=(buffer[4]+A)&0XFFFFFFFF;

        }
        String num="" ;
        for(int i=0;i<5;i++){
            num+=Integer.toBinaryString(buffer[i]);
        }
//        System.out.println("num:"+num);
        return  new BigInteger(num,2);

    }

    private static int CLS(int input,int n){
        return ((input<<n)&0xffffffff|(input>>(32-n)));
    }
}
