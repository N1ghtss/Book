package com.example.book.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;
import com.example.book.library.Set1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Study extends AppCompatActivity implements View.OnClickListener {
    public String username;
    public TextView record;
    public ClientThread c;
    public Handler handler = new Handler(msg -> {
        String mes = msg.getData().getString("mes");
        record.append(mes);
        return true;
    });

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "设置");
        menu.add(0, 2, 0, "应用字体设置");
        menu.add(0, 3, 0, "清除聊天记录");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(this, Set1.class));
                break;
            case 2:
                com.example.book.chat.changetext.change(this, record);
                break;
            case 3:
                record.setText("");
                break;
        }
        return true;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        record = findViewById(R.id.record);
        try {
            FileInputStream in = this.openFileInput("Study.txt");
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                record.append(new String(b, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button send = findViewById(R.id.send);
        send.setOnClickListener(this);
        int i = 6500;
        c = new ClientThread(handler, i);
        c.start();
    }

    @Override
    public void onClick(View v) {
        EditText message = findViewById(R.id.mes);
        String str_mes = message.getText().toString();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        username = sp.getString("username", "匿名");
        if (str_mes.equals("")) {
            return;
        }
        new SendThread(c.s, str_mes, username).start();
        message.setText("");
    }

    protected void onStop() {
        try {
            FileOutputStream out = openFileOutput("Study.txt", MODE_PRIVATE);
            out.write(record.getText().toString().getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
        c.interrupt();
    }
}
