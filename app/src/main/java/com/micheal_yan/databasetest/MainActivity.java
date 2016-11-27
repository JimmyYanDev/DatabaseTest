package com.micheal_yan.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button createDatabase;
    private Button updateDatabase;
    private Button addData;
    private Button updateData;
    private Button deleteData;
    private Button queryData;

    private MyDatabaseHelper mHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView() {
        createDatabase = (Button) findViewById(R.id.createDatabase);
        updateDatabase = (Button) findViewById(R.id.updateDatabase);
        addData = (Button) findViewById(R.id.addData);
        updateData = (Button) findViewById(R.id.updateData);
        deleteData = (Button) findViewById(R.id.deleteData);
        queryData = (Button) findViewById(R.id.queryData);

        mHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
    }

    private void initEvent() {
        createDatabase.setOnClickListener(this);
        updateDatabase.setOnClickListener(this);
        addData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
        queryData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createDatabase:
                db = mHelper.getWritableDatabase();
                break;
            case R.id.updateDatabase:
                db = mHelper.getWritableDatabase();
                break;
            case R.id.addData:
                db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values); // 插入第一条数据 values.clear();
                values.clear();
                // 开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Steve Bob");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values); // 插入第二条数据
                Toast.makeText(this, "成功向图书表添加两条数据", Toast.LENGTH_LONG).show();
                break;
            case R.id.updateData:
                db = mHelper.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("price", 10.99);
                db.update("Book", values2, "name = ?", new String[]{"The Da Vinci Code"});
                Toast.makeText(this, "已将《The Da Vinci Code》的售价改为10.99元", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteData:
                db = mHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    db.delete("Book", null, null);
                    ContentValues values3 = new ContentValues();
                    values3.put("name", "Game of Thrones");
                    values3.put("author", "George Martin");
                    values3.put("pages", 720);
                    values3.put("price", 20.85);
                    db.insert("Book", null, values3);
                    Toast.makeText(this, "已成功删除所有书，并重新添加了一本新书《Game of Thrones》", Toast.LENGTH_SHORT).show();
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                break;
            case R.id.queryData:
                db = mHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        Double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Toast.makeText(this, "《" + name + "》\n作者： "
                                + author + "\n共有：" + pages + "页\n售价： " + price + "元。", Toast.LENGTH_LONG).show();
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                break;
        }
    }
}
