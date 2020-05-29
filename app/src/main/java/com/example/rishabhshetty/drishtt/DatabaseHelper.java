package com.example.rishabhshetty.drishtt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Drisht.db";
    public static final String TABLE_NAME="MessageTable.db";
    public static final String COL_1="ID.db";
    public static final String COL_3="NUMBER.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);//DB CREATED ON CALL
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NUMBER INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);


    }
}
