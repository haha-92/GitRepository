package RC4;

public class Rc4 {
	
//	1.��������������
	
	
//	1.1������������S
	private void changeS(int[] S,String key) {
		for(int i=0;i<S.length;i++) {
			S[i]=i;
		}
		int j=0;
		for(int i=0;i<255;i++) {
			j=(j+S[i]+key.charAt(i%key.length()))%256;
			swap(S,i,j);
		}
	}
	
	
//	1.2���������������е�����S���������ⳤ�ȵ���Կ��
	private void rpga(int[] S,int[] keySchedul,int length) {
		int i=0,j=0;
		for(int k=0;k<length;k++) {
			i=(i+1)%256;
			j=(j+S[i])%256;
			swap(S,i,j);
			keySchedul[k]=(S[(S[i]+S[j])%256]);
		}
	}
	
	
	
	public byte[] encrypt(int[] S,byte[] p,String key) {
		changeS(S,key);
		byte[] c=new byte[p.length];
		int[] keySchedul=new int[p.length];
		rpga(S,keySchedul,p.length);
		
		for(int i=0;i<p.length;i++) {
			c[i]=(byte)(((p[i]+128)^keySchedul[i])%256-128);
		}
		return c;
	}
	
	
	public void swap(int[] s,int i,int j) {
		Integer temp=s[i];
		s[i]=s[j];
		s[j]=temp;
	}
	
	
	
}
