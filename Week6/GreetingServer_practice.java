// File Name GreetingServer_practice_practice.java
import java.net.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class GreetingServer_practice extends Thread {
   private ServerSocket serverSocket;
   
   public GreetingServer_practice(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      //serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            System.out.println(in.readUTF());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Date and Time: "+dtf.format(now));
            /*out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
               + "\nGoodbye!");*/
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      try {
         Thread t = new GreetingServer_practice(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}