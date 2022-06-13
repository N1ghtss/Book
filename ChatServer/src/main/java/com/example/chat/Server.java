package com.example.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    //用数组创建三个端口 用于监听
    private static final int[] PORTS = {6500, 6501, 6502};
    static List<Socket> study = new ArrayList<>();
    static List<Socket> life = new ArrayList<>();
    static List<Socket> play = new ArrayList<>();

    public static void main(String[] args) {
        for (int port : PORTS) {
            ServerSocket server;
            try {
                server = new ServerSocket(port);
                new MyThread(server).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addclient(Socket socket) {
        switch (socket.getLocalPort()) {
            case 6500:
                study.add(socket);
                break;
            case 6501:
                life.add(socket);
                break;
            case 6502:
                play.add(socket);
                break;
        }
    }

    public void removeclient(Socket socket) {
        switch (socket.getLocalPort()) {
            case 6500:
                study.remove(socket);
                break;
            case 6501:
                life.remove(socket);
                break;
            case 6502:
                play.remove(socket);
                break;
        }
    }

    public List<Socket> getclient(int port) {
        switch (port) {
            case 6500:
                return study;
            case 6501:
                return life;
            case 6502:
                return play;
        }
        return null;
    }
}
