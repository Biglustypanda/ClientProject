package clientServer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class UDPClient extends Thread {
	int Port = 89;
	private final static String HOSTNAME = "localhost";
	int currentAckno=0;
	int packetSize=0;
	int timeInterval=0;
	int windowSize=0;
	double curruptData=0;
	private InetAddress hostIPAddress;
	private DatagramSocket socket;
	byte [] fileArray ;
	String filePath = "C:/Users/Huy/Desktop/Note.txt" ;
	boolean done = true;
	ArrayList <Packet> list = new ArrayList<Packet>();

	public UDPClient(String input,DatagramSocket socket,InetAddress hostIPAddress) {
		int start=0;
		getData(input);  // method to get data and assign it to specific variable
		this.socket = socket;
		// find the IP address of the server from its name
		this.hostIPAddress=hostIPAddress;
		//System.out.println("Attempting to send to " + hostIPAddress + " via UDP port " + Port);

		File myFile = new File(filePath);
		fileArray = new byte [(int)myFile.length()];

		try{
			FileInputStream inputStream = new FileInputStream(myFile);
			inputStream.read(fileArray);
			System.out.println("length: "+fileArray.length);
			inputStream.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

		splitFile(fileArray,start);  //call method to split the byte array into packets array.
	}

	public void run(){


		Thread send = new Thread(new Runnable() {
			public void run() {
			}


		});

		Thread receive = new Thread(new Runnable() {
			final int SIZE= 1024;
			public void run() {
				byte[] receiveData = new byte[SIZE];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					socket.receive(receivePacket);
					ByteArrayInputStream inStream = new ByteArrayInputStream(
							receivePacket.getData());
					ObjectInputStream objFromClient = new ObjectInputStream(
							inStream);
					Packet received = (Packet) objFromClient
							.readObject();
					
					if(received.getAckno()==currentAckno){
						System.out.println("Ackno # Received: "+currentAckno);
						increaseAckno();
					}
				} catch (IOException ex) {
					System.err.println(ex);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		Thread timer = new Thread(new Runnable() {
			public void run() {
			}


		});
	}




	public void splitFile(byte[] byteArray,int start){
		int seqno=0;
		while(start<byteArray.length){
			byte[] temp= new byte[packetSize];
			System.out.println(start);
			System.arraycopy(byteArray, start, temp, 0, packetSize);
			Packet packet = new Packet(temp,seqno);
			list.add(packet);

			start+=packetSize;
			if(byteArray.length-start<packetSize){
				packetSize=byteArray.length-start;
			}
			seqno++;
		}
	}

	public void getData(String input){
		String[] inputArray = input.split(" ");
		for(int i = 0; i<inputArray.length-1; i++){
			int temp=i+1;	
			switch(inputArray[i]){
			case "-s":
				packetSize = Integer.parseInt(inputArray[temp]);
				break;
			case "t":
				timeInterval= Integer.parseInt(inputArray[temp]);
				break;
			case "-w":
				windowSize= Integer.parseInt(inputArray[temp]);
				break;
			case "-d":
				curruptData= Double.parseDouble(inputArray[temp]);
				break;			
			}
		}
		Port = Integer.parseInt(inputArray[inputArray.length-1]);
	}
	
	public void increaseAckno(){
		currentAckno++;
	}


	//		getData(input);  // method to get data and assign it to specific variable
	//
	//		try (DatagramSocket socket = new DatagramSocket(0)) {
	//			// find the IP address of the server from its name
	//			InetAddress hostIPAddress = InetAddress.getByName(HOSTNAME);
	//			//System.out.println("Attempting to send to " + hostIPAddress + " via UDP port " + Port);
	//
	//			File myFile = new File(filePath);
	//			byte [] fileArray = new byte [(int)myFile.length()];
	//			FileInputStream inputStream = new FileInputStream(myFile);
	//			inputStream.read(fileArray);
	//			System.out.println("length: "+fileArray.length);
	//
	//			int start=0;
	//			byte[] receiveData = new byte[packetSize];
	//			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	//			
	//			splitFile(fileArray,start);   //add all the packets into a list
	//			
	//			while(done){
	//				for(int i=0;i<list.size();i++){
	//					byte[] sendPacket = list.get(i).createPacket();
	//					DatagramPacket request = new DatagramPacket(sendPacket,sendPacket.length,hostIPAddress,Port);
	//					socket.send(request);
	//
	////					socket.receive(receivePacket);
	////				    ByteArrayInputStream in = new ByteArrayInputStream(receivePacket.getData());
	////				    ObjectInputStream is = new ObjectInputStream(in);
	////					Packet received = (Packet)is.readObject();
	//				}
	//				break;
	//			}
	//
	//			//			for(int i=0;i<=end;i++){
	//			//				Packet packet = new Packet(splitFile(fileArray,start),packetSize);
	//			//				byte[] sendPacket = packet.createPacket();
	//			//				DatagramPacket request = new DatagramPacket(fileArray,fileArray.length,hostIPAddress,Port);
	//			//				socket.send(request);
	//			//				
	//			//				socket.receive(receivePacket);
	//			//				
	//			//				start+=packetSize;
	//			//			}
	//
	//
	//			//			DatagramPacket request = new DatagramPacket(fileArray,fileArray.length,hostIPAddress,Port);
	//			//			socket.send(request);
	//
	//
	//			//int dataLength = 1000;
	//			//byte[] receiveData = new byte[packetSize];
	//			//DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	//
	//			/*while(true){
	//				// ask the user to enter a message
	//				//System.out.println("Enter Message:");
	//				//String message = scan.next();
	//
	//				byte[] sendData = new byte[message.length() * 8];
	//				sendData = message.getBytes();
	//				DatagramPacket request = new DatagramPacket(sendData, sendData.length, hostIPAddress, PORT);
	//
	//				socket.send(request);
	//
	//				socket.receive(receivePacket);
	//				String response = new String(receivePacket.getData());
	//
	//
	//				// trim the message to remove empty bytes
	//				response = response.trim();
	//				System.out.println("Received: " + response);
	//			}*/
	//
	//		} catch (IOException ex) {
	//			ex.printStackTrace();
	//		}		 
	//	}
	//

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("The UDP Client has been startng up, please enter port: \n");
		String input = scan.nextLine();
		try {
			InetAddress ia = InetAddress.getByName(HOSTNAME);
			DatagramSocket socket = new DatagramSocket();
			UDPClient client = new UDPClient(input,socket,ia);
			client.run();

		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}

}