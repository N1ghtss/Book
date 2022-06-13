package com.example.book.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class modify extends AppCompatActivity implements View.OnClickListener {
    String modify_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);
        Intent i = getIntent();
        TextView id = findViewById(R.id.num);
        TextView name = findViewById(R.id.name);
        TextView author = findViewById(R.id.author);
        TextView location = findViewById(R.id.location);
        id.setText(i.getStringExtra("id"));
        modify_id = i.getStringExtra("id");
        name.setText(i.getStringExtra("name"));
        author.setText(i.getStringExtra("author"));
        location.setText(i.getStringExtra("location"));
        Button modify = findViewById(R.id.modify);
        modify.setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        TextView id = findViewById(R.id.num);
        TextView name = findViewById(R.id.name);
        TextView author = findViewById(R.id.author);
        TextView location = findViewById(R.id.location);
        String mid = id.getText().toString();
        String mname = name.getText().toString();
        String mauthor = author.getText().toString();
        String mlocation = location.getText().toString();
        try {
            DBConnection db = new DBConnection(this);
            if (mid.equals("")) {
                Toast.makeText(this, "图书编号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!modify_id.equals(mid)) {
                Cursor cursor = db.getWritableDatabase().query("book", new String[]{"id"}, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    if (cursor.getString(cursor.getColumnIndex("id")).equals(mid)) {
                        Toast.makeText(this, "该图书编号已存在", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        return;
                    }
                }
            }
            String sql = "update book set id='" + mid + "',name='" + mname + "',author='" + mauthor + "',location='" + mlocation + "' where id='" + modify_id + "'";
            db.getWritableDatabase().execSQL(sql);
            Toast.makeText(this, "修改成功!", Toast.LENGTH_LONG).show();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
