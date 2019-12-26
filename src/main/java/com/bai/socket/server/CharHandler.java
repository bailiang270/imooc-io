package com.bai.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CharHandler implements Runnable {


    private CharServer server;
    private Socket socket;

    public CharHandler(CharServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            //添加客户端
            server.addClient(socket);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            String msg = null;
            while ((msg=reader.readLine())!=null){
                String fwdMsg = "客户端：["+socket.getPort()+"]:"+msg+"\n";
                System.out.println(fwdMsg);

                //将消息转发给聊天室里其他的用户
                server.forwardMessage(socket,fwdMsg);

                //检查用户是否退出
                if (server.readyToQuit(msg)){
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                server.removeClient(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
