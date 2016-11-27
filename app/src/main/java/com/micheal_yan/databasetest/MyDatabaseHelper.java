package com.micheal_yan.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by micheal-yan on 2016/11/27.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContex;

    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement,"
            + "author text,"
            + "price real,"
            + "pages integer,"
            + "name text,"
            + "category_id integer)";
    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContex = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContex, "成功创建图书表", Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_CATEGORY);
                Toast.makeText(mContex, "成功创建类目表", Toast.LENGTH_SHORT).show();
            case 2:
                db.execSQL("alter table Book add column category_id integer");
                Toast.makeText(mContex, "成功为图书表增加category_id字段", Toast.LENGTH_SHORT).show();
            default:
        }
    }
}
