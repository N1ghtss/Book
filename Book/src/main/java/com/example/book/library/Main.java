package com.example.book.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.ActivityCollector;
import com.example.book.R;
import com.example.book.chat.Life;
import com.example.book.chat.Play;
import com.example.book.chat.Study;


public class Main extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.main);
        Button btn1 = findViewById(R.id.chaxun);
        Button btn2 = findViewById(R.id.xinzeng);
        Button btn3 = findViewById(R.id.chat);
        Button btn4 = findViewById(R.id.life);
        Button btn5 = findViewById(R.id.play);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        ActivityCollector.addActivity(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chaxun:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.xinzeng:
                Intent s = new Intent(this, Add.class);
                startActivity(s);
                break;
            case R.id.chat:
                Intent c = new Intent(this, Study.class);
                startActivity(c);
                break;
            case R.id.life:
                Intent l = new Intent(this, Life.class);
                startActivity(l);
                break;
            case R.id.play:
                Intent p = new Intent(this, Play.class);
                startActivity(p);
                break;
        }

    }
}
