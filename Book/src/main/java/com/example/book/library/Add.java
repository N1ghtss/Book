package com.example.book.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class Add extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Button add = findViewById(R.id.query);
        add.setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View view) {
        EditText bookname = findViewById(R.id.name);
        EditText booknum = findViewById(R.id.num);
        EditText bookauthor = findViewById(R.id.author);
        EditText booklocation = findViewById(R.id.location);
        String bn = bookname.getText().toString();
        String bnum = booknum.getText().toString();
        String bauthor = bookauthor.getText().toString();
        String blocation = booklocation.getText().toString();
        //SQl
        try (DBConnection db = new DBConnection(this)) {
            if (bnum.equals("")) {
                Toast.makeText(this, "图书编号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursor = db.getWritableDatabase().query("book", new String[]{"id"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("id")).equals(bnum)) {
                    Toast.makeText(this, "该图书编号已存在", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    return;
                }
            }


            String sql = "insert into book(id,name,author,location) values('" + bnum + "','" + bn + "','" + bauthor + "','" + blocation + "')";
            try {
                db.getWritableDatabase().execSQL(sql);
                Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        ContentValues c=new ContentValues();
//        c.put("name",bn);
//        c.put("id",bnum);
//        c.put("author",bauthor);
//        c.put("location",blocation);
//        db.getWritableDatabase().insert("book",null,c);

        Intent i = new Intent(this, information.class);
        i.putExtra("bookname", bn);
        i.putExtra("booknum", bnum);
        i.putExtra("bookauthor", bauthor);
        i.putExtra("booklocation", blocation);

        startActivity(i);
        finish();

    }
}
