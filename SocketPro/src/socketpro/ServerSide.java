/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socketpro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Finley
 */
public class ServerSide {
    public static void main(String args[]){
        int port = 1234;
        
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            
            while(true){
                Socket clientSocket = serverSocket.accept();
                ServerThread serverthread = new ServerThread(clientSocket);
                serverthread.start();
            }
            
        } catch(IOException e){
            System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
