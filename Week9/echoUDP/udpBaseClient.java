import java.io.*;
import java.net.*;
import java.util.*;

public class udpBaseClient
{
	public static void main(String args[]) throws IOException
	{
		Scanner sc = new Scanner(System.in);

		// Step 1:Create the socket object for carrying the data.
		DatagramSocket ds = new DatagramSocket();

		InetAddress ip = InetAddress.getLocalHost();
		byte[] receive = new byte[65535];
		byte buf[] = null;

		DatagramPacket DpReceive = null;
		// loop while user not enters "bye"
		while (true)
		{
			String inp = sc.nextLine();

			// convert the String input into the byte array.
			buf = inp.getBytes();

			// Step 2 : Create the datagramPacket for sending the data.
			DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234);

			// Step 3 : invoke the send call to actually send the data.
			ds.send(DpSend);

			DpReceive = new DatagramPacket(receive, receive.length, ip, 1234);
			ds.receive(DpReceive);

			System.out.println("Echo:-" + data(receive));
			// Clear the buffer after every message.
			receive = new byte[65535];

			// break the loop if user enters "bye"
			if (inp.equals("bye"))
				break;
		}
	}
	// A utility method to convert the byte array data into a string representation.
	public static StringBuilder data(byte[] a)
	{
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0)
		{
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}
}
