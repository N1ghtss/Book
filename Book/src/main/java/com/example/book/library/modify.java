package com.example.book.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;
import com.example.book.sqlserver.sqlThread;

public class modify extends AppCompatActivity implements View.OnClickListener {
    String modify_id;
    boolean sqlquery = false;
    public Handler handler = new Handler(msg -> {
        String message = msg.getData().getString("read");
        if (Boolean.parseBoolean(message)) {
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
        }

        return true;
    });

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
        sqlquery = i.getBooleanExtra("sqlquery", false);
///        Toast.makeText(this, sqlquery, Toast.LENGTH_SHORT).show();
        Button modify = findViewById(R.id.modify);
        modify.setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        String id = ((EditText) findViewById(R.id.num)).getText().toString();
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String author = ((EditText) findViewById(R.id.author)).getText().toString();
        String location = ((EditText) findViewById(R.id.location)).getText().toString();
        if (sqlquery) {
            String data = "update++++" + id + "++++" + name + "++++" + author + "++++" + location + "++++" + modify_id;
            sqlThread sql = new sqlThread(handler, data);
            sql.start();
        } else {
            try {
                DBConnection db = new DBConnection(this);
                if (id.equals("")) {
                    Toast.makeText(this, "图书编号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!modify_id.equals(id)) {
                    Cursor cursor = db.getWritableDatabase().query("book", new String[]{"id"}, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex("id")).equals(id)) {
                            Toast.makeText(this, "该图书编号已存在", Toast.LENGTH_SHORT).show();
                            cursor.close();
                            return;
                        }
                    }
                }
                String sql = "update book set id='" + id + "',name='" + name + "',author='" + author + "',location='" + location + "' where id='" + modify_id + "'";
                db.getWritableDatabase().execSQL(sql);
                Toast.makeText(this, "修改成功!", Toast.LENGTH_LONG).show();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
