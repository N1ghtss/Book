package com.example.Server;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public Client() {
        System.out.println("李霖");
        try {

            Socket s = new Socket("127.0.0.1", 8010);
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            Scanner scan = new Scanner(System.in);
            while (true) {
                System.out.println("请输入：");
                String mes = scan.next();
                out.write(mes.getBytes());
                byte[] buffer = new byte[1024];
                int num = in.read(buffer);
                String mes1 = new String(buffer, 0, num);
                System.out.println("Server tell me:" + mes1);
                if (mes.equals("exit")) break;
            }
            in.close();
            out.close();
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        //noinspection InstantiationOfUtilityClass
        new Client();
    }
}