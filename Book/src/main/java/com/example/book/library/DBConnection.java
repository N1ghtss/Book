package com.example.book.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
    public DBConnection(Context context) {
        super(context, "lilin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table book(id varchar(30) primary key,name varchar(30)not null,author varchar(30) not null, location varchar(30) not null)";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
