package RC4;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] test=new byte[256];
		for(short i=0;i<test.length;i++) {
			test[i]=(byte)i;
			System.out.println(test[i]);
		}
	}

}
