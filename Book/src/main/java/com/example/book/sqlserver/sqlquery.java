package com.example.book.sqlserver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.book.R;
import com.example.book.library.Add;
import com.example.book.library.MainActivity;
import com.example.book.library.information;

public class sqlquery implements View.OnClickListener {
    Activity a;

    public sqlquery(Activity a) {
        this.a = a;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addsql: {
                String id = ((EditText) a.findViewById(R.id.num)).getText().toString();
                String name = ((EditText) a.findViewById(R.id.name)).getText().toString();
                String author = ((EditText) a.findViewById(R.id.author)).getText().toString();
                String location = ((EditText) a.findViewById(R.id.location)).getText().toString();
                String data = "insert++++" + id + "++++" + name + "++++" + author + "++++" + location;
                sqlThread sql = new sqlThread(((Add) a).handler, data);
                sql.start();
                if (id.equals("")) {
                    return;
                }
                Intent i = new Intent(a, information.class);
                i.putExtra("bookname", name);
                i.putExtra("booknum", id);
                i.putExtra("bookauthor", author);
                i.putExtra("booklocation", location);
                a.startActivity(i);
                a.finish();
                break;
            }
            case R.id.sqlquery: {
                String id = ((EditText) a.findViewById(R.id.num)).getText().toString();
                String name = ((EditText) a.findViewById(R.id.name)).getText().toString();
                String author = ((EditText) a.findViewById(R.id.author)).getText().toString();
                String sql = "where 1=1 ";
                if (!id.equals("")) {
                    sql += "and id like '%" + id + "%'";
                }
                if (!name.equals("")) {
                    sql += "and name like '%" + name + "%'";
                }
                if (!author.equals("")) {
                    sql += "and author like '%" + author + "%'";
                }
                ((MainActivity) a).gotoserver("search++++" + sql);
                break;
            }


        }
    }
}
