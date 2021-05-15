import java.io.*;
import java.net.*;
import java.util.*;
public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    ArrayList<Socket> clients;
    public ClientHandler(Socket socket){
        this.clientSocket = socket;        
    }
    public void run(){
        PrintWriter out = null;
        BufferedReader in = null;
        try{
            
            // get the input stream of client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while (true){
                if((line = in.readLine()) != null){
                    //writing the recieved message from client
                    System.out.println("Sent from client: "+line);
                }
                this.clients = Server.clients;
                for(int i = 0; i<clients.size();i++){
                    Socket client = clients.get(i);
                    if(this.clientSocket != client){
                        //get the output stream of client
                        out = new PrintWriter(client.getOutputStream(), true);
                        out.println(line);
                        out.flush();
                    }
                }
                
                if (line.trim().equals("BYE")){
                    System.out.println("Client socket close");
                    break;
                }
            }
            clientSocket.close();        
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(out != null){
                    out.close();
                }
                if (in != null){
                    in.close();
                    clientSocket.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
