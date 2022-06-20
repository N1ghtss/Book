package com.example.book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.book.library.Main;

public class Login extends Activity implements View.OnClickListener {
    private EditText username, password;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        username.setText("admin");
        password.setText("admin");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                finish();
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
