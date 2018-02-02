package com.example.moorg.registroeps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MoorG on 15/05/2017.
 */

public class DataBase extends SQLiteOpenHelper{
    public DataBase(Context context)
    {
        super(context, "AlertMapsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Dados (id INTEGER PRIMARY KEY, nome VARCHAR(250), cont VARCHAR(20))");// db.execSQL(ScriptSQLLatLng.getCreateDataBase());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
