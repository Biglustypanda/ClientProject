package clientServer;

import java.nio.ByteBuffer;

public class Packet {
	short cksum; //16-bit 2-byte
	short len; //16-bit 2-byte
	int ackno; //32-bit 4-byte
	int seqno ; //32-bit 4-byte Data packet Only
	byte[] data; //0-500 bytes. Data packet only. Variable
	int packetSize;
	
	public Packet(byte[]data,int size){
		this.data=data;
		this.packetSize= size;
		seqno=1;
	}
	
	public byte[] createPacket(){
		ByteBuffer buffer = ByteBuffer.allocate(packetSize);
		//pack all the stuff into packet
		buffer.putShort(cksum);
		buffer.put(data);
		buffer.putInt(seqno);
		
		//return buffer.array();
		return data;
	}
	
	public short createChk(byte[] data){
		String s1;
		String s2;
		int total=0;
		for(int i=0;i<data.length;i++){
			s1 = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');
			s2 = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');
			int number0 = Integer.parseInt(s1, 2);
			int number1 = Integer.parseInt(s2, 2);
			total=number0+number1;
			i++;
		}
		System.out.println(total);
		
		
		return cksum;
	}

}
