package com.bai.socket.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {


    public static void main(String[] args) {
        final int DEFAULT_PORT = 8888;
        final String QUIT = "quit";

        ServerSocket serverSocket = null;

        try {
            //绑定监听端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器短裤：" + DEFAULT_PORT);

            while (true) {
                //等带客户端连接
                Socket socket = serverSocket.accept();

                System.out.println("客户端：[" + socket.getPort() + "] 已连接");


                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                );

                String msg = null;
                while ((msg = reader.readLine()) != null) {

                    System.out.println("客户端：【" + socket.getPort() + "】" + msg);

                    //回复客户端
                    writer.write("服务器" + msg + "\n");
                    writer.flush();

                    if (QUIT.equals(msg)){
                        System.out.println("客户端：【"+socket.getPort()+"】已退出");
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                System.out.println("关闭");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
