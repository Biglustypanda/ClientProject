package clientServer;


public class Packet {
	short cksum; //16-bit 2-byte
	short len; //16-bit 2-byte
	int ackno; //32-bit 4-byte
	int seqno ; //32-bit 4-byte Data packet Only
	byte[] data; //0-500 bytes. Data packet only. Variable
	
	public Packet(byte[]data,int seqno){
		this.data=data;
		this.seqno=seqno;
	}
	
	
	public int getSeqno() {
		return seqno;
	}
	
	public int getAckno() {
		return ackno;
	}


	public void setAckno(int ackno) {
		this.ackno = ackno;
	}

//	public short createChk(byte[] data){
//		String s1;
//		String s2;
//		int total=0;
//		for(int i=0;i<data.length;i++){
//			s1 = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');
//			s2 = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');
//			int number0 = Integer.parseInt(s1, 2);
//			int number1 = Integer.parseInt(s2, 2);
//			total=number0+number1;
//			i++;
//		}
//		System.out.println(total);
//		
//		
//		return cksum;
//	}

}
