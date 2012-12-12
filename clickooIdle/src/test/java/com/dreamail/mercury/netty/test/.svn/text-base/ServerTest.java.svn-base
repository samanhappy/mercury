package com.dreamail.mercury.netty.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

	 public static void main(String[] args)  
     {  
         try{  
             ServerSocket s = new ServerSocket(2536);  
             Socket incoming = s.accept();  
             BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));  
             PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /* autoFlush */);  
             out.println("Hello! Enter BYE to exit.");  
             boolean done = false;  
             while (!done)  
             {  
                 String line = in.readLine();  
                 if (line == null)  
                     done = true;  
                 else{  
                     out.println("Echo: " + line);  
                     if (line.trim().equals("BYE"))  
                         done = true;  
                 }  
             }  
             incoming.close();  
         }  
         catch (Exception e){  
             System.out.println(e);  
         }  
     }  

}
