package com.example.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyThread extends Thread {
    Server client = new Server();
    Socket socket;
    ServerSocket server;

    public MyThread(ServerSocket server) {
        this.server = server;
    }

    public void run() {
        OutputStream out;
        InputStream in;
        try {
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket.getLocalAddress().getHostName() + " : " + socket.getPort());
        new MyThread(server).start();
        client.addclient(socket);
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int num = in.read(buffer);
                String mes = new String(buffer, 0, num, "GBK");
                System.out.println(socket.getPort() + " Data is : " + mes);
                for (int i = 0; i < client.getclient(socket.getLocalPort()).size(); i++) {
                    Socket c = client.getclient(socket.getLocalPort()).get(i);
                    c.getOutputStream().write(mes.getBytes("GBK"));
                }
                if (mes.equals("exit")) break;
            }
            out.flush();
            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            client.removeclient(socket);
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("客户异常退出");
        }
    }
}


