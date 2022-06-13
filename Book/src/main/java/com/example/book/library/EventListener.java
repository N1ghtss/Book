package com.example.book.library;

import static com.example.book.R.*;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EventListener extends AppCompatActivity implements View.OnClickListener {

    Activity a;

    public EventListener(Activity a) {
        this.a = a;

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(a.getBaseContext(), "外部监听器实现类响应事件", Toast.LENGTH_LONG).show();
        EditText ed1 = a.findViewById(id.num);
        EditText ed2 = a.findViewById(id.author);
        EditText ed3 = a.findViewById(id.name);
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");

    }
}
