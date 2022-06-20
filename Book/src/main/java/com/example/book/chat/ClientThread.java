package com.example.book.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    public Socket s;
    Handler handler;
    int port;

    public ClientThread(Handler handler, int i) {
        this.handler = handler;
        this.port = i;
    }

    public void run() {
        InputStream in;
        try {
            s = new Socket("192.168.1.102", port);
            in = s.getInputStream();
            while (true) {
                byte[] buffer = new byte[1024];
                int num;
                StringBuilder mes = new StringBuilder();
                while ((num = in.read(buffer)) != -1) {
                    mes.append(new String(buffer, 0, num, "GBK"));
                    if (mes.toString().contains("\n")) {
                        break;
                    }
                }
                Bundle b = new Bundle();
                b.putString("mes", mes.toString());
                Message message = new Message();
                message.setData(b);
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
