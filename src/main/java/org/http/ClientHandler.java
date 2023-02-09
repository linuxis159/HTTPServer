package org.http;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket acceptSocket;
    OutputStreamWriter out;
    InputStreamReader in;
    public ClientHandler(Socket acceptSocket) throws IOException {
        this.acceptSocket = acceptSocket;
        out = new OutputStreamWriter(acceptSocket.getOutputStream());
        in = new InputStreamReader(acceptSocket.getInputStream());
    }

    @Override
    public void run() {
        while(acceptSocket.isConnected()){
            char[] readingMessage = new char[100];
            try {
                in.read(readingMessage);
                String message = String.valueOf(readingMessage).trim();
                System.out.println("방금읽어들인 메세지:"+message);
                if(readingMessage.toString().equals("EXIT"))
                    break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            acceptSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
