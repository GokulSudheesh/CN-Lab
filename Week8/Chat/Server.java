import java.io.*;
import java.net.*;
import java.util.*;

class Server{
    public static ArrayList<Socket> clients;
    public static void main(String[] args) {
        ServerSocket server = null;
        try{
            //server is listening on port 8009
            server = new ServerSocket(8009);
            //setReuseAddress() method of Java Socket class
            //enables or diables te SO_REUSEADDR socket option.
            //The initial setting of SO_REUSEADDR is disabled
            server.setReuseAddress(true);
            clients = new ArrayList<Socket>();
            //running an infinite loop for getting client requests.
            while (true){
                //socket object to recieve incomming client requests
                Socket client = server.accept();
                clients.add(client);
                /*Displaying that new client is connected to server
                getInetAddress() method either returns the 
                remote IP address to which the socket is connected 
                getHostAddress() method of InetAddress class
                returns the IP address string in textual presentation.*/
                System.out.println("New client connected"+client.getInetAddress().getHostAddress());
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println("Connected!");
                out.flush();
                //create a new thread object
                ClientHandler clientSock = new ClientHandler(client);
                
                //This thread will handle the client separately.
                new Thread(clientSock).start();  
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}