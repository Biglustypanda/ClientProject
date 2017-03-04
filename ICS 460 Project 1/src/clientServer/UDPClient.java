package clientServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
	private static int Port = 89;
	private final static String HOSTNAME = "localhost";
	int packetSize=0;
	int timeInterval=0;
	int windowSize=0;
	double curruptData=0;
	String filePath = "C:/Users/Huy/Desktop/Note.txt" ;
	
	public UDPClient(String input){
		getData(input);  // method to get data and assign it to specific variable

		try (DatagramSocket socket = new DatagramSocket(0)) {
			// find the IP address of the server from its name
			InetAddress hostIPAddress = InetAddress.getByName(HOSTNAME);
			System.out.println("Attempting to send to " + hostIPAddress + " via UDP port " + Port);
			
			File myFile = new File(filePath);
			byte [] fileArray = new byte [(int)myFile.length()];
			FileInputStream inputStream = new FileInputStream(myFile);
			inputStream.read(fileArray);
			
			DatagramPacket request = new DatagramPacket(fileArray,fileArray.length,hostIPAddress,Port);
			socket.send(request);
			
			//int dataLength = 1000;
			//byte[] receiveData = new byte[packetSize];
			//DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			/*while(true){
				// ask the user to enter a message
				//System.out.println("Enter Message:");
				//String message = scan.next();
				
				byte[] sendData = new byte[message.length() * 8];
				sendData = message.getBytes();
				DatagramPacket request = new DatagramPacket(sendData, sendData.length, hostIPAddress, PORT);

				socket.send(request);

				socket.receive(receivePacket);
				String response = new String(receivePacket.getData());

				// trim the message to remove empty bytes
				response = response.trim();
				System.out.println("Received: " + response);
			}*/
		
		} catch (IOException ex) {
			ex.printStackTrace();
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

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("The UDP Client has been startng up, please enter port: \n");
		String input = scan.nextLine();
		UDPClient client = new UDPClient(input);

	}

}