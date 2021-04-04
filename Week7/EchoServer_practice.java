import java.io.*;
import java.net.*;

public class EchoServer_practice {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8008);
            //Create a new socket. Here, 8008 is port.
            while(true){
                //Accept method returns the socket and 
                //establishes a connection between server and client.
                System.out.println("Waiting for client.");
                Socket incoming = s.accept();//The next lines of code will be executed only after accepting.
                System.out.println("Connected.");
                //Reads bytes and decodes them into characters using a specified charset.
                //getInputStream() returns the InputStream attached with this socket.
                BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
                
                PrintWriter out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));

                out.println("Hello! BITS Dubai");
                out.println("Enter BYE to exit.");
                out.flush();//It is used to flush the stream.

                while(true){
                    String str = in.readLine();
                    if (str == null){
                        break;
                    }
                    else{
                        str = str.toUpperCase();
                        out.println("Echo: "+str);
                        out.flush();
                        if (str.trim().equals("BYE"))
                            break;
                    }
                }
                incoming.close();//Closes the Socket.
            }    
        } catch (Exception e) {
            //TODO: handle exception
        }
    }    
}
