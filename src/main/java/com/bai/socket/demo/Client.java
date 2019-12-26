package com.bai.socket.demo;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        final String QUIT = "quit";

        Socket socket = null;
        BufferedWriter writer = null;

        try{

            //创建socket
            socket = new Socket(DEFAULT_SERVER_HOST,DEFAULT_SERVER_PORT);

            //创建io
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            //等带用户输入
            BufferedReader consoleReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );

           while (true){
               String input = consoleReader.readLine();
               //发送消息
               writer.write(input+"\n");
               writer.flush();

               //读取消息
               String msg = reader.readLine();
               System.out.println(msg);
               if (QUIT.equals(input)){
                   break;
               }

           }


        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
