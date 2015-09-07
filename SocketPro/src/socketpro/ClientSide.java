/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socketpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.omg.CORBA.portable.UnknownException;


/**
 *
 * @author Finley
 */
public class ClientSide {
    
    public static void main(String args[]){
        int port = 1234;
        
        try{
            BufferedReader keyBoard = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.print("input server ip : ");
            
            String ip = keyBoard.readLine();
            Socket connect = new Socket(ip,1234);
            
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            PrintWriter outputStream= new PrintWriter(connect.getOutputStream(),true);
            
            ReadInput in = new ReadInput(inputStream);
            in.start();
            
            String inputKeyBoard;
            do{
                System.out.println(">> ");
                inputKeyBoard= keyBoard.readLine();
                outputStream.println(inputKeyBoard);
                outputStream.flush();
            }while (!inputKeyBoard.equals("quit"));
            System.out.println("User has quit");
        }catch(UnknownException e){
            System.err.println("unknown host");
            System.exit(1);        
        }catch(IOException e){
            System.err.println("fail at connecting");
            System.exit(1);
    }
        
    }
}
