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
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Finley
 */
public class ServerThread extends Thread{
    final Socket client;
    String username = "";
    DataOutputStream outputStream = null;
    BufferedReader inputstream = null;
    static Map<String,DataOutputStream> users = new HashMap<>();
    
    public ServerThread(Socket fromClient){
        this.client = fromClient;
    }
    
    public void run(){
        DataOutputStream outputStream;
        try {
            outputStream = new DataOutputStream(client.getOutputStream());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            while (true) {
                outputStream.writeBytes("submit your username " + "\n");
                username = inputStream.readLine();
                if (username == null){
                    return;
                }
                synchronized (users) {
                    if (!users.containsKey(username)){
                        outputStream.writeBytes("username has already been used " + "\n");
                        users.put(username, outputStream);
                        break;
                    }
                }
            }
            
            String inputLine, message;
            while ((inputLine = inputStream.readLine()) != null && !inputLine.equals("quit")){
                message = username + " said : " + inputLine;
                System.out.println(message);
                for (DataOutputStream d: users.values()){
                    d.writeBytes(message +"\n");
                    d.flush();
                }
            }
            if (username != null) {
                users.remove(username);
            }
            if (outputStream != null){
                users.remove(outputStream);
            }
            client.close();
        } catch (IOException ex) {
            
        }
            
    }
}
