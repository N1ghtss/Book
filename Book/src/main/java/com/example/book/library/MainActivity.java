package com.example.book.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.ActivityCollector;
import com.example.book.R;
import com.example.book.sqlserver.sqlquery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TableRow del_tr;
    boolean Clickquery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        TableRow tr = findViewById(R.id.data_row);
        registerForContextMenu(tr);
        Button query = findViewById(R.id.query);
        query.setOnClickListener(this);
        Button btn0 = findViewById(R.id.btn0);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button back = findViewById(R.id.sqlquery);
        back.setOnClickListener(new sqlquery(this));
        btn0.setOnClickListener(new EventListener(this));
        btn2.setOnClickListener(this);
        change();


        btn3.setOnClickListener(view -> {
                    Toast.makeText(MainActivity.this, "内部匿名事件", Toast.LENGTH_LONG).show();
                    EditText ed1 = findViewById(R.id.num);
                    EditText ed2 = findViewById(R.id.author);
                    EditText ed3 = findViewById(R.id.name);
                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");
                }


        );

    }

    public void change() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String textsize = sp.getString("textsize", "25");
        String color = sp.getString("color", "#000000");
        TextView num = findViewById(R.id.booknumber);
        TextView name = findViewById(R.id.bookname);
        TextView author = findViewById(R.id.bookauthor);

        num.setTextSize(Integer.parseInt(textsize));
        name.setTextSize(Integer.parseInt(textsize));
        author.setTextSize(Integer.parseInt(textsize));
        num.setTextColor(Color.parseColor(color));
        name.setTextColor(Color.parseColor(color));
        author.setTextColor(Color.parseColor(color));
    }

    public void click(View view) {
        Toast.makeText(this, "属性绑定响应事件", Toast.LENGTH_LONG).show();
        EditText ed1 = findViewById(R.id.num);
        EditText ed2 = findViewById(R.id.author);
        EditText ed3 = findViewById(R.id.name);
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn2:
                Toast.makeText(this, "监听器实现类事件", Toast.LENGTH_LONG).show();
                EditText ed1 = findViewById(R.id.num);
                EditText ed2 = findViewById(R.id.author);
                EditText ed3 = findViewById(R.id.name);
                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
                break;

            case R.id.query:
                EditText num = findViewById(R.id.num);
                EditText bookauthor = findViewById(R.id.author);
                EditText bookname = findViewById(R.id.name);
                String id = num.getText().toString();
                String name = bookname.getText().toString();
                String author = bookauthor.getText().toString();
                String where = " 1=1 ";
                List<String> list = new ArrayList<>();
                if (!id.equals("")) {
                    where = where + " and id=? ";
                    list.add(id);
                }
                if (!name.equals("")) {
                    where = where + " and name=? ";
                    list.add(name);
                }
                if (!author.equals("")) {
                    where = where + " and author=? ";
                    list.add(author);
                }
                String[] whereargs = new String[list.size()];
                if (list.size() > 0) {
                    list.toArray(whereargs);
                } else {
                    where = null;
                    whereargs = null;
                }
                myquery(where, whereargs);
                break;
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, "关于");
        menu.add(0, 2, 0, "设置");
        menu.add(0, 3, 0, "退出");
        menu.add(0, 4, 0, "应用字体设置");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "李霖 1904405121", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Intent i = new Intent(this, Set1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
                break;
            case 3:
                ActivityCollector.finishALL();
                break;
            case 4:
                change();
                break;
        }

        return true;

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        del_tr = (TableRow) v;
        menu.add(0, 1, 0, "修改");
        menu.add(0, 2, 0, "删除");
        super.onCreateContextMenu(menu, v, info);

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "修改", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, modify.class);
                TextView modify_id = (TextView) del_tr.getChildAt(0);
                TextView modify_name = (TextView) del_tr.getChildAt(1);
                TextView modify_author = (TextView) del_tr.getChildAt(2);
                TextView modify_location = (TextView) del_tr.getChildAt(3);
                i.putExtra("id", modify_id.getText().toString());
                i.putExtra("name", modify_name.getText().toString());
                i.putExtra("author", modify_author.getText().toString());
                i.putExtra("location", modify_location.getText().toString());
                startActivity(i);
                break;
            case 2:
                TextView del_id = (TextView) del_tr.getChildAt(0);
                String id = del_id.getText().toString();
                DBConnection db = new DBConnection(this);
                String sql = "delete from book where id='" + id + "' ";
                db.getWritableDatabase().execSQL(sql);
                Toast.makeText(this, "删除", Toast.LENGTH_LONG).show();
                myquery(null, null);
                break;
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    public void myquery(String where, String[] whereargs) {
        try (DBConnection db = new DBConnection(this)) {
            Cursor cur = db.getWritableDatabase().query("book", null, where, whereargs, null, null, "id");
            TableLayout table = findViewById(R.id.table);
            for (int i = 1; i < table.getChildCount() - 1; ) {
                table.removeViewAt(i);
            }
            while (cur.moveToNext()) {
                @SuppressLint("Range") String id = cur.getString(cur.getColumnIndex("id"));
                @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex("name"));
                @SuppressLint("Range") String author = cur.getString(cur.getColumnIndex("author"));
                @SuppressLint("Range") String location = cur.getString(cur.getColumnIndex("location"));
                TextView tid = new TextView(this);
                tid.setText(id);
                TextView tname = new TextView(this);
                tname.setText(name);
                TextView tauthor = new TextView(this);
                tauthor.setText(author);
                TextView tlocation = new TextView(this);
                tlocation.setText(location);
                TableRow tr = new TableRow(this);
                registerForContextMenu(tr);
                tr.addView(tid);
                tr.addView(tname);
                tr.addView(tauthor);
                tr.addView(tlocation);
                table.addView(tr, table.getChildCount() - 1);
            }
            cur.close();
            TextView total = findViewById(R.id.total);
            total.setText("共" + (table.getChildCount() - 2) + "条数据");
            Clickquery = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void onRestart() {
        super.onRestart();
        if (Clickquery) {
            myquery(null, null);
        }
//        change();


    }
}







