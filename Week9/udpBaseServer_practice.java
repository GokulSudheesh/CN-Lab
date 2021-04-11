import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class udpBaseServer_practice
{
	public static void main(String[] args) throws IOException
	{
		// Step 1 : Create a socket to listen at port 1234
		DatagramSocket ds = new DatagramSocket(1234);
		byte[] receive = new byte[65535];

		DatagramPacket DpReceive = null;
		while (true)
		{

			// Step 2 : create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receive, receive.length);

			// Step 3 : revieve the data in byte buffer.
			ds.receive(DpReceive);

			String line = data(receive).toString();
			System.out.println("Client:-" + line);

			if(line.split(" ")[0].equalsIgnoreCase("addition")){
				System.out.println(Integer.toString(Integer.parseInt(line.split(" ")[1]) + Integer.parseInt(line.split(" ")[2])));    
			}
			else if(line.split(" ")[0].equalsIgnoreCase("subtraction")){
				System.out.println(Integer.toString(Integer.parseInt(line.split(" ")[1]) - Integer.parseInt(line.split(" ")[2])));
				
			}
			else if(line.split(" ")[0].equalsIgnoreCase("division")){
				System.out.println(Integer.toString(Integer.parseInt(line.split(" ")[1]) / Integer.parseInt(line.split(" ")[2])));
			}
			else if(line.split(" ")[0].equalsIgnoreCase("multiplication")){
				System.out.println(Integer.toString(Integer.parseInt(line.split(" ")[1]) * Integer.parseInt(line.split(" ")[2])));
			}

			// Exit the server if the client sends "bye"
			if (line.equals("bye"))
			{
				System.out.println("Client sent bye.....EXITING");
				break;
			}

			// Clear the buffer after every message.
			receive = new byte[65535];
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
