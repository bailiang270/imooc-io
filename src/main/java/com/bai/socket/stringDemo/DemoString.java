package com.bai.socket.stringDemo;

import java.io.FileInputStream;
import java.io.FilterReader;
import java.io.InputStream;

public class DemoString {


    public static void main(String[] args) {
        String str = "123456";

        StringBuffer strBuilder = new StringBuffer(str);
        strBuilder.reverse();

        System.out.println(strBuilder.toString());
    }


}
