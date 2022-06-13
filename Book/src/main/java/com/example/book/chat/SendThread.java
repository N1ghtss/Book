package com.example.book.chat;

import java.io.OutputStream;
import java.net.Socket;

public class SendThread extends Thread {
    Socket s;
    String mes;
    String username;

    public SendThread(Socket s, String mes, String username) {
        this.mes = username + ":" + mes + "\n";
        this.s = s;
        this.username = username;
    }

    public void run() {
        OutputStream out = null;
        try {
            out = s.getOutputStream();
            out.write(mes.getBytes("GBK"));
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
