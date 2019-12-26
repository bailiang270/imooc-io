package com.bai.socket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatHandler implements Runnable {

    private ChatClient chatClient;

    public ChatHandler(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run() {

        //等带用户输入信息
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        try {
            while (true) {
                //等待用户输入信息
                String input = reader.readLine();
                //向服务器发送消息
                chatClient.send(input);
                if (chatClient.readyToQuit(input)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
