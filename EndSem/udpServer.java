import java.io.*;
import java.net.*;

public class udpServer
{
	public static void main(String[] args) throws IOException{
	
		DatagramSocket ds = new DatagramSocket(1234);
		byte[] receive = new byte[65535];

		DatagramPacket DpReceive = null;
		while (true)
		{

			
			DpReceive = new DatagramPacket(receive, receive.length);

			
			ds.receive(DpReceive);

			System.out.println("Client:-" + data(receive));

			if (data(receive).toString().equals("bye"))
			{
				System.out.println("Client sent bye.....EXITING");
				break;
			}

			int num = Integer.parseInt(data(receive).toString());
			
			if (strong_num(num)==1){
				System.out.println("It is a strong number.");
			}
			if (is_perfect(num)==1){
				System.out.println("It is a perfect number.");
			}
			if (is_neon(num)==1){
				System.out.println("It is a neon number.");
			}
			if (is_arm(num)==1){
				System.out.println("It is a armstrong number.");
			}
			
			

			
			receive = new byte[65535];
		}
	}

	
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
	public static int fact(int num){
		int result = 1;
		while(num!=0){
			result = result * num;
			num--;
		}
		return result;
	}
	public static int strong_num(int num){
		int N=num;
		int sum = 0;
		while(num>0){
			sum = sum + fact(num%10);
			num = num/10;
		}
		if(N==sum){
			return 1;		
		}
		return 0;	
	}
	public static int is_perfect(int num){
		int sum = 0;
		for (int i = 1; i<=num/2; i++){
			if(num%i == 0){
				sum =sum + i;
			}
		}
		if(sum == num){
			return 1;
		}
		return 0;
	}
	public static int is_neon(int num){
		int N = num;
		int sum = 0;
		num = num *num;
		while(num>0){
			sum = sum + num%10;
			num = num/10;
		}
		if(N==sum){
			return 1;
		}
		return 0;
	} 
	public static int is_arm(int num){
		int N = num;
		int sum = 0;
		while(num>0){
			int d = num%10;
			sum = sum + (d*d*d);
			num = num/10;
		}
		if(N==sum){
			return 1;
		}
		return 0;
	}
}
