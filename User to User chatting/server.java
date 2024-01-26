
package group.chatting.application;
import java.net.*;  
import java.io.*;   
import java.util.*;

public class server implements Runnable
{
    Socket socket;
     public static Vector client = new Vector(); //to add messages
   // to run multiple clients using multithreading
 public server(Socket socket){ //constructor for initializing values
     try{
         this.socket=socket;
     }catch(Exception e){
         e.printStackTrace();
     }
 }
 
 //overriding run method
 //multithreading concept
 public void run(){
     try{
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
         client.add(writer); //adding messages
         while(true){
             String data=reader.readLine().trim(); //trim(to display spaces)
             System.out.println("Received "+data);
            
             for(int i=0;i<client.size();i++){
                 try{
                     //for message broadcast
                     BufferedWriter bw=(BufferedWriter) client.get(i);
                    bw.write(data);
                    bw.write("\r\n");
                    bw.flush();  //to send message
                 }catch(Exception e){
                     e.printStackTrace();
                 }
             }
         }
     }catch(Exception e){
         e.printStackTrace();
     }
 }
public static void main(String[] args) throws Exception
{
    
    ServerSocket s=new ServerSocket(2003);
   //to connect infinite users
   while(true){
        Socket socket=s.accept();
        server server=new server(socket);
        Thread thread=new Thread(server); 
        thread.start(); //call thread class object (start will internally call run() method)
   }
    
}
}
