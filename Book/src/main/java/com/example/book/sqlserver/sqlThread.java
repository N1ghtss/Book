package com.example.book.sqlserver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class sqlThread extends Thread {
    private final String data;
    private final Handler handler;
    private Socket socket;

    public sqlThread(Handler handler, String data) {
        this.handler = handler;
        this.data = data;
    }

    public void run() {
        if (socket == null) {
            try {
                socket = new Socket("192.168.1.102", 6503);
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                out.write(data.getBytes("GBK"));
                out.flush();
                byte[] buffer = new byte[1024];
                int len = in.read(buffer);
                if (len != -1) {
                    String message = new String(buffer, 0, len, "GBK");
                    Bundle bundle = new Bundle();
                    bundle.putString("read", message);
                    Message m = new Message();
                    m.setData(bundle);
                    handler.sendMessage(m);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
