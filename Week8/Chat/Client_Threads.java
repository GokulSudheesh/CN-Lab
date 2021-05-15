import java.io.*;
import java.net.*;
import java.util.*;
class out extends Thread{
    Socket socks;
    out(Socket socks){
        this.socks = socks;
    }
    public void run(){
        try{
            while(true){
                Scanner sc = new Scanner(System.in);
                String line = null;
                /*if (Client_Threads.RWmode==1){
                    Thread.sleep(1000);
                }*/
                //if(sc.hasNext())
                line = sc.nextLine();
                //}
                Client_Threads.RWmode = 2;
                
                //writing to server
                PrintWriter out = new PrintWriter(socks.getOutputStream(), true);
                
                //sending the user input to server
                if (line!=null){
                    out.println(line);
                    out.flush();
                }
                out.println("");//gotta push an empty string each time :(
                out.flush();
                //sc.close(); //if its closed there's an error :( 
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
class in extends Thread{
    Socket socks;
    in(Socket socks){
        this.socks = socks;
    }
    public void run(){
        try{
            while(true){
                /*if (Client_Threads.RWmode==2){
                    Thread.sleep(1000);
                }*/
                Client_Threads.RWmode = 1;
                //reading from server
                BufferedReader in = new BufferedReader(new InputStreamReader(socks.getInputStream()));
                //displaying server reply
                if(in.readLine()!=null){
                    System.out.println("Server replied "+in.readLine());
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
class Client_Threads{
    public static int RWmode; //1 for read 2 for write
    public static void main(String[] args) {
        //establish a connection by providing host and port number
        try{
            Socket socket = new Socket("localhost", 8009);
            out o = new out(socket);
            in i = new in(socket);
            o.start();
            i.start();               
            //closing the scanner object
            //sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}