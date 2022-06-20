package com.example.book.library;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.book.R;

public class information extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        Intent i = getIntent();
        String bn = i.getStringExtra("bookname");
        String bnum = i.getStringExtra("booknum");
        String bauthor = i.getStringExtra("bookauthor");
        String blocation = i.getStringExtra("booklocation");
        TextView bookname = findViewById(R.id.name);
        TextView booknum = findViewById(R.id.num);
        TextView bookauthor = findViewById(R.id.author);
        TextView booklocation = findViewById(R.id.location);
        bookname.setText(bn);
        booknum.setText(bnum);
        bookauthor.setText(bauthor);
        booklocation.setText(blocation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
