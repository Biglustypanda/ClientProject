package clientServer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
	private final static int PORT = 89;

	public static void main(String[] args) throws IOException {
		// create UDP server socket with the port number 89
		DatagramSocket serverSocket = new DatagramSocket(PORT);
		String filePath = "C:/Users/Huy/Desktop/Note2.txt";
		System.out.println("The UDP Server has been started on port " + PORT);

		// initialize the size of the buffer
		int dataLength = 1000;
		byte[] receiveData = new byte[dataLength];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		System.out.println("The UDP Server is waiting for incoming messages on port " + PORT);
		
		serverSocket.receive(receivePacket);
		byte[] message = receivePacket.getData();
		//String strMessage = new String(receivePacket.getData());
		
		try{
			FileOutputStream output = new FileOutputStream(filePath);
			output.write(message);
			output.close();
		}
		catch (IOException e){
			System.out.println("exception: " + e);
		}
		
		
//		while (true) {
//			System.out.println("The UDP Server is waiting for incoming messages on port " + PORT);
//			serverSocket.receive(receivePacket);
//			byte[] message = receivePacket.getData();
			//InetAddress IPAddress = receivePacket.getAddress();
			//int clientPort = receivePacket.getPort();
			//String message = new String(receivePacket.getData());

			// trim the message to remove empty bytes
			//message = message.trim();
			//System.out.println("The UDP server has received the following message: " + message);

			//byte[] sendData = new byte[1024];
			//String responseMessage = "Thank you client, I received your message.";
			//sendData = responseMessage.getBytes();

			//DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
			//serverSocket.send(sendPacket);

		//}

	}

}
