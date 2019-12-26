package com.bai.socket.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CharServer {


    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";

    private ServerSocket serverSocket;
    private Map<Integer, Writer> connectedClients;
    private ExecutorService executorService;

    public CharServer() {
        connectedClients = new HashMap<>();
        executorService = Executors.newFixedThreadPool(10);
    }

    public synchronized void addClient(Socket socket) throws IOException {

        if (socket!=null){
            int port = socket.getPort();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    )
            );
            connectedClients.put(port,writer);
            System.out.println("客户端：["+port+"] 已添加到服务器");
        }

    }

    public synchronized void removeClient(Socket socket) throws IOException {
        if (socket!=null){
            int port = socket.getPort();
            if (connectedClients.containsKey(port)){
                connectedClients.get(port).close();
            }
            connectedClients.remove(port);
            System.out.println("客户端：["+port+"] 已经断开连接");
        }
    }

    public synchronized void forwardMessage(Socket socket,String fwdMsg) throws IOException {
        for(Integer id:connectedClients.keySet()){
            if (!id.equals(socket.getPort())){
                Writer writer = connectedClients.get(id);
                writer.write(fwdMsg);
                writer.flush();
            }
        }
    }

    public void close(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean readyToQuit(String msg){
        return QUIT.equals(msg);
    }


    public void start(){

        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务端已启动，监听端口："+DEFAULT_PORT);

            while (true){
                //等待客户连接
                Socket socket = serverSocket.accept();
                //创建handler进程
                executorService.execute(new CharHandler(this,socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public static void main(String[] args) {
        CharServer server = new CharServer();
        server.start();
    }


}
