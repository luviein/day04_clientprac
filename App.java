package com.yl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NumberFormatException, UnknownHostException, IOException
    {
        String serverFile = args[0];
        String serverPort = args[1];

        Socket socket = new Socket(serverFile, Integer.parseInt(serverPort));

        //setup console input from keyboard
        Console cons = System.console();

        //variables to save keyboard input
        String keyboardInput = "";

        //variable to save msgReceived
        String msgReceived = "";


        //try input and output stream

        try(InputStream is = socket.getInputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
             
            try(OutputStream os = socket.getOutputStream()){
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                
                while(!keyboardInput.equals("close")){
                    keyboardInput = cons.readLine("Enter a command.\n");

                    //writing data to cookie
                    dos.writeUTF(keyboardInput);
                    dos.flush();


                    //get data from server
                    msgReceived = dis.readUTF();
                    
                    //prints msg received from server
                    System.out.println(msgReceived);
                }

            
                dos.close();
                bos.close();
                os.close();
            }catch(EOFException e){
                e.printStackTrace();
            }

            dis.close();
            bis.close();
            is.close();    

        }catch(EOFException e){
            e.printStackTrace();
            socket.close();
        }
    }
}
