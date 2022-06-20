package com.example.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    public static String url = "jdbc:sqlserver://localhost:1433;databaseName=lilin";
    public static String user = "sa";
    public static String password = "123456";
    public static Connection conn = null;

    public DBConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean add(String[] args) {
        try {
            String sql = "insert into books" + "(id,name,author,location)" + "values( ?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, args[1]);
            pst.setString(2, args[2]);
            pst.setString(3, args[3]);
            pst.setString(4, args[4]);
            pst.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String[] args) {
        try {
            String sql = "update books set id=?,name=?,author=?,location=? where id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, args[1]);
            pst.setString(2, args[2]);
            pst.setString(3, args[3]);
            pst.setString(4, args[4]);
            pst.setString(5, args[5]);
            pst.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            String sql = "delete from books where id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> search(String where) {
        List<Book> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from books  " + where);
            Book book;
            while (rs.next()) {
                book = new Book();
                book.id = rs.getString("id");
                book.name = rs.getString("name");
                book.author = rs.getString("author");
                book.location = rs.getString("location");
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

