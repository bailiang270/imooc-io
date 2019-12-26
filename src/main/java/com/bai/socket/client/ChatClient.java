package com.bai.socket.client;

import java.io.*;
import java.net.Socket;

public class ChatClient {


    private final String DEFAULT_SERVER_HOST = "127.0.0.1";
    private final int DEFAULT_SERVER_PORT = 8888;
    private String QUIT = "quit";


    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    //发送消息给服务器
    public void send(String msg){
        if (!socket.isOutputShutdown()){
            try {
                writer.write(msg+"\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //接受消息
    public String receive() throws IOException {

        String msg = null;
        if (!socket.isInputShutdown()){
            msg = reader.readLine();
        }
        return msg;
    }

    public boolean readyToQuit(String msg){
        return QUIT.equals(msg);
    }

    public void close(){
        if (writer !=null){
            try {
                System.out.println("关闭socket");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){

        try {
            //创建socket
            socket = new Socket(DEFAULT_SERVER_HOST,DEFAULT_SERVER_PORT);
            //创建io
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            //处理用户输入
            new Thread(new ChatHandler(this)).start();
            //读取服务器转发的信息
            String msg = null;
            while ((msg = reader.readLine())!=null){
                System.out.println(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }


}
