package org.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Scanner;


public class Client {
    static String message;

    public static void main(String[] args) throws IOException {
            Scanner sc = new Scanner(System.in);
            Socket clientSocket = new Socket("localhost",7777);
            OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
            while(clientSocket.isConnected()) {
                System.out.println("서버에 연결됨");
                System.out.println("보낼메세지:");
                message = sc.next();
                System.out.println("입력된 메세지:" + message);
                out.write(message);
                out.flush();
            }
        }
}
