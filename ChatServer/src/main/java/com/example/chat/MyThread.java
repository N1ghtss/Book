package com.example.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

public class MyThread extends Thread {
    Server client = new Server();
    Socket socket;
    ServerSocket server;

    public MyThread(ServerSocket server) {
        this.server = server;
    }

    public void run() {
        OutputStream out = null;
        InputStream in = null;
        byte[] buffer = new byte[1024];
        try {
            socket = server.accept();
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket.getLocalAddress().getHostName() + " : " + socket.getPort());
        new MyThread(server).start();
        if (socket.getLocalPort() == 6503) {
            DBConnection db = new DBConnection();
            int len;
            try {
                len = Objects.requireNonNull(in).read(buffer, 0, 1024);
                String str = new String(buffer, 0, len, "GBK");
                if (len != -1) {
                    String[] args = str.split("\\+\\+\\+\\+");
                    switch (args[0]) {
                        case "insert": {
                            boolean b = db.add(args);
                            System.out.println(b);
                            Objects.requireNonNull(out).write(String.valueOf(b).getBytes());
                            break;
                        }
                        case "update": {
                            boolean b = db.update(args);
                            System.out.println(b);
                            Objects.requireNonNull(out).write(String.valueOf(b).getBytes());
                            break;
                        }
                        case "delete": {
                            boolean b = db.delete(args[1]);
                            System.out.println(b);
                            Objects.requireNonNull(out).write(String.valueOf(b).getBytes());
                            break;
                        }
                        case "search": {
                            List<Book> list = db.search(args[1]);
                            StringBuilder s = new StringBuilder();
                            for (int i = 0; i < list.size(); i++) {
                                Book book = list.get(i);
                                s.append(book.id);
                                s.append("+++").append(book.name);
                                s.append("+++").append(book.author);
                                s.append("+++").append(book.location);
                                s.append("++++");
                            }
                            System.out.println(s);
                            Objects.requireNonNull(out).write(s.toString().getBytes());
                            break;
                        }
                        default: {
                            System.out.println(str);
                        }
                    }
                }
                db.close();
                in.close();
                Objects.requireNonNull(out).close();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            client.addclient(socket);
            try {
                while (true) {
                    int num = Objects.requireNonNull(in).read(buffer);
                    String mes = new String(buffer, 0, num, "GBK");
                    System.out.println(socket.getPort() + " Data is : " + mes);
                    for (int i = 0; i < client.getclient(socket.getLocalPort()).size(); i++) {
                        Socket c = client.getclient(socket.getLocalPort()).get(i);
                        c.getOutputStream().write(mes.getBytes("GBK"));
                    }
                    if (mes.equals("exit")) break;
                }
                Objects.requireNonNull(out).flush();
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
}



