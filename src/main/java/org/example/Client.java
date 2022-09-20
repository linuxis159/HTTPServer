package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void sendMessage () {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": "+messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMesssage(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String msgFromGroupChat;
                while(socket.isConnected()) {
                try {
                    msgFromGroupChat = bufferedReader.readLine();
                    System.out.println(msgFromGroupChat);
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
                }

            }
        }).start();

    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try{
            if(bufferedReader != null)
                bufferedReader.close();
            if(bufferedWriter != null)
                bufferedReader.close();
            if(socket != null)
                socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for thoe group chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost",6060);
        Client clinet = new Client(socket, username);
        clinet.listenForMesssage();
        clinet.sendMessage();


    }
}