import java.io.*;
import java.net.*;
public class ClientHandler_practice implements Runnable{
    private final Socket clientSocket;
    public ClientHandler_practice(Socket socket){
        this.clientSocket = socket;
    }
    public void run(){
        PrintWriter out = null;
        BufferedReader in = null;
        try{
            //get the output stream of client
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // get the input stream of client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while((line = in.readLine()) != null){
                //writing the recieved message from client
                System.out.println("Sent from client: "+line); 
                if(line.split(" ")[0].equalsIgnoreCase("addition")){
                    line = Integer.toString(Integer.parseInt(line.split(" ")[1]) + Integer.parseInt(line.split(" ")[2]));    
                }
                else if(line.split(" ")[0].equalsIgnoreCase("subtraction")){
                    line = Integer.toString(Integer.parseInt(line.split(" ")[1]) - Integer.parseInt(line.split(" ")[2]));
                    
                }
                else if(line.split(" ")[0].equalsIgnoreCase("division")){
                    line = Integer.toString(Integer.parseInt(line.split(" ")[1]) / Integer.parseInt(line.split(" ")[2]));
                }
                else if(line.split(" ")[0].equalsIgnoreCase("multiplication")){
                    line = Integer.toString(Integer.parseInt(line.split(" ")[1]) * Integer.parseInt(line.split(" ")[2]));
                }
                else{
                    line = line.toUpperCase();
                }
                out.println(line);
                out.flush();
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
