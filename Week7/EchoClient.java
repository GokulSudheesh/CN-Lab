import java.net.*;
import java.io.*;
import java.util.*;
public class EchoClient {

   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

         PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
         Scanner sc = new Scanner(System.in);
         System.out.println(in.readLine());
         System.out.println(in.readLine());
         while(true){
            String str= sc.nextLine();
            out.println(str);
            out.flush();
            if (str == null){
               break;
           }
           else{
              if (str.trim().toUpperCase().equals("BYE")){
                 System.out.println("Connection to host lost.");
                 break;
              }
           }
            str = in.readLine();
            System.out.println(str);
         }
         client.close();         
      }catch (IOException e) {
         e.printStackTrace();
      }
   }
}