package com.example.book.chat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class changetext {
    public changetext() {
    }

    public static void change(AppCompatActivity a, TextView t) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(a);
        String font = sp.getString("textsize", "10");
        t.setTextSize(Integer.parseInt(font));
        String color = sp.getString("color", "black");
        t.setTextColor(Color.parseColor(color));
    }
}